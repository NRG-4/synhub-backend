package nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources;

import java.util.Date;


public record TaskResource(
        Long id,
        String title,
        String description,
        String dueDate,
        String createdOn,
        String status,
        TaskMemberResource member
) {
}
