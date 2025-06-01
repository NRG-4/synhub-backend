package nrg.inc.synhubbackend.groups.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByCodeQuery;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByMemberIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.GroupResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.GroupResourceFromEntityAssembler;
import nrg.inc.synhubbackend.shared.application.external.outboundedservices.ExternalIamService;
import nrg.inc.synhubbackend.tasks.domain.model.queries.GetAllTasksByGroupIdQuery;
import nrg.inc.synhubbackend.tasks.domain.services.TaskQueryService;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.TaskResource;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.UserMemberResource;
import nrg.inc.synhubbackend.tasks.interfaces.rest.transform.TaskResourceFromEntityAssembler;
import nrg.inc.synhubbackend.tasks.interfaces.rest.transform.UserMemberResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/groups")
@Tag(name = "Groups", description = "Group management API")
public class GroupController {
    private final GroupQueryService groupQueryService;
    private final ExternalIamService externalIamService;
    private final TaskQueryService taskQueryService;

    public GroupController(GroupQueryService groupQueryService, ExternalIamService externalIamService, TaskQueryService taskQueryService) {
        this.groupQueryService = groupQueryService;
        this.externalIamService = externalIamService;
        this.taskQueryService = taskQueryService;
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

    @GetMapping("/members/{memberId}")
    @Operation(summary = "Get group by member ID", description = "Get the group associated with a specific member ID")
    public ResponseEntity<GroupResource> getGroupByMemberId(@PathVariable Long memberId) {
        var getGroupByMemberIdQuery = new GetGroupByMemberIdQuery(memberId);
        var group = this.groupQueryService.handle(getGroupByMemberIdQuery);
        if (group.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var groupResource = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResource);
    }

    @GetMapping("{groupId}/members")
    @Operation(summary = "Get all group members", description = "Retrieve all members of a group")
    public ResponseEntity<List<UserMemberResource>> getAllMembersByGroupId(@PathVariable Long groupId) {
        var members = externalIamService.getUsersByGroup_Id(groupId);
        var memberResources = members.stream()
                .map(UserMemberResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberResources);
    }

    @GetMapping("{groupId}/tasks")
    @Operation(summary = "Get all tasks by group ID", description = "Retrieve all tasks associated with a specific group ID")
    public ResponseEntity<List<TaskResource>> getAllTasksByGroupId(@PathVariable Long groupId) {
        var getAllTasksByGroupIdQuery = new GetAllTasksByGroupIdQuery(groupId);
        var tasks = taskQueryService.handle(getAllTasksByGroupIdQuery);
        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResources);
    }
}
