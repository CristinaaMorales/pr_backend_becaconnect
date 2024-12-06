-- Crear tabla `user`
CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
    );

-- Crear tabla `post`
CREATE TABLE IF NOT EXISTS post (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
    content VARCHAR(5000) NOT NULL,
    posted_by VARCHAR(255) NOT NULL,
    img VARCHAR(255),
    date DATETIME(6) NOT NULL,
    like_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    comment_count INT DEFAULT 0
    );

-- Crear tabla `post_tags`
CREATE TABLE IF NOT EXISTS post_tags (
                                         post_id BIGINT NOT NULL,
                                         tag VARCHAR(255) NOT NULL,
    PRIMARY KEY (post_id, tag),
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
    );

-- Crear tabla `comment`
CREATE TABLE IF NOT EXISTS comment (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       content VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    user_id BIGINT,
    post_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE SET NULL,
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
    );
