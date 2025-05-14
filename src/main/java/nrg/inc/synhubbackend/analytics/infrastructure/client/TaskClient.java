package nrg.inc.synhubbackend.analytics.infrastructure.client;

import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.TaskResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "task-service", url = "${task.service.url}")
public interface TaskClient {
    @GetMapping("/api/v1/tasks")
    List<TaskResource> getAllTasks();

    @GetMapping("/api/v1/tasks/status/{status}")
    List<TaskResource> getTasksByStatus(@PathVariable String status);

    @GetMapping("/api/v1/{memberId}/tasks")
    List<TaskResource> getTasksByMemberId(@PathVariable Long memberId);
}