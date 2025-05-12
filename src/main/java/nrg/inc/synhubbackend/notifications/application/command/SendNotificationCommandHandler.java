package nrg.inc.synhubbackend.notifications.application.command;

import nrg.inc.synhubbackend.notifications.domain.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class SendNotificationCommandHandler {
    private final NotificationCommandService notificationCommandService;

    public SendNotificationCommandHandler(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    public void handle(Notification notification) {
        notificationCommandService.send(notification);
    }
}