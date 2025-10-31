package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.domain.Member;
import spring_study.spring_study.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Integer id){
        return memberRepository.findById(id);
    }

    @Transactional
    public void delete(Integer id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        memberRepository.deleteById(id);
    }

    @Transactional
    public void update(Integer id, String userName, String userAge){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        member.setUserName(userName);
        member.setUserAge(userAge);
        memberRepository.save(member);
    }

    //ifPresent method에 대해 더 공부해보기.
    private void validMemberCheck(Member member) {
        if (Integer.parseInt(member.getUserAge()) <= 0) {
            throw new IllegalArgumentException("age 값은 0보다 커야합니다.");
        }
        memberRepository.findByUserName(member.getUserName())
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }
}
