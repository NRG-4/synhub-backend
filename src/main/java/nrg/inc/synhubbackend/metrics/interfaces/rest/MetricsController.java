// src/main/java/nrg/inc/synhubbackend/metrics/interfaces/rest/MetricsController.java
package nrg.inc.synhubbackend.metrics.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.metrics.application.service.GroupMetricsService;
import nrg.inc.synhubbackend.metrics.application.service.TaskMetricsService;
import nrg.inc.synhubbackend.metrics.domain.model.GroupMetrics;
import nrg.inc.synhubbackend.metrics.interfaces.resources.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/metrics")
@Tag(name = "Metrics", description = "Provides access to analytics and group metrics")
public class MetricsController {

    private final GroupMetricsService groupMetricsService;
    private final GroupQueryService groupQueryService;
    private final TaskMetricsService taskMetricsService;

    public MetricsController(GroupMetricsService groupMetricsService, GroupQueryService groupQueryService, TaskMetricsService taskMetricsService) {
        this.groupMetricsService = groupMetricsService;
        this.groupQueryService = groupQueryService;
        this.taskMetricsService = taskMetricsService;
    }

    @GetMapping("/groups/{groupId}/member-count")
    @Operation(summary = "Get group metrics", description = "Returns group metrics including member count")
    public ResponseEntity<GroupMetrics> getGroupMemberCount(@PathVariable Long groupId) {
        var metrics = groupMetricsService.getGroupMetrics(groupId);
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/groups/{groupId}/raw-member-count")
    @Operation(summary = "Get raw member count", description = "Returns the raw member count directly from the group entity")
    public ResponseEntity<Integer> getRawMemberCount(@PathVariable Long groupId) {
        return groupQueryService.handle(new GetGroupByIdQuery(groupId))
                .map(group -> ResponseEntity.ok(group.getMemberCount()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get time passed for a completed task",
            description = "Returns the time passed in milliseconds for a completed task.",
            tags = {"Metrics"}
    )
    @GetMapping("/task/{taskId}/time-passed")
    public TaskTimePassedResource getTaskTimePassed(@PathVariable Long taskId) {
        return taskMetricsService.getTaskTimePassed(taskId);
    }
    
    @GetMapping("/tasks/overview/{groupId}")
    @Operation(summary = "Get task overview for group", description = "Returns general task status count for a group")
    public TaskOverviewResource getTaskOverview(@PathVariable Long groupId) {
        return taskMetricsService.getTaskOverview(groupId);
    }

    @GetMapping("/tasks/distribution/{groupId}")
    @Operation(summary = "Get task distribution for group", description = "Returns the number of tasks per member in a group")
    public TaskDistributionResource getTaskDistribution(@PathVariable Long groupId) {
        return taskMetricsService.getTaskDistribution(groupId);
    }

    @GetMapping("/tasks/rescheduled/{groupId}")
    @Operation(summary = "Get rescheduled tasks", description = "Returns the count of rescheduled vs non-rescheduled tasks")
    public RescheduledTasksResource getRescheduledTasks(@PathVariable Long groupId) {
        return taskMetricsService.getRescheduledTasks(groupId);
    }

    @GetMapping("/tasks/avg-solution-time/{leaderId}")
    @Operation(summary = "Get average solution time", description = "Returns the average solution time in days for tasks completed by a leader")
    public AvgSolutionTimeResource getAvgSolutionTime(@PathVariable Long leaderId) {
        return taskMetricsService.getAvgSolutionTime(leaderId);
    }

    @GetMapping("/tasks/avg-dev-time/{memberId}")
    @Operation(summary = "Get average development time", description = "Returns the average time spent on tasks by a member")
    public AvgDevelopmentTimeResource getAvgDevTime(@PathVariable Long memberId) {
        return taskMetricsService.getAvgDevTime(memberId);
    }
}