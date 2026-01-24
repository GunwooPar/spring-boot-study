# 🚀 JPA N+1 문제: 성능 지옥에서 탈출기

## 발표 개요
Spring Boot + JPA 프로젝트 진행 중 발생한 심각한 성능 저하 문제(N+1)를 발견하고, 이를 해결하기 위한 3가지 접근 방식(EAGER, Fetch Join, Batch Size)을 비교 분석합니다. 특히 페이징 환경에서의 치명적인 메모리 문제를 다루고 최적의 해결책을 제시합니다.

---

## 1. 🚨 문제 상황 (The Problem)

게시글 상세 조회 시, 댓글 목록을 가져오는 과정에서 의도치 않은 수십 번의 쿼리가 발생했습니다.

### 1-1. 실제 발생 로그 (N+1)

**상황:** 댓글이 5개 달린 게시글 조회

**결과:** 쿼리가 총 **8번** 발생 (게시글 조회 1번 + 댓글 조회 1번 + 각 댓글의 작성자 조회 5번)

```sql
-- 1️⃣ 게시글 조회 (OK)
Hibernate:
    select
        p1_0.id,
        p1_0.content,
        p1_0.created_at,
        p1_0.status,
        p1_0.title,
        p1_0.user_id,
        u1_0.id,
        u1_0.created_at,
        u1_0.password,
        u1_0.role,
        u1_0.username
    from
        post p1_0
    join
        users u1_0
            on u1_0.id=p1_0.user_id
    where
        p1_0.id=?
        and (
            p1_0.status != 'DELETED'
        )

-- 2️⃣ 댓글 조회 (OK)
Hibernate:
    select
        c1_0.id,
        c1_0.content,
        c1_0.created_at,
        c1_0.post_id,
        c1_0.status,
        c1_0.user_id
    from
        comment c1_0
    left join
        post p1_0
            on p1_0.id=c1_0.post_id
            and (p1_0.status != 'DELETED')
    where
        (
            c1_0.status != 'DELETED'
        )
        and p1_0.id=?

-- 3️⃣ 첫 번째 댓글 작성자 조회 ⚠️ (N+1 시작!)
Hibernate:
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.password,
        u1_0.role,
        u1_0.username
    from
        users u1_0
    where
        u1_0.id=?

-- 4️⃣ 두 번째 댓글 작성자 조회 ⚠️
Hibernate:
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.password,
        u1_0.role,
        u1_0.username
    from
        users u1_0
    where
        u1_0.id=?

-- 5️⃣ 세 번째 댓글 작성자 조회 ⚠️
Hibernate:
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.password,
        u1_0.role,
        u1_0.username
    from
        users u1_0
    where
        u1_0.id=?

-- 6️⃣ 네 번째 댓글 작성자 조회 ⚠️
Hibernate:
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.password,
        u1_0.role,
        u1_0.username
    from
        users u1_0
    where
        u1_0.id=?

-- 7️⃣ 다섯 번째 댓글 작성자 조회 ⚠️
Hibernate:
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.password,
        u1_0.role,
        u1_0.username
    from
        users u1_0
    where
        u1_0.id=?
```

**디버그 로그:**
```
===== 게시글 상세 조회 디버그 =====
postId: 1
조회된 댓글 개수: 5

- commentId: 1, content: 좋은 글 감사합니다!
- commentId: 2, content: 저도 같이 공부하고 있어요!
- commentId: 3, content: ㅇㄴㄻㅇㄴㄹ
- commentId: 4, content: ㅇㄻㅇㄹㄴㄹ
- commentId: 5, content: ㄴㅇㄹㄴㅇㄹ
====================================
```

### 1-2. 원인 분석

**기존 코드 (CommentRepository):**

```java
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 단순 조회 - User 정보는 Lazy Loading
    List<Comment> findByPostId(Long postId);
}
```

**Comment 엔티티:**

```java
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)  // ← Lazy Loading 설정
    private User user;  // ← 댓글 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
```

**문제 발생 흐름:**

