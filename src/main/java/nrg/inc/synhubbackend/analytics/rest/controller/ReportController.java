package nrg.inc.synhubbackend.analytics.rest.controller;

import nrg.inc.synhubbackend.analytics.application.command.GenerateReportCommandHandler;
import nrg.inc.synhubbackend.analytics.application.query.ReportQueryService;
import nrg.inc.synhubbackend.analytics.domain.model.Report;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportQueryService reportQueryService;
    private final GenerateReportCommandHandler generateReportCommandHandler;

    public ReportController(ReportQueryService reportQueryService,
                            GenerateReportCommandHandler generateReportCommandHandler) {
        this.reportQueryService = reportQueryService;
        this.generateReportCommandHandler = generateReportCommandHandler;
    }


    @GetMapping("/user/{userId}")
    public List<Report> getUserReports(@PathVariable Long userId) {
        return reportQueryService.findByUserId(userId);
    }

    @PostMapping("/generate/{userId}")
    public Report generateReport(@PathVariable Long userId) {
        return generateReportCommandHandler.handle(userId);
    }
}