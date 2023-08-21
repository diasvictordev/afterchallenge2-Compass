CREATE TABLE IF NOT EXISTS `student` (
                                         `attendance` float DEFAULT NULL,
                                         `grade` float DEFAULT NULL,
                                         `end_date` DATETIME(6) DEFAULT NULL,
                                         `squad_id` bigint DEFAULT NULL,
                                         `start_date` DATETIME(6) DEFAULT NULL,
                                         `user_id` bigint NOT NULL AUTO_INCREMENT,
                                         `password` varchar(20) DEFAULT NULL,
    `city` varchar(255) DEFAULT NULL,
    `college` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    KEY (`squad_id`),
    FOREIGN KEY (`squad_id`) REFERENCES `squad`(`id`)
    );