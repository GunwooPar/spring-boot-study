package spring_study.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_study.domain.Answer;
import spring_study.domain.Question;
import spring_study.repository.AnswerRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = Answer.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .question(question)
                .build();
        this.answerRepository.save(answer);

    }
}
