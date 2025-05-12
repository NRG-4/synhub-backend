package nrg.inc.synhubbackend.notifications.domain.model;


public enum NotificationType {
    INFO("Informativa general"),
    TASK("Relacionado con tareas"),
    REMINDER("Recordatorio"),
    ALERT("Alerta importante");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}