package nrg.inc.synhubbackend.notifications.application.event;


import nrg.inc.synhubbackend.notifications.application.command.SendNotificationCommandHandler;
import nrg.inc.synhubbackend.notifications.domain.model.Notification;
import nrg.inc.synhubbackend.notifications.domain.model.NotificationType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TaskAssignedEventHandler {
    private final SendNotificationCommandHandler sendNotificationCommandHandler;

    public TaskAssignedEventHandler(SendNotificationCommandHandler sendNotificationCommandHandler) {
        this.sendNotificationCommandHandler = sendNotificationCommandHandler;
    }

    @TransactionalEventListener
    public void handleTaskAssignedEvent(TaskAssignedEvent event) {
        Notification notification = new Notification(
                event.getAssignedUserId(),
                "Nueva tarea asignada",
                "Se te ha asignado la tarea: " + event.getTaskTitle(),
                NotificationType.TASK
        );

        sendNotificationCommandHandler.handle(notification);
    }
}

// Evento necesario para el manejador
class TaskAssignedEvent {
    private Long assignedUserId;
    private String taskTitle;

    public TaskAssignedEvent(Long assignedUserId, String taskTitle) {
        this.assignedUserId = assignedUserId;
        this.taskTitle = taskTitle;
    }

    // Getters
    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }
}