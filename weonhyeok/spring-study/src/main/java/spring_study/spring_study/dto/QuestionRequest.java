package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 질문 요청 DTO
 * @param subject 질문 제목
 * @param content 질문 내용
 */
public record QuestionRequest (
        @NotBlank(message = "질문 제목은 필수입니다.")
        @Size(max=200)
        String subject,
        @NotBlank(message = "내용은 필수 입니다.")
        String content
) { }
