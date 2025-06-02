package nrg.inc.synhubbackend.metrics.domain.repository;


import nrg.inc.synhubbackend.metrics.domain.model.GroupMetrics;

public interface GroupMetricsRepository {
    GroupMetrics getGroupMetrics(Long groupId);
}
