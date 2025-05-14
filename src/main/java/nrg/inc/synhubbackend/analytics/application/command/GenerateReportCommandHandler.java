package nrg.inc.synhubbackend.analytics.application.command;


import nrg.inc.synhubbackend.analytics.domain.model.Report;
import org.springframework.stereotype.Component;

@Component
public class GenerateReportCommandHandler {
    private final ReportCommandService reportCommandService;

    public GenerateReportCommandHandler(ReportCommandService reportCommandService) {
        this.reportCommandService = reportCommandService;
    }

    public void handle(Report report) {
        reportCommandService.saveReport(report);
    }
}