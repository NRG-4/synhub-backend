package nrg.inc.synhubbackend.metrics.interfaces.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class AvgCompletionTimeResource {
    private String type;
    private double value;
    private Map<String, Integer> details;
}
