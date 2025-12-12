package spring_study.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Data
@Table(name = "QUESTION")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "Text")
    private String content;

    private LocalDateTime createDate;

    //부모
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();

    @Builder
    public Question(String subject, String content, LocalDateTime createDate) {
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
    }
}