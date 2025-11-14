package spring_study.spring_study.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.domain.Answer;
import spring_study.spring_study.domain.Question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class QuestionRepoTest {
    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp(){
        questionRepository.deleteAll();

        Question testQuestion = Question.builder()
                .subject("질문 제목")
                .content("질문 내용")
                .createDate(LocalDateTime.now())
                .build();
        questionRepository.save(testQuestion);

        Question testQuestion2 = Question.builder()
                .subject("질문 제목2")
                .content("question content")
                .createDate(LocalDateTime.of(2000,1,1,1,1,1))
                .build();
        questionRepository.save(testQuestion2);
    }

    @Test
    void findAll_테스트() {
        //given
        List<Question> all = questionRepository.findAll();
        assertEquals(2, all.size());

        Question getFirstQuestion = all.getFirst();
        assertEquals("질문 제목", getFirstQuestion.getSubject());

    }

    @Test
    void findById_테스트() {
        //given
        Optional<Question> result = questionRepository.findById(1L);

        if (result.isPresent()) {
            Question question = result.get();
            assertEquals("질문 제목", question.getSubject());
        }
    }

    @Test
    void findBySubject_테스트() {
        final String subject = "질문 제목";
        Optional<Question> subject2 = questionRepository.findBySubject(subject);

        if (subject2.isPresent()) {
            Question question = subject2.get();
            assertEquals(subject, question.getSubject());
        }
    }


    @Test
    void AND연산자_테스트() {
        final String subject = "질문 제목";
        final String content = "질문은 본문";
        Optional<Question> findQuestData = questionRepository.findBySubjectAndContent(subject, content);

        if (findQuestData.isPresent()) {
            Question question = findQuestData.get();
            assertEquals(subject, question.getSubject());
            assertEquals(content, question.getContent());
        }
    }

    /**
     * @Transactional이 없다면 에러 발생 (테스트 실행)
     * 이유: findBySubject 이후 세션이 중간에 끊기기 때문
     */
    @Test
    void 질문도메인에서_답변된글들_찾기(){
        Optional<Question> getQuestion = questionRepository.findBySubject("질문 제목");
        assertTrue(getQuestion.isPresent());
        Question question = getQuestion.get();

        List<Answer> answerList = question.getAnswerList();

        assertEquals(1,answerList.size());
        assertEquals("질문 대답 자동 생성",answerList.get(0).getContent());
    }
}
