package nrg.inc.synhubbackend.taskManagement.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import nrg.inc.synhubbackend.groups.domain.model.aggregates.Group;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateMemberCommand;

@Getter
@Entity
public class Member extends AuditableAbstractAggregateRoot<Member> {

    private String name;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Member() {}

    public Member(CreateMemberCommand command) {
        this.name = command.name();
    }
}
