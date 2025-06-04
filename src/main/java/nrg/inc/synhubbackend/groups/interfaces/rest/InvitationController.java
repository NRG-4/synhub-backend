package nrg.inc.synhubbackend.groups.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.commands.CancelInvitationCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.CreateInvitationCommand;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetInvitationByMemberIdQuery;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetInvitationsByGroupIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.InvitationCommandService;
import nrg.inc.synhubbackend.groups.domain.services.InvitationQueryService;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.CreateInvitationRequestResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.InvitationResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.InvitationResourceFromEntityAssembler;
import nrg.inc.synhubbackend.shared.application.external.outboundedservices.ExternalIamService;
import nrg.inc.synhubbackend.shared.application.external.outboundedservices.ExternalMemberService;
import nrg.inc.synhubbackend.tasks.domain.services.MemberQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/invitations")
@Tag(name = "Invitations", description = "Invitation Management Endpoints")
public class InvitationController {
    private final InvitationQueryService invitationQueryService;
    private final InvitationCommandService invitationCommandService;
    private final ExternalMemberService externalMemberService;

    public InvitationController(InvitationQueryService invitationQueryService, InvitationCommandService invitationCommandService, ExternalMemberService externalMemberService) {
        this.invitationQueryService = invitationQueryService;
        this.invitationCommandService = invitationCommandService;
        this.externalMemberService = externalMemberService;
    }

    @PostMapping
    @Operation(summary = "Create a new invitation", description = "Create a new invitation for a group")
    public ResponseEntity<InvitationResource> createInvitation(@RequestBody CreateInvitationRequestResource resource) {
        var member = this.externalMemberService.getMemberById(resource.memberId());
        if (member.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var createInvitationCommand = new CreateInvitationCommand(resource.memberId(), resource.groupId());
        var createdInvitation = this.invitationCommandService.handle(createInvitationCommand);
        if (createdInvitation.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var invitationResource = InvitationResourceFromEntityAssembler.toResourceFromEntity(createdInvitation.get(),member.get());
        return ResponseEntity.ok(invitationResource);
    }

    @GetMapping("groups/{groupId}")
    @Operation(summary = "Get all invitations for a group", description = "Get all invitations for a specific group")
    public ResponseEntity<List<InvitationResource>> getInvitationByGroupId(@PathVariable Long groupId) {
        var getInvitationsByGroupIdQuery = new GetInvitationsByGroupIdQuery(groupId);
        var invitations = this.invitationQueryService.handle(getInvitationsByGroupIdQuery);
        var invitationResources = invitations.stream()
                .map(invitation -> {
                    var member = this.externalMemberService.getMemberById(invitation.getMember().getId());
                    return InvitationResourceFromEntityAssembler.toResourceFromEntity(invitation, member.orElse(null));
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(invitationResources);
    }

    @DeleteMapping("/member/{memberId}")
    @Operation(summary = "Cancel an invitation", description = "Cancel an existing invitation by a member")
    public ResponseEntity<Void> cancelInvitation(@PathVariable Long memberId) {
        var getInvitationByMemberIdQuery = new GetInvitationByMemberIdQuery(memberId);
        var invitationId = this.invitationQueryService.handle(getInvitationByMemberIdQuery).get().getId();
        var cancelInvitationCommand = new CancelInvitationCommand(memberId, invitationId);
        this.invitationCommandService.handle(cancelInvitationCommand);
        return ResponseEntity.noContent().build();
    }
}
