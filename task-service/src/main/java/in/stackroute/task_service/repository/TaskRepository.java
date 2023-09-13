package in.stackroute.task_service.repository;

import in.stackroute.task_service.model.Task;
import in.stackroute.task_service.model.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findAllByAssignedTo(String assignedTo);

    List<Task> findAllByAssignedToAndStatus(String uid, TaskStatus taskStatus);
}
