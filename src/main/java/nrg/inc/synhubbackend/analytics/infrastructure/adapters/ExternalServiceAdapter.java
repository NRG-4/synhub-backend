package nrg.inc.synhubbackend.analytics.infrastructure.adapters;

import nrg.inc.synhubbackend.analytics.domain.model.MemberData;
import nrg.inc.synhubbackend.analytics.domain.model.TaskData;
import nrg.inc.synhubbackend.analytics.infrastructure.client.TaskManagementClient;
import nrg.inc.synhubbackend.analytics.infrastructure.client.MemberManagementClient;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.MemberResource;
import nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources.TaskResource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExternalServiceAdapter {

    private final TaskManagementClient taskManagementClient;
    private final MemberManagementClient memberManagementClient;

    public ExternalServiceAdapter(
            TaskManagementClient taskManagementClient,
            MemberManagementClient memberManagementClient
    ) {
        this.taskManagementClient = taskManagementClient;
        this.memberManagementClient = memberManagementClient;
    }

    public List<TaskData> getAllTasks() {
        List<TaskResource> tasks = taskManagementClient.getAllTasks();
        return tasks.stream()
                .map(this::convertToTaskData)
                .collect(Collectors.toList());
    }

    public List<TaskData> getTasksByMemberId(Long memberId) {
        List<TaskResource> tasks = taskManagementClient.getTasksByMemberId(memberId);
        return tasks.stream()
                .map(this::convertToTaskData)
                .collect(Collectors.toList());
    }

    public List<MemberData> getMembersByGroupId(Long groupId) {
        List<MemberResource> members = memberManagementClient.getMembersByGroupId(groupId);
        return members.stream()
                .map(member -> new MemberData(member.name()))
                .collect(Collectors.toList());
    }

    private TaskData convertToTaskData(TaskResource task) {
        return new TaskData(
                task.id(),
                task.title(),
                task.status(),
                parseDate(task.createdAt()),
                parseDate(task.updatedAt()),
                parseDate(task.dueDate()),
                task.member() != null ?
                        new MemberData(task.member().name()) :
                        null
        );
    }

    private LocalDateTime parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            return LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            return null;
        }
    }
}