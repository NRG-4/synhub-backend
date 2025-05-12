package nrg.inc.synhubbackend.notifications.rest.controller;

import nrg.inc.synhubbackend.notifications.application.command.NotificationCommandService;
import nrg.inc.synhubbackend.notifications.application.query.NotificationQueryService;
import nrg.inc.synhubbackend.notifications.domain.model.Notification;
import nrg.inc.synhubbackend.notifications.domain.model.NotificationType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationQueryService notificationQueryService;
    private final NotificationCommandService notificationCommandService;

    public NotificationController(NotificationQueryService notificationQueryService,
                                  NotificationCommandService notificationCommandService) {
        this.notificationQueryService = notificationQueryService;
        this.notificationCommandService = notificationCommandService;
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationQueryService.findByUserId(userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<Notification> getUserUnreadNotifications(@PathVariable Long userId) {
        return notificationQueryService.findUnreadByUserId(userId);
    }

    @PostMapping("/mark-as-read/{notificationId}")
    public void markAsRead(@PathVariable Long notificationId) {
        notificationCommandService.markAsRead(notificationId);
    }

    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationRequest request) {
        Notification notification = new Notification(
                request.getUserId(),
                request.getTitle(),
                request.getMessage(),
                request.getType()
        );
        notificationCommandService.send(notification);
    }
}

// DTO para recibir las solicitudes
class NotificationRequest {
    private Long userId;
    private String title;
    private String message;
    private NotificationType type;

    // Getters y Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}