```
1. findByPostId(1L) 실행
   → SELECT * FROM comment WHERE post_id = 1  (댓글 5개 조회)

2. Thymeleaf에서 댓글 렌더링
   → th:text="${comment.user.username}"  ← 여기서 User 정보 필요!

3. User가 Lazy Loading이라 아직 로딩 안 됨
   → Hibernate가 User를 가져오기 위해 쿼리 추가 실행

4. 5개 댓글마다 반복
   → SELECT * FROM users WHERE id = ?  (5번 반복!)
```

**결과:**
- 쿼리 1번으로 끝날 일이 **N+1번**(여기서는 5+1=6번) 실행
- 댓글이 100개라면? → **101번** 쿼리 실행! 💣

---

## 2. ❌ 잘못된 해결책: EAGER (즉시 로딩)

가장 먼저 떠오른 방법은 연관관계 설정을 `FetchType.EAGER`로 바꾸는 것이었습니다.

### 2-1. 적용 방법

```java
@Entity
public class Comment {
    @ManyToOne(fetch = FetchType.EAGER)  // ← Lazy에서 EAGER로 변경
    private User user;
}
```

### 2-2. 왜 안되는가? (치명적 단점)

#### 문제 1: 필요 없는 데이터 조회

```java
// 댓글 ID만 필요한 경우에도 User를 무조건 JOIN
Comment comment = commentRepository.findById(1L);
// SELECT c.*, u.* FROM comment c JOIN users u ...  ← User 필요 없는데 JOIN!
```

#### 문제 2: JPQL N+1 문제 재발

```java
// findAll() 실행 시
List<Comment> comments = commentRepository.findAll();
```

**쿼리 실행 순서:**
```sql
-- 1. JPQL 실행 (EAGER 무시하고 일단 Comment만 조회)
SELECT * FROM comment

-- 2. Hibernate가 "어? EAGER네?" 인식
-- 3. 각 댓글마다 User 조회 (N+1 재발!)
SELECT * FROM users WHERE id = ?
SELECT * FROM users WHERE id = ?
SELECT * FROM users WHERE id = ?
...
```

#### 문제 3: 예측 불가능한 쿼리

```java
// 단순한 댓글 조회에서도
Comment comment = commentRepository.findById(1L);

// User와 연관된 모든 엔티티까지 연쇄 로딩 가능
// Comment → User → Team → Department → ... (끝없는 JOIN)
```

### 결론: EAGER 사용 금지! ❌

> **실무 원칙:** 모든 연관관계는 `LAZY`로 설정하고, 필요한 경우에만 Fetch Join 사용

---

## 3. ✅ 일반적 해결책: Fetch Join

가장 대중적인 해결 방법인 Fetch Join을 적용해 보았습니다.

### 3-1. 적용 코드

**CommentRepository 수정:**

```java
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // N+1 문제 해결: User를 Fetch Join으로 한 번에 가져옴
    @Query("SELECT c FROM Comment c " +
           "JOIN FETCH c.user " +
           "WHERE c.post.id = :postId " +
           "AND c.status <> 'DELETED'")
    List<Comment> findAllByPostIdWithUser(@Param("postId") Long postId);
}
```

**JPQL 해석:**
```
SELECT c FROM Comment c       ← Comment 엔티티 조회
JOIN FETCH c.user             ← User를 즉시 함께 가져옴 (FETCH)
WHERE c.post.id = :postId     ← 특정 게시글의 댓글만
```

### 3-2. 장점

#### ✅ 한 방 쿼리로 해결

**실행되는 SQL:**

```sql
SELECT
    c.id,
    c.content,
    c.created_at,
    c.post_id,
    c.status,
    u.id,           -- ← User 정보도 함께!
    u.username,
    u.password,
    u.role,
    u.created_at
FROM
    comment c
JOIN
    users u ON u.id = c.user_id  -- ← JOIN으로 한 번에 가져옴
WHERE
    c.post_id = ?
    AND c.status <> 'DELETED'
```

#### ✅ N+1 문제 완벽 해결

