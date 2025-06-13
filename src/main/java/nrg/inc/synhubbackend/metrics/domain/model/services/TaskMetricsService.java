package nrg.inc.synhubbackend.metrics.domain.model.services;

import nrg.inc.synhubbackend.metrics.interfaces.rest.resources.*;

public interface TaskMetricsService {
    TaskTimePassedResource getTaskTimePassed(Long taskId);
    AvgCompletionTimeResource getAvgCompletionTime(Long leaderId);
    RescheduledTasksResource getRescheduledTasks(Long groupId);
    TaskDistributionResource getTaskDistribution(Long groupId);
    TaskOverviewResource getTaskOverview(Long groupId);
}