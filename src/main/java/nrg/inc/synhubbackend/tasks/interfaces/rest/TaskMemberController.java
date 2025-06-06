package nrg.inc.synhubbackend.tasks.interfaces.rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.tasks.domain.model.queries.GetAllTasksByMemberId;
import nrg.inc.synhubbackend.tasks.domain.model.queries.GetMemberByUsernameQuery;
import nrg.inc.synhubbackend.tasks.domain.services.MemberQueryService;
import nrg.inc.synhubbackend.tasks.domain.services.TaskCommandService;
import nrg.inc.synhubbackend.tasks.domain.services.TaskQueryService;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.CreateTaskResource;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.TaskResource;
import nrg.inc.synhubbackend.tasks.interfaces.rest.transform.CreateTaskCommandFromResourceAssembler;
import nrg.inc.synhubbackend.tasks.interfaces.rest.transform.TaskResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/members")
@Tag(name = "Tasks Member ", description = "Tasks Member endpoints")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
public class TaskMemberController {
    private final TaskCommandService taskCommandService;
    private final TaskQueryService taskQueryService;
    private final MemberQueryService memberQueryService;
    public TaskMemberController(TaskCommandService taskCommandService, TaskQueryService taskQueryService, MemberQueryService memberQueryService) {
        this.taskCommandService = taskCommandService;
        this.taskQueryService = taskQueryService;
        this.memberQueryService = memberQueryService;
    }

    @PostMapping("/{memberId}/tasks")
    @Operation(summary = "Create a new task", description = "Creates a new task")
    public ResponseEntity<TaskResource> createTask(@PathVariable Long memberId, @RequestBody CreateTaskResource resource) {
        var createTaskCommand = CreateTaskCommandFromResourceAssembler.toCommandFromResource(resource, memberId);
        var task = taskCommandService.handle(createTaskCommand);

        if(task.isEmpty()) return ResponseEntity.badRequest().build();

        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(task.get());

        return new ResponseEntity<>(taskResource, HttpStatus.valueOf(201));
    }

    @GetMapping("/{memberId}/tasks")
    @Operation(summary = "Get all tasks by member id", description = "Get all tasks by member id")
    public ResponseEntity<List<TaskResource>> getAllTasksByMemberId(@PathVariable Long memberId) {
        var getAllTasksByMemberId = new GetAllTasksByMemberId(memberId);
        var tasks = taskQueryService.handle(getAllTasksByMemberId);
        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResources);
    }

    @GetMapping("/tasks")
    @Operation(summary = "Get all tasks by authenticated member", description = "Fetches all tasks for the authenticated member.")
    public ResponseEntity<List<TaskResource>> getAllTasksByMemberAuthenticated(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        var getMemberByUsernameQuery = new GetMemberByUsernameQuery(username);

        var member = this.memberQueryService.handle(getMemberByUsernameQuery);

        if(member.isEmpty()) return ResponseEntity.notFound().build();

        var getAllTasksByMemberId = new GetAllTasksByMemberId(member.get().getId());

        var tasks = taskQueryService.handle(getAllTasksByMemberId);

        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(taskResources);
    }
}
