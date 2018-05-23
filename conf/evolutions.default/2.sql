# --- !Ups
ALTER TABLE `user_contact_book` ADD COLUMN `soft_deleted` int(2) DEFAULT 0;


# --- !Downs
ALTER TABLE `user_contact_book` DROP COLUMN `soft_deleted`;
