package nrg.inc.synhubbackend.groups.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.sql.Time;

@Entity
@Getter
@Setter
public class Leader extends AuditableAbstractAggregateRoot<Leader> {

    Time averageSolutionTime;

    Integer solvedRequests;

    public Leader() {
        this.averageSolutionTime = new Time(0);
        this.solvedRequests = 0;
    }

}
