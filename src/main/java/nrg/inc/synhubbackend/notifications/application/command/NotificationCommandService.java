package nrg.inc.synhubbackend.notifications.application.command;

import nrg.inc.synhubbackend.notifications.domain.model.Notification;
import nrg.inc.synhubbackend.notifications.domain.model.User;
import nrg.inc.synhubbackend.notifications.domain.model.UserPreferences;
import nrg.inc.synhubbackend.notifications.domain.repository.NotificationRepository;
import nrg.inc.synhubbackend.notifications.domain.repository.UserPreferencesRepository;
import nrg.inc.synhubbackend.notifications.domain.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationCommandService {
    private final NotificationRepository notificationRepository;
    private final UserPreferencesRepository userPreferencesRepository;
    private final NotificationService notificationService;

    public NotificationCommandService(NotificationRepository notificationRepository,
                                      UserPreferencesRepository userPreferencesRepository,
                                      NotificationService notificationService) {
        this.notificationRepository = notificationRepository;
        this.userPreferencesRepository = userPreferencesRepository;
        this.notificationService = notificationService;
    }

    public void send(Notification notification) {
        UserPreferences preferences = userPreferencesRepository.findByUserId(notification.getUserId());
        User user = new User(notification.getUserId(), null, preferences);

        notificationService.sendNotification(notification, user);
        notificationRepository.save(notification);
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findByUserId(notificationId).stream()
                .filter(n -> n.getId().equals(notificationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        notification.markAsRead();
        notificationRepository.save(notification);
    }
}