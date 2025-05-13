package nrg.inc.synhubbackend.requests.domain.model.aggregates;

import jakarta.persistence.Entity;
import nrg.inc.synhubbackend.requests.domain.model.commands.CreateRequestCommand;
import nrg.inc.synhubbackend.requests.domain.model.valueobjects.RequestStatus;
import nrg.inc.synhubbackend.requests.domain.model.valueobjects.RequestType;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
public class Request extends AuditableAbstractAggregateRoot<Request> {
    private String description;

    private RequestType requestType;

    private RequestStatus requestStatus;

    private Long taskId;

    private Long memberId;

    public Request() {
        this.requestStatus = RequestStatus.PENDING;
        this.requestType = RequestType.TASK_SUBMISSION;
    }

    public Request(String description, RequestType requestType, RequestStatus requestStatus, Long taskId, Long memberId) {
        this.description = description;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.taskId = taskId;
        this.memberId = memberId;
    }

    public Request(CreateRequestCommand command) {
        this.description = command.description();
        this.requestType = RequestType.fromString(command.type());
        this.requestStatus = RequestStatus.PENDING;
        this.taskId = command.taskId();
        this.memberId = command.memberId();
    }
}
