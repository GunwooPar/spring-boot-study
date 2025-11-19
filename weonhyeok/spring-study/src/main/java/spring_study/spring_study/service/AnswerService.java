package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_study.spring_study.domain.Answer;
import spring_study.spring_study.domain.Question;
import spring_study.spring_study.repository.AnswerRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    /**
     * 질문 상세 정보 페이지에 존재하는 답변다는 창 textarea 태그의 내용을 이용
     * QuestionResponseDto를 인수로 사용하려 했지만, Answer domain은 dto가 아닌 question domain에 의존하여 dto를 못쓴다.
     * @param question 답변 달릴 질문글에 대한 객체
     * @param content input 태그의 내용
     */
    public void createAnswer(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answerRepository.save(answer);
    }
}
