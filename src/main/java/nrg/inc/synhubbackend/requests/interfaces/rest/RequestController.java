package nrg.inc.synhubbackend.requests.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.requests.domain.model.queries.GetRequestByIdQuery;
import nrg.inc.synhubbackend.requests.domain.model.queries.GetRequestByTaskIdQuery;
import nrg.inc.synhubbackend.requests.domain.model.valueobjects.RequestType;
import nrg.inc.synhubbackend.requests.domain.services.RequestCommandService;
import nrg.inc.synhubbackend.requests.domain.services.RequestQueryService;
import nrg.inc.synhubbackend.requests.interfaces.rest.resources.CreateRequestResource;
import nrg.inc.synhubbackend.requests.interfaces.rest.resources.RequestResource;
import nrg.inc.synhubbackend.requests.interfaces.rest.transform.CreateRequestCommandFromResourceAssembler;
import nrg.inc.synhubbackend.requests.interfaces.rest.transform.RequestResourceFromEntityAssembler;
import nrg.inc.synhubbackend.requests.interfaces.rest.transform.UpdateRequestCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping(value = "/api/v1/tasks/{taskId}/request", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Request", description = "Request management API")
public class RequestController {

    private final RequestCommandService requestCommandService;
    private final RequestQueryService requestQueryService;

    public RequestController(RequestCommandService requestCommandService, RequestQueryService requestQueryService) {
        this.requestCommandService = requestCommandService;
        this.requestQueryService = requestQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new request", description = "Create a new request")
    public ResponseEntity<RequestResource> createRequest(@PathVariable Long taskId, @RequestBody CreateRequestResource resource) {
        try {
            RequestType.fromString(resource.requestType());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        var createRequestCommand = CreateRequestCommandFromResourceAssembler.toCommandFromResource(resource, taskId);
        var requestId = requestCommandService.handle(createRequestCommand);

        if(requestId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getRequestByIdQuery = new GetRequestByIdQuery(requestId);
        var optionalRequest = this.requestQueryService.handle(getRequestByIdQuery);

        if (optionalRequest.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var requestResource = RequestResourceFromEntityAssembler.toResourceFromEntity(optionalRequest.get());
        return new ResponseEntity<>(requestResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get a request from a task", description = "Get a request from a task id")
    public ResponseEntity<RequestResource> getRequestByTaskId(@PathVariable Long taskId) {
        var getRequestByTaskIdQuery = new GetRequestByTaskIdQuery(taskId);
        var optionalRequest = this.requestQueryService.handle(getRequestByTaskIdQuery);

        if (optionalRequest.isPresent()) {
            var requestResource = RequestResourceFromEntityAssembler.toResourceFromEntity(optionalRequest.get());
            return ResponseEntity.ok(requestResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/status/{status}")
    @Operation(summary = "Update a request status from a task", description = "Update the status of a request from a task")
    public ResponseEntity<RequestResource> updateRequestStatus(@PathVariable Long taskId, @PathVariable String status) {
        var getRequestByTaskIdQuery = new GetRequestByTaskIdQuery(taskId);
        var optionalRequest = this.requestQueryService.handle(getRequestByTaskIdQuery);

        if (optionalRequest.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var requestId = optionalRequest.get().getId();
        var updateRequestCommand = UpdateRequestCommandFromResourceAssembler.toCommandFromResource(requestId, status);
        var updatedRequest = this.requestCommandService.handle(updateRequestCommand);

        if (updatedRequest.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var requestResource = RequestResourceFromEntityAssembler.toResourceFromEntity(updatedRequest.get());
        return ResponseEntity.ok(requestResource);
    }

    // For another controller?
    // TODO: Delete request endpoint ( DELETE /api/v1/groups/{groupId}/requests/{requestId} )
    // TODO: Get requests from a member ( GET /api/v1/members/{memberId}/requests )
}
