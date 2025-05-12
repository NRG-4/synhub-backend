package nrg.inc.synhubbackend.analytics.domain.model;

import java.time.LocalDateTime;

public class ActivityLog {
    private Long id;
    private Long userId;
    private String actionType;
    private LocalDateTime timestamp;
    private String metadata;

    public ActivityLog() {}

    public ActivityLog(Long userId, String actionType, String metadata) {
        this.userId = userId;
        this.actionType = actionType;
        this.metadata = metadata;
        this.timestamp = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    // Métodos de dominio
    public void createLog() {
        this.timestamp = LocalDateTime.now();
    }

    public String getSummary() {
        return String.format("User %d performed action '%s' at %s",
                userId, actionType, timestamp.toString());
    }
}