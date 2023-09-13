package in.stackroute.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDto(
        @NotEmpty(message = "Username cannot be empty")
        @Email(message = "Username must be a valid email")
        String userName,
        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 4, max = 30, message = "Password must be between 4 and 30 chars")
        String password
) { }
