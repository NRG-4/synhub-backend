package nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform;

import nrg.inc.synhubbackend.taskManagement.domain.model.commands.UpdateTaskCommand;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.UpdateTaskResource;

public class UpdateTaskCommandFromResourceAssembler {
    public static UpdateTaskCommand toCommandFromResource(UpdateTaskResource command, Long taskId) {
        return new UpdateTaskCommand(
                taskId,
                command.title(),
                command.description(),
                command.dueDate(),
                command.memberId()
        );

    }
}
