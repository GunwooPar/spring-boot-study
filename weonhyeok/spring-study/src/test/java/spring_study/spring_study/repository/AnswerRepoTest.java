package spring_study.spring_study.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring_study.spring_study.domain.Answer;
import spring_study.spring_study.domain.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class AnswerRepoTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    //우선 테스트 코드에서는 setter를 사용한다.
    @Test
    void 답변등록_테스트(){
        Optional<Question> getQuestion = questionRepository.findBySubject("질문 제목");
        //question에 참조변수값 null이면 테스트 실패
        assertTrue(getQuestion.isPresent());
        Question question = getQuestion.get();

        Answer answer = new Answer();
        answer.setContent("질문 대답 자동 생성");
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    @Test
    void 답변데이터_조회_테스트(){
        Optional<Answer> getAnswer = answerRepository.findById(1L);
        assertTrue(getAnswer.isPresent());
        Answer answer = getAnswer.get();
        assertEquals(41,answer.getQuestion().getId());
    }
}
