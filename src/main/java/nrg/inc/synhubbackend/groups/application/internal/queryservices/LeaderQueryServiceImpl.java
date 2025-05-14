package nrg.inc.synhubbackend.groups.application.internal.queryservices;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Leader;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetLeaderByIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.LeaderQueryService;
import nrg.inc.synhubbackend.groups.infrastructure.persistence.jpa.repositories.LeaderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeaderQueryServiceImpl implements LeaderQueryService {

    private final LeaderRepository leaderRepository;

    public LeaderQueryServiceImpl(LeaderRepository leaderRepository) {
        this.leaderRepository = leaderRepository;
    }

    @Override
    public Optional<Leader> handle(GetLeaderByIdQuery query) {
        return leaderRepository.findById(query.leaderId());
    }
}
