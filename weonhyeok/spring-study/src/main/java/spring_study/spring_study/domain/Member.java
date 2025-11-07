package spring_study.spring_study.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String userName;

    //나중에 암호화 확장성 예정
    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String userAge;

    //"USER", "ADMIN" 등급 설정을 위한 컬럼
    @Column(nullable = false)
    private String role;

    private LocalDateTime createDate;

    public Member(String userName, String password, String userAge, String role, LocalDateTime createDate) {
        this.userName = userName;
        this.password = password;
        this.userAge = userAge;
        this.role = role;
        this.createDate = createDate;
    }
}
