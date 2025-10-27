package spring_study.spring_study.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;



    public enum Role {
        USER,
        ADMIN
    }

}
