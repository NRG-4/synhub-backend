package nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
