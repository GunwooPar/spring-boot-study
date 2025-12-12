package spring_study.spring_study.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  id (질문 고유 번호)
 *  subject (제목)
 *  content (질문 내용)
 *  create (작성 일시)
 *  answerList (답변 엔티티 조회를 위한 필드)
 */
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    @NotBlank
    private String subject;

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String content;

    private LocalDateTime createDate;

    // cascade 속성 이용 질문 삭제시 질문에 답변 달았던 답변들도 모두 삭제.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
