package nrg.inc.synhubbackend.taskManagement.application;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateMemberCommand;
import nrg.inc.synhubbackend.taskManagement.domain.services.MemberCommandService;
import nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;

    public MemberCommandServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public Optional<Member> handle(CreateMemberCommand command) {
        var member = new Member(command);
        var createdMember = memberRepository.save(member);
        return Optional.of(createdMember);
    }
}
