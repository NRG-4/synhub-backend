package nrg.inc.synhubbackend.notifications.application.event;

import nrg.inc.synhubbackend.notifications.application.command.SendNotificationCommandHandler;
import nrg.inc.synhubbackend.notifications.domain.model.Notification;
import nrg.inc.synhubbackend.notifications.domain.model.NotificationType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class GroupJoinedEventHandler {
    private final SendNotificationCommandHandler sendNotificationCommandHandler;

    public GroupJoinedEventHandler(SendNotificationCommandHandler sendNotificationCommandHandler) {
        this.sendNotificationCommandHandler = sendNotificationCommandHandler;
    }

    @TransactionalEventListener
    public void handleGroupJoinedEvent(GroupJoinedEvent event) {
        // Notificar al usuario que se unió
        Notification userNotification = new Notification(
                event.getUserId(),
                "Bienvenido al grupo",
                "Te has unido al grupo: " + event.getGroupName(),
                NotificationType.INFO
        );
        sendNotificationCommandHandler.handle(userNotification);

        // Notificar al coordinador (si es diferente al usuario)
        if (event.getCoordinatorId() != null && !event.getCoordinatorId().equals(event.getUserId())) {
            Notification coordinatorNotification = new Notification(
                    event.getCoordinatorId(),
                    "Nuevo miembro en el grupo",
                    event.getUserName() + " se ha unido al grupo " + event.getGroupName(),
                    NotificationType.INFO
            );
            sendNotificationCommandHandler.handle(coordinatorNotification);
        }
    }
}

// Evento necesario para el manejador
class GroupJoinedEvent {
    private Long userId;
    private String userName;
    private Long coordinatorId;
    private String groupName;

    public GroupJoinedEvent(Long userId, String userName, Long coordinatorId, String groupName) {
        this.userId = userId;
        this.userName = userName;
        this.coordinatorId = coordinatorId;
        this.groupName = groupName;
    }

    // Getters
    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getCoordinatorId() {
        return coordinatorId;
    }

    public String getGroupName() {
        return groupName;
    }
}