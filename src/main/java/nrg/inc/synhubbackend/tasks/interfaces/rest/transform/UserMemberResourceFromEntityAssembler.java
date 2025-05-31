package nrg.inc.synhubbackend.tasks.interfaces.rest.transform;

import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.UserMemberResource;

public class UserMemberResourceFromEntityAssembler {
    public static UserMemberResource toResourceFromEntity(User user) {
        Long groupId = (user.getMember() != null && user.getMember().getGroup() != null && user.getMember().getGroup().getId() != null)
                ? user.getMember().getGroup().getId()
                : 0L;
        return new UserMemberResource(
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getImgUrl(),
                user.getEmail(),
                groupId
        );
    }
}
