package nrg.inc.synhubbackend.taskManagement.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllMembersQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetMemberByIdQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetMembersByGroupIdQuery;
import nrg.inc.synhubbackend.taskManagement.domain.services.MemberCommandService;
import nrg.inc.synhubbackend.taskManagement.domain.services.MemberQueryService;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.CreateMemberResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.MemberResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform.CreateMemberCommandFromResourceAssembler;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform.MemberResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    @Operation(summary = "Create a new member", description = "Creates a new member")
    public ResponseEntity<MemberResource> createMember(@RequestBody CreateMemberResource resource) {
        var createMemberCommand = CreateMemberCommandFromResourceAssembler.toCommandFromResource(resource);
        var member = memberCommandService.handle(createMemberCommand);

        if(member.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(member.get());

        return new ResponseEntity<>(memberResource, HttpStatus.valueOf(201));
    }

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

    @GetMapping("/{memberId}")
    @Operation(summary = "Get a member by id", description = "Get a member by id")
    public ResponseEntity<MemberResource> getMemberById(@PathVariable Long memberId) {
        var getMemberByIdQuery = new GetMemberByIdQuery(memberId);
        var member = this.memberQueryService.handle(getMemberByIdQuery);

        if(member.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(member.get());
        return ResponseEntity.ok(memberResource);
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "Get members by group id", description = "Get members by group id")
    public ResponseEntity<List<MemberResource>> getMembersByGroupId(@PathVariable Long groupId) {
        var getMembersByGroupIdQuery = new GetMembersByGroupIdQuery(groupId);
        var members = memberQueryService.handle(getMembersByGroupIdQuery);
        var memberResources = members.stream()
                .map(MemberResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(memberResources);
    }

}
