package nrg.inc.synhubbackend.metrics.domain.model.services;

import nrg.inc.synhubbackend.metrics.domain.model.aggregates.GroupMetrics;
import java.util.Optional;

public interface GroupMetricsService {
    Optional<GroupMetrics> getGroupMetrics(Long groupId);
}
