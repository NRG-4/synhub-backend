package nrg.inc.synhubbackend.tasks.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByMemberIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.GroupResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.GroupResourceFromEntityAssembler;
import nrg.inc.synhubbackend.tasks.domain.model.queries.GetAllTasksByMemberId;
import nrg.inc.synhubbackend.tasks.domain.model.queries.GetMemberByUsernameQuery;
import nrg.inc.synhubbackend.tasks.domain.services.MemberQueryService;
import nrg.inc.synhubbackend.tasks.domain.services.TaskQueryService;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.MemberResource;
import nrg.inc.synhubbackend.tasks.interfaces.rest.resources.TaskResource;
import nrg.inc.synhubbackend.tasks.interfaces.rest.transform.MemberResourceFromEntityAssembler;
import nrg.inc.synhubbackend.tasks.interfaces.rest.transform.TaskResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/member")
@Tag(name = "Member", description = "Member API")
@ApiResponse(responseCode = "201", description = "Member created")
public class MemberController {
    private final MemberQueryService memberQueryService;
    private final GroupQueryService groupQueryService;
    private final TaskQueryService taskQueryService;
    public MemberController(MemberQueryService memberQueryService, GroupQueryService groupQueryService, TaskQueryService taskQueryService) {
        this.memberQueryService = memberQueryService;
        this.groupQueryService = groupQueryService;
        this.taskQueryService = taskQueryService;
    }

    @GetMapping("/details")
    @Operation(summary = "Get member details by authentication", description = "Fetches the details of the authenticated member.")
    public ResponseEntity<MemberResource> getMemberByAuthentication(@AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        var getMemberByUsernameQuery = new GetMemberByUsernameQuery(username);

        var member = this.memberQueryService.handle(getMemberByUsernameQuery);

        if(member.isEmpty()) return ResponseEntity.notFound().build();

        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(member.get());

        return ResponseEntity.ok(memberResource);
    }

    @GetMapping("/group")
    @Operation(summary = "Get group by member authenticated", description = "Retrieve the group associated with the authenticated member")
    public ResponseEntity<GroupResource> getGroupByMemberId(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var getMemberByUsernameQuery = new GetMemberByUsernameQuery(username);
        var member = this.memberQueryService.handle(getMemberByUsernameQuery);
        if (member.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var getGroupByMemberIdQuery = new GetGroupByMemberIdQuery(member.get().getId());
        var group = this.groupQueryService.handle(getGroupByMemberIdQuery);
        if (group.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var groupResource = GroupResourceFromEntityAssembler.toResourceFromEntity(group.get());
        return ResponseEntity.ok(groupResource);
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
