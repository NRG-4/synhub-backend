package nrg.inc.synhubbackend.groups.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nrg.inc.synhubbackend.groups.domain.model.valueobjects.ImgUrl;
import nrg.inc.synhubbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Group extends AuditableAbstractAggregateRoot<Group> {

    @NotNull
    private String name;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;

    @Embedded
    private ImgUrl imgUrl;

    @OneToOne
    @JoinColumn(name = "leader_id")
    private Leader leader;

    @NotNull
    private Integer memberCount;

    public Group(String name, String imgUrl , Leader leader, String description, Integer memberCount) {
        this.name = name;
        this.imgUrl = new ImgUrl(imgUrl);
        this.leader = leader;
        this.description = description;
        this.memberCount = memberCount;
    }



}
