package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.domain.Member;
import spring_study.spring_study.exception.*;
import spring_study.spring_study.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void join(Member member) {
        validMemberCheck(member);
        memberRepository.save(member);
    }

    public Member login(String userName, String password){
        Member loginMember = memberRepository.findByUserName(userName)
                .orElseThrow(MemberNotFoundException::new);

        if (! loginMember.getPassword().equals(password)){
            throw new InvalidPasswordException();
        }

        return loginMember;
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        memberRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, String userName, String userAge) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        member.setUserName(userName);
        member.setUserAge(userAge);
        memberRepository.save(member);
    }

    //ifPresent method에 대해 더 공부해보기
    //exception 디렉토리에 존재하는 예외 클래스들로 예외 발생
    private void validMemberCheck(Member member) {
        if (Long.parseLong(member.getUserAge()) <= 0) {
            throw new InvalidAgeException();
        }
        if (member.getUserName().isEmpty()) {
            throw new EmptyUserNameException();
        }
        memberRepository.findByUserName(member.getUserName())
                .ifPresent(m -> {
                    throw new DuplicateMemberException();
                });
    }
}
