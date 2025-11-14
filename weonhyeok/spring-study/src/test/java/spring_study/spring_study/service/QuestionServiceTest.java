package spring_study.spring_study.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.dto.QuestionResponse;
import spring_study.spring_study.repository.QuestionRepository;

@SpringBootTest
@Transactional
public class QuestionServiceTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;

    @DisplayName("없는 질문 검색으로 예외 발생시키기")
    @Test
    void 질문상세페이지가져오기_테스트(){
        //given
        final Long id = 999L;

        //when
        QuestionResponse question = questionService.getQuestion(id);

        //then
    }
}
