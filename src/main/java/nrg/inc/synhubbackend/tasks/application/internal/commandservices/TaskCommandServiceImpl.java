package nrg.inc.synhubbackend.tasks.application.internal.commandservices;

import nrg.inc.synhubbackend.tasks.domain.model.aggregates.Task;
import nrg.inc.synhubbackend.tasks.domain.model.commands.CreateTaskCommand;
import nrg.inc.synhubbackend.tasks.domain.model.commands.DeleteTaskCommand;
import nrg.inc.synhubbackend.tasks.domain.model.commands.UpdateTaskCommand;
import nrg.inc.synhubbackend.tasks.domain.model.commands.UpdateTaskStatusCommand;
import nrg.inc.synhubbackend.tasks.domain.services.TaskCommandService;
import nrg.inc.synhubbackend.tasks.infrastructure.persistence.jpa.repositories.MemberRepository;
import nrg.inc.synhubbackend.tasks.infrastructure.persistence.jpa.repositories.TaskRepository;
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

    @Override
    public Optional<Task> handle(UpdateTaskCommand command) {
        var taskId = command.taskId();
        var memberId = command.memberId();
        if(!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task with id " + taskId + " does not exist");
        }
        var taskToUpdate = this.taskRepository.findById(taskId).get();
        if(memberId != null && memberId != 0) {
            if(!memberRepository.existsById(memberId)) {
                throw new IllegalArgumentException("Member with id " + memberId + " does not exist");
            }
            var member = this.memberRepository.findById(memberId);
            taskToUpdate.setMember(member.get());
        }

        taskToUpdate.updateTask(command);

        try{
            var updatedTask = this.taskRepository.save(taskToUpdate);
            return Optional.of(updatedTask);
        } catch (Exception e){
            throw new IllegalArgumentException("Error updating task: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteTaskCommand command) {
        var taskId = command.taskId();
        if(!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task with id " + taskId + " does not exist");
        }
        try {
            this.taskRepository.deleteById(taskId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting task: " + e.getMessage());
        }
    }

    @Override
    public Optional<Task> handle(UpdateTaskStatusCommand command) {
        var taskId = command.taskId();
        if(!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task with id " + taskId + " does not exist");
        }

        var taskToUpdate = this.taskRepository.findById(taskId).get();

        taskToUpdate.updateStatus(command);

        try{
            var updatedTask = this.taskRepository.save(taskToUpdate);
            return Optional.of(updatedTask);
        } catch (Exception e){
            throw new IllegalArgumentException("Error updating task status: " + e.getMessage());
        }
    }
}
