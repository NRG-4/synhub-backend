package nrg.inc.synhubbackend.iam.application.external.outboundedservices;

import nrg.inc.synhubbackend.iam.domain.model.commands.CreateUserMemberCommand;
import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import nrg.inc.synhubbackend.taskManagement.interfaces.acl.MemberContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalMemberService {
    private final MemberContextFacade memberContextFacade;

    public ExternalMemberService(MemberContextFacade memberContextFacade) {
        this.memberContextFacade = memberContextFacade;
    }


    public Optional<Member> createUserMember(CreateUserMemberCommand command){
        var member = this.memberContextFacade.createMember();
        if(member.isEmpty()) {
            throw new IllegalArgumentException("Error creating member");
        }
        return member;
    }
}
