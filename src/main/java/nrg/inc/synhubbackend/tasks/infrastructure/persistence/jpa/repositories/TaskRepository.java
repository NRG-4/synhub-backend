package nrg.inc.synhubbackend.tasks.infrastructure.persistence.jpa.repositories;

import nrg.inc.synhubbackend.tasks.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.tasks.domain.model.valueobjects.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByMemberId(Long memberId);
    List<Task> findByStatus(TaskStatus status);
}
