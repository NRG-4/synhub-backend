package nrg.inc.synhubbackend.taskManagement.interfaces.acl;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;

import java.util.Optional;

public interface MemberContextFacade {
    Optional<Member> createMember();
}
