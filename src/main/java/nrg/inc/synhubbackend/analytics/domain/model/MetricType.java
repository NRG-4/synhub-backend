package nrg.inc.synhubbackend.analytics.domain.model;

public enum MetricType {
    TASK_DISTRIBUTION("Distribución de tareas"),
    AVG_DEV_TIME("Tiempo promedio de desarrollo"),
    TASK_OVERVIEW("Vista general de tareas"),
    RESCHEDULED_TASKS("Tareas reprogramadas vs no reprogramadas"),
    AVG_SOLUTION_TIME("Tiempo promedio de solución (líder)");

    private final String description;

    MetricType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}