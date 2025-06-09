package nrg.inc.synhubbackend.tasks.application.internal.acl;

import nrg.inc.synhubbackend.tasks.domain.model.commands.DeleteTasksByMemberId;
import nrg.inc.synhubbackend.tasks.domain.services.TaskCommandService;
import nrg.inc.synhubbackend.tasks.interfaces.acl.TasksContextFacade;
import org.springframework.stereotype.Service;

@Service
public class TaskContextFacadeImpl implements TasksContextFacade {

    private final TaskCommandService taskCommandService;

    public TaskContextFacadeImpl(TaskCommandService taskCommandService) {
        this.taskCommandService = taskCommandService;
    }

    @Override
    public void deleteTasksByMemberId(Long memberId) {
        var deleteTasksByMemberId = new DeleteTasksByMemberId(memberId);
        taskCommandService.handle(deleteTasksByMemberId);
    }
}
