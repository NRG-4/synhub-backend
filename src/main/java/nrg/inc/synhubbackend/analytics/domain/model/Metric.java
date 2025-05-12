package nrg.inc.synhubbackend.analytics.domain.model;

import java.util.List;

public class Metric {
    private String name;
    private Double value;
    private String context;

    public Metric(String name, Double value, String context) {
        this.name = name;
        this.value = value;
        this.context = context;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public String getContext() {
        return context;
    }

    // Métodos de dominio
    public Double calculate(List<ActivityLog> logs) {
        // Lógica específica para calcular la métrica
        // Esto debería ser implementado por subclases o estrategias específicas
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f (%s)", name, value, context);
    }
}