package nrg.inc.synhubbackend.requests.interfaces.rest.transform;

import nrg.inc.synhubbackend.requests.domain.model.aggregates.Request;
import nrg.inc.synhubbackend.requests.interfaces.rest.resources.RequestResource;

public class RequestResourceFromEntityAssembler {
    public static RequestResource toResourceFromEntity(Request entity) {
        return new RequestResource(
                entity.getId(),
                entity.getDescription(),
                entity.getRequestType().toString(),
                entity.getRequestStatus().toString(),
                entity.getTaskId(),
                entity.getMemberId()
        );
    }
}
