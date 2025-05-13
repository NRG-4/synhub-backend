package nrg.inc.synhubbackend.taskManagement.domain.model.valueobjects;

public enum TaskStatus {
    ON_HOLD,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    public static TaskStatus fromString(String status) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.name().equalsIgnoreCase(status)) {
                return taskStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
