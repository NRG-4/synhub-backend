package nrg.inc.synhubbackend.groups.application.internal.commandservices;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Group;
import nrg.inc.synhubbackend.groups.domain.model.aggregates.Leader;
import nrg.inc.synhubbackend.groups.domain.model.commands.CreateGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.DeleteGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.commands.UpdateGroupCommand;
import nrg.inc.synhubbackend.groups.domain.model.valueobjects.ImgUrl;
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
        Group group = new Group(command.name(), command.imgUrl(), leader, command.description(), 0);
        groupRepository.save(group);

        return Optional.of(group);
    }

    @Override
    public Optional<Group> handle(UpdateGroupCommand command) {

        Optional<Group> groupOptional = groupRepository.findById(command.groupId());

        if (groupOptional.isEmpty()) {
            throw new IllegalArgumentException("Group not found");
        }

        Group group = groupOptional.get();
        group.setName(command.name());
        group.setDescription(command.description());
        group.setImgUrl(new ImgUrl(command.imgUrl()));
        group.setMemberCount(command.memberCount());
        groupRepository.save(group);
        return Optional.of(group);
    }

    @Override
    public void handle(DeleteGroupCommand command) {

        Optional<Group> groupOptional = groupRepository.findById(command.groupId());

        if (groupOptional.isEmpty()) {
            throw new IllegalArgumentException("Group not found");
        }

        Group group = groupOptional.get();
        groupRepository.delete(group);

    }
}
