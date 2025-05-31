package nrg.inc.synhubbackend.groups.domain.services;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Leader;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetLeaderByIdQuery;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetUserLeaderByIdQuery;
import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;

import java.util.Optional;

public interface LeaderQueryService {
    Optional<Leader> handle(GetLeaderByIdQuery query);
    Optional<User> handle(GetUserLeaderByIdQuery query);
}
