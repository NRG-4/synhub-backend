package nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Member;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.TaskMemberResource;

public class TaskMemberResourceFromEntityAssembler {
    public static TaskMemberResource toResourceFromEntity(Member entity) {
        return new TaskMemberResource(
                entity.getId(),
                entity.getName()
        );
    }
}
