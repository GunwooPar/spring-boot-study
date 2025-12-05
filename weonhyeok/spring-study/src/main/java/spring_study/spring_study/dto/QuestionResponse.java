package spring_study.spring_study.dto;

import spring_study.spring_study.domain.Question;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Question응답 DTO이다. question 엔티티에 반드시 필요한 맴버들이 존재
 * @param id question id
 * @param subject question title
 * @param content question content
 * @param createDate question create date
 * @param answerList 자식 엔티티인 모든 answer 엔티티
 */
public record QuestionResponse (
        Long id,
        String subject,
        String content,
        LocalDateTime createDate,
        List<AnswerResponse> answerList
) {
    public static QuestionResponse from(Question q) {
        return new QuestionResponse(
                q.getId(),
                q.getSubject(),
                q.getContent(),
                q.getCreateDate(),
                q.getAnswerList().stream().map(AnswerResponse::from).toList()
        );
    }
}
