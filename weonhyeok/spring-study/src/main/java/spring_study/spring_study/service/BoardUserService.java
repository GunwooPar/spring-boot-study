package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_study.spring_study.domain.BoardUser;
import spring_study.spring_study.repository.BoardUserRepository;

@Service
@RequiredArgsConstructor
public class BoardUserService {

    private final BoardUserRepository boardUserRepository;
    private final PasswordEncoder passwordEncoder;

    public BoardUser create(String userId, String userPassword, String userEmail) {
        BoardUser user = BoardUser.builder()
                .userId(userId)
                .userPassword(userPassword)
                .userEmail(userEmail)
                .build();

        user.setUserPassword(passwordEncoder.encode(userPassword));
        boardUserRepository.save(user);
        return user;
    }

}
