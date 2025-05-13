package nrg.inc.synhubbackend.requests.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import nrg.inc.synhubbackend.requests.domain.model.commands.CreateRequestCommand;
import nrg.inc.synhubbackend.requests.domain.model.valueobjects.RequestStatus;
import nrg.inc.synhubbackend.requests.domain.model.valueobjects.RequestType;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
@Table(name = "requests")
public class Request extends AuditableAbstractAggregateRoot<Request> {
    @NonNull
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private RequestStatus requestStatus;

    @NonNull
    @Column(name = "task_id")
    private Long taskId;

    @NonNull
    @Column(name = "member_id")
    private Long memberId;

    public Request() {
    }

    public String getRequestType() {
        return requestType.toString();
    }

    public String getRequestStatus() {
        return requestStatus.toString();
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
        this.requestType = RequestType.fromString(command.requestType());
        this.requestStatus = RequestStatus.PENDING;
        this.taskId = command.taskId();
        this.memberId = command.memberId();
    }

    public Request updateRequestStatus(String requestStatus) {
        this.requestStatus = RequestStatus.fromString(requestStatus);
        return this;
    }
}
