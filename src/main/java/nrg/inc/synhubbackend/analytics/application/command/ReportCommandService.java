package nrg.inc.synhubbackend.analytics.application.command;

import nrg.inc.synhubbackend.analytics.domain.model.ActivityLog;
import nrg.inc.synhubbackend.analytics.domain.model.Metric;
import nrg.inc.synhubbackend.analytics.domain.model.Report;
import nrg.inc.synhubbackend.analytics.domain.repository.ActivityLogRepository;
import nrg.inc.synhubbackend.analytics.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportCommandService {
    private final ActivityLogRepository activityLogRepository;
    private final ReportRepository reportRepository;

    public ReportCommandService(ActivityLogRepository activityLogRepository,
                                ReportRepository reportRepository) {
        this.activityLogRepository = activityLogRepository;
        this.reportRepository = reportRepository;
    }

    public Report generateReport(Long userId) {
        List<ActivityLog> userLogs = activityLogRepository.findByUserId(userId);

        // Calcular métricas básicas
        List<Metric> metrics = calculateMetrics(userId, userLogs);

        // Crear reporte
        String reportName = "User Activity Report - " + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Report report = new Report(reportName, metrics);
        report.generateFromLogs(userLogs);

        // Guardar reporte
        reportRepository.save(report);

        return report;
    }

    private List<Metric> calculateMetrics(Long userId, List<ActivityLog> logs) {
        List<Metric> metrics = new ArrayList<>();

        // 1. Total de actividades
        metrics.add(new Metric("Total Activities", (double) logs.size(), "user=" + userId));

        // 2. Actividades por tipo
        Map<String, Long> activitiesByType = logs.stream()
                .collect(Collectors.groupingBy(ActivityLog::getActionType, Collectors.counting()));

        activitiesByType.forEach((type, count) ->
                metrics.add(new Metric("Activities - " + type, count.doubleValue(), "user=" + userId)));

        // 3. Última actividad
        LocalDateTime lastActivity = logs.stream()
                .map(ActivityLog::getTimestamp)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        if (lastActivity != null) {
            long hoursSinceLastActivity = ChronoUnit.HOURS.between(lastActivity, LocalDateTime.now());
            metrics.add(new Metric("Hours Since Last Activity", (double) hoursSinceLastActivity, "user=" + userId));
        }

        return metrics;
    }
}