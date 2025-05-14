package nrg.inc.synhubbackend.taskManagement.domain.model.queries;

/**
 * GetAllTaskByStatusQuery: Query to get all tasks by status
 * @param taskStatus
 */
public record GetAllTaskByStatusQuery(String taskStatus) {
}
