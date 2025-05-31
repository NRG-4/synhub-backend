package nrg.inc.synhubbackend.groups.application.internal.commandservices;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Group;
import nrg.inc.synhubbackend.groups.domain.model.aggregates.Leader;
import nrg.inc.synhubbackend.groups.domain.model.commands.CreateGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.DeleteGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.UpdateGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.valueobjects.GroupCode;
import nrg.inc.synhubbackend.groups.domain.services.GroupCommandService;
import nrg.inc.synhubbackend.groups.infrastructure.persistence.jpa.repositories.GroupRepository;
import nrg.inc.synhubbackend.groups.infrastructure.persistence.jpa.repositories.LeaderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupCommandServiceImpl implements GroupCommandService {

    private final GroupRepository groupRepository;
    private final LeaderRepository leaderRepository;

    public GroupCommandServiceImpl(GroupRepository groupRepository, LeaderRepository leaderRepository) {
        this.groupRepository = groupRepository;
        this.leaderRepository = leaderRepository;
    }


    @Override
    public Optional<Group> handle(CreateGroupCommand command) {

        Leader leader = leaderRepository.findById(command.leaderId()).get();

        Group group = new Group(
                command.name(),
                command.description(),
                command.imgUrl(),
                leader,
                GroupCode.random()
                );
        while( groupRepository.existsByCode(group.getCode())) {
            group.setCode(GroupCode.random());
        }

        groupRepository.save(group);

        return Optional.of(group);
    }

    @Override
    public Optional<Group> handle(UpdateGroupCommand command) {
        var group = groupRepository.findByLeader_Id(command.leaderId()).get();
        var groupId = group.getId();
        if(!this.groupRepository.existsById(groupId)) {
            throw new IllegalArgumentException("Group with id " + groupId + " does not exist");
        }

        var groupToUpdate = groupRepository.findById(groupId).get();
        groupToUpdate.updateInformation(command);

        try{
            var updatedGroup = groupRepository.save(groupToUpdate);
            return Optional.of(updatedGroup);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating group: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteGroupCommand command) {

        var groupId = groupRepository.findByLeader_Id(command.leaderId()).get().getId();

        var group = groupRepository.findById(groupId);

        if(!this.groupRepository.existsById(groupId)) {
            throw new IllegalArgumentException("Vehicle with id " + groupId + " does not exist");
        }
        try {
            groupRepository.delete(group.get());
        }catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting group: " + e.getMessage());
        }
    }
}
