package nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
