package nrg.inc.synhubbackend.notifications.domain.repository;


import nrg.inc.synhubbackend.notifications.domain.model.UserPreferences;

public interface UserPreferencesRepository {
    UserPreferences findByUserId(Long userId);
    void update(Long userId, UserPreferences preferences);
}