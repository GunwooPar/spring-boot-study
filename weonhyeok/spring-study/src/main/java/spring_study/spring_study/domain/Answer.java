package spring_study.spring_study.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * id (답변 고유 번호)
 * content (답변 내용)
 * createDate (작성 일시)
 * question (질문 엔티티와 연결)
 * - 어떤 질문을 대답할지 알아야 하므로 Question의 정보가 필요하다.
 */
@Entity
@Getter
@Setter
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String content;

    private LocalDateTime createDate;

    // 부모 엔티티: Question  자식 엔티티: Answer
    @ManyToOne
    private Question question;
}
