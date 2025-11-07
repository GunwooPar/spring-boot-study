package spring_study.spring_study.repository_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.domain.Member;
import spring_study.spring_study.repository.*;

import java.time.LocalDateTime;

@DataJpaTest
@Transactional
public class MemberRepoTest {
    @Autowired
    private MemberRepository memberRepository;

    /**
     *  test 코드 실행 전 db 초기화
     */
    @AfterEach
    public void clearRepository(){
        memberRepository.deleteAllInBatch();
    }


    @DisplayName("Create 테스트")
    @Test
    void 회원등록(){
        //given
        Member member = new Member("weon","20",LocalDateTime.now());

        //when
        memberRepository.save(member);
        //then
        assertEquals(1, memberRepository.count());
    }

    @DisplayName("ID 자동증가 테스트")
    @Test
    void ID값_테스트(){
        final int userCount = 2;
        //given
        Member createUser1 = new Member("user1", "10", LocalDateTime.now());
        Member createUser2 = new Member("user2", "20", LocalDateTime.now());

        //when
        memberRepository.save(createUser1);
        memberRepository.save(createUser2);

        //then
        assertThat(userCount).isEqualTo(memberRepository.count());
    }

}
