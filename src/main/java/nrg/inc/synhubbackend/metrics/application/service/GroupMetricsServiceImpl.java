package nrg.inc.synhubbackend.metrics.application.service;

import nrg.inc.synhubbackend.metrics.domain.model.aggregates.GroupMetrics;
import nrg.inc.synhubbackend.metrics.domain.model.services.GroupMetricsService;
import nrg.inc.synhubbackend.metrics.infrastructure.persistenence.jpa.repositories.GroupMetricsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupMetricsServiceImpl implements GroupMetricsService {

    private final GroupMetricsRepository groupMetricsRepository;

    public GroupMetricsServiceImpl(GroupMetricsRepository groupMetricsRepository) {
        this.groupMetricsRepository = groupMetricsRepository;
    }

    @Override
    public Optional<GroupMetrics> getGroupMetrics(Long groupId) {
        return groupMetricsRepository.getGroupMetrics(groupId);
    }
}
