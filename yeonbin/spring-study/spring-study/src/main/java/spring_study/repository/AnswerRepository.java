package spring_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_study.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
