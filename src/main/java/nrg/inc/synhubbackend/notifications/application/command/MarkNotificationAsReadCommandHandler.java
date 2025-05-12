package nrg.inc.synhubbackend.notifications.application.command;

import org.springframework.stereotype.Component;

@Component
class MarkNotificationAsReadCommandHandler {
    private final NotificationCommandService notificationCommandService;

    public MarkNotificationAsReadCommandHandler(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    public void handle(Long notificationId) {
        notificationCommandService.markAsRead(notificationId);
    }
}