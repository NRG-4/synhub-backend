package nrg.inc.synhubbackend.metrics.application.service;


import nrg.inc.synhubbackend.metrics.domain.model.GroupMetrics;
import nrg.inc.synhubbackend.metrics.domain.repository.GroupMetricsRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupMetricsService {

    private final GroupMetricsRepository repository;

    public GroupMetricsService(GroupMetricsRepository repository) {
        this.repository = repository;
    }

    public GroupMetrics getGroupMetrics(Long groupId) {
        return repository.getGroupMetrics(groupId);
    }
}
