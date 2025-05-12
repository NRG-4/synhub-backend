package nrg.inc.synhubbackend.notifications.application.query;


import nrg.inc.synhubbackend.notifications.domain.model.Notification;
import nrg.inc.synhubbackend.notifications.domain.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationQueryService {
    private final NotificationRepository notificationRepository;

    public NotificationQueryService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> findUnreadByUserId(Long userId) {
        return notificationRepository.findUnreadByUserId(userId);
    }
}