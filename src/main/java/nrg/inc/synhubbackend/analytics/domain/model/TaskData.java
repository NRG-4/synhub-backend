package nrg.inc.synhubbackend.analytics.domain.model;


import java.time.LocalDateTime;

public class TaskData {
    private final Long id;
    private final String title;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime dueDate;
    private final MemberData assignedMember;

    public TaskData(Long id, String title, String status,
                    LocalDateTime createdAt, LocalDateTime updatedAt,
                    LocalDateTime dueDate, MemberData assignedMember) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dueDate = dueDate;
        this.assignedMember = assignedMember;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDueDate() { return dueDate; }
    public MemberData getAssignedMember() { return assignedMember; }
}