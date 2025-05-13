package nrg.inc.synhubbackend.taskManagement.application;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllTaskByStatusQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllTasksByMemberId;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllTasksQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetTaskByIdQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.valueobjects.TaskStatus;
import nrg.inc.synhubbackend.taskManagement.domain.services.TaskQueryService;
import nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskQueryServiceImpl implements TaskQueryService {

    private final TaskRepository taskRepository;

    public TaskQueryServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public List<Task> handle(GetAllTasksQuery query) {
        return this.taskRepository.findAll();
    }

    @Override
    public Optional<Task> handle(GetTaskByIdQuery query) {
        return this.taskRepository.findById(query.taskId());
    }

    @Override
    public List<Task> handle(GetAllTasksByMemberId query) {
        return this.taskRepository.findByMemberId(query.memberId());
    }

    @Override
    public List<Task> handle(GetAllTaskByStatusQuery query) {
        TaskStatus taskStatus = TaskStatus.valueOf(query.taskStatus());
        return taskRepository.findByStatus(taskStatus);
    }
}
