package spring_study.spring_study.dto;

import spring_study.spring_study.domain.Answer;

import java.time.LocalDateTime;

public record AnswerResponseDTO(
        Long id,
        String content,
        LocalDateTime createDate
) {
    public static AnswerResponseDTO from(Answer answer) {
        return new AnswerResponseDTO(
                answer.getId(),
                answer.getContent(),
                answer.getCreateDate()
        );
    }
}
