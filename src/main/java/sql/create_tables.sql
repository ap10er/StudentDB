CREATE TABLE IF NOT EXISTS `Groups` (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        group_number VARCHAR(10) NOT NULL UNIQUE,
                                        faculty_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS `Students` (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          first_name VARCHAR(50) NOT NULL,
                                          last_name VARCHAR(50) NOT NULL,
                                          middle_name VARCHAR(50),
                                          birth_date DATE NOT NULL,
                                          group_id BIGINT NOT NULL,
                                          FOREIGN KEY (group_id) REFERENCES `Groups`(id) ON DELETE RESTRICT
);