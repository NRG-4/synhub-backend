package nrg.inc.synhubbackend.tasks.domain.model.commands;

import java.util.Date;

public record CreateTaskCommand(
        String title,
        String description,
        Date dueDate,
        Long memberId
) {
}
