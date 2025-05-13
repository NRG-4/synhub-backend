package nrg.inc.synhubbackend.taskManagement.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateTaskCommand;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.UpdateTaskCommand;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.UpdateTaskStatusCommand;
import nrg.inc.synhubbackend.taskManagement.domain.model.valueobjects.TaskStatus;

import java.security.Timestamp;
import java.sql.Time;
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
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NonNull
    private Integer timesRearranged;

    @NonNull
    private Long timePassed;

    public Task() {
        this.status = TaskStatus.IN_PROGRESS;
        this.timesRearranged = 0;
    }

    public Task(CreateTaskCommand command) {
        this.title = command.title();
        this.description = command.description();
        this.dueDate = command.dueDate();
    }

    public void updateStatus(UpdateTaskStatusCommand command) {
        if(this.status == TaskStatus.IN_PROGRESS && command.status() == TaskStatus.COMPLETED.toString()) {
            if(timesRearranged > 0){
                long updatedAt = this.getUpdatedAt().getTime();
                this.timePassed += new Date().getTime() - updatedAt;
            }else {
                this.timePassed = new Date().getTime() - this.getCreatedAt().getTime();
            }

        } else if(this.status == TaskStatus.COMPLETED && command.status() == TaskStatus.IN_PROGRESS.toString()) {
            timesRearranged++;
        }
        this.status = TaskStatus.valueOf(command.status());
    }

    public void updateTask(UpdateTaskCommand command) {
        if(command.title() != null) {
            this.title = command.title();
        }
        if(command.description() != null) {
            this.description = command.description();
        }
        if(command.dueDate() != null) {
            this.dueDate = command.dueDate();
        }
    }
}
