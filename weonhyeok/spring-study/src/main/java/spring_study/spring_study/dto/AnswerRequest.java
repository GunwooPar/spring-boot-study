package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * 답변 요청 DTO
 * @param id 답변 ID
 * @param content 답변 제목
 * @param createDate 답변 내용
 */
public record AnswerRequest(
    Long id,
    @NotBlank(message = "답변 제목은 필수입니다.")
    @Size(max = 200)
    String content,
    @NotBlank(message = "답변 내용은 필수입니다.")
    LocalDateTime createDate
) {}
