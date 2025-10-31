package spring_study.spring_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_study.spring_study.domain.Member;

import java.util.Optional;

// Member클래스의 기본키는 Integer이다.
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUserName(String userName);
}
