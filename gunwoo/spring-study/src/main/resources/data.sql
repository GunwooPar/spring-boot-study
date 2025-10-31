-- 테스트용 사용자 데이터
INSERT INTO users (username, role, created_at) VALUES ('admin', 'ADMIN', CURRENT_TIMESTAMP);
INSERT INTO users (username, role, created_at) VALUES ('user', 'USER', CURRENT_TIMESTAMP);

-- 테스트용 게시글 데이터
INSERT INTO post (title, content, user_id, status, created_at) VALUES ('첫 번째 게시글', '테스트 게시글 내용입니다.', 1, 'PUBLISHED', CURRENT_TIMESTAMP);
INSERT INTO post (title, content, user_id, status, created_at) VALUES ('두 번째 게시글', '두 번째 테스트 내용입니다.', 2, 'PUBLISHED', CURRENT_TIMESTAMP);