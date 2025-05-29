package nrg.inc.synhubbackend.taskManagement.application.internal.queryservices;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllMembersQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetMemberByIdQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetMembersByGroupIdQuery;
import nrg.inc.synhubbackend.taskManagement.domain.services.MemberQueryService;
import nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberQueryServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> handle(GetMemberByIdQuery query) {
        return this.memberRepository.findById(query.memberId());
    }

    @Override
    public List<Member> handle(GetAllMembersQuery query) {
        return this.memberRepository.findAll();
    }

    @Override
    public List<Member> handle(GetMembersByGroupIdQuery query) {
        return memberRepository.findMembersByGroup_Id(query.groupId());
    }
}
