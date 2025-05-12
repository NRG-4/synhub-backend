package nrg.inc.synhubbackend.notifications.domain.repository;


import nrg.inc.synhubbackend.notifications.domain.model.Notification;

import java.util.List;

public interface NotificationRepository {
    List<Notification> findByUserId(Long userId);
    List<Notification> findUnreadByUserId(Long userId);
    void save(Notification notification);
}