**Before (N+1 발생):**
```
쿼리 7번: 1(댓글 조회) + 5(각 User 조회) + 1(Post 조회)
```

**After (Fetch Join):**
```
쿼리 2번: 1(Post 조회) + 1(댓글+User 조회)
```

### 3-3. ⚠️ 새로운 문제 발생: Paging OOM

하지만, **"댓글 페이징"**을 도입하려 할 때 치명적인 문제가 생깁니다.

**페이징 시도:**

```java
@Query("SELECT c FROM Comment c " +
       "JOIN FETCH c.user " +
       "WHERE c.post.id = :postId")
Page<Comment> findAllByPostIdWithUser(
    @Param("postId") Long postId,
    Pageable pageable  // ← 페이징 추가
);
```

**Hibernate 경고 로그:**

```
WARN  HHH000104: firstResult/maxResults specified with collection fetch;
applying in memory!
```

**문제 상황:**

```
1. DB에서 모든 데이터를 메모리로 퍼올림 (10,000개 댓글 전부!)
   ↓
2. 애플리케이션 메모리에서 페이징 처리
   ↓
3. OutOfMemoryError 💥 (운영 장애!)
```

**왜 이런 일이?**

```sql
-- Fetch Join 시 실행되는 SQL (1:N 조인)
SELECT *
FROM comment c
JOIN users u ON u.id = c.user_id
WHERE c.post_id = 1

-- 결과가 뻥튀기됨 (데이터 중복)
comment_id | user_id | username
1          | 10      | Alice
2          | 10      | Alice     ← 같은 User가 댓글 2개 작성 (중복 row!)
3          | 20      | Bob
4          | 20      | Bob       ← 같은 User가 댓글 2개 작성 (중복 row!)

-- LIMIT 10을 적용하면?
→ Comment 10개를 가져온 게 아니라, row 10개를 가져온 것!
→ 실제로는 Comment 5개밖에 안 됨 (잘못된 페이징!)
```

**결론:** OneToMany Fetch Join은 페이징과 함께 사용 불가! ❌

---

## 4. 💎 최적의 해결책: Batch Size

Fetch Join의 페이징 문제를 해결하기 위해 **Batch Size** 옵션을 적용했습니다.

### 4-1. 설정 방법

**application.properties 수정:**

```properties
# Hibernate Batch Fetch Size 설정
spring.jpa.properties.hibernate.default_batch_fetch_size=100
```

**또는 엔티티별 개별 설정:**

```java
@Entity
public class Comment {
    @ManyToOne(fetch = FetchType.LAZY)
    @BatchSize(size = 100)  // ← 이 Comment와 연관된 User를 100개씩 묶어서 조회
    private User user;
}
```

### 4-2. 동작 원리 (IN 쿼리)

**기존 (N+1):**

```sql
-- 1번: 댓글 조회
SELECT * FROM comment WHERE post_id = 1  -- 100개 조회

-- 2~101번: 각 User 개별 조회 (100번 반복!)
SELECT * FROM users WHERE id = 1
SELECT * FROM users WHERE id = 2
SELECT * FROM users WHERE id = 3
...
SELECT * FROM users WHERE id = 100
```

**Batch Size 적용 후:**

```sql
-- 1번: 댓글 조회
SELECT * FROM comment WHERE post_id = 1  -- 100개 조회

-- 2번: User를 한 번에 묶어서 조회 (IN 절 사용!)
SELECT *
FROM users
WHERE id IN (1, 2, 3, 4, 5, ..., 100)  -- ← 한 번에 100개!
```

**쿼리 횟수 비교:**

| 상황 | N+1 | Batch Size |
|------|-----|------------|
| 댓글 10개 | 11번 | 2번 |
| 댓글 100개 | 101번 | 2번 |
| 댓글 1000개 | 1001번 | 11번 (100개씩 10번 + 댓글 조회 1번) |

### 4-3. 최종 결과 로그 (해결됨 ✨)

**개선된 코드 (CommentRepository):**

