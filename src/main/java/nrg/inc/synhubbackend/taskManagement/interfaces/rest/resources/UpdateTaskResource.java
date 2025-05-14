package nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources;

import java.util.Date;

public record UpdateTaskResource(
        String title,
        String description,
        Date dueDate,
        Long memberId
) {
}
