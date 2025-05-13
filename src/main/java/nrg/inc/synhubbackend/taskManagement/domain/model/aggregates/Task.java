package nrg.inc.synhubbackend.taskManagement.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.shared.domain.model.entities.AuditableModel;
import nrg.inc.synhubbackend.taskManagement.domain.model.valueobjects.Task_Status;

import java.security.Timestamp;
import java.util.Date;

@Getter
@Entity
public class Task extends AuditableAbstractAggregateRoot<Task> {
    @NonNull
    private String title;

    @NonNull
    private String description;

    @Enumerated(EnumType.STRING)
    private Task_Status status;

    @NonNull
    private Date dueDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @NonNull
    private Date createdOn;

    @NonNull
    private Timestamp time_passed;

    public Task() {
        this.status = Task_Status.IN_PROGRESS;
        this.createdOn = new Date();
    }
}
