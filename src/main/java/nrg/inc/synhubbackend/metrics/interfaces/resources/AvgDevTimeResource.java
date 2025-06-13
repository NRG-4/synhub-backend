package nrg.inc.synhubbackend.metrics.interfaces.resources;

import java.util.Map;

public record AvgDevTimeResource(
        String type,
        double value,
        Map<String, Integer> details
) {}
