package spring_study.spring_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_study.spring_study.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // List<User> → Optional<User> 변경 (username은 unique하므로)
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username); // 중복체크

}
