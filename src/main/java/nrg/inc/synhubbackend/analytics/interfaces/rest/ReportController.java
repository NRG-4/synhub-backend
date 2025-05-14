package nrg.inc.synhubbackend.analytics.interfaces.rest;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.synhubbackend.analytics.application.query.ReportQueryService;
import nrg.inc.synhubbackend.analytics.application.service.MetricCalculatorService;
import nrg.inc.synhubbackend.analytics.domain.model.Metric;
import nrg.inc.synhubbackend.analytics.domain.model.Report;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Report", description = "Analytics and Report endpoints")
public class ReportController {
    private final ReportQueryService reportQueryService;
    private final MetricCalculatorService metricCalculatorService;

    public ReportController(ReportQueryService reportQueryService,
                            MetricCalculatorService metricCalculatorService) {
        this.reportQueryService = reportQueryService;
        this.metricCalculatorService = metricCalculatorService;
    }

    @GetMapping("/task-distribution/{groupId}")
    public Metric getTaskDistribution(@PathVariable Long groupId) {
        return metricCalculatorService.calculateTaskDistribution(groupId);
    }

    @GetMapping("/avg-dev-time/{memberId}")
    public Metric getAverageDevTime(@PathVariable Long memberId) {
        return metricCalculatorService.calculateAverageDevTime(memberId);
    }

    @GetMapping("/task-overview/{groupId}")
    public Metric getTaskOverview(@PathVariable Long groupId) {
        return metricCalculatorService.calculateTaskOverview(groupId);
    }

    @GetMapping("/rescheduled-tasks/{groupId}")
    public Metric getRescheduledTasks(@PathVariable Long groupId) {
        return metricCalculatorService.calculateRescheduledTasks(groupId);
    }

    @GetMapping("/avg-solution-time/{leaderId}")
    public Metric getAverageSolutionTime(@PathVariable Long leaderId) {
        return metricCalculatorService.calculateAverageSolutionTime(leaderId);
    }

    @GetMapping("/generate-full-report/{groupId}")
    public Report generateFullReport(@PathVariable Long groupId) {
        List<Metric> metrics = Arrays.asList(
                metricCalculatorService.calculateTaskDistribution(groupId),
                metricCalculatorService.calculateTaskOverview(groupId),
                metricCalculatorService.calculateRescheduledTasks(groupId)
        );

        String reportName = "Reporte Completo - Grupo " + groupId + " - " + LocalDateTime.now().toString();
        Report report = new Report(reportName, metrics);
        return report;
    }
}