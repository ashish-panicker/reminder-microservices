package in.stackroute.task_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    private String id;

    private String title;

    private TaskStatus status;

    private String assignedTo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate assignedOn;
}
