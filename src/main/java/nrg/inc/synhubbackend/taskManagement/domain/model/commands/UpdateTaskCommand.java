package nrg.inc.synhubbackend.taskManagement.domain.model.commands;

import java.util.Date;

public record UpdateTaskCommand(
        Long taskId,
        String title,
        String description,
        Date dueDate,
        Long memberId

) {
}
