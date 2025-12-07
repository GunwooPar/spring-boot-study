-- 🔄 비밀번호 필드 추가 (BCrypt 해시값)
-- 비밀번호: "password" → BCrypt로 암호화한 값
INSERT INTO users (username, password, created_at, role) VALUES
                                                             ('alice', '$2a$10$eImiTXuWVxfM37uY4JANjOEn6YqxDd7dR0.0uBUoOx5.GpVBOJAwy', CURRENT_TIMESTAMP, 'USER'),
                                                             ('bob', '$2a$10$eImiTXuWVxfM37uY4JANjOEn6YqxDd7dR0.0uBUoOx5.GpVBOJAwy', CURRENT_TIMESTAMP, 'USER'),
                                                             ('charlie', '$2a$10$eImiTXuWVxfM37uY4JANjOEn6YqxDd7dR0.0uBUoOx5.GpVBOJAwy', CURRENT_TIMESTAMP, 'ADMIN'),
                                                             ('user', '$2a$12$I2vo8zp8Y8VximiU1U2cpuSHOzPbTNAlW9ZoHWEqta.4qWmdTeANq', CURRENT_TIMESTAMP, 'USER'),
                                                             ('admin', '$2a$12$I2vo8zp8Y8VximiU1U2cpuSHOzPbTNAlW9ZoHWEqta.4qWmdTeANq', CURRENT_TIMESTAMP, 'ADMIN');

-- 게시글 데이터 (기존과 동일)
INSERT INTO post (title, content, user_id, status, created_at) VALUES
                                                                    ('첫 번째 게시글', '안녕하세요!', 1, 'PUBLISHED', CURRENT_TIMESTAMP),
                                                                    ('두 번째 게시글', 'Spring Boot 학습 중입니다.', 2, 'PUBLISHED', CURRENT_TIMESTAMP);

-- 댓글 데이터 (기존과 동일)
INSERT INTO comment (content, post_id, user_id, created_at) VALUES
                                                                 ('좋은 글 감사합니다!', 1, 2, CURRENT_TIMESTAMP),
                                                                 ('저도 같이 공부하고 있어요!', 1, 3, CURRENT_TIMESTAMP);