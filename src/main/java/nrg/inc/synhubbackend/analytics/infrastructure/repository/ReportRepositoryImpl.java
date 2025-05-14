package nrg.inc.synhubbackend.analytics.infrastructure.repository;


import nrg.inc.synhubbackend.analytics.domain.model.Report;
import nrg.inc.synhubbackend.analytics.domain.repository.ReportRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReportRepositoryImpl implements ReportRepository {
    private final ConcurrentHashMap<Long, Report> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Report findById(Long reportId) {
        return storage.get(reportId);
    }

    @Override
    public List<Report> findByUserId(Long userId) {
        List<Report> userReports = new ArrayList<>();
        for (Report report : storage.values()) {
            if (report.getMetrics().stream()
                    .anyMatch(metric -> metric.getContext().contains("user=" + userId))) {
                userReports.add(report);
            }
        }
        return userReports;
    }

    @Override
    public void save(Report report) {
        if (report.getId() == null) {
            report.setId(idGenerator.getAndIncrement());
        }
        storage.put(report.getId(), report);
    }

    @Override
    public boolean existsById(Long reportId) {
        return storage.containsKey(reportId);
    }
}