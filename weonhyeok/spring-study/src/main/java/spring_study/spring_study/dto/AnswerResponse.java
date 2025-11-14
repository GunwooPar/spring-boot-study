package spring_study.spring_study.dto;

import spring_study.spring_study.domain.Answer;

import java.time.LocalDateTime;

public record AnswerResponse (
        Long id,
        String content,
        LocalDateTime createDate
) {
    public static AnswerResponse form(Answer answer) {
        return new AnswerResponse(
                answer.getId(),
                answer.getContent(),
                answer.getCreateDate()
        );
    }
}
