package nrg.inc.synhubbackend.taskManagement.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllTasksByMemberId;
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
@RequestMapping("/api/v1/{memberId}/tasks")
@Tag(name = "Member Tasks", description = "Member Tasks endpoints")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
public class TaskMemberController {
    private final TaskCommandService taskCommandService;
    private final TaskQueryService taskQueryService;

    public TaskMemberController(TaskCommandService taskCommandService, TaskQueryService taskQueryService) {
        this.taskCommandService = taskCommandService;
        this.taskQueryService = taskQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new task", description = "Creates a new task")
    public ResponseEntity<TaskResource> createTask(@PathVariable Long memberId, @RequestBody CreateTaskResource resource) {
        var createTaskCommand = CreateTaskCommandFromResourceAssembler.toCommandFromResource(resource, memberId);
        var task = taskCommandService.handle(createTaskCommand);

        if(task.isEmpty()) return ResponseEntity.badRequest().build();

        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());

        return new ResponseEntity<>(taskResource, HttpStatus.valueOf(201));
    }

    @GetMapping
    @Operation(summary = "Get all tasks by member id", description = "Get all tasks by member id")
    public ResponseEntity<List<TaskResource>> getAllTasksByMemberId(@PathVariable Long memberId) {
        var getAllTasksByMemberId = new GetAllTasksByMemberId(memberId);
        var tasks = taskQueryService.handle(getAllTasksByMemberId);
        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResources);
    }
}
