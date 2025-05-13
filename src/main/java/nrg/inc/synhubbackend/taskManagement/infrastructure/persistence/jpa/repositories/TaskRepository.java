package nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.taskManagement.domain.model.valueobjects.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByMemberId(Long memberId);
    Optional<Task> findByStatus(TaskStatus status);
}
