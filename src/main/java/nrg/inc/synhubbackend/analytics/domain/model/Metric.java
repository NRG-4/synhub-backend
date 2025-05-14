package nrg.inc.synhubbackend.analytics.domain.model;

import java.util.Map;

public class Metric {
    private final MetricType type;
    private final Double value;
    private final Map<String, ?> details;
    private final String context;

    public Metric(MetricType type, Double value, Map<String, ?> details, String context) {
        this.type = type;
        this.value = value;
        this.details = details;
        this.context = context;
    }

    // Getters
    public MetricType getType() { return type; }
    public Double getValue() { return value; }
    public Map<String, ?> getDetails() { return details; }
    public String getContext() { return context; }

    public String getSummary() {
        return String.format("%s: %.2f (%s)", type.getDescription(), value, context);
    }
}