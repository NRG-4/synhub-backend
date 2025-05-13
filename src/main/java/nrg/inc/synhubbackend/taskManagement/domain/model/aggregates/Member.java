package nrg.inc.synhubbackend.taskManagement.domain.model.aggregates;

import jakarta.persistence.Entity;
import lombok.Getter;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
public class Member extends AuditableAbstractAggregateRoot<Member> {

    private String name;

    public Member() {}
}
