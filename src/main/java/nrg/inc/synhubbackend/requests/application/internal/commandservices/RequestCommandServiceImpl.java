package nrg.inc.synhubbackend.requests.application.internal.commandservices;

import nrg.inc.synhubbackend.requests.domain.model.aggregates.Request;
import nrg.inc.synhubbackend.requests.domain.model.commands.CreateRequestCommand;
import nrg.inc.synhubbackend.requests.domain.services.RequestCommandService;
import nrg.inc.synhubbackend.requests.infrastructure.persistence.jpa.repositories.RequestRepository;
import org.springframework.stereotype.Service;

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
}
