package nrg.inc.synhubbackend.groups.interfaces.rest.transform;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Leader;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.LeaderResource;

public class LeaderResourceFromEntityAssembler {
    public static LeaderResource toResourceFromEntity(Leader leader) {
        return new LeaderResource(
                leader.getName()
        );
    }
}
