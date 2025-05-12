package nrg.inc.synhubbackend.notifications.infrastructure.repository;


import nrg.inc.synhubbackend.notifications.domain.model.Notification;
import nrg.inc.synhubbackend.notifications.domain.repository.NotificationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
    private final ConcurrentHashMap<Long, Notification> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Notification> findByUserId(Long userId) {
        return storage.values().stream()
                .filter(notification -> notification.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findUnreadByUserId(Long userId) {
        return storage.values().stream()
                .filter(notification -> notification.getUserId().equals(userId))
                .filter(notification -> !notification.isRead())
                .collect(Collectors.toList());
    }

    @Override
    public void save(Notification notification) {
        if (notification.getId() == null) {
            notification.setId(idGenerator.getAndIncrement());
        }
        storage.put(notification.getId(), notification);
    }
}