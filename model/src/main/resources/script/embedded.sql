DROP TABLE IF EXISTS `gift_certificates`;
CREATE TABLE `gift_certificates`(
`id` INTEGER PRIMARY KEY NOT NULL auto_increment,
`name` VARCHAR(50) NOT NULL,
`description` TEXT NOT NULL,
`price` DOUBLE NOT NULL,
`duration` INTEGER NOT NULL,
`create_date` TIMESTAMP NOT NULL,
`last_update_date` TIMESTAMP NULL
);

DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`(
`id` INTEGER PRIMARY KEY NOT NULL auto_increment,
`name` VARCHAR(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS `gift_certificates_tags`;
create table `gift_certificates_tags` (
`id` INTEGER PRIMARY KEY NOT NULL auto_increment,
`gift_certificate_id` INTEGER NOT NULL,
`tag_id` INTEGER NOT NULL,
CONSTRAINT `fk_gift_certificate_id` FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificates` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT `fk_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE);

