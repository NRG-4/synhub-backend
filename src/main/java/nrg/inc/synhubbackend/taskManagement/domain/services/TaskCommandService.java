package nrg.inc.synhubbackend.taskManagement.domain.services;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateTaskCommand;

import java.util.Optional;

public interface TaskCommandService {
    Optional<Task> handle(CreateTaskCommand command);
}
