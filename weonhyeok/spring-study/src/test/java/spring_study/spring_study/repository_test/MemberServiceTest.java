package spring_study.spring_study.repository_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.domain.Member;
import spring_study.spring_study.repository.MemberRepository;
import spring_study.spring_study.service.MemberService;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@Transactional
public class MemberServiceTest {
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void clearRepository(){
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("create 테스트")
    @Test
    void create_테스트(){
        //given
        Member test1 = new Member("테스트1", "1", LocalDateTime.now());

        //when
        memberService.join(test1);

        //then
        Member found = memberRepository.findByUserName("테스트1").orElseThrow(
                ()-> new IllegalArgumentException("저장된 회원 없습니다.")
        );
        assertThat(found.getUserName()).isEqualTo("테스트1");
    }


    @DisplayName("read 테스트")
    @Test
    void read_전체조회_테스트(){
        //given
        Member member1 = new Member("A", "30", LocalDateTime.now());
        Member member2 = new Member("B","33",LocalDateTime.now());
        memberService.join(member1);
        memberService.join(member2);

        //when
        List<Member> all = memberRepository.findAll();

        //then
        assertThat(all).hasSize(2);
        assertThat(all)
                .extracting(Member::getUserName)
                .containsExactlyInAnyOrder("A","B");
    }

    @DisplayName("삭제 테스트")
    @Test
    void 삭제_테스트(){
        //given
        Member member1 = new Member("A", "30", LocalDateTime.now());
        memberService.join(member1);

        //when
        memberService.delete(1);

        //then
        assertThat(memberRepository.findById(1)).isEmpty();
    }
}
