CREATE TABLE IF NOT EXISTS `students`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `last_name`  varchar(32) NOT NULL,
    `first_name` varchar(32) NOT NULL,
    `patronymic` varchar(32) NOT NULL,
    `class_id`   bigint NOT NULL,
    `street` varchar(255) NOT NULL,
    `house_number` int NOT NULL,
    `flat_number` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `class_id` (`class_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  AUTO_INCREMENT = 0;

ALTER TABLE `students`
    ADD CONSTRAINT `students_ibfk_4`
    FOREIGN KEY (`class_id`)
    REFERENCES `classes` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION;
