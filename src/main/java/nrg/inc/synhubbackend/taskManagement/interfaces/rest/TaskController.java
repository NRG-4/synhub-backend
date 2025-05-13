package nrg.inc.synhubbackend.taskManagement.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.taskManagement.domain.services.TaskCommandService;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.CreateTaskResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.TaskResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform.CreateTaskCommandFromResourceAssembler;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform.TaskResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/tasks")
@Tag(name = "Task", description = "Task management API")
@ApiResponse(responseCode = "201", description = "Task Created")
public class TaskController {

    private final TaskCommandService taskCommandService;

    public TaskController(TaskCommandService taskCommandService) {
        this.taskCommandService = taskCommandService;
    }

    @PostMapping
    @Operation(summary = "Create a new task", description = "Creates a new task")
    public ResponseEntity<TaskResource> createTask(@RequestBody CreateTaskResource resource) {
        var createTaskCommand = CreateTaskCommandFromResourceAssembler.toCommandFromResource(resource);
        var task = taskCommandService.handle(createTaskCommand);

        if(task.isEmpty()) return ResponseEntity.badRequest().build();

        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());

        return new ResponseEntity<>(taskResource, HttpStatus.valueOf(201));
    }
}
