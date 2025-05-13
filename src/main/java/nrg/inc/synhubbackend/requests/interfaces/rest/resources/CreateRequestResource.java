package nrg.inc.synhubbackend.requests.interfaces.rest.resources;

public record CreateRequestResource(
        String description,
        String requestType,
        Long taskId,
        Long  memberId
) {
}
