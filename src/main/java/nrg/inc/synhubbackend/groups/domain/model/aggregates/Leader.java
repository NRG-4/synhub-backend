package nrg.inc.synhubbackend.groups.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
public class Leader extends AuditableAbstractAggregateRoot<Leader> {

    @NotNull
    String name;

}
