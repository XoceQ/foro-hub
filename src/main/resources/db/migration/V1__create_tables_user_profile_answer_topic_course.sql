CREATE TABLE users (
                       id        BIGINT NOT NULL AUTO_INCREMENT,
                       username  VARCHAR(100) NOT NULL UNIQUE,
                       password  VARCHAR(300) NOT NULL,
                       PRIMARY KEY (id)
);



CREATE TABLE profiles (
                          id       BIGINT NOT NULL AUTO_INCREMENT,
                          name     VARCHAR(100) NOT NULL,
                          email    VARCHAR(100) NOT NULL UNIQUE,
                          id_user  BIGINT,
                          active   TINYINT DEFAULT 1,
                          PRIMARY KEY (id),
                          CONSTRAINT fk_profile_user_id FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE topics (
                        id            BIGINT NOT NULL AUTO_INCREMENT,
                        title         VARCHAR(100) NOT NULL,
                        message       VARCHAR(100) NOT NULL,
                        creation_date DATETIME NOT NULL,
                        status        TINYINT DEFAULT 0,
                        id_autor      BIGINT NOT NULL,
                        id_course     BIGINT NOT NULL,
                        active        TINYINT DEFAULT 1,
                        PRIMARY KEY (id),
                        CONSTRAINT fk_topic_autor_id FOREIGN KEY (id_autor) REFERENCES profiles(id) ON DELETE CASCADE,
                        CONSTRAINT fk_topic_course_id FOREIGN KEY (id_course) REFERENCES courses(id) ON DELETE CASCADE
);

