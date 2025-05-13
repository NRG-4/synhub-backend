package nrg.inc.synhubbackend.requests.domain.model.valueobjects;

public enum RequestType {
    TASK_SUBMISSION,
    TASK_MODIFICATION;

    public static RequestType fromString(String type) {
        for (RequestType requestType : RequestType.values()) {
            if (requestType.name().equalsIgnoreCase(type)) {
                return requestType;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
