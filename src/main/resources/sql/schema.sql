CREATE TABLE labels
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE writers
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255),
    lastname  VARCHAR(255)
);
CREATE TABLE posts
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    content   TEXT,
    created   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    writer_id BIGINT,
    status    VARCHAR(255)
);

CREATE TABLE post_labels
(
    post_id  BIGINT NOT NULL,
    label_id BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts (id),
    FOREIGN KEY (label_id) REFERENCES labels (id),
    UNIQUE (post_id, label_id)
);