package nrg.inc.synhubbackend.metrics.application.internal.queryservice;

import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import nrg.inc.synhubbackend.metrics.domain.model.services.TaskMetricsQueryService;
import nrg.inc.synhubbackend.metrics.interfaces.rest.resources.*;
import nrg.inc.synhubbackend.tasks.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.tasks.domain.model.valueobjects.TaskStatus;
import nrg.inc.synhubbackend.tasks.infrastructure.persistence.jpa.repositories.TaskRepository;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByLeaderIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import nrg.inc.synhubbackend.metrics.domain.model.queries.GetTaskTimePassedQuery;
import nrg.inc.synhubbackend.metrics.domain.model.queries.GetAvgCompletionTimeQuery;
import nrg.inc.synhubbackend.metrics.domain.model.queries.GetRescheduledTasksQuery;
import nrg.inc.synhubbackend.metrics.domain.model.queries.GetTaskDistributionQuery;
import nrg.inc.synhubbackend.metrics.domain.model.queries.GetTaskOverviewQuery;
import nrg.inc.synhubbackend.metrics.domain.model.queries.GetTaskOverviewForMemberQuery;
import nrg.inc.synhubbackend.metrics.domain.model.queries.GetTaskDistributionForMemberQuery;
import nrg.inc.synhubbackend.metrics.domain.model.queries.GetRescheduledTasksForMemberQuery;
import nrg.inc.synhubbackend.metrics.domain.model.queries.GetAvgCompletionTimeForMemberQuery;

