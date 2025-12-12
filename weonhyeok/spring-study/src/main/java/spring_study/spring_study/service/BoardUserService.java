package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_study.spring_study.domain.BoardUser;
import spring_study.spring_study.repository.BoardUserRepository;

//테스트 코드 돌리기 위해 일단 대충 만들었습니다.
@Service
@RequiredArgsConstructor
public class BoardUserService {

    private final BoardUserRepository boardUserRepository;

    public void createUser(BoardUser boardUser) {
        boardUserRepository.save(boardUser);
    }

    public BoardUser getBoardUser(Long id) {
        BoardUser byId = boardUserRepository.getReferenceById(id);
        return  byId;
    }
}
