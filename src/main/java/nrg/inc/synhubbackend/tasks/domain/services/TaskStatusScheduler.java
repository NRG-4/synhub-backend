package nrg.inc.synhubbackend.tasks.domain.services;

import nrg.inc.synhubbackend.tasks.domain.model.valueobjects.TaskStatus;
import nrg.inc.synhubbackend.tasks.infrastructure.persistence.jpa.repositories.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class TaskStatusScheduler {
    private final TaskRepository taskRepository;

    public TaskStatusScheduler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Scheduled(fixedRate = 30000) // Ejecuta cada 60 segundos
    @Transactional
    public void updateExpiredTasks() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        taskRepository.findAllByStatusAndDueDateBefore(TaskStatus.IN_PROGRESS, now)
                .forEach(task -> {
                    task.setStatus(TaskStatus.EXPIRED);
                    taskRepository.save(task);
                });
    }
}
