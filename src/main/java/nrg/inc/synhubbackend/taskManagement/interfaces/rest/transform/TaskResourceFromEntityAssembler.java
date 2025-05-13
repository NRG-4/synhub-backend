package nrg.inc.synhubbackend.taskManagement.interfaces.rest.transform;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.TaskResource;

public class TaskResourceFromEntityAssembler {
    public static TaskResource toResourceFromEntity(Task entity){
        return new TaskResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDueDate().toString(),
                entity.getCreatedOn().toString(),
                entity.getStatus().toString(),
                TaskMemberResourceFromEntityAssembler.toResourceFromEntity(entity.getMember())
        );
    }
}
