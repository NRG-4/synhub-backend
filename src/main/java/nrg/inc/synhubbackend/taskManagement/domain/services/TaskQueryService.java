package nrg.inc.synhubbackend.taskManagement.domain.services;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetAllTasksQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetTaskByIdQuery;
import nrg.inc.synhubbackend.taskManagement.domain.model.queries.GetTasksByMemberId;

import java.util.List;
import java.util.Optional;

public interface TaskQueryService {
    /**
     * Retrieves all tasks.
     * @param getAllTasksQuery
     * @return
     */
    List<Task> handle(GetAllTasksQuery getAllTasksQuery);

    /**
     * Retrieves tasks assigned to a specific member.
     * @param getTaskByIdQuery
     * @return
     */
    Optional<Task> handle(GetTaskByIdQuery getTaskByIdQuery);

    /**
     * Retrieves tasks assigned to a specific member.
     * @param getTasksByMemberId
     * @return
     */
    Optional<Task> handle(GetTasksByMemberId getTasksByMemberId);
}
