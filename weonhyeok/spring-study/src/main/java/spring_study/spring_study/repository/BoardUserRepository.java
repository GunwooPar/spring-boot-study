package spring_study.spring_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_study.spring_study.domain.BoardUser;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {

}
