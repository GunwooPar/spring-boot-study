package spring_study.spring_study.dto;

import spring_study.spring_study.domain.Question;

import java.time.LocalDateTime;

public record QuestionResponse (
        Long id,
        String subject,
        String content,
        LocalDateTime createDate
) {
    public static QuestionResponse from(Question q) {
        return new QuestionResponse(
                q.getId(),
                q.getSubject(),
                q.getContent(),
                q.getCreateDate()
        );
    }
}
