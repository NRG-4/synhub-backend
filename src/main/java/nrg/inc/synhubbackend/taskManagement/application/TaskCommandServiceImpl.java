package nrg.inc.synhubbackend.taskManagement.application;

import nrg.inc.synhubbackend.taskManagement.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.taskManagement.domain.model.commands.CreateTaskCommand;
import nrg.inc.synhubbackend.taskManagement.domain.services.TaskCommandService;
import nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories.MemberRepository;
import nrg.inc.synhubbackend.taskManagement.infrastructure.persistence.jpa.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskCommandServiceImpl implements TaskCommandService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    public TaskCommandServiceImpl(TaskRepository taskRepository, MemberRepository memberRepository) {
        this.taskRepository = taskRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Task> handle(CreateTaskCommand command) {
        var task = new Task(command);
        var member = this.memberRepository.findById(command.memberId());
        if (member.isEmpty()) {
            throw new IllegalArgumentException("Member with id " + command.memberId() + " does not exist");
        }
        task.setMember(member.get());

        var createdTask = taskRepository.save(task);

        return Optional.of(createdTask);
    }
}
