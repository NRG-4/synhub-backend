package nrg.inc.synhubbackend.analytics.domain.model;


import java.time.LocalDateTime;
import java.util.List;

public class Report {
    private Long id;
    private String name;
    private LocalDateTime generatedAt;
    private List<Metric> metrics;

    public Report() {}

    public Report(String name, List<Metric> metrics) {
        this.name = name;
        this.metrics = metrics;
        this.generatedAt = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }
}