package nrg.inc.synhubbackend.analytics.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Report {
    private Long id;
    private String name;
    private LocalDateTime generatedAt;
    private List<Metric> data;

    public Report() {}

    public Report(String name, List<Metric> data) {
        this.name = name;
        this.data = data;
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

    public List<Metric> getData() {
        return data;
    }

    public void setData(List<Metric> data) {
        this.data = data;
    }

    // Métodos de dominio
    public void generateFromLogs(List<ActivityLog> logs) {
        // Lógica para generar métricas a partir de logs
        // Esto debería ser implementado más completamente en el servicio
        this.generatedAt = LocalDateTime.now();
    }
}