package nrg.inc.synhubbackend.requests.interfaces.rest.transform;

import nrg.inc.synhubbackend.requests.domain.model.commands.UpdateRequestCommand;
import nrg.inc.synhubbackend.requests.interfaces.rest.resources.RequestResource;

public class UpdateRequestCommandFromResourceAssembler {
    public static UpdateRequestCommand toCommandFromResource(Long requestId, RequestResource resource) {
        return new UpdateRequestCommand(requestId, resource.requestStatus());
    }
}
