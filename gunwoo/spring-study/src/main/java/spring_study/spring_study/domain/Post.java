package spring_study.spring_study.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private Status status;
    private Instant createdAt;


    public enum Status {
        DELETED,
        PUBLISHED
    }

}
