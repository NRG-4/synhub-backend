package nrg.inc.synhubbackend.analytics.application.service;

import nrg.inc.synhubbackend.analytics.domain.model.*;
import nrg.inc.synhubbackend.analytics.infrastructure.adapters.ExternalServiceAdapter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MetricCalculatorService {

    private final ExternalServiceAdapter externalServiceAdapter;

    public MetricCalculatorService(ExternalServiceAdapter externalServiceAdapter) {
        this.externalServiceAdapter = externalServiceAdapter;
    }

    public Metric calculateTaskDistribution(Long groupId) {
        List<TaskData> allTasks = externalServiceAdapter.getAllTasks();
        List<MemberData> members = externalServiceAdapter.getMembersByGroupId(groupId);

        Map<String, Long> distribution = new HashMap<>();
        members.forEach(member -> distribution.put(member.getName(), 0L));

        allTasks.stream()
                .filter(task -> task.getAssignedMember() != null)
                .forEach(task -> {
                    String memberName = task.getAssignedMember().getName();
                    distribution.put(memberName, distribution.getOrDefault(memberName, 0L) + 1);
                });

        return new Metric(
                MetricType.TASK_DISTRIBUTION,
                (double) allTasks.size(),
                distribution,
                "groupId=" + groupId
        );
    }

    public Metric calculateAverageDevTime(Long memberId) {
        List<TaskData> memberTasks = externalServiceAdapter.getTasksByMemberId(memberId);

        double averageHours = memberTasks.stream()
                .filter(task -> task.getCreatedAt() != null && task.getUpdatedAt() != null)
                .mapToDouble(task -> {
                    Duration duration = Duration.between(task.getCreatedAt(), task.getUpdatedAt());
                    return duration.toHours();
                })
                .average()
                .orElse(0.0);

        Map<String, Object> details = new HashMap<>();
        details.put("taskCount", memberTasks.size());

        return new Metric(
                MetricType.AVG_DEV_TIME,
                averageHours,
                details,
                "memberId=" + memberId
        );
    }

    public Metric calculateTaskOverview(Long groupId) {
        List<TaskData> allTasks = externalServiceAdapter.getAllTasks();

        Map<String, Long> statusCount = allTasks.stream()
                .collect(Collectors.groupingBy(
                        TaskData::getStatus,
                        Collectors.counting()
                ));

        return new Metric(
                MetricType.TASK_OVERVIEW,
                (double) allTasks.size(),
                statusCount,
                "groupId=" + groupId
        );
    }

    public Metric calculateRescheduledTasks(Long groupId) {
        List<TaskData> allTasks = externalServiceAdapter.getAllTasks();

        long rescheduled = allTasks.stream()
                .filter(task -> task.getDueDate() != null && task.getUpdatedAt() != null)
                .filter(task -> task.getUpdatedAt().isAfter(task.getDueDate()))
                .count();

        double ratio = allTasks.isEmpty() ? 0 : (double) rescheduled / allTasks.size();

        Map<String, Object> details = new HashMap<>();
        details.put("rescheduled", rescheduled);
        details.put("total", allTasks.size());

        return new Metric(
                MetricType.RESCHEDULED_TASKS,
                ratio,
                details,
                "groupId=" + groupId
        );
    }

    public Metric calculateAverageSolutionTime(Long leaderId) {
        List<TaskData> leaderTasks = externalServiceAdapter.getTasksByMemberId(leaderId);

        double averageHours = leaderTasks.stream()
                .filter(task -> "COMPLETED".equals(task.getStatus()))
                .filter(task -> task.getCreatedAt() != null && task.getUpdatedAt() != null)
                .mapToDouble(task -> {
                    Duration duration = Duration.between(task.getCreatedAt(), task.getUpdatedAt());
                    return duration.toHours();
                })
                .average()
                .orElse(0.0);

        Map<String, Object> details = new HashMap<>();
        details.put("completedTasks", leaderTasks.size());

        return new Metric(
                MetricType.AVG_SOLUTION_TIME,
                averageHours,
                details,
                "leaderId=" + leaderId
        );
    }
}