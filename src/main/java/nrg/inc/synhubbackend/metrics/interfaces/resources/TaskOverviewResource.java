package nrg.inc.synhubbackend.metrics.interfaces.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class TaskOverviewResource {
    private String type;
    private int value;
    private Map<String, Integer> details;
}
