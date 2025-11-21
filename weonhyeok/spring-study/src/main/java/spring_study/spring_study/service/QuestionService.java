package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_study.spring_study.domain.Question;
import spring_study.spring_study.dto.QuestionResponse;
import spring_study.spring_study.exception.question_exception.QuestionExceptionMessageEnum;
import spring_study.spring_study.exception.question_exception.QuestionNotFoundException;
import spring_study.spring_study.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    //DTO QuestionResponse 사용. 엔티티 클래스는 타임리프에 보내지 않도록 설계하자!
    public List<QuestionResponse> getQuestionList() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionResponse::from).toList();
        // .map(question -> QuestionResponse.from(question)).toList();  축약
    }

    /**
     * 질문 ID를 DB에서 찾아 질문 상세 데이터 Controller로 넘기기
     *
     * @param id 찾을 질문 ID
     * @return DB에 존재하는 ID이면 QuestionResponse DTO return  / 없다면 사용자 예외 발생
     */
    public QuestionResponse getQuestionDto(Long id) {
        return questionRepository.findById(id)
                .map(QuestionResponse::from)
                .orElseThrow(()->new QuestionNotFoundException(
                        QuestionExceptionMessageEnum.NO_DATA_EXCEPTION
                ));
    }

    /**
     * 의존역전 문제로 인해 getQuestion 만들었다. dto가 아닌 그냥 도메인을 넘긴다.
     */
    public Question getQuestion(Long id) {
        Optional<Question> getQuestion = questionRepository.findById(id);
        if (getQuestion.isPresent()) {
            return getQuestion.get();
        }

        throw new QuestionNotFoundException(QuestionExceptionMessageEnum.NO_DATA_EXCEPTION);
    }
}
