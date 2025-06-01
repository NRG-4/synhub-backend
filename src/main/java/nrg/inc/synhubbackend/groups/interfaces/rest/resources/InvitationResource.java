package nrg.inc.synhubbackend.groups.interfaces.rest.resources;

import nrg.inc.synhubbackend.iam.interfaces.rest.resources.UserResource;

public record InvitationResource(
        Long id,
        UserMemberInvitationResource member,
        GroupResource group
) {
}
