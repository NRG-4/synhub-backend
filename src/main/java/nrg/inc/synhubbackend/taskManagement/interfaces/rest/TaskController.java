package nrg.inc.synhubbackend.taskManagement.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllTaskByStatusQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllTasksQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetTaskByIdQuery;
import nrg.inc.synhubbackend.taskManagement.domain.services.TaskCommandService;
import nrg.inc.synhubbackend.taskManagement.domain.services.TaskQueryService;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.CreateTaskResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.TaskResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform.CreateTaskCommandFromResourceAssembler;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform.TaskResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/tasks")
@Tag(name = "Task", description = "Task management API")
@ApiResponse(responseCode = "201", description = "Task Created")
public class TaskController {

    private final TaskQueryService taskQueryService;

    public TaskController(TaskQueryService taskQueryService) {
        this.taskQueryService = taskQueryService;
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get a task by id", description = "Get a task by id")
    public ResponseEntity<TaskResource> getTaskById(@PathVariable Long taskId) {
        var getTaskByIdQuery = new GetTaskByIdQuery(taskId);
        var task = this.taskQueryService.handle(getTaskByIdQuery);

        if (task.isEmpty()) return ResponseEntity.notFound().build();

        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());
        return ResponseEntity.ok(taskResource);
    }

    //get all tasks
    @GetMapping
    @Operation(summary = "Get all tasks", description = "Get all tasks")
    public ResponseEntity<List<TaskResource>> getAllTasks() {
        var getAllTasksQuery = new GetAllTasksQuery();
        var tasks = taskQueryService.handle(getAllTasksQuery);
        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResources);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get all tasks by status", description = "Get all tasks by status")
    public ResponseEntity<List<TaskResource>> getAllTasksByStatus(@PathVariable String status) {
        var getAllTasksByStatusQuery = new GetAllTaskByStatusQuery(status);
        var tasks = taskQueryService.handle(getAllTasksByStatusQuery);
        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResources);
    }
}
