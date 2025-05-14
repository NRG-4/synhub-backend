package nrg.inc.synhubbackend.analytics.domain.repository;


import nrg.inc.synhubbackend.analytics.domain.model.ActivityLog;

import java.util.List;

public interface ActivityLogRepository {
    List<ActivityLog> findByUserId(Long userId);
    void save(ActivityLog log);
}