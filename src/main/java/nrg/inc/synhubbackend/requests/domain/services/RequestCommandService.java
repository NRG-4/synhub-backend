package nrg.inc.synhubbackend.requests.domain.services;

import nrg.inc.synhubbackend.requests.domain.model.commands.CreateRequestCommand;

public interface RequestCommandService {
    Long handle(CreateRequestCommand command);
}
