package nrg.inc.synhubbackend.taskManagement.domain.model.commands;

public record CreateTaskCommand(
        String title,
        String description,
        String status,
        String dueDate,
        Long memberId
) {
}
