CREATE TABLE IF NOT EXISTS topics (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL,
    author VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL
);
