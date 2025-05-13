package nrg.inc.synhubbackend.groups.domain.services;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Group;
import nrg.inc.synhubbackend.groups.domain.model.commands.CreateGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.DeleteGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.UpdateGroupCommand;
import java.util.Optional;

public interface GroupCommandService {
    Optional<Group> handle(CreateGroupCommand command);
    Optional<Group> handle(UpdateGroupCommand command);
    void handle(DeleteGroupCommand command);
}
