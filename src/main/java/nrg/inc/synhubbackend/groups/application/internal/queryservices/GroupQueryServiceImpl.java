package nrg.inc.synhubbackend.groups.application.internal.queryservices;

import nrg.inc.synhubbackend.groups.domain.model.aggregates.Group;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByCodeQuery;
import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByLeaderIdQuery;
import nrg.inc.synhubbackend.groups.domain.model.valueobjects.GroupCode;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.groups.infrastructure.persistence.jpa.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupQueryServiceImpl implements GroupQueryService {
    private final GroupRepository groupRepository;
    public GroupQueryServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Optional<Group> handle(GetGroupByLeaderIdQuery query) {
        return this.groupRepository.findByLeader_Id(query.leaderId());
    }

    @Override
    public Optional<Group> handle(GetGroupByCodeQuery query) {
        var code = new GroupCode(query.code());
        var group = this.groupRepository.findByCode(code);
        if (group.isEmpty()) {
            return Optional.empty();
        }
        return group;
    }


}
