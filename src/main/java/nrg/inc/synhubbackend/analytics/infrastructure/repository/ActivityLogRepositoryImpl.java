package nrg.inc.synhubbackend.analytics.infrastructure.repository;


import nrg.inc.synhubbackend.analytics.domain.model.ActivityLog;
import nrg.inc.synhubbackend.analytics.domain.repository.ActivityLogRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ActivityLogRepositoryImpl implements ActivityLogRepository {
    private final ConcurrentHashMap<Long, ActivityLog> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<ActivityLog> findByUserId(Long userId) {
        List<ActivityLog> userLogs = new ArrayList<>();
        for (ActivityLog log : storage.values()) {
            if (log.getUserId().equals(userId)) {
                userLogs.add(log);
            }
        }
        return userLogs;
    }

    @Override
    public void save(ActivityLog log) {
        if (log.getId() == null) {
            log.setId(idGenerator.getAndIncrement());
        }
        storage.put(log.getId(), log);
    }
}