package spring_study.spring_study.dto;

import spring_study.spring_study.domain.Question;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Question응답 DTO이다. question 엔티티에 반드시 필요한 맴버들이 존재
 *
 * @param id         질문 고유 아이디값
 * @param subject    질문 제목
 * @param content    질문 내용
 * @param createDate 질문 생성 날짜
 * @param answerList 자식 엔티티인 답변 엔티티
 */
public record QuestionResponseDTO(
        Long id,
        String subject,
        String content,
        LocalDateTime createDate,
        List<AnswerResponseDTO> answerList
) {
    public static QuestionResponseDTO from(Question q) {
        return new QuestionResponseDTO(
                q.getId(),
                q.getSubject(),
                q.getContent(),
                q.getCreateDate(),
                q.getAnswerList().stream().map(AnswerResponseDTO::from).toList()
        );
    }
}
