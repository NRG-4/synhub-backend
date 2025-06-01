package nrg.inc.synhubbackend.groups.interfaces.rest.transform;

import nrg.inc.synhubbackend.groups.interfaces.rest.resources.UserMemberInvitationResource;
import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.UserMemberResource;

public class UserMemberInvitationResourceFromEntityAssembler {
    public static UserMemberInvitationResource toResourceFromEntity(User user){
        return new UserMemberInvitationResource(
                user.getMember().getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getImgUrl()
        );
    }
}
