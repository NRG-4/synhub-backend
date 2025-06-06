package nrg.inc.synhubbackend.metrics.application.service;

import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import nrg.inc.synhubbackend.metrics.interfaces.resources.*;
import nrg.inc.synhubbackend.tasks.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.tasks.domain.model.valueobjects.TaskStatus;
import nrg.inc.synhubbackend.tasks.infrastructure.persistence.jpa.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskMetricsService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskMetricsService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskTimePassedResource getTaskTimePassed(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return new TaskTimePassedResource(taskId, task.getTimePassed());
    }

    public AvgDevelopmentTimeResource getAvgDevTime(Long memberId) {
        List<Task> tasks = taskRepository.findByMemberId(memberId).stream()
                .filter(t -> t.getStatus() == TaskStatus.COMPLETED)
                .collect(Collectors.toList());

        double avg = tasks.stream()
                .mapToLong(Task::getTimePassed)
                .average()
                .orElse(0);

        return new AvgDevelopmentTimeResource("AVG_DEV_TIME", avg / (1000 * 60 * 60 * 24),
                Map.of("taskCount", tasks.size()),
                "memberId=" + memberId,
                "Tiempo promedio de desarrollo: %.2f días".formatted(avg / (1000 * 60 * 60 * 24))
        );
    }

    public AvgSolutionTimeResource getAvgSolutionTime(Long leaderId) {
        List<User> users = userRepository.findAll();

        List<Task> leaderTasks = users.stream()
                .filter(u -> u.getLeader() != null && u.getLeader().getId().equals(leaderId))
                .flatMap(u -> {
                    var member = u.getMember();
                    if (member != null) {
                        return taskRepository.findByMemberId(member.getId()).stream();
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
                Map.of("completedTasks", leaderTasks.size()),
                "leaderId=" + leaderId,
                "Tiempo promedio de solución (líder): %.2f días".formatted(avg / (1000 * 60 * 60 * 24))
        );
    }

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

        return new RescheduledTasksResource("RESCHEDULED_TASKS", rescheduled,
                details,
                "groupId=" + groupId,
                "Tareas reprogramadas vs no reprogramadas: %.2f".formatted((double) rescheduled)
        );
    }

    public TaskDistributionResource getTaskDistribution(Long groupId) {
        List<Task> groupTasks = taskRepository.findAll().stream()
                .filter(task -> task.getMember() != null
                        && task.getMember().getGroup() != null
                        && task.getMember().getGroup().getId().equals(groupId))
                .collect(Collectors.toList());

        List<User> users = userRepository.findAll();

        Map<String, Long> distribution = groupTasks.stream()
                .filter(task -> task.getMember() != null)
                .collect(Collectors.groupingBy(task -> {
                    Long memberId = task.getMember().getId();
                    Optional<User> userOpt = users.stream()
                            .filter(u -> u.getMember() != null && u.getMember().getId().equals(memberId))
                            .findFirst();

                    return userOpt.map(u -> u.getName() + " " + u.getSurname()).orElse("Desconocido");
                }, Collectors.counting()));

        Map<String, Integer> converted = distribution.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().intValue()
                ));

        return new TaskDistributionResource("TASK_DISTRIBUTION", groupTasks.size(),
                converted,
                "groupId=" + groupId,
                "Distribución de tareas: %.2f".formatted((double) groupTasks.size())
        );
    }

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

        return new TaskOverviewResource("TASK_OVERVIEW", groupTasks.size(),
                converted,
                "groupId=" + groupId,
                "Vista general de tareas: %.2f".formatted((double) groupTasks.size())
        );
    }
}