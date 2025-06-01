package nrg.inc.synhubbackend.tasks.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.synhubbackend.groups.domain.model.aggregates.Group;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.tasks.domain.model.commands.CreateMemberCommand;

import java.util.List;

@Getter
@Setter
@Entity
public class Member extends AuditableAbstractAggregateRoot<Member> {

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;

    @OneToMany(mappedBy = "member")
    private List<Task> tasks;
  
    public Member() {}

    public Member(CreateMemberCommand command) {
    }
}
