package nrg.inc.synhubbackend.requests.domain.model.commands;

public record CreateRequestCommand(
        String description,
        String type,
        Long taskId,
        Long memberId
) {
}
