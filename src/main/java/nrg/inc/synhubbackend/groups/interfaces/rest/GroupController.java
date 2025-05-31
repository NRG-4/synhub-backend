package nrg.inc.synhubbackend.groups.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByCodeQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.GroupResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.GroupResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/groups")
@Tag(name = "Groups", description = "Group management API")
public class GroupController {
    private final GroupQueryService groupQueryService;

    public GroupController(GroupQueryService groupQueryService) {
        this.groupQueryService = groupQueryService;
    }

    @GetMapping("/search")
    @Operation(summary = "Search for a group by code", description = "Search for a group by code")
    public ResponseEntity<GroupResource> searchGroupByCode(@RequestParam String code) {
        var getGroupByCodeQuery = new GetGroupByCodeQuery(code);
        var group = this.groupQueryService.handle(getGroupByCodeQuery);
        if (group.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var groupResource = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResource);
    }
}
