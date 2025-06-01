package nrg.inc.synhubbackend.groups.interfaces.rest.transform;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Invitation;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.InvitationResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.UserMemberInvitationResource;
import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;

public class InvitationResourceFromEntityAssembler {
    public static InvitationResource toResourceFromEntity(
            Invitation invitation,
            User member
    ) {
        return new InvitationResource(
                invitation.getId(),
                UserMemberInvitationResourceFromEntityAssembler.toResourceFromEntity(member),
                GroupResourceFromEntityAssembler.toResourceFromEntity(invitation.getGroup())
        );
    }
}
