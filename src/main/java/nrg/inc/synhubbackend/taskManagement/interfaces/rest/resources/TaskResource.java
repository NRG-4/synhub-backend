package nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources;

import java.util.Date;

public record TaskResource(
        Long id,
        String title,
        String description,
        Date dueDate,
        Date createdOn,
        String status,
        MemberResource member
) {
}
