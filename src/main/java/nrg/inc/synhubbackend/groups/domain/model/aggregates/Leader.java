package nrg.inc.synhubbackend.groups.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class Leader extends AuditableAbstractAggregateRoot<Leader> {

    @NotNull
    String name;

}
