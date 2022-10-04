CREATE TABLE `gift_certificates`(
`id` INTEGER NOT NULL auto_increment,
`name` VARCHAR(50) NOT NULL,
`description` TEXT NOT NULL,
`price` DOUBLE NOT NULL,
`duration` INTEGER NOT NULL,
`create_date` TIMESTAMP NOT NULL,
`last_update_date` TIMESTAMP NULL,
primary key(`id`)
);

CREATE TABLE `tags`(
`id` INTEGER NOT NULL auto_increment,
`name` VARCHAR(50) NOT NULL UNIQUE,
primary key(`id`)
);

create table `gift_certificates_tags` (
`id` INTEGER NOT NULL auto_increment,
`gift_certificate_id` INTEGER NOT NULL,
`tag_id` INTEGER NOT NULL,
CONSTRAINT `fk_gift_certificate_id` FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificates` (`id`),
CONSTRAINT `fk_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
);

INSERT INTO `tags` (`id` ,`name`) VALUES('1','#funny');
INSERT INTO `tags` (`id` ,`name`) VALUES('2','#cool');
INSERT INTO `tags` (`id` ,`name`) VALUES('3','#warm');
INSERT INTO `tags` (`id` ,`name`) VALUES('4','#cold');
INSERT INTO `tags` (`id` ,`name`) VALUES('5','#relax');

INSERT INTO `gift_certificates` (`id`, `name`, `description`, `price`, `duration`, `create_date`)
VALUES(1, 'Car', 'Fast car', '99.99', 4, '2011-11-19T11:10:11.111');

INSERT INTO `gift_certificates` (`id`, `name`, `description`, `price`, `duration`, `create_date`)
VALUES(2, 'Sand', 'Yellow sand', '2.35', 24, '2020-05-05T23:42:12.112');

INSERT INTO `gift_certificates` (`id`, `name`, `description`, `price`, `duration`, `create_date`)
VALUES(3, 'City', 'Large city', '1000', 2, '2021-05-10T08:42:10.145');

INSERT INTO `gift_certificates` (`id`, `name`, `description`, `price`, `duration`, `create_date`)
VALUES(4, 'Plane', 'Fly plain', '452.12', 6, '2016-01-29T16:30:21.211');

INSERT INTO `gift_certificates` (`id`, `name`, `description`, `price`, `duration`, `create_date`)
VALUES(5, 'Ferry', 'Ferryman', '0.99', 14, '2019-11-19T11:10:11.111');

INSERT INTO `gift_certificates_tags` (`id`, `gift_certificate_id`, `tag_id`) VALUES (1, 1, 1);
INSERT INTO `gift_certificates_tags` (`id`, `gift_certificate_id`, `tag_id`) VALUES (2, 1, 2);
INSERT INTO `gift_certificates_tags` (`id`, `gift_certificate_id`, `tag_id`) VALUES (3, 3, 5);
INSERT INTO `gift_certificates_tags` (`id`, `gift_certificate_id`, `tag_id`) VALUES (4, 3, 3);
INSERT INTO `gift_certificates_tags` (`id`, `gift_certificate_id`, `tag_id`) VALUES (5, 4, 4);
