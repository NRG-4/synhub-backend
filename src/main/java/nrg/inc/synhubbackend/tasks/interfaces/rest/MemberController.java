package nrg.inc.synhubbackend.tasks.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.tasks.domain.model.queries.GetMemberByUsernameQuery;
import nrg.inc.synhubbackend.tasks.domain.services.MemberCommandService;
import nrg.inc.synhubbackend.tasks.domain.services.MemberQueryService;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.MemberResource;
import nrg.inc.synhubbackend.tasks.interfaces.rest.transform.MemberResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@Tag(name = "Member", description = "Member API")
@ApiResponse(responseCode = "201", description = "Member created")
public class MemberController {
    private final MemberQueryService memberQueryService;

    public MemberController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @GetMapping("/details")
    @Operation(summary = "Get member details by authentication", description = "Fetches the details of the authenticated member.")
    public ResponseEntity<MemberResource> getMemberByAuthentication(@AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        var getMemberByUsernameQuery = new GetMemberByUsernameQuery(username);

        var member = this.memberQueryService.handle(getMemberByUsernameQuery);

        if(member.isEmpty()) return ResponseEntity.notFound().build();

        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(member.get());

        return ResponseEntity.ok(memberResource);
    }
}
