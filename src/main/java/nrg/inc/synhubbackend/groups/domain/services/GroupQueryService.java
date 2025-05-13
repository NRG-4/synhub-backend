package nrg.inc.synhubbackend.groups.domain.services;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Group;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByIdQuery;

import java.util.Optional;

public interface GroupQueryService {
    Optional<Group> handle(GetGroupByIdQuery query);


}
