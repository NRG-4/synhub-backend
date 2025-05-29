package nrg.inc.synhubbackend.groups.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.commands.CreateLeaderCommand;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetLeaderByIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.LeaderCommandService;
import nrg.inc.synhubbackend.groups.domain.services.LeaderQueryService;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.LeaderResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.LeaderResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{leaderId}")
    @Operation(summary = "Get a leader by ID", description = "Gets a leader by ID")
    public ResponseEntity<LeaderResource> getLeaderById(@PathVariable Long leaderId) {
        var getLeaderByIdQuery = new GetLeaderByIdQuery(leaderId);
        var leader = this.leaderQueryService.handle(getLeaderByIdQuery);

        if (leader.isEmpty()) return ResponseEntity.notFound().build();

        var leaderResource = LeaderResourceFromEntityAssembler.toResourceFromEntity(leader.get());
        return ResponseEntity.ok(leaderResource);
    }

    @PostMapping
    @Operation(summary = "Create a new leader", description = "Creates a new leader")
    public ResponseEntity<LeaderResource> createLeader(@RequestBody LeaderResource leaderResource) {
        var createLeaderCommand = new CreateLeaderCommand();
        var leader = this.leaderCommandService.handle(createLeaderCommand);

        var createdLeaderResource = LeaderResourceFromEntityAssembler.toResourceFromEntity(leader.get());
        return ResponseEntity.status(201).body(createdLeaderResource);
    }


}
