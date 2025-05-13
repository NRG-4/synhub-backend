package nrg.inc.synhubbackend.taskManagement.domain.model.valueobjects;

public enum Task_Status {
    ON_HOLD,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    public static Task_Status fromString(String status) {
        for (Task_Status taskStatus : Task_Status.values()) {
            if (taskStatus.name().equalsIgnoreCase(status)) {
                return taskStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
