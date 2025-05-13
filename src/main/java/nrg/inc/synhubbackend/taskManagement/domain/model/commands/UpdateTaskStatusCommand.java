package nrg.inc.synhubbackend.taskManagement.domain.model.commands;

public record UpdateTaskStatusCommand(
        Long taskId,
        String status
) {
}
