package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AnswerRequest(
    Long id,
    @NotBlank(message = "답변 제목은 필수입니다.")
    @Size(max = 200)
    String content,
    @NotBlank(message = "답변 내용은 필수입니다.")
    LocalDateTime createDate
) {}
