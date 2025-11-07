package spring_study.spring_study.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(name = "users") // H2 DB 예약어에 user있어서 users로 변경
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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
