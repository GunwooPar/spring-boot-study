package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.domain.Question;
import spring_study.spring_study.dto.QuestionResponseDTO;
import spring_study.spring_study.exception.question_exception.QuestionExceptionMessageEnum;
import spring_study.spring_study.exception.question_exception.QuestionNotFoundException;
import spring_study.spring_study.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;

    //DTO QuestionResponse 사용. 엔티티 클래스는 타임리프에 보내지 않도록 설계하자!
    public List<QuestionResponseDTO> getQuestionList() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionResponseDTO::from).toList();
        // .map(question -> QuestionResponse.from(question)).toList();  축약
    }

    /**
     * 질문 ID를 DB에서 찾아 질문 상세 데이터 Controller로 넘기기
     *
     * @param id 찾을 질문 ID
     * @return DB에 존재하는 ID이면 QuestionResponse DTO return  / 없다면 사용자 예외 발생
     */
    public QuestionResponseDTO getQuestionDto(Long id) {
        return questionRepository.findById(id)
                .map(QuestionResponseDTO::from)
                .orElseThrow(() -> new QuestionNotFoundException(
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

    /**
     * 새로운 Question 도메인 생성 메서드
     *
     * @param subject 질문 제목
     * @param content 질문 내용
     */
    public void create(String subject, String content) {
        Question question = Question.builder()
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .build();
        questionRepository.save(question);
    }

    //한 페이지에 10개씩  최신 게시글부터 내림차순 정렬
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }
}
