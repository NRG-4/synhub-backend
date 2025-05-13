package nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform;

import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateMemberCommand;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.CreateMemberResource;

public class CreateMemberCommandFromResourceAssembler {
    public static CreateMemberCommand toCommandFromResource(CreateMemberResource resource) {
        return new CreateMemberCommand(
                resource.name()
        );
    }
}
