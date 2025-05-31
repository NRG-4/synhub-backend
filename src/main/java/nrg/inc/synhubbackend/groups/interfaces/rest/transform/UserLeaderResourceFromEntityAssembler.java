package nrg.inc.synhubbackend.groups.interfaces.rest.transform;

import nrg.inc.synhubbackend.groups.interfaces.rest.resources.UserLeaderResource;
import nrg.inc.synhubbackend.iam.domain.model.aggregates.User;

public class UserLeaderResourceFromEntityAssembler {
    public static UserLeaderResource toResourceFromEntity(User user){
        return new UserLeaderResource(
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getImgUrl(),
                user.getEmail(),
                user.getLeader().getAverageSolutionTime().toString(), // Assuming averageSolutionTime is not available in User
                user.getLeader().getSolvedRequests()  // Assuming solvedRequests is not available in User
        );
    }
}
