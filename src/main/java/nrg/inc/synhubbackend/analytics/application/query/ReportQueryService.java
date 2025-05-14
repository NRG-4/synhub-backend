package nrg.inc.synhubbackend.analytics.application.query;


import nrg.inc.synhubbackend.analytics.domain.model.Report;
import nrg.inc.synhubbackend.analytics.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportQueryService {
    private final ReportRepository reportRepository;

    public ReportQueryService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public boolean existsById(Long reportId) {
        return reportRepository.existsById(reportId);
    }

    public List<Report> findByUserId(Long userId) {
        return reportRepository.findByUserId(userId);
    }
}