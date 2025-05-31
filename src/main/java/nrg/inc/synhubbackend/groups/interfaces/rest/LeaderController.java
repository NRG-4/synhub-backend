package nrg.inc.synhubbackend.groups.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.commands.CreateLeaderCommand;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetLeaderByIdQuery;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetUserLeaderByIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.LeaderCommandService;
import nrg.inc.synhubbackend.groups.domain.services.LeaderQueryService;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.LeaderResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.UserLeaderResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.LeaderResourceFromEntityAssembler;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.UserLeaderResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/v1/leaders")
@Tag(name = "Leaders", description = "Leader management API")
public class LeaderController {

    private final LeaderQueryService leaderQueryService;
    private final LeaderCommandService leaderCommandService;

    public LeaderController(LeaderQueryService leaderQueryService, LeaderCommandService leaderCommandService) {
        this.leaderQueryService = leaderQueryService;
        this.leaderCommandService = leaderCommandService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get a leader by user ID", description = "Gets a leader by user ID")
    public ResponseEntity<UserLeaderResource> getLeaderById(@PathVariable Long userId) {

        var getUserLeaderByIdQuery = new GetUserLeaderByIdQuery(userId);

        var leader = this.leaderQueryService.handle(getUserLeaderByIdQuery);

        if (leader.isEmpty()) return ResponseEntity.notFound().build();

        var role = leader.get().getRoles().stream().findFirst().get().getName().toString();

        if (!role.equals("ROLE_LEADER")) {
            return ResponseEntity.notFound().build();
        }

        var leaderResource = UserLeaderResourceFromEntityAssembler.toResourceFromEntity(leader.get());
        return ResponseEntity.ok(leaderResource);
    }

}
