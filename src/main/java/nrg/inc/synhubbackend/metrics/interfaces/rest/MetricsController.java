package nrg.inc.synhubbackend.metrics.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.metrics.application.service.GroupMetricsService;
import nrg.inc.synhubbackend.metrics.domain.model.GroupMetrics;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/metrics")
@Tag(name = "Metrics", description = "Provides access to analytics and group metrics")
public class MetricsController {

    private final GroupMetricsService groupMetricsService;
    private final GroupQueryService groupQueryService;

    public MetricsController(GroupMetricsService groupMetricsService, GroupQueryService groupQueryService) {
        this.groupMetricsService = groupMetricsService;
        this.groupQueryService = groupQueryService;
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
}
