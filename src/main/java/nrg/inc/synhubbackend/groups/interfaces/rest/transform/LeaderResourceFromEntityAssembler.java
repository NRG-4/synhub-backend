package nrg.inc.synhubbackend.groups.interfaces.rest.transform;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Leader;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.LeaderResource;
import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;

public class LeaderResourceFromEntityAssembler {
    public static LeaderResource toResourceFromEntity(User user, Leader leader) {
        return new LeaderResource(
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getImgUrl(),
                user.getEmail(),
                leader.getAverageSolutionTime().toString(),
                leader.getSolvedRequests()
        );
    }
}
