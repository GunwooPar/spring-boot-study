package spring_study.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    //자식 @joincolumn(name = question_id ) 생략
    @ManyToOne
    private Question question;

    @Builder
    public Answer(String subject, String content, LocalDateTime createDate, Question question) {
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.question = question;
    }
}
