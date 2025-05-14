package nrg.inc.synhubbackend.groups.interfaces.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.commands.CreateGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.DeleteGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.UpdateGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupCommandService;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.CreateGroupResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.GroupResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.UpdateGroupResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.GroupResourceFromEntityAssembler;
import nrg.inc.synhubbackend.shared.interfaces.rest.resources.MessageResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/groups")
@Tag(name = "Groups", description = "Group management API")
public class GroupController {

    private final GroupQueryService groupQueryService;
    private final GroupCommandService groupCommandService;

    public GroupController(GroupQueryService groupQueryService, GroupCommandService groupCommandService) {
        this.groupQueryService = groupQueryService;
        this.groupCommandService = groupCommandService;
    }


    @GetMapping("/{groupId}")
    @Operation(summary = "Get a group by ID", description = "Gets a group by ID")
    public ResponseEntity<GroupResource> getGroupById(@PathVariable Long groupId) {
        var getGroupByIdQuery = new GetGroupByIdQuery(groupId);
        var group = this.groupQueryService.handle(getGroupByIdQuery);

        if (group.isEmpty()) return ResponseEntity.notFound().build();

        var groupResource = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResource);
    }

    @DeleteMapping("/{groupId}")
    @Operation(summary = "Delete a group", description = "Deletes a group")
    public ResponseEntity<MessageResource> deleteGroup(@PathVariable Long groupId) {

        var getGroupByIdQuery = new GetGroupByIdQuery(groupId);

        var deleteGroupCommand = new DeleteGroupCommand(groupId);
        this.groupCommandService.handle(deleteGroupCommand);

        var group = this.groupQueryService.handle(getGroupByIdQuery);

        if (group.isPresent()) {
            throw new IllegalArgumentException("Group found");
        }

        return ResponseEntity.ok(new MessageResource("Group deleted successfully"));
    }

    @PutMapping("/{groupId}")
    @Operation(summary = "Update a group", description = "Updates a group")
    public ResponseEntity<GroupResource> updateGroup(@PathVariable Long groupId, @RequestBody UpdateGroupResource groupResource) {
        var updateGroupCommand = new UpdateGroupCommand(
                groupId,
                groupResource.name(),
                groupResource.description(),
                groupResource.memberCount(),
                groupResource.imgUrl()
        );
        var group = this.groupCommandService.handle(updateGroupCommand);

        if (group.isEmpty()) return ResponseEntity.notFound().build();

        var groupResourceUpdated = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResourceUpdated);
    }

    @PostMapping("/{leaderId}")
    @Operation(summary = "Create a new group", description = "Creates a new group")
    public ResponseEntity<GroupResource> createGroup(@RequestBody CreateGroupResource createGroupResource, @PathVariable Long leaderId) {
        var createGroupCommand = new CreateGroupCommand(
                createGroupResource.name(),
                createGroupResource.imgUrl(),
                createGroupResource.description(),
                leaderId
        );
        var group = this.groupCommandService.handle(createGroupCommand);

        if (group.isEmpty()) return ResponseEntity.notFound().build();

        var groupResourceCreated = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResourceCreated);
    }

    @GetMapping("/leader/{leaderId}")
    @Operation(summary = "Get a group by leader ID", description = "Gets a group by leader ID")
    public ResponseEntity<GroupResource> getGroupByLeaderId(@PathVariable Long leaderId) {
        var getGroupByLeaderIdQuery = new GetGroupByIdQuery(leaderId);
        var group = this.groupQueryService.handle(getGroupByLeaderIdQuery);

        if (group.isEmpty()) return ResponseEntity.notFound().build();

        var groupResource = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResource);
    }

}
