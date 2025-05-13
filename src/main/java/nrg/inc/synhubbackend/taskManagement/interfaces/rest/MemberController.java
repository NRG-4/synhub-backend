package nrg.inc.synhubbackend.taskManagement.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.taskManagement.domain.services.MemberCommandService;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.CreateMemberResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.MemberResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform.CreateMemberCommandFromResourceAssembler;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform.MemberResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@Tag(name = "Member", description = "Member API")
@ApiResponse(responseCode = "201", description = "Member created")
public class MemberController {
    private final MemberCommandService memberCommandService;

    public MemberController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
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
}
