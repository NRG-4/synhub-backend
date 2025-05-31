package nrg.inc.synhubbackend.groups.interfaces.rest.resources;

public record UserLeaderResource(
        String username,
        String name,
        String surname,
        String imgUrl,
        String email,
        String averageSolutionTime,
        Integer solvedRequests
) {
}
