package nrg.inc.synhubbackend.analytics.application.command;


import nrg.inc.synhubbackend.analytics.domain.model.Report;
import nrg.inc.synhubbackend.analytics.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportCommandService {
    private final ReportRepository reportRepository;

    public ReportCommandService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void saveReport(Report report) {
        reportRepository.save(report);
    }
}