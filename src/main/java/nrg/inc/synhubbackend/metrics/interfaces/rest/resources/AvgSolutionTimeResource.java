package nrg.inc.synhubbackend.metrics.interfaces.rest.resources;

import java.util.Map;

public record AvgSolutionTimeResource(
        String type,
        double value,
        Map<String, Integer> details
) {}
