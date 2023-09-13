package in.stackroute.task_service.service;

import in.stackroute.task_service.dto.TaskCreated;
import in.stackroute.task_service.dto.TaskDto;
import in.stackroute.task_service.model.Task;
import in.stackroute.task_service.model.TaskStatus;
import in.stackroute.task_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskCreated createTask(TaskDto dto) {
        Task task = toEntity(dto);
        log.info("Creating task {}", task);
        task = taskRepository.save(task);
        return new TaskCreated(task.getId(), task.getAssignedTo(), "Task created successfully");
    }

    @Override
    public List<TaskDto> getAllTasks(String uid) {
        List<Task> tasks = taskRepository.findAllByAssignedTo(uid);
        if (tasks.isEmpty()) {
            log.info("No tasks found for user {}", uid);
            return List.of();
        }
        return tasks.stream().map(this::toDto).toList();
    }

    @Override
    public List<TaskDto> getAllTasks(String uid, String status) {
        List<Task> tasks = taskRepository.findAllByAssignedToAndStatus(uid, TaskStatus.fromString(status));
        if (tasks.isEmpty()) {
            log.info("No tasks found for user {} with status {}", uid, status);
            return List.of();
        }
        return tasks.stream().map(this::toDto).toList();
    }

    @Override
    public Optional<TaskDto> getTaskById(String id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            log.info("No task found with id {}", id);
            return Optional.empty();
        }
        return Optional.of(toDto(task.get()));
    }

    @Override
    public Optional<TaskDto> updateTaskById(String id, TaskDto dto) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            log.info("No task found with id {}", id);
            return Optional.empty();
        }
        Task updatedTask = task.get();
        updatedTask.setTitle(dto.title());
        updatedTask.setStatus(TaskStatus.fromString(dto.status()));
        updatedTask.setAssignedTo(dto.assignedTo());
        updatedTask.setAssignedOn(LocalDate.parse(dto.assignedOn(), TaskDto.DATE_FORMATTER));
        updatedTask = taskRepository.save(updatedTask);
        return Optional.of(toDto(updatedTask));
    }

    private Task toEntity(TaskDto dto) {
        return Task.builder()
                .title(dto.title())
                .status(TaskStatus.fromString(dto.status()))
                .assignedTo(dto.assignedTo())
                .assignedOn(LocalDate.parse(dto.assignedOn(), TaskDto.DATE_FORMATTER))
                .build();
    }

    private TaskDto toDto(Task task) {
       return new TaskDto(
               task.getTitle(),
               task.getStatus().toString(),
               task.getAssignedTo(),
               task.getAssignedOn().format(TaskDto.DATE_FORMATTER)
       );
    }

}
