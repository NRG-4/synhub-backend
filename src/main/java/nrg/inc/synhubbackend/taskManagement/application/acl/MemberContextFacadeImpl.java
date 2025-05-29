package nrg.inc.synhubbackend.taskManagement.application.acl;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories.MemberRepository;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.acl.MemberContextFacade;

import java.util.Optional;

public class MemberContextFacadeImpl implements MemberContextFacade {

    private final MemberRepository memberRepository;

    public MemberContextFacadeImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> createMember() {
        var member = new Member();
        var createdMember = memberRepository.save(member);
        return Optional.of(createdMember);
    }
}
