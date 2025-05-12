package nrg.inc.synhubbackend.notifications.domain.model;

import java.util.Map;

public class UserPreferences {
    private boolean emailEnabled;
    private boolean pushEnabled;
    private Map<NotificationType, Boolean> typePreferences;

    public UserPreferences() {}

    public UserPreferences(boolean emailEnabled, boolean pushEnabled,
                           Map<NotificationType, Boolean> typePreferences) {
        this.emailEnabled = emailEnabled;
        this.pushEnabled = pushEnabled;
        this.typePreferences = typePreferences;
    }

    // Getters
    public boolean isEmailEnabled() {
        return emailEnabled;
    }

    public boolean isPushEnabled() {
        return pushEnabled;
    }

    public Map<NotificationType, Boolean> getTypePreferences() {
        return typePreferences;
    }

    // Métodos de negocio
    public boolean isNotificationTypeEnabled(NotificationType type) {
        return typePreferences.getOrDefault(type, true);
    }
}