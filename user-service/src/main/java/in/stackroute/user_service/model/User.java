package in.stackroute.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data               // Lombok annotation to generate getters, setters, toString, hashcode, equals
@AllArgsConstructor // Lombok annotation to generate constructor with all fields
@NoArgsConstructor  // Lombok annotation to generate constructor with no fields
@Builder            // Lombok annotation to generate builder pattern for the object
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String userName;

    private String password;
}
