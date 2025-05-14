package nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources;

import java.util.Date;

public record CreateTaskResource(
        String title,
        String description,
        Date dueDate
) {
}
