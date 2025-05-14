package nrg.inc.synhubbackend.analytics.infrastructure.client;

import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.MemberResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.TaskResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "taskManagementClient",
        url = "http://localhost:8080/api/v1" // Hardcoded base URL
)
public interface TaskManagementClient {

    @GetMapping("/tasks")
    List<TaskResource> getAllTasks();

    @GetMapping("/tasks/status/{status}")
    List<TaskResource> getTasksByStatus(@PathVariable String status);

    @GetMapping("/{memberId}/tasks")
    List<TaskResource> getTasksByMemberId(@PathVariable Long memberId);

    @GetMapping("/members/group/{groupId}")
    List<MemberResource> getMembersByGroupId(@PathVariable Long groupId);
}