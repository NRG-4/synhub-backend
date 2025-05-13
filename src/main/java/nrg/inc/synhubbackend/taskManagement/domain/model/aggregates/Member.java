package nrg.inc.synhubbackend.taskManagement.domain.model.aggregates;

import jakarta.persistence.Entity;
import lombok.Getter;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateMemberCommand;

@Getter
@Entity
public class Member extends AuditableAbstractAggregateRoot<Member> {

    private String name;

    public Member() {}

    public Member(CreateMemberCommand command) {
        this.name = command.name();
    }
}
