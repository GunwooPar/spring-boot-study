package spring_study.spring_study.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(name = "users") // H2 DB 예약어에 user있어서 users로 변경
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "password")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true,  nullable = false)
    public String password;

    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = Instant.now();
    }

    public enum Role {
        USER,
        ADMIN
    }

}
