package nrg.inc.synhubbackend.taskManagement.application.external.outboundedservices;

import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalIamService {
    private final IamContextFacade iamContextFacade;

    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }
    public Optional<User> getUserByMemberId(Long memberId) {
        var user = this.iamContextFacade.fetchByMemberId(memberId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found for memberId: " + memberId);
        }
        return user;
    }
}
