package nrg.inc.synhubbackend.metrics.infrastructure.client;

import nrg.inc.synhubbackend.groups.domain.model.queries.GetGroupByIdQuery;
import nrg.inc.synhubbackend.groups.domain.services.GroupQueryService;
import nrg.inc.synhubbackend.metrics.domain.model.GroupMetrics;
import nrg.inc.synhubbackend.metrics.domain.repository.GroupMetricsRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GroupsClient implements GroupMetricsRepository {

    private final GroupQueryService groupQueryService;

    public GroupsClient(GroupQueryService groupQueryService) {
        this.groupQueryService = groupQueryService;
    }

    @Override
    public GroupMetrics getGroupMetrics(Long groupId) {
        return groupQueryService.handle(new GetGroupByIdQuery(groupId))
                .map(group -> new GroupMetrics(groupId, group.getMemberCount()))
                .orElseThrow(() -> new IllegalArgumentException("Group with id " + groupId + " not found"));
    }
}
