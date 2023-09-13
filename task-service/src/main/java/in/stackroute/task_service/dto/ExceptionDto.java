package in.stackroute.task_service.dto;

import org.springframework.http.HttpStatus;

public record ExceptionDto(String message, HttpStatus status, Object details) {
}

