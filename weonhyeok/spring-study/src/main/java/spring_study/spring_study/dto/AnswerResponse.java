package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;
import spring_study.spring_study.domain.Answer;

import java.time.LocalDateTime;

public record AnswerResponse (
        Long id,
        @NotBlank
        String content,
        LocalDateTime createDate
) {
    public static AnswerResponse from(Answer answer) {
        return new AnswerResponse(
                answer.getId(),
                answer.getContent(),
                answer.getCreateDate()
        );
    }
}
