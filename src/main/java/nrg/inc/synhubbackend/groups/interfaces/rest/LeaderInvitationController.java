package nrg.inc.synhubbackend.groups.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.commands.AcceptInvitationCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.CancelInvitationCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.RejectInvitationCommand;
import nrg.inc.synhubbackend.groups.domain.services.InvitationCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leaders/{leaderId}/invitations")
@Tag(name = "Invitations", description = "Invitation Management Endpoints")
public class LeaderInvitationController {
    private final InvitationCommandService invitationCommandService;

    public LeaderInvitationController(InvitationCommandService invitationCommandService) {
        this.invitationCommandService = invitationCommandService;
    }

    @PatchMapping("/{invitationId}")
    @Operation(summary = "Accept or decline an invitation", description = "Accept or decline an invitation for a leader")
    public ResponseEntity<Void> processInvitation(
            @PathVariable Long invitationId, @PathVariable Long leaderId,
            @RequestParam(defaultValue = "false") boolean accept) {
        if (accept) {
            var acceptInvitationCommand = new AcceptInvitationCommand(leaderId, invitationId);
            this.invitationCommandService.handle(acceptInvitationCommand);
        } else {
            var cancelInvitationCommand = new RejectInvitationCommand(leaderId, invitationId);
            this.invitationCommandService.handle(cancelInvitationCommand);
        }
        return ResponseEntity.ok().build();
    }

}
