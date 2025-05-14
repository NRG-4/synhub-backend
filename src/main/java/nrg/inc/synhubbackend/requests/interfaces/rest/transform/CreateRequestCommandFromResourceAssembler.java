package nrg.inc.synhubbackend.requests.interfaces.rest.transform;

import nrg.inc.synhubbackend.requests.domain.model.commands.CreateRequestCommand;
import nrg.inc.synhubbackend.requests.interfaces.rest.resources.CreateRequestResource;

public class CreateRequestCommandFromResourceAssembler {
    public static CreateRequestCommand toCommandFromResource(CreateRequestResource resource) {
        return new CreateRequestCommand(
                resource.description(),
                resource.requestType(),
                resource.taskId(),
                resource.memberId()
        );
    }
}
