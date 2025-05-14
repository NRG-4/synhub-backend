package nrg.inc.synhubbackend.taskManagement.domain.services;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateTaskCommand;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.DeleteTaskCommand;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.UpdateTaskCommand;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.UpdateTaskStatusCommand;

import java.util.Optional;

public interface TaskCommandService {
    Optional<Task> handle(CreateTaskCommand command);
    Optional<Task> handle(UpdateTaskCommand command);
    void handle(DeleteTaskCommand command);
    Optional<Task> handle(UpdateTaskStatusCommand command);
}
