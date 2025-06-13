package nrg.inc.synhubbackend.metrics.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByLeaderIdQuery;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetLeaderByUsernameQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.groups.domain.services.LeaderQueryService;
import nrg.inc.synhubbackend.metrics.domain.model.services.TaskMetricsQueryService;
import nrg.inc.synhubbackend.metrics.domain.model.queries.*;
import nrg.inc.synhubbackend.metrics.interfaces.rest.resources.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/metrics")
@Tag(name = "Metrics", description = "Provides access to analytics and group metrics")
public class MetricsController {

    private final TaskMetricsQueryService taskMetricsQueryService;
    private final LeaderQueryService leaderQueryService;
    private final GroupQueryService groupQueryService;

    public MetricsController(TaskMetricsQueryService taskMetricsQueryService, LeaderQueryService leaderQueryService, GroupQueryService groupQueryService) {
        this.taskMetricsQueryService = taskMetricsQueryService;
        this.leaderQueryService = leaderQueryService;
        this.groupQueryService = groupQueryService;
    }

    @GetMapping("/task/member/{memberId}/time-passed")
    @Operation(
        summary = "Get time passed for a member's completed task",
        description = "Returns the time passed in milliseconds for a completed task assigned to the given member.",
        tags = {"Metrics", "Tasks"}
    )
    public ResponseEntity<TaskTimePassedResource> getAverageTaskTimePassed(@PathVariable Long memberId) {
        var query = new GetTaskTimePassedQuery(memberId);
        var resource = taskMetricsQueryService.handle(query);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/tasks/overview")
    @Operation(
        summary = "Get task overview for group",
        description = "Returns a summary of task statuses for the authenticated leader's group.",
        tags = {"Metrics", "Tasks"}
    )
    public ResponseEntity<TaskOverviewResource> getTaskOverview(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var leader = leaderQueryService.handle(new GetLeaderByUsernameQuery(username));
        if (leader.isEmpty()) return ResponseEntity.notFound().build();
        var group = groupQueryService.handle(new GetGroupByLeaderIdQuery(leader.get().getId()));
        if (group.isEmpty()) return ResponseEntity.notFound().build();
        var query = new GetTaskOverviewQuery(group.get().getId());
        var resource = taskMetricsQueryService.handle(query);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/tasks/distribution")
    @Operation(
        summary = "Get task distribution for group",
        description = "Returns the number of tasks assigned to each member in the authenticated leader's group.",
        tags = {"Metrics", "Tasks"}
    )
    public ResponseEntity<TaskDistributionResource> getTaskDistribution(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var leader = leaderQueryService.handle(new GetLeaderByUsernameQuery(username));
        if (leader.isEmpty()) return ResponseEntity.notFound().build();
        var group = groupQueryService.handle(new GetGroupByLeaderIdQuery(leader.get().getId()));
        if (group.isEmpty()) return ResponseEntity.notFound().build();
        var query = new GetTaskDistributionQuery(group.get().getId());
        var resource = taskMetricsQueryService.handle(query);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/tasks/rescheduled")
    @Operation(
        summary = "Get rescheduled tasks for group",
        description = "Returns the count of rescheduled vs non-rescheduled tasks for the authenticated leader's group.",
        tags = {"Metrics", "Tasks"}
    )
    public ResponseEntity<RescheduledTasksResource> getRescheduledTasks(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var leader = leaderQueryService.handle(new GetLeaderByUsernameQuery(username));
        if (leader.isEmpty()) return ResponseEntity.notFound().build();
        var group = groupQueryService.handle(new GetGroupByLeaderIdQuery(leader.get().getId()));
        if (group.isEmpty()) return ResponseEntity.notFound().build();
        var query = new GetRescheduledTasksQuery(group.get().getId());
        var resource = taskMetricsQueryService.handle(query);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/tasks/avg-completion-time")
    @Operation(
        summary = "Get average completion time for group",
        description = "Returns the average time (in days) it takes to complete tasks in the authenticated leader's group.",
        tags = {"Metrics", "Tasks"}
    )
    public ResponseEntity<AvgCompletionTimeResource> getAvgCompletionTime(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var leader = leaderQueryService.handle(new GetLeaderByUsernameQuery(username));
        if (leader.isEmpty()) return ResponseEntity.notFound().build();
        var query = new GetAvgCompletionTimeQuery(leader.get().getId());
        var resource = taskMetricsQueryService.handle(query);
        return ResponseEntity.ok(resource);
    }
}
