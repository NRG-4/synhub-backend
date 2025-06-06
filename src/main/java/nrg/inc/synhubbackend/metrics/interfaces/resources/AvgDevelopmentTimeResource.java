package nrg.inc.synhubbackend.metrics.interfaces.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class AvgDevelopmentTimeResource {
    private String type;
    private double value;
    private Map<String, Integer> details;
    private String context;
    private String summary;
}