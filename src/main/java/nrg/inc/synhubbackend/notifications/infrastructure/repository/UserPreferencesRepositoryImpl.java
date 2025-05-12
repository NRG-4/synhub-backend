package nrg.inc.synhubbackend.notifications.infrastructure.repository;


import nrg.inc.synhubbackend.notifications.domain.model.NotificationType;
import nrg.inc.synhubbackend.notifications.domain.model.UserPreferences;
import nrg.inc.synhubbackend.notifications.domain.repository.UserPreferencesRepository;
import org.springframework.stereotype.Repository;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserPreferencesRepositoryImpl implements UserPreferencesRepository {
    private final ConcurrentHashMap<Long, UserPreferences> storage = new ConcurrentHashMap<>();

    @Override
    public UserPreferences findByUserId(Long userId) {
        return storage.getOrDefault(userId, getDefaultPreferences());
    }

    @Override
    public void update(Long userId, UserPreferences preferences) {
        storage.put(userId, preferences);
    }

    private UserPreferences getDefaultPreferences() {
        Map<NotificationType, Boolean> defaultTypePrefs = new EnumMap<>(NotificationType.class);
        defaultTypePrefs.put(NotificationType.INFO, true);
        defaultTypePrefs.put(NotificationType.TASK, true);
        defaultTypePrefs.put(NotificationType.REMINDER, true);
        defaultTypePrefs.put(NotificationType.ALERT, true);

        return new UserPreferences(true, true, defaultTypePrefs);
    }
}