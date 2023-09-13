package in.stackroute.task_service.controller;

import in.stackroute.task_service.dto.ExceptionDto;
import in.stackroute.task_service.dto.TaskCreated;
import in.stackroute.task_service.dto.TaskDto;
import in.stackroute.task_service.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/tasks")
@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    // POST /api/v1/tasks - Create a new task
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskCreated> createTask(@Valid @RequestBody TaskDto dto) {
        log.info("Creating task {}", dto);
        TaskCreated created = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/v1/tasks?uid={uid} - Get all tasks for a user
    @GetMapping(params = "uid")
    public ResponseEntity<?> getAllTasks(@RequestParam("uid") String uid) {
        log.info("Getting all tasks for user {}", uid);
        var list = taskService.getAllTasks(uid);
        if (list.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    // GET /api/v1/tasks?uid={uid}&status={status} - Get all tasks for a user with a specific status
    @GetMapping(params = {"uid", "status"})
    public ResponseEntity<?> getAllTasks(@RequestParam("uid") String uid, @RequestParam("status") String status) {
        log.info("Getting all tasks for user {} with status {}", uid, status);
        var list = taskService.getAllTasks(uid, status);
        if (list.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    // GET /api/v1/tasks/{id} - Get a task by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable("id") String id) {
        log.info("Getting task by id {}", id);
        var task = taskService.getTaskById(id);
        if (task.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task.get());
    }

    // PUT /api/v1/tasks/{id} - Update a task by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskById(@PathVariable("id") String id, @Valid @RequestBody TaskDto dto) {
        log.info("Updating task by id {}", id);
        var task = taskService.updateTaskById(id, dto);
        if (task.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task.get());
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
