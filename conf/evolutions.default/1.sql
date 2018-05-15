
# --- !Ups
CREATE TABLE user_contact_book (
    `user_id` bigint(20) not null AUTO_INCREMENT,
    `user_name` varchar(255) DEFAULT NULL,
    `phone_number` varchar(15) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY `email_UNIQUE` (`email`),
    KEY phone_number_idx (phone_number(15)),
    KEY email_idx (email(50))
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# --- !Downs

DROP TABLE IF EXISTS `user_contact_book`;
