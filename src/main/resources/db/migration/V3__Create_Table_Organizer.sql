CREATE TABLE IF NOT EXISTS `organizer` (
                             `user_id` bigint NOT NULL,
                             `password` varchar(20) DEFAULT NULL,
                             `city` varchar(255) DEFAULT NULL,
                             `email` varchar(255) DEFAULT NULL,
                             `name` varchar(255) DEFAULT NULL,
                             `role` enum('Coordinator','Instructor','ScrumMaster') DEFAULT NULL,
                             PRIMARY KEY (`user_id`)
)
/*
*/

/*
CREATE TABLE IF NOT EXISTS `student_seq` (
    `next_val` bigint DEFAULT NULL
)

CREATE TABLE `organizer_seq` (
    `next_val` bigint DEFAULT NULL
)

CREATE TABLE IF NOT EXISTS `classes_seq` (
    `next_val` bigint DEFAULT NULL
)

CREATE TABLE IF NOT EXISTS `squad_seq` (
    `next_val` bigint DEFAULT NULL
);*/
