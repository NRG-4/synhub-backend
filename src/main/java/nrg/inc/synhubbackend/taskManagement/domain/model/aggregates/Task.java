package nrg.inc.synhubbackend.taskManagement.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateTaskCommand;
import nrg.inc.synhubbackend.taskManagement.domain.model.valueobjects.TaskStatus;

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
    private TaskStatus status;

    @NonNull
    private Date dueDate;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @NonNull
    private Date createdOn;

    @NonNull
    private Timestamp time_passed;

    public Task() {
        this.status = TaskStatus.IN_PROGRESS;
        this.createdOn = new Date();
    }

    public Task(CreateTaskCommand command) {
        this.title = command.title();
        this.description = command.description();
        this.status = TaskStatus.IN_PROGRESS;
        this.dueDate = command.dueDate();
        this.createdOn = new Date();
    }
}
