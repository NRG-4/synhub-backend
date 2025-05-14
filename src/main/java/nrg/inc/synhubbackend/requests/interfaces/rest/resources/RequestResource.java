package nrg.inc.synhubbackend.requests.interfaces.rest.resources;

public record RequestResource(
        Long id,
        String description,
        String requestType,
        String requestStatus,
        Long taskId,
        Long memberId
) {
}
