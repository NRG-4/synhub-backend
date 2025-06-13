package nrg.inc.synhubbackend.metrics.domain.model.aggregates;

public class GroupMetrics {
    private Long groupId;
    private int memberCount;

    public GroupMetrics(Long groupId, int memberCount) {
        this.groupId = groupId;
        this.memberCount = memberCount;
    }

    public Long getGroupId() {
        return groupId;
    }

    public int getMemberCount() {
        return memberCount;
    }
}