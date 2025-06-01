package nrg.inc.synhubbackend.tasks.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.shared.application.external.outboundedservices.ExternalIamService;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.UserMemberResource;
import nrg.inc.synhubbackend.tasks.interfaces.rest.transform.UserMemberResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/groups")
@Tag(name = "Groups", description = "Group management API")
public class GroupMemberController {
    private final ExternalIamService externalIamService;
    public GroupMemberController(ExternalIamService externalIamService) {
        this.externalIamService = externalIamService;
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
}
