package nrg.inc.synhubbackend.metrics.application.service;

import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import nrg.inc.synhubbackend.metrics.domain.model.services.TaskMetricsService;
import nrg.inc.synhubbackend.metrics.interfaces.rest.resources.*;
import nrg.inc.synhubbackend.tasks.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.tasks.domain.model.valueobjects.TaskStatus;
import nrg.inc.synhubbackend.tasks.infrastructure.persistence.jpa.repositories.TaskRepository;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByLeaderIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskMetricsServiceImpl implements TaskMetricsService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final GroupQueryService groupQueryService;

    public TaskMetricsServiceImpl(TaskRepository taskRepository, UserRepository userRepository, GroupQueryService groupQueryService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.groupQueryService = groupQueryService;
    }

    @Override
    public TaskTimePassedResource getTaskTimePassed(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return new TaskTimePassedResource(taskId, task.getTimePassed());
    }

    @Override
    public AvgDevelopmentTimeResource getAvgDevTime(Long memberId) {
        List<Task> tasks = taskRepository.findAll().stream()
                .filter(t -> t.getMember() != null && t.getMember().getId().equals(memberId))
                .filter(t -> t.getStatus() == TaskStatus.COMPLETED)
                .collect(Collectors.toList());

        double avg = tasks.stream()
                .mapToLong(Task::getTimePassed)
                .average()
                .orElse(0);

        return new AvgDevelopmentTimeResource("AVG_DEV_TIME", avg / (1000 * 60 * 60 * 24),
                Map.of("taskCount", tasks.size())
        );
    }

    @Override
    public AvgSolutionTimeResource getAvgSolutionTime(Long leaderId) {
        List<User> users = userRepository.findAll();

        List<Task> leaderTasks = users.stream()
                .filter(u -> u.getLeader() != null && u.getLeader().getId().equals(leaderId))
                .flatMap(u -> {
                    var member = u.getMember();
                    if (member != null) {
                        return taskRepository.findAll().stream()
                                .filter(t -> t.getMember() != null && t.getMember().getId().equals(member.getId()));
                    }
                    return Stream.empty();
                })
                .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                .collect(Collectors.toList());

        double avg = leaderTasks.stream()
                .mapToLong(Task::getTimePassed)
                .average()
                .orElse(0);

        return new AvgSolutionTimeResource("AVG_SOLUTION_TIME", avg / (1000 * 60 * 60 * 24),
                Map.of("completedTasks", leaderTasks.size())
        );
    }

    @Override
    public AvgCompletionTimeResource getAvgCompletionTime(Long leaderId) {
        var groupOpt = groupQueryService.handle(new GetGroupByLeaderIdQuery(leaderId));
        if (groupOpt.isEmpty()) {
            return new AvgCompletionTimeResource(
                "AVG_COMPLETION_TIME",
                0,
                Map.of("completedTasks", 0)
            );
        }
        Long groupId = groupOpt.get().getId();

        List<Task> completedTasks = taskRepository.findAll().stream()
                .filter(task -> task.getMember() != null
                        && task.getMember().getGroup() != null
                        && task.getMember().getGroup().getId().equals(groupId)
                        && task.getStatus() == TaskStatus.COMPLETED)
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
    public RescheduledTasksResource getRescheduledTasks(Long groupId) {
        List<Task> groupTasks = taskRepository.findAll().stream()
                .filter(task -> task.getMember() != null
                        && task.getMember().getGroup() != null
                        && task.getMember().getGroup().getId().equals(groupId))
                .collect(Collectors.toList());

        long rescheduled = groupTasks.stream()
                .filter(task -> task.getTimesRearranged() > 0)
                .count();

        Map<String, Integer> details = Map.of(
                "total", groupTasks.size(),
                "rescheduled", (int) rescheduled
        );

        return new RescheduledTasksResource("RESCHEDULED_TASKS", rescheduled, details);
    }

    @Override
    public TaskDistributionResource getTaskDistribution(Long groupId) {
        List<Task> groupTasks = taskRepository.findAll().stream()
                .filter(task -> task.getMember() != null
                        && task.getMember().getGroup() != null
                        && task.getMember().getGroup().getId().equals(groupId))
                .collect(Collectors.toList());

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
    public TaskOverviewResource getTaskOverview(Long groupId) {
        List<Task> groupTasks = taskRepository.findAll().stream()
                .filter(task -> task.getMember() != null
                        && task.getMember().getGroup() != null
                        && task.getMember().getGroup().getId().equals(groupId))
                .collect(Collectors.toList());

        Map<String, Long> overview = groupTasks.stream()
                .collect(Collectors.groupingBy(task -> task.getStatus().name(), Collectors.counting()));

        Map<String, Integer> converted = overview.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().intValue()
                ));

        return new TaskOverviewResource("TASK_OVERVIEW", groupTasks.size(), converted);
    }
}
