package in.stackroute.task_service.model;

public enum TaskStatus {

    OPEN, IN_PROGRESS, COMPLETED, CLOSED;

    public static TaskStatus fromString(String status) {
        switch (status) {
            case "OPEN":
                return OPEN;
            case "IN_PROGRESS":
                return IN_PROGRESS;
            case "COMPLETED":
                return COMPLETED;
            case "CLOSED":
                return CLOSED;
            default:
                return null;
        }
    }
}
