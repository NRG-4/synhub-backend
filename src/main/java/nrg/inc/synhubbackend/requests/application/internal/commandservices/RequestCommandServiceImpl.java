package nrg.inc.synhubbackend.requests.application.internal.commandservices;

import nrg.inc.synhubbackend.requests.domain.model.aggregates.Request;
import nrg.inc.synhubbackend.requests.domain.model.commands.CreateRequestCommand;
import nrg.inc.synhubbackend.requests.domain.model.commands.UpdateRequestCommand;
import nrg.inc.synhubbackend.requests.domain.services.RequestCommandService;
import nrg.inc.synhubbackend.requests.infrastructure.persistence.jpa.repositories.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestCommandServiceImpl implements RequestCommandService {

    private final RequestRepository requestRepository;

    public RequestCommandServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Long handle(CreateRequestCommand command) {
        if (this.requestRepository.existsByTaskId(command.taskId())) {
            throw new IllegalArgumentException("A request already exists for current task");
        }

        var request = new Request(command);
        this.requestRepository.save(request);
        return request.getId();
    }

    @Override
    public Optional<Request> handle(UpdateRequestCommand command) {
        var requestId = command.requestId();

        // If the request does not exist, throw an exception
        if (!this.requestRepository.existsById(requestId)) {
            throw new IllegalArgumentException("Request with id " + requestId + " does not exist");
        }

        var requestToUpdate = this.requestRepository.findById(requestId).get();
        requestToUpdate.updateRequestStatus(command.requestStatus());

        try {
            var updatedRequest = this.requestRepository.save(requestToUpdate);
            return Optional.of(updatedRequest);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating request: " + e.getMessage());
        }
    }
}
