CREATE TABLE IF NOT EXISTS `classes` (
                                         `sprint` int NOT NULL,
                                         `class_id` bigint NOT NULL,
                                         `learning_path` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`class_id`),
    CONSTRAINT `classes_chk_1` CHECK ((`sprint` >= 1))
    )