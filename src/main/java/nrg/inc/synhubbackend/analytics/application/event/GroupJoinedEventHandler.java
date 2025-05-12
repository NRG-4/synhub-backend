package nrg.inc.synhubbackend.analytics.application.event;

import nrg.inc.synhubbackend.analytics.domain.model.ActivityLog;
import nrg.inc.synhubbackend.analytics.domain.repository.ActivityLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class GroupJoinedEventHandler {
    private final ActivityLogRepository activityLogRepository;

    public GroupJoinedEventHandler(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @TransactionalEventListener
    public void handleGroupJoinedEvent(GroupJoinedEvent event) {
        ActivityLog log = new ActivityLog(
                event.getUserId(),
                "group_joined",
                "groupId=" + event.getGroupId()
        );
        log.createLog();
        activityLogRepository.save(log);
    }
}

class GroupJoinedEvent {
    private Long userId;
    private Long groupId;

    public GroupJoinedEvent(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGroupId() {
        return groupId;
    }
}