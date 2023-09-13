package in.stackroute.user_service.controller;

import in.stackroute.user_service.dto.ExceptionDto;
import in.stackroute.user_service.dto.UserCreatedDto;
import in.stackroute.user_service.dto.UserDto;
import in.stackroute.user_service.dto.ValidUserDto;
import in.stackroute.user_service.exceptions.InvalidUserDetailException;
import in.stackroute.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // POST /api/v1/users - create a new user
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserCreatedDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("POST -> Creating user {}", userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
    }

    // POST /api/v1/users/authenticate - validate a user
    @PostMapping("/authenticate")
    public ResponseEntity<ValidUserDto> authenticateUser(@Valid @RequestBody UserDto userDto) {
        log.info("POST -> Authenticating user {}", userDto);
        ValidUserDto authenticated = userService.authenticate(userDto);
        return ResponseEntity.ok(authenticated);
    }

    @ExceptionHandler(InvalidUserDetailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidUserDetailException(InvalidUserDetailException ex) {
        log.error("Exception occurred while authenticating user {}", ex.getMessage());
        ExceptionDto dto = new ExceptionDto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                null
        );
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Exception occurred while saving user {}", ex.getMessage());
        ExceptionDto dto = new ExceptionDto(
                "Duplicate username",
                HttpStatus.BAD_REQUEST,
                null
        );
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Exception occurred while saving user {}", ex.getMessage());
        ExceptionDto dto = new ExceptionDto(
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                ex.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toArray(String[]::new)
        );
        return ResponseEntity.badRequest().body(dto);
    }

}
