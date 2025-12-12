# 구현 계획
<hr>

- [ ] crud 관련 예외 처리 및 테스트 코드 마저 생성, 커스텀 예외처리(공통주제)
- [ ] 로그인 구현
- [ ] sql 쪽을 flyway 마이그레이션 도입해서 모든 환경에 알아서 테이블같은거 생성되도록 만들기?
- [ ] N+1 문제 해결을 위한 DTO 변환 코드 추가 하기
- [ ] restful 하게 리팩토링 
- [ ] aop로 검증 로직분리 
- [ ] UI/UX 개선


# 느낀점
<hr>


# 공유하면 좋을점
<hr>

- 예)UsernameNotFoundException::new라고 쓰면, Java는 "파라미터를 받지 않는 기본 생성자"나 "파라미터를 그대로 전달받는 생성자"를 찾습니다.

# 트러블슈팅
<hr>

- [ ] 안녕하세요, <span sec:authentication="principal.user.username">사용자</span>님! 에서 안불러지는지 기본값인 사용자 만 나옴 


# 코드 리뷰에서 나온 수정 해야할 것들
<hr>

- [ ] 쿼리파리미터 대신 URL 깔끔하게 RedirectAttributes 사용(???????)
- [ ] URL 접근 규칙이 대체로 적절하나, H2 콘솔 보안을 고려하세요.(선택사항)
- [ ] Potential NullPointerException risk on line 60.(PostService), The same issue exists in deletePost at lines 76-78.

# 논의해볼 점
<hr>

- pr을 그냥 week 단위말고 기능 단위? 별로 하는게 리뷰할때도 편할듯하다? -> 그러면 감안해야할게 브랜치명이다.(브랜치명은 그냥 유지하는것도 하나의 방법이지 않을까?)
- 코드 컨벤션
- 이슈, 풀리퀘스트 컨벤션
- 깃헙 위키


