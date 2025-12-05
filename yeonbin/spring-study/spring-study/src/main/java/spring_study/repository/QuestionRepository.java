package spring_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_study.domain.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    //JPA에 리포지토리의 메소드명을 분석해 쿼리를 만들고 실행하는 기능이 있음 > 메소드 선언만해도 ok
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    //특정 문자열을 포함한 데이터를 열에서 찾을 때 List 사용
    List<Question> findBySubjectLike(String subject);
}
