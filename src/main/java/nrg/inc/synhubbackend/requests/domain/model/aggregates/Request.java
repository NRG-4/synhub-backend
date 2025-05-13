package nrg.inc.synhubbackend.requests.domain.model.aggregates;

import jakarta.persistence.Entity;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
public class Request extends AuditableAbstractAggregateRoot<Request> {
    private String description;

    //TODO: Make this a reference to a RequestType entity
    private String type;

    //TODO: Make this a reference to a RequestStatus entity
    private String status;

    private int taskId;

    private int memberId;
}
