package spring_study.spring_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_study.spring_study.domain.BoardUser;

import java.util.Optional;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
    Optional<BoardUser> findByUserId(String userId);

//    void findUserEmail(String userEmail);
}