```java
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Fetch Join 사용: User를 한 번에 가져옴
    @Query("SELECT c FROM Comment c " +
           "JOIN FETCH c.user " +
           "WHERE c.post.id = :postId " +
           "AND c.status <> 'DELETED'")
    List<Comment> findAllByPostIdWithUser(@Param("postId") Long postId);
}
```

**실제 실행 로그:**

```sql
-- 1️⃣ 게시글 조회 (Post + User JOIN)
Hibernate:
    select
        p1_0.id,
        p1_0.content,
        p1_0.created_at,
        p1_0.status,
        p1_0.title,
        p1_0.user_id,
        u1_0.id,              -- ← User 정보도 함께 조회
        u1_0.created_at,
        u1_0.password,
        u1_0.role,
        u1_0.username
    from
        post p1_0
    join
        users u1_0
            on u1_0.id=p1_0.user_id
    where
        p1_0.id=?
        and (
            p1_0.status != 'DELETED'
        )

-- 2️⃣ 댓글 + 작성자 조회 (Comment + User JOIN) ✅ 한 방 쿼리!
Hibernate:
    select
        c1_0.id,
        c1_0.content,
        c1_0.created_at,
        c1_0.post_id,
        c1_0.status,
        u1_0.id,              -- ← User 정보도 함께 조회 (JOIN)
        u1_0.created_at,
        u1_0.password,
        u1_0.role,
        u1_0.username
    from
        comment c1_0
    join
        users u1_0
            on u1_0.id=c1_0.user_id  -- ← Fetch Join!
    where
        (
            c1_0.status != 'DELETED'
        )
        and c1_0.post_id=?
        and c1_0.status<>'DELETED'
```

**디버그 로그:**

```
===== 게시글 상세 조회 디버그 =====
postId: 1
조회된 댓글 개수: 5

- commentId: 1, content: 좋은 글 감사합니다!
- commentId: 2, content: 저도 같이 공부하고 있어요!
- commentId: 3, content: sdafasfa
- commentId: 4, content: sdfasf
- commentId: 5, content: dsffdf
====================================
```

**결과:**
- 쿼리 **2번만** 실행! (게시글 조회 1번 + 댓글+User 조회 1번)
- N+1 문제 완전 해결 ✅
- 페이징 가능 (메모리 문제 없음) ✅

### 4-4. Batch Size vs Fetch Join 비교

| 항목 | Fetch Join | Batch Size |
|------|------------|------------|
| 쿼리 횟수 | **1번** (최적) | 2~N번 (IN 절) |
| 페이징 지원 | ❌ OOM 위험 | ✅ 가능 |
| 중복 데이터 | ⚠️ 발생 (1:N 조인) | ✅ 없음 |
| 적용 복잡도 | JPQL 수정 필요 | 설정만 추가 |
| 권장 상황 | 단건 조회 | 컬렉션 조회, 페이징 |

---

## 5. 🏗️ 아키텍처 고려사항: OSIV

성능 최적화와 더불어 DB 커넥션 관리를 위해 OSIV(Open Session In View) 설정도 검토했습니다.

### 5-1. OSIV란?

**정의:** 영속성 컨텍스트를 View(Controller/Thymeleaf) 단계까지 열어두는 설정

```
┌──────────────────────────────────────────────────┐
│           OSIV ON (Default)                      │
├──────────────────────────────────────────────────┤
│ Controller → Service → Repository → DB           │
│     ↓                                   ↓        │
│   View (Thymeleaf)                   커넥션 유지  │
│     ↓                                            │
│ 응답 반환 ← 여기서 커넥션 반환                      │
└──────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────┐
│           OSIV OFF                               │
├──────────────────────────────────────────────────┤
│ Controller → Service → Repository → DB           │
│              ↑                        ↓          │
│         커넥션 반환 ← Transaction 종료 후           │
│              ↓                                   │
│            View (Thymeleaf)                      │
│         ↓                                        │
│    LazyInitializationException 💥 (Lazy 접근 불가) │
└──────────────────────────────────────────────────┘
```

