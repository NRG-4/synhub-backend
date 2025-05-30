package nrg.inc.synhubbackend.taskManagement.application.internal.queryservices;

import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import nrg.inc.synhubbackend.taskManagement.application.external.outboundedservices.ExternalIamService;
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
    private final ExternalIamService externalIamService;

    public MemberQueryServiceImpl(MemberRepository memberRepository, ExternalIamService externalIamService) {
        this.memberRepository = memberRepository;
        this.externalIamService = externalIamService;
    }

    @Override
    public Optional<User> handle(GetMemberByIdQuery query) {
        return this.externalIamService.getUserByMemberId(query.memberId());
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
