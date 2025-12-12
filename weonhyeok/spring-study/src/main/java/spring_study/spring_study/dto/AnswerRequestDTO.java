package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * 답변 요청 DTO
 * @param content 답변 제목
 * @param createDate 답변 생성 날짜
 */
public record AnswerRequestDTO(
    @NotBlank(message = "답변 제목은 필수입니다.")
    @Size(max = 500)
    String content,
    LocalDateTime createDate
) {}
