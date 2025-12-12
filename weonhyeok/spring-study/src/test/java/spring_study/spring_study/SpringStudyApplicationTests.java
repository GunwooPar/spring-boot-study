package spring_study.spring_study;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.service.QuestionService;

@SpringBootTest
@Transactional
class SpringStudyApplicationTests {
    @Autowired
    private QuestionService questionService;

    // 질문등록 300개 테스트하기
    @Test
    void testJpa() {
        for(int i=1; i<=300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]",i);
            String content = "테스트입니다."+i;
            questionService.create(subject,content);
        }
    }
}
