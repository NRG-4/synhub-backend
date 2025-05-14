package nrg.inc.synhubbackend.requests.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.requests.domain.model.queries.GetRequestByIdQuery;
import nrg.inc.synhubbackend.requests.domain.model.valueobjects.RequestType;
import nrg.inc.synhubbackend.requests.domain.services.RequestCommandService;
import nrg.inc.synhubbackend.requests.domain.services.RequestQueryService;
import nrg.inc.synhubbackend.requests.interfaces.rest.resources.CreateRequestResource;
import nrg.inc.synhubbackend.requests.interfaces.rest.resources.RequestResource;
import nrg.inc.synhubbackend.requests.interfaces.rest.resources.UpdateRequestStatusResource;
import nrg.inc.synhubbackend.requests.interfaces.rest.transform.CreateRequestCommandFromResourceAssembler;
import nrg.inc.synhubbackend.requests.interfaces.rest.transform.RequestResourceFromEntityAssembler;
import nrg.inc.synhubbackend.requests.interfaces.rest.transform.UpdateRequestCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping(value = "/api/v1/{memberId}/tasks/{taskId}/requests", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Request", description = "Request management API")
public class RequestController {

    private final RequestCommandService requestCommandService;
    private final RequestQueryService requestQueryService;

    public RequestController(RequestCommandService requestCommandService, RequestQueryService requestQueryService) {
        this.requestCommandService = requestCommandService;
        this.requestQueryService = requestQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new request", description = "Creates a new request for a task")
    public ResponseEntity<RequestResource> createRequest(@PathVariable Long memberId, @PathVariable Long taskId, @RequestBody CreateRequestResource resource) {
        try {
            RequestType.fromString(resource.requestType());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        var createRequestCommand = CreateRequestCommandFromResourceAssembler.toCommandFromResource(resource);
        var requestId = requestCommandService.handle(createRequestCommand);

        if(requestId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getRequestByIdQuery = new GetRequestByIdQuery(requestId);
        var optionalRequest = this.requestQueryService.handle(getRequestByIdQuery);

        var requestResource = RequestResourceFromEntityAssembler.toResourceFromEntity(optionalRequest.get());
        return new ResponseEntity<>(requestResource, HttpStatus.CREATED);
    }

    @GetMapping("/{requestId}")
    @Operation(summary = "Get request by ID", description = "Retrieves a request by its ID")
    public ResponseEntity<RequestResource> getRequestById(@PathVariable Long memberId, @PathVariable Long taskId, @PathVariable Long requestId) {
        var getRequestByIdQuery = new GetRequestByIdQuery(requestId);
        var optionalRequest = this.requestQueryService.handle(getRequestByIdQuery);

        if (optionalRequest.isPresent()) {
            var requestResource = RequestResourceFromEntityAssembler.toResourceFromEntity(optionalRequest.get());
            return ResponseEntity.ok(requestResource);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{requestId}/status/{requestStatus}")
    @Operation(summary = "Update request status", description = "Updates the status of a request")
    public ResponseEntity<RequestResource> updateRequestStatus(@PathVariable Long memberId, @PathVariable Long taskId, @PathVariable Long requestId, @PathVariable String requestStatus) {
            var updateRequestCommand = UpdateRequestCommandFromResourceAssembler.toCommandFromResource(requestId, requestStatus);
            var optionalRequest = this.requestCommandService.handle(updateRequestCommand);

        if(optionalRequest.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var requestResource = RequestResourceFromEntityAssembler.toResourceFromEntity(optionalRequest.get());
        return ResponseEntity.ok(requestResource);
    }
}
