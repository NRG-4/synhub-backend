package nrg.inc.synhubbackend.metrics.domain.model.services;

import nrg.inc.synhubbackend.metrics.domain.model.queries.*;
import nrg.inc.synhubbackend.metrics.interfaces.rest.resources.*;

public interface TaskMetricsService {
    TaskTimePassedResource getTaskTimePassed(GetTaskTimePassedQuery query);
    AvgCompletionTimeResource getAvgCompletionTime(GetAvgCompletionTimeQuery query);
    RescheduledTasksResource getRescheduledTasks(GetRescheduledTasksQuery query);
    TaskDistributionResource getTaskDistribution(GetTaskDistributionQuery query);
    TaskOverviewResource getTaskOverview(GetTaskOverviewQuery query);
}
