package nrg.inc.synhubbackend.analytics.application.event;

import nrg.inc.synhubbackend.analytics.domain.model.ActivityLog;
import nrg.inc.synhubbackend.analytics.domain.repository.ActivityLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TaskCompletedEventHandler {
    private final ActivityLogRepository activityLogRepository;

    public TaskCompletedEventHandler(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @TransactionalEventListener
    public void handleTaskCompletedEvent(TaskCompletedEvent event) {
        ActivityLog log = new ActivityLog(
                event.getUserId(),
                "task_completed",
                "taskId=" + event.getTaskId()
        );
        log.createLog();
        activityLogRepository.save(log);
    }
}

// Evento necesario para el manejador
class TaskCompletedEvent {
    private Long userId;
    private Long taskId;

    public TaskCompletedEvent(Long userId, Long taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    // Getters
    public Long getUserId() {
        return userId;
    }

    public Long getTaskId() {
        return taskId;
    }
}