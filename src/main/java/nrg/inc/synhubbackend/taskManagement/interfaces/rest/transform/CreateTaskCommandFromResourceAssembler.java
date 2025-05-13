package nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform;

import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateTaskCommand;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.CreateTaskResource;

public class CreateTaskCommandFromResourceAssembler {
    public static CreateTaskCommand toCommandFromResource(CreateTaskResource resource) {
        return new CreateTaskCommand(
                resource.title(),
                resource.description(),
                resource.dueDate(),
                resource.memberId()
        );
    }
}
