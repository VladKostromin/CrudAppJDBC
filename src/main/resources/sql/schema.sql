CREATE SCHEMA crudtest;
CREATE TABLE crudtest.writers (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              firstname VARCHAR(255),
                              lastname VARCHAR(255)
);
CREATE TABLE crudtest.posts (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            content TEXT,
                            created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            writer_id BIGINT,
                            status VARCHAR(255),
                            FOREIGN KEY (writer_id) REFERENCES crudtest.writers(id)
);
CREATE TABLE crudtest.labels (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255),
                             post_id BIGINT,
                             FOREIGN KEY (post_id) REFERENCES crudtest.posts(id)
);