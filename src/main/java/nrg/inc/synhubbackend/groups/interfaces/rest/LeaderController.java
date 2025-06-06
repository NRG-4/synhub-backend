package nrg.inc.synhubbackend.groups.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetLeaderByUsernameQuery;
import nrg.inc.synhubbackend.groups.domain.services.LeaderCommandService;
import nrg.inc.synhubbackend.groups.domain.services.LeaderQueryService;
import nrg.inc.synhubbackend.groups.interfaces.rest.resources.LeaderResource;
import nrg.inc.synhubbackend.groups.interfaces.rest.transform.LeaderResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/leader/details")
@Tag(name = "Leaders", description = "Leader management API")
public class LeaderController {

    private final LeaderQueryService leaderQueryService;
    private final LeaderCommandService leaderCommandService;

    public LeaderController(LeaderQueryService leaderQueryService, LeaderCommandService leaderCommandService) {
        this.leaderQueryService = leaderQueryService;
        this.leaderCommandService = leaderCommandService;
    }

    @GetMapping
    @Operation(summary = "Get leader details by authentication", description = "Fetches the details of the authenticated leader.")
    public ResponseEntity<LeaderResource> getLeaderById(Authentication authentication) {

        String username = authentication.getName();

        var getLeaderByUsernameQuery = new GetLeaderByUsernameQuery(username);

        var leader = this.leaderQueryService.handle(getLeaderByUsernameQuery);

        if (leader.isEmpty()) return ResponseEntity.notFound().build();

        var leaderResource = LeaderResourceFromEntityAssembler.toResourceFromEntity(leader.get());

        return ResponseEntity.ok(leaderResource);
    }

}
