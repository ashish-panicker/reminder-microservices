package in.stackroute.task_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.format.DateTimeFormatter;

public record TaskDto(
        @NotBlank(message = "Title is mandatory")
        String title,
        @NotBlank(message = "Status is mandatory")
        @Pattern(regexp = "^(OPEN|IN_PROGRESS|COMPLETED|CLOSED)$",
                message = "Status should be one of the following: OPEN, IN_PROGRESS, COMPLETED, CLOSED")
        String status,
        @NotBlank(message = "Assigning the task to a user mandatory")
        @Pattern(regexp = "^[a-f0-9]{8}(-[a-f0-9]{4}){3}-[a-f0-9]{12}$", message = "User id should be in uuid format")
        String assignedTo,
        @NotBlank(message = "Assigning the task on a date is mandatory")
        @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Date should be in dd/MM/yyyy format")
        String assignedOn
) {
        public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
