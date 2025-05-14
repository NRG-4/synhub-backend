package nrg.inc.synhubbackend.taskManagement.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateMemberCommand;

import java.util.List;

@Getter
@Entity
public class Member extends AuditableAbstractAggregateRoot<Member> {

    private String name;
    @OneToMany(mappedBy = "member")
    private List<Task> tasks;
    public Member() {}

    public Member(CreateMemberCommand command) {
        this.name = command.name();
    }
}
