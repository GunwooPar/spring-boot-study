# 🏗️ Builder 패턴 완벽 가이드

## 목차
1. [Builder란 무엇인가?](#1-builder란-무엇인가)
2. [왜 Builder를 사용하나?](#2-왜-builder를-사용하나)
3. [사용 방법](#3-사용-방법)
4. [실전 예시](#4-실전-예시)
5. [장단점](#5-장단점)
6. [언제 사용하나?](#6-언제-사용하나)

---

## 1. Builder란 무엇인가?

### 📝 정의
**Builder 패턴**은 복잡한 객체를 단계별로 생성할 수 있게 해주는 **디자인 패턴**입니다.

### 🎯 핵심 개념
```java
// Builder 없이 (생성자 사용)
Post post = new Post(null, "제목", "내용", user, Status.PUBLISHED, Instant.now());
// ↑ 뭐가 뭔지 모르겠음...

// Builder 사용
Post post = Post.builder()
    .title("제목")        // 명확함!
    .content("내용")      // 가독성 좋음!
    .user(user)
    .status(Status.PUBLISHED)
    .createdAt(Instant.now())
    .build();
```

---

## 2. 왜 Builder를 사용하나?

### 문제 상황: 생성자 지옥

#### ❌ 생성자만 사용할 때의 문제

**예시: Post 객체 생성**
```java
public class Post {
    private Long id;
    private String title;
    private String content;
    private User user;
    private Status status;
    private Instant createdAt;

    // 생성자 1: 전체 필드
    public Post(Long id, String title, String content, User user, Status status, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.status = status;
        this.createdAt = createdAt;
    }

    // 생성자 2: id 없이
    public Post(String title, String content, User user, Status status, Instant createdAt) {
        this(null, title, content, user, status, createdAt);
    }

    // 생성자 3: status 기본값
    public Post(String title, String content, User user, Instant createdAt) {
        this(null, title, content, user, Status.PUBLISHED, createdAt);
    }

    // 생성자 4, 5, 6... 계속 늘어남 😱
}
```

**사용할 때:**
```java
// 1. 파라미터 순서를 외워야 함
Post post1 = new Post("제목", "내용", user, Instant.now());

// 2. 뭐가 뭔지 모름
Post post2 = new Post(null, "제목", "내용", user, Status.PUBLISHED, Instant.now());
//                    ↑ 이게 뭐지?

// 3. 타입이 같으면 실수하기 쉬움
Post post3 = new Post("내용", "제목", user, Instant.now());  // 제목이랑 내용 순서 바뀜! 버그!
```

### ✅ Builder 패턴의 해결

```java
Post post = Post.builder()
    .title("제목")        // 명확한 필드명
    .content("내용")      // 순서 상관없음
    .user(user)
    .status(Status.PUBLISHED)
    .createdAt(Instant.now())
    .build();
```

**장점:**
1. ✅ **가독성**: 필드명이 명시되어 뭐가 뭔지 명확
2. ✅ **순서 자유**: 파라미터 순서를 외울 필요 없음
3. ✅ **선택적 설정**: 필요한 필드만 설정
4. ✅ **실수 방지**: 타입이 같아도 필드명으로 구분
5. ✅ **유지보수**: 필드 추가/제거가 쉬움

---

## 3. 사용 방법

### 3.1 Lombok @Builder 사용 (가장 간단)

#### Post 엔티티
```java
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor  // Builder에 필요
@Builder             // 이것만 추가하면 끝!
@ToString(exclude = {"user"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Instant createdAt;

    public enum Status {
        DELETED,
        PUBLISHED
    }
}
```

#### PostService에서 사용
```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(String title, String content, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));

        // Builder 사용!
        Post post = Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .status(Post.Status.PUBLISHED)
                .createdAt(Instant.now())
                .build();

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }
}
```

### 3.2 Lombok이 자동 생성하는 코드

Lombok의 `@Builder`는 내부적으로 이런 코드를 자동 생성합니다:

```java
public class Post {
    // 필드들...

    // Lombok이 자동 생성
    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {
        private String title;
        private String content;
        private User user;
        private Status status;
        private Instant createdAt;

        PostBuilder() {}

        public PostBuilder title(String title) {
            this.title = title;
            return this;  // 체이닝을 위해 자기 자신 반환
        }

        public PostBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostBuilder user(User user) {
            this.user = user;
            return this;
        }

        public PostBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public PostBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Post build() {
            return new Post(null, title, content, user, status, createdAt);
        }
    }
}
```

### 3.3 메서드 체이닝의 비밀

```java
Post.builder()           // PostBuilder 객체 반환
    .title("제목")        // this 반환 (PostBuilder)
    .content("내용")      // this 반환 (PostBuilder)
    .user(user)          // this 반환 (PostBuilder)
    .build();            // Post 객체 생성 및 반환
```

**핵심**: 각 메서드가 `this`를 반환해서 계속 체이닝할 수 있습니다!

---

## 4. 실전 예시

### 4.1 게시글 생성 (다양한 상황)

#### 일반 게시글
```java
Post post = Post.builder()
    .title("Spring Boot 입문")
    .content("Spring Boot를 배워봅시다.")
    .user(user)
    .status(Post.Status.PUBLISHED)
    .createdAt(Instant.now())
    .build();
```

#### 임시 저장 (Draft)
```java
Post draft = Post.builder()
    .title("작성 중인 글")
    .content("아직 완성 안됨")
    .user(user)
    .status(Post.Status.DRAFT)  // 임시 저장 상태
    .createdAt(Instant.now())
    .build();
```

#### 예약 발행
```java
Post scheduledPost = Post.builder()
    .title("내일 발행될 글")
    .content("예약 발행 테스트")
    .user(user)
    .status(Post.Status.SCHEDULED)
    .publishAt(Instant.now().plus(1, ChronoUnit.DAYS))  // 내일
    .createdAt(Instant.now())
    .build();
```

### 4.2 User 생성 예시

```java
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER,
        ADMIN
    }
}
```

#### 사용
```java
// 일반 사용자 생성
User user = User.builder()
    .username("john")
    .role(User.Role.USER)
    .createdAt(Instant.now())
    .build();

// 관리자 생성
User admin = User.builder()
    .username("admin")
    .role(User.Role.ADMIN)
    .createdAt(Instant.now())
    .build();
```

### 4.3 Comment 생성 예시

```java
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private Instant createdAt;
}
```

#### 사용
```java
Comment comment = Comment.builder()
    .content("좋은 글이네요!")
    .post(post)
    .user(user)
    .createdAt(Instant.now())
    .build();
```

---

## 5. 장단점

### ✅ 장점

#### 1. 가독성
```java
// 생성자 방식 (나쁨)
Post post = new Post(null, "제목", "내용", user, Status.PUBLISHED, Instant.now());

// Builder 방식 (좋음)
Post post = Post.builder()
    .title("제목")
    .content("내용")
    .user(user)
    .status(Status.PUBLISHED)
    .createdAt(Instant.now())
    .build();
```

#### 2. 유연성
```java
// 필수 필드만 설정
Post minimalPost = Post.builder()
    .title("제목")
    .user(user)
    .build();

// 모든 필드 설정
Post fullPost = Post.builder()
    .title("제목")
    .content("내용")
    .user(user)
    .status(Status.PUBLISHED)
    .createdAt(Instant.now())
    .build();
```

#### 3. 불변 객체 생성
```java
@Value  // 불변 객체
@Builder
public class ImmutablePost {
    String title;
    String content;
    // setter가 없음 → 생성 후 변경 불가
}
```

#### 4. 기본값 설정
```java
@Builder
public class Post {
    private String title;

    @Builder.Default  // 기본값 설정
    private Status status = Status.DRAFT;

    @Builder.Default
    private Instant createdAt = Instant.now();
}

// 사용
Post post = Post.builder()
    .title("제목")
    .build();  // status는 DRAFT, createdAt은 현재 시간
```

### ❌ 단점

#### 1. 코드량 증가
- 실제 Builder 클래스를 만들면 코드가 길어짐
- Lombok 사용하면 해결됨

#### 2. 런타임 에러 가능성
```java
Post post = Post.builder()
    .title("제목")
    // user를 설정 안함!
    .build();

// 나중에 NullPointerException 발생 가능
```

**해결 방법: 필수 필드 검증**
```java
@Builder
public class Post {
    @NonNull  // Lombok: null 체크
    private String title;

    @NonNull
    private User user;
}

// 또는 build() 메서드에서 검증
public Post build() {
    if (title == null || user == null) {
        throw new IllegalStateException("필수 필드 누락");
    }
    return new Post(this);
}
```

---

## 6. 언제 사용하나?

### ✅ Builder를 사용하면 좋은 경우

#### 1. **필드가 많을 때** (4개 이상)
```java
// 필드가 10개 → Builder 필수!
User user = User.builder()
    .username("john")
    .email("john@example.com")
    .password("encrypted")
    .firstName("John")
    .lastName("Doe")
    .age(30)
    .address("Seoul")
    .phone("010-1234-5678")
    .role(Role.USER)
    .createdAt(Instant.now())
    .build();
```

#### 2. **선택적 파라미터가 많을 때**
```java
// 검색 조건: 모두 선택적
SearchCriteria criteria = SearchCriteria.builder()
    .keyword("Spring")
    .category("Programming")
    .minPrice(10000)
    // maxPrice는 설정 안함
    // author는 설정 안함
    .build();
```

#### 3. **불변 객체를 만들 때**
```java
@Value
@Builder
public class Money {
    BigDecimal amount;
    String currency;
}

Money price = Money.builder()
    .amount(new BigDecimal("10000"))
    .currency("KRW")
    .build();
// 생성 후 변경 불가!
```

#### 4. **복잡한 객체 생성 로직이 있을 때**
```java
Order order = Order.builder()
    .orderNumber(generateOrderNumber())
    .customer(customer)
    .items(items)
    .totalAmount(calculateTotal(items))
    .discount(calculateDiscount(customer, items))
    .shippingFee(calculateShippingFee(items))
    .orderDate(Instant.now())
    .build();
```

### ❌ Builder를 사용하지 않아도 되는 경우

#### 1. 필드가 적을 때 (1-3개)
```java
// 이 정도는 생성자가 더 간단
public class SimpleDto {
    private String name;
    private int age;

    public SimpleDto(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

SimpleDto dto = new SimpleDto("John", 30);  // 이게 더 깔끔
```

#### 2. 모든 필드가 필수일 때
```java
// 모두 필수 → 생성자가 나음
public class Coordinate {
    private double latitude;
    private double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
```

---

## 7. 코드리뷰 대비 설명

### Q: "왜 Builder를 사용했나요?"

**답변 예시:**
"Post 엔티티에 필드가 6개 있고, 생성 시 선택적으로 설정해야 하는 경우가 있어서 Builder 패턴을 사용했습니다. 생성자 오버로딩보다 가독성이 좋고, 필드명이 명시되어 실수를 줄일 수 있습니다. Lombok의 @Builder를 사용해 보일러플레이트 코드 없이 깔끔하게 구현했습니다."

### Q: "@NoArgsConstructor와 @AllArgsConstructor를 같이 쓰는 이유는?"

**답변 예시:**
"@NoArgsConstructor(access = AccessLevel.PROTECTED)는 JPA가 Entity를 생성할 때 필요하고, @AllArgsConstructor는 @Builder가 내부적으로 사용하기 위해 필요합니다. PROTECTED로 설정해서 외부에서 직접 생성자를 호출할 수 없게 하고, Builder를 통해서만 생성하도록 강제했습니다."

### Q: "Builder 대신 정적 팩토리 메서드는 어떤가요?"

**답변 예시:**
"정적 팩토리 메서드도 좋은 선택입니다. 상황에 따라 다른데, 필드가 많고 조합이 다양하면 Builder가 유리하고, 생성 패턴이 명확하고 제한적이면 정적 팩토리 메서드가 더 적합합니다. 현재는 필드가 많고 유연한 생성이 필요해서 Builder를 선택했습니다."

---

## 8. 요약

### 핵심 정리

```java
// Builder 사용 전
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    // ...
    // Service에서 new Post() 불가능!
}

// Builder 사용 후
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor  // Builder에 필요
@Builder             // 추가!
public class Post {
    // ...
}

// Service에서 사용
Post post = Post.builder()
    .title("제목")
    .content("내용")
    .user(user)
    .status(Status.PUBLISHED)
    .createdAt(Instant.now())
    .build();
```

### 체크리스트

Builder 패턴을 사용하기 전에:
- [ ] 필드가 4개 이상인가?
- [ ] 선택적 파라미터가 있는가?
- [ ] 생성자 오버로딩이 복잡한가?
- [ ] 가독성이 중요한가?

→ 하나라도 Yes면 Builder 사용 고려!

---

**Builder 패턴은 복잡한 객체 생성을 우아하게 만드는 강력한 도구입니다! 💪**