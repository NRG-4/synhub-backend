package nrg.inc.synhubbackend.groups.application.internal.queryservices;

import nrg.inc.synhubbackend.shared.application.external.outboundedservices.ExternalIamService;
import nrg.inc.synhubbackend.groups.domain.model.aggregates.Leader;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetLeaderByIdQuery;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetUserLeaderByIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.LeaderQueryService;
import nrg.inc.synhubbackend.groups.infrastructure.persistence.jpa.repositories.LeaderRepository;
import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeaderQueryServiceImpl implements LeaderQueryService {

    private final LeaderRepository leaderRepository;
    private final ExternalIamService externalIamService;

    public LeaderQueryServiceImpl(LeaderRepository leaderRepository, ExternalIamService externalIamService) {
        this.leaderRepository = leaderRepository;
        this.externalIamService = externalIamService;
    }

    @Override
    public Optional<Leader> handle(GetLeaderByIdQuery query) {
        return leaderRepository.findById(query.leaderId());
    }

    @Override
    public Optional<User> handle(GetUserLeaderByIdQuery query) {
        return externalIamService.getUserById(query.userId());
    }
}
