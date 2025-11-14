package spring_study.spring_study.dto;

import java.time.LocalDateTime;

public record QuestionRequest (
        Long id,
        String subject,
        String content,
        LocalDateTime createDate
) { }
