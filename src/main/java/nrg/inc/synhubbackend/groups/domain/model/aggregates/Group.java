package nrg.inc.synhubbackend.groups.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nrg.inc.synhubbackend.groups.domain.model.valueobjects.ImgUrl;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Group extends AuditableAbstractAggregateRoot<Group> {

    @NotNull
    private String name;

    @Embedded
    private ImgUrl imgUrl;

    @OneToOne
    @JoinColumn(name = "leader_id")
    private Leader leader;

    public Group(String name, String imgUrl , Leader leader) {
        this.name = name;
        this.imgUrl = new ImgUrl(imgUrl);
        this.leader = leader;
    }



}
