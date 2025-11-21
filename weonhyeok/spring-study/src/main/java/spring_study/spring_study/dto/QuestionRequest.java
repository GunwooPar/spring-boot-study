package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record QuestionRequest (
        Long id,
        @NotBlank
        String subject,
        @NotBlank
        String content,
        LocalDateTime createDate
) { }
