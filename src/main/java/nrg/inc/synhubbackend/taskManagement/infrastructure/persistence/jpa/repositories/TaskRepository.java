package nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
