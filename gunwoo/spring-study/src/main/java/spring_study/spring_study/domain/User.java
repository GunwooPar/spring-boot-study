package spring_study.spring_study.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(name = "users") // H2 DB 예약어에 user있어서 users로 변경
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "password") // 비밀번호는 로그에 노출되지 않도록
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private Instant createdAt;

    @Enumerated(EnumType.STRING)  // JPA(DB)를 위한 것이지, Java 언어를 위한 것이 아님(DB에 저장할 때만 작동)
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
