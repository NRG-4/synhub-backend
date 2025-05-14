package nrg.inc.synhubbackend.analytics.domain.repository;


import nrg.inc.synhubbackend.analytics.domain.model.Report;

import java.util.List;

public interface ReportRepository {
    Report findById(Long reportId);
    List<Report> findByUserId(Long userId);
    void save(Report report);
    boolean existsById(Long reportId);
}