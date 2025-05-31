package nrg.inc.synhubbackend.tasks.interfaces.acl;

import nrg.inc.synhubbackend.tasks.domain.model.aggregates.Member;

import java.util.Optional;

public interface MemberContextFacade {
    Optional<Member> createMember();
}
