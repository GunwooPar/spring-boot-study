package spring_study;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import spring_study.domain.Answer;
import spring_study.domain.Question;
import spring_study.repository.AnswerRepository;
import spring_study.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QNARepoTest {

    //질문 엔티티의 데이터를 생성할 때 리포지토리가 필요 -> 의존성 주입
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @LocalServerPort
    private int port;

    private Question savedQ1;
    private Question savedQ2;
    private Answer savedA;

    @Test
    @DisplayName("서버 포트 확인 테스트")
    void checkPort() {
        log.info(">>>>>> 할당된 서버 포트: {}", port);
        assertEquals(true, port > 0, "서버 포트가 할당되지 않았습니다.");
        log.info(">>>>>> H2 Console URL: http://localhost:{}/h2-console", port);
    }

    @Test
    @DisplayName("DB 연결 및 데이터 삽입 기본 확인")
    void checkDbConnection() {
        long count = questionRepository.count();
        log.info(">>>>>> 현재 DB에 저장된 Question 수: {}", count);
    }

    @BeforeEach
    void setUp() {
        Question q1 = Question.builder()
                .subject("질문제목1")
                .content("질문내용1")
                .createDate(LocalDateTime.now())
                .build();
        this.savedQ1 = this.questionRepository.save(q1);

        Question q2 = Question.builder()
                .subject("질문제목2")
                .content("질문내용2")
                .createDate(LocalDateTime.now())
                .build();
        this.savedQ2 = this.questionRepository.save(q2);

        //Q2 -> a
        Optional<Question> oq = this.questionRepository.findById(savedQ2.getId());
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = Answer.builder()
                .content("네 자동으로 생성됩니다.")
                .question(q)
                .createDate(LocalDateTime.now())
                .build();
        this.savedA= this.answerRepository.save(a);

        List<Answer> allAnswer = this.answerRepository.findAll();
        log.info("DB에 저장된 총 Answer 개수 (setUp 내부 확인): {}", allAnswer.size());
    }

    @Test
    void test1() {
        List<Question> all = this.questionRepository.findAll();
        log.info("데이터 총 개수: {}", all.size());
        assertEquals(2, all.size());
    }

    @Test
    void test2() {
        /*optional을 사용하지 않으면 매번 null체크해야함
        optional은 op.isPresent(), get(), orElse()와 같은 상자관리용 메소드만 가짐
        getSubject()와 같은 데이터를 다루는 메소드X*/
        Optional<Question> oq = this.questionRepository.findById(savedQ1.getId());
        if (oq.isPresent()) {
            Question q = oq.get();
            assertEquals("질문제목1", q.getSubject());
        }
    }

    @Test
    void test3() {
        Question q = this.questionRepository.findBySubject("질문제목1");
        assertEquals("질문내용1", q.getContent());
    }

    @Test
    void test4() {
        Question q = this.questionRepository.findBySubjectAndContent("질문제목2", "질문내용2");
        assertEquals(savedQ2.getId(), q.getId());
    }

    @Test
    void test5() {
        List<Question> qList = this.questionRepository.findBySubjectLike("%1");
        Question q = qList.get(0);
        assertEquals("질문제목1", q.getSubject());
    }
    @Test
    void test6() {
        //builder는 수정할 수 없고 값 넣기만 가능 -> setter 사용
        Optional<Question> oq = this.questionRepository.findById(savedQ1.getId());
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);
    }

    @Test
    void test7() {
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(savedQ1.getId());
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        log.info("아이디 : {}", q.getId());
        assertEquals(1, this.questionRepository.count());
    }

    @Test
    void test8() {
        Optional<Answer> oa = this.answerRepository.findById(savedA.getId());
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(savedQ2.getId(),a.getQuestion().getId());
    }


    @Test
    void test9() {
        Optional<Question> oq = this.questionRepository.findById(savedQ2.getId());
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        //question, answer 연결X
        assertNotNull(answerList, "null이 아니어야");
        log.info("개수: {}", answerList.size());

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
}
