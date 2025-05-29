package nrg.inc.synhubbackend.taskManagement.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.synhubbackend.groups.domain.model.aggregates.Group;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateMemberCommand;

import java.util.List;

@Getter
@Setter
@Entity
public class Member extends AuditableAbstractAggregateRoot<Member> {


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;

    @OneToMany(mappedBy = "member")
    private List<Task> tasks;
  
    public Member() {}

    public Member(CreateMemberCommand command) {
    }
}
