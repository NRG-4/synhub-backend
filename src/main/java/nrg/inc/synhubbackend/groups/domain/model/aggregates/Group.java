package nrg.inc.synhubbackend.groups.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import nrg.inc.synhubbackend.groups.domain.model.valueobjects.ImgUrl;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;

import java.util.List;

@Entity
@NoArgsConstructor
public class Group extends AuditableAbstractAggregateRoot<Group> {

    private String name;

    @Embedded
    private ImgUrl imgUrl;

    @OneToOne
    @JoinColumn(name = "leader_id")
    private Leader leader;

    @OneToMany(mappedBy = "group")
    private List<Member> members;



}
