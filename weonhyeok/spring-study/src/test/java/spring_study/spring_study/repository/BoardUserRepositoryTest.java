package spring_study.spring_study.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.domain.BoardUser;
import spring_study.spring_study.service.BoardUserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class BoardUserRepositoryTest {

    @Autowired
    private BoardUserService userService;

    @Test
    @DisplayName("유저 insert 테스트")
    void 유저리포지토리_가입_테스트() {
        //given
        final Long id = 1L;
        BoardUser boardUser = BoardUser.builder()
                .userPassword("123")
                .userEmail("test@email")
                .userId("12345").build();
        //when
        userService.createUser(boardUser);

        //then
        assertEquals(id,userService.getBoardUser(1L).getId());

    }
}
