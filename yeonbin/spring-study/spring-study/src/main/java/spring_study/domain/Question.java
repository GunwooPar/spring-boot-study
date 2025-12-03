package spring_study.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
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
    //컬렉션에서 특정 답변 엔티티만 제거해도 해당 답변이 데이터베이스에서 자동으로 삭제
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Answer> answerList;

    @Builder
    public Question(String subject, String content, LocalDateTime createDate) {
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
    }
}