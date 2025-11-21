package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record AnswerRequest(
    Long id,
    @NotBlank
    String content,
    @NotBlank
    LocalDateTime createDate
) {}
