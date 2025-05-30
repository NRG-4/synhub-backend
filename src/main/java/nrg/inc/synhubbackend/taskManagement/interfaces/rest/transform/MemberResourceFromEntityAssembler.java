package nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform;

import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.MemberResource;

public class MemberResourceFromEntityAssembler {
    public static MemberResource toResourceFromEntity(User entity) {
        return new MemberResource(
                entity.getUsername(),
                entity.getName(),
                entity.getSurname(),
                entity.getImgUrl(),
                entity.getEmail()
        );
    }
}