@Service
public class TaskMetricsQueryServiceImpl implements TaskMetricsQueryService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final GroupQueryService groupQueryService;

    public TaskMetricsQueryServiceImpl(TaskRepository taskRepository, UserRepository userRepository, GroupQueryService groupQueryService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.groupQueryService = groupQueryService;
    }

    @Override
    public TaskTimePassedResource handle(GetTaskTimePassedQuery query) {
        List<Task> memberTasks = taskRepository.findByMember_Id(query.memberId());

        double avgTimePassed = memberTasks.isEmpty() ? 0 :
            memberTasks.stream().mapToLong(Task::getTimePassed).average().orElse(0);

        return new TaskTimePassedResource(query.memberId(), (long) avgTimePassed);
    }

    @Override
    public AvgCompletionTimeResource handle(GetAvgCompletionTimeQuery query) {
        var groupOpt = groupQueryService.handle(new GetGroupByLeaderIdQuery(query.leaderId()));
        if (groupOpt.isEmpty()) {
            return new AvgCompletionTimeResource(
                    "AVG_COMPLETION_TIME",
                    0,
                    Map.of("completedTasks", 0)
            );
        }
        Long groupId = groupOpt.get().getId();

        List<Task> groupTasks = taskRepository.findByGroup_Id(groupId);

        List<Task> completedTasks = groupTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE)
                .collect(Collectors.toList());

        double avg = completedTasks.stream()
                .mapToLong(Task::getTimePassed)
                .average()
                .orElse(0);

        return new AvgCompletionTimeResource(
                "AVG_COMPLETION_TIME",
                avg / (1000 * 60 * 60 * 24),
                Map.of("completedTasks", completedTasks.size())
        );
    }

    @Override
    public RescheduledTasksResource handle(GetRescheduledTasksQuery query) {
        List<Task> groupTasks = taskRepository.findByGroup_Id(query.groupId());

        long totalRescheduledTimes = groupTasks.stream()
                .mapToLong(Task::getTimesRearranged)
                .sum();

        Map<String, Integer> details = Map.of(
                "total", groupTasks.size(),
                "rescheduled", (int) totalRescheduledTimes
        );

        List<Long> rescheduledMemberIds = groupTasks.stream()
                .filter(task -> task.getTimesRearranged() > 0 && task.getMember() != null)
                .map(task -> task.getMember().getId())
                .distinct()
                .collect(Collectors.toList());

        return new RescheduledTasksResource("RESCHEDULED_TASKS", totalRescheduledTimes, details, rescheduledMemberIds);
    }

    @Override
    public TaskDistributionResource handle(GetTaskDistributionQuery query) {
        List<Task> groupTasks = taskRepository.findByGroup_Id(query.groupId());

        List<User> users = userRepository.findAll();

        Map<Long, List<Task>> tasksByMemberId = groupTasks.stream()
                .filter(task -> task.getMember() != null)
                .collect(Collectors.groupingBy(task -> task.getMember().getId()));

        Map<String, MemberTaskInfo> details = new HashMap<>();
        for (Map.Entry<Long, List<Task>> entry : tasksByMemberId.entrySet()) {
            Long memberId = entry.getKey();
            int taskCount = entry.getValue().size();
            Optional<User> userOpt = users.stream()
                    .filter(u -> u.getMember() != null && u.getMember().getId().equals(memberId))
                    .findFirst();
            String memberName = userOpt.map(u -> u.getName() + " " + u.getSurname()).orElse("Desconocido");
            details.put(memberId.toString(), new MemberTaskInfo(memberName, taskCount));
        }

        return new TaskDistributionResource("TASK_DISTRIBUTION", groupTasks.size(), details);
    }

    @Override
    public TaskOverviewResource handle(GetTaskOverviewQuery query) {
        List<Task> groupTasks = taskRepository.findByGroup_Id(query.groupId());

        Map<String, Long> overview = groupTasks.stream()
                .collect(Collectors.groupingBy(task -> task.getStatus().name(), Collectors.counting()));

        Map<String, Integer> converted = overview.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().intValue()
                ));

        return new TaskOverviewResource("TASK_OVERVIEW", groupTasks.size(), converted);
    }

    @Override
    public TaskOverviewResource handle(GetTaskOverviewForMemberQuery query) {
        List<Task> memberTasks = taskRepository.findByMember_Id(query.memberId());
        Map<String, Long> overview = memberTasks.stream()
                .collect(Collectors.groupingBy(task -> task.getStatus().name(), Collectors.counting()));
        Map<String, Integer> converted = overview.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().intValue()
                ));
        return new TaskOverviewResource("TASK_OVERVIEW_MEMBER", memberTasks.size(), converted);
    }

    @Override
    public TaskDistributionResource handle(GetTaskDistributionForMemberQuery query) {
        List<Task> memberTasks = taskRepository.findByMember_Id(query.memberId());
        Optional<User> userOpt = userRepository.findAll().stream()
                .filter(u -> u.getMember() != null && u.getMember().getId().equals(query.memberId()))
                .findFirst();
        String memberName = userOpt.map(u -> u.getName() + " " + u.getSurname()).orElse("Desconocido");
        Map<String, MemberTaskInfo> details = Map.of(
                query.memberId().toString(), new MemberTaskInfo(memberName, memberTasks.size())
        );
        return new TaskDistributionResource("TASK_DISTRIBUTION_MEMBER", memberTasks.size(), details);
    }

    @Override
    public RescheduledTasksResource handle(GetRescheduledTasksForMemberQuery query) {
        List<Task> memberTasks = taskRepository.findByMember_Id(query.memberId());
        long totalRescheduledTimes = memberTasks.stream()
                .mapToLong(Task::getTimesRearranged)
                .sum();
        Map<String, Integer> details = Map.of(
                "total", memberTasks.size(),
                "rescheduled", (int) totalRescheduledTimes
        );
        List<Long> rescheduledMemberIds = totalRescheduledTimes > 0 ? List.of(query.memberId()) : List.of();
        return new RescheduledTasksResource("RESCHEDULED_TASKS_MEMBER", totalRescheduledTimes, details, rescheduledMemberIds);
    }

    @Override
    public AvgCompletionTimeResource handle(GetAvgCompletionTimeForMemberQuery query) {
        List<Task> memberTasks = taskRepository.findByMember_Id(query.memberId());
        List<Task> completedTasks = memberTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE) // Cambiado a DONE
                .collect(Collectors.toList());
        double avg = completedTasks.stream()
                .mapToLong(Task::getTimePassed)
                .average()
                .orElse(0);
        return new AvgCompletionTimeResource(
                "AVG_COMPLETION_TIME_MEMBER",
                avg / (1000 * 60 * 60 * 24),
                Map.of("completedTasks", completedTasks.size())
        );
    }
}