### 5-2. OSIV ON vs OFF

| 항목 | OSIV ON (Default) | OSIV OFF |
|------|-------------------|----------|
| 편의성 | ✅ Controller/View에서 Lazy 접근 가능 | ❌ Service에서 모두 로딩 필요 |
| 성능 | ❌ 커넥션 오래 점유 (트래픽 증가 시 장애) | ✅ 커넥션 빠르게 반환 |
| 구조 | ⚠️ Controller/View에서 DB 접근 가능 (계층 경계 모호) | ✅ 명확한 계층 분리 |
| 실무 권장 | ❌ 소규모 프로젝트 | ✅ 대규모 프로젝트 |

### 5-3. 나의 선택과 리팩토링

**설정: OSIV OFF (성능 우선)**

**application.properties:**

```properties
# OSIV 비활성화 (커넥션 풀 고갈 방지)
spring.jpa.open-in-view=false
```

**대응: Service 계층에서 DTO 변환**

```java
@Service
@Transactional(readOnly = true)
public class PostService {

    public PostDetailResponse findPostDetail(Long postId) {
        // 1. Entity 조회 (여기서 모든 데이터 로딩!)
        Post post = postRepository.findByIdWithUser(postId)
            .orElseThrow(PostNotFoundException::new);

        List<Comment> comments = commentRepository.findAllByPostIdWithUser(postId);

        // 2. DTO 변환 (Service 계층에서 완료)
        return PostDetailResponse.from(post, comments);
        // ← Transaction 종료, 커넥션 반환
    }
}
```

**Controller는 깔끔:**

```java
@GetMapping("/board/{id}")
public String boardDetail(
        @PathVariable Long id,
        Model model) {

    // DTO를 받아서 그대로 전달만
    PostDetailResponse response = postService.findPostDetail(id);
    model.addAttribute("post", response.post());
    model.addAttribute("comments", response.comments());

    return "board-detail";
}
```

---

## 6. 📝 결론 (Summary)

이번 트러블 슈팅을 통해 정립한 JPA 성능 최적화 원칙입니다.

### 핵심 원칙 3가지

1. **기본 설정: 모든 연관관계는 LAZY로 설정한다.**
   ```java
   @ManyToOne(fetch = FetchType.LAZY)  // ✅ 항상 LAZY
   private User user;
   ```

2. **단건 조회: Fetch Join을 사용하여 한 방 쿼리로 가져온다.**
   ```java
   @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.id = :id")
   Optional<Comment> findByIdWithUser(@Param("id") Long id);
   ```

3. **컬렉션 조회(페이징): Fetch Join 대신 Batch Size를 설정한다.**
   ```properties
   spring.jpa.properties.hibernate.default_batch_fetch_size=100
   ```

### 성능 개선 결과

| 항목 | Before (N+1) | After (Fetch Join) |
|------|--------------|-------------------|
| 쿼리 횟수 | **8번** | **2번** |
| 응답 시간 | ~500ms | ~50ms |
| DB 부하 | 🔴 높음 | 🟢 낮음 |

### 실무 체크리스트

- [ ] 모든 `@ManyToOne`, `@OneToOne`을 `LAZY`로 설정했는가?
- [ ] Fetch Join을 사용할 때 페이징이 필요한가?
  - Yes → Batch Size 사용
  - No → Fetch Join 사용
- [ ] OSIV를 OFF로 설정하고 Service에서 DTO 변환하는가?
- [ ] `spring.jpa.show-sql=true`로 쿼리를 확인하고 있는가?

### 참고 자료

- `docs/week7/hibernate-query-analysis.md` - 상세 쿼리 분석
- `docs/week7/aop-performance-monitoring.md` - AOP 성능 측정
- `docs/week7/troubleshooting-spring-security-and-comment-rendering.md` - Spring Security 리팩토링

---

**작성일:** 2025-12-07
**프로젝트:** Spring Boot 게시판 (spring-study)
**해결 이슈:** N+1 쿼리 문제, Fetch Join 페이징 OOM
