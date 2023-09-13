package in.stackroute.task_service.service;

import in.stackroute.task_service.dto.TaskCreated;
import in.stackroute.task_service.dto.TaskDto;
import org.springframework.data.util.Optionals;

import java.util.List;
import java.util.Optional;

public interface TaskService {


    TaskCreated createTask(TaskDto dto);

    List<TaskDto> getAllTasks(String uid);

    List<TaskDto> getAllTasks(String uid, String status);

    Optional<TaskDto> getTaskById(String id);

    Optional<TaskDto> updateTaskById(String id, TaskDto dto);
}
