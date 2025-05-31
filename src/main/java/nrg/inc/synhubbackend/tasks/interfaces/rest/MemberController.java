package nrg.inc.synhubbackend.tasks.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.tasks.domain.model.queries.GetUserMemberById;
import nrg.inc.synhubbackend.tasks.domain.services.MemberCommandService;
import nrg.inc.synhubbackend.tasks.domain.services.MemberQueryService;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.UserMemberResource;
import nrg.inc.synhubbackend.tasks.interfaces.rest.transform.UserMemberResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@Tag(name = "Member", description = "Member API")
@ApiResponse(responseCode = "201", description = "Member created")
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    public MemberController(MemberCommandService memberCommandService, MemberQueryService memberQueryService) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
    }

    /*
    @GetMapping
    @Operation(summary = "Get all members", description = "Get all members")
    public ResponseEntity<List<MemberResource>> getAllMembers() {
        var getAllMembersQuery = new GetAllMembersQuery();
        var members = memberQueryService.handle(getAllMembersQuery);
        var memberResources = members.stream()
                .map(MemberResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(memberResources);
    }
    */

    @GetMapping("/{userId}")
    @Operation(summary = "Get a member by id", description = "Get a member by id")
    public ResponseEntity<UserMemberResource> getMemberById(@PathVariable Long userId) {
        var getUserMemberByIdQuery = new GetUserMemberById(userId);
        var member = this.memberQueryService.handle(getUserMemberByIdQuery);

        if(member.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userMemberResource = UserMemberResourceFromEntityAssembler.toResourceFromEntity(member.get());
        return ResponseEntity.ok(userMemberResource);
    }

    /*
    @GetMapping("/group/{groupId}")
    @Operation(summary = "Get members by group id", description = "Get members by group id")
    public ResponseEntity<List<MemberResource>> getMembersByGroupId(@PathVariable Long groupId) {
        var getMembersByGroupIdQuery = new GetMembersByGroupIdQuery(groupId);
        var members = memberQueryService.handle(getMembersByGroupIdQuery);
        /*
        var memberResources = members.stream()
                .map(MemberResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(memberResources);
    }

    @PostMapping("/group/{groupId}/member/{memberId}")
    @Operation(summary = "Add a group to a member", description = "Add a group to a member")
    public ResponseEntity<MemberResource> addGroupToMember(@PathVariable Long groupId, @PathVariable Long memberId) {
        var addGroupToMemberCommand = new AddGroupToMemberCommand(groupId, memberId);
        var member = memberCommandService.handle(addGroupToMemberCommand);

        if(member.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }


        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(member.get());
        return ResponseEntity.ok(memberResource);
    }
    */
}
