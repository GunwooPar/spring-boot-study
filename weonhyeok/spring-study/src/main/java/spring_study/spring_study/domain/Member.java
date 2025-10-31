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
    private Integer id;

    @Column(length = 50, nullable = false)
    private String userName;

    @Column(length = 10, nullable = false)
    private String userAge;

    private LocalDateTime createDate;


    public Member(String userName, String userAge, LocalDateTime createDate) {
        this.userName = userName;
        this.userAge = userAge;
        this.createDate = createDate;
    }

}
