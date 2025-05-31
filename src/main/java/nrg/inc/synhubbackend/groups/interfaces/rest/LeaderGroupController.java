package nrg.inc.synhubbackend.groups.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.commands.CreateGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.DeleteGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.UpdateGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByLeaderIdQuery;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetLeaderByIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupCommandService;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.groups.domain.services.LeaderQueryService;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.CreateGroupResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.GroupResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.UpdateGroupResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.GroupResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/leaders")
@Tag(name = "Groups", description = "Group management API")

public class LeaderGroupController {

    private final GroupQueryService groupQueryService;
    private final GroupCommandService groupCommandService;
    private final LeaderQueryService leaderQueryService;

    public LeaderGroupController(GroupQueryService groupQueryService, GroupCommandService groupCommandService, LeaderQueryService leaderQueryService) {
        this.groupQueryService = groupQueryService;
        this.groupCommandService = groupCommandService;
        this.leaderQueryService = leaderQueryService;
    }

    @PostMapping("{leaderId}/group")
    @Operation(summary = "Create a new group", description = "Creates a new group")
    public ResponseEntity<GroupResource> createGroup(@RequestBody CreateGroupResource resource, @PathVariable Long leaderId) {

        var getLeaderByIdQuery = new GetLeaderByIdQuery(leaderId);

        var leader = this.leaderQueryService.handle(getLeaderByIdQuery);

        if( leader.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var createGroupCommand = new CreateGroupCommand(
                resource.name(),
                resource.imgUrl(),
                resource.description(),
                leader.get().getId()
        );

        var group = this.groupCommandService.handle(createGroupCommand);

        if (group.isEmpty()) return ResponseEntity.notFound().build();

        var groupResourceCreated = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResourceCreated);
    }

    @PutMapping("/{leaderId}/group")
    @Operation(summary = "Update a group", description = "Updates a group")
    public ResponseEntity<GroupResource> updateGroup(@PathVariable Long leaderId, @RequestBody UpdateGroupResource groupResource) {
        var updateGroupCommand = new UpdateGroupCommand(
                leaderId,
                groupResource.name(),
                groupResource.description(),
                groupResource.imgUrl()
        );

        var group = this.groupCommandService.handle(updateGroupCommand);

        if (group.isEmpty()) return ResponseEntity.notFound().build();

        var groupResourceUpdated = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResourceUpdated);
    }

    @DeleteMapping("/{leaderId}/group")
    @Operation(summary = "Delete a group", description = "Deletes a group")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long leaderId) {
        var deleteGroupCommand = new DeleteGroupCommand(leaderId);
        this.groupCommandService.handle(deleteGroupCommand);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{leaderId}/group")
    @Operation(summary = "Get a group by ID", description = "Gets a group by ID")
    public ResponseEntity<GroupResource> getGroupById(@PathVariable Long leaderId) {
        var getGroupByLeaderIdQuery = new GetGroupByLeaderIdQuery(leaderId);
        var group = this.groupQueryService.handle(getGroupByLeaderIdQuery);
        if (group.isEmpty()) return ResponseEntity.notFound().build();
        var groupResource = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResource);
    }
}
