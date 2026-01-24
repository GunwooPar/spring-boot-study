package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring_study.spring_study.domain.BoardUser;
import spring_study.spring_study.domain.BoardUserRole;
import spring_study.spring_study.repository.BoardUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardUserSecurityService implements UserDetailsService {

    private final BoardUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<BoardUser> siteUser = userRepository.findByUserId(userId);

        if(siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        BoardUser user = siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(userId)) {
            authorities.add(new SimpleGrantedAuthority(BoardUserRole.ADMIN.getGrade()));
        } else {
            authorities.add(new SimpleGrantedAuthority(BoardUserRole.USER.getGrade()));
        }
        return new User(user.getUserId(), user.getUserPassword(), authorities);
    }
}
