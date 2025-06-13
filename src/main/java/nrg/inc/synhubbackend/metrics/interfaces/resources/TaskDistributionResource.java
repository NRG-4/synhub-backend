package nrg.inc.synhubbackend.metrics.interfaces.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class TaskDistributionResource {
    private String type;
    private int value;
    private Map<String, MemberTaskInfo> details;

    @Getter
    @AllArgsConstructor
    public static class MemberTaskInfo {
        private String memberName;
        private int taskCount;
    }
}
