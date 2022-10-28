INSERT INTO `tags` (`name`) VALUES('funny');
INSERT INTO `tags` (`name`) VALUES('cool');
INSERT INTO `tags` (`name`) VALUES('warm');
INSERT INTO `tags` (`name`) VALUES('cold');
INSERT INTO `tags` (`name`) VALUES('relax');

INSERT INTO `gift_certificates` (`name`, `description`, `price`, `duration`, `create_date`, `last_update_date`)
VALUES('Car', 'Fast car', '99.99', 4, '2011-11-19T11:10:11.111', '2011-10-19T11:10:11.111');

INSERT INTO `gift_certificates` (`name`, `description`, `price`, `duration`, `create_date`, `last_update_date`)
VALUES('Sand', 'Yellow sand', '2.35', 24, '2020-05-05T23:42:12.112', '2012-11-19T11:10:11.111');

INSERT INTO `gift_certificates` (`name`, `description`, `price`, `duration`, `create_date`, `last_update_date`)
VALUES('City', 'Large city', '1000', 2, '2021-05-10T08:42:10.145', '2013-11-19T11:10:11.111');

INSERT INTO `gift_certificates` (`name`, `description`, `price`, `duration`, `create_date`, `last_update_date`)
VALUES('Plane', 'Fly plain', '452.12', 6, '2016-01-29T16:30:21.211', '2013-11-19T11:10:11.111');

INSERT INTO `gift_certificates` (`name`, `description`, `price`, `duration`, `create_date`, `last_update_date`)
VALUES('Ferry', 'Ferryman', '0.99', 14, '2019-11-19T11:10:11.111', '2014-11-19T11:10:11.111');

INSERT INTO `gift_certificates_tags` (`gift_certificate_id`, `tag_id`) VALUES (2, 1);
INSERT INTO `gift_certificates_tags` (`gift_certificate_id`, `tag_id`) VALUES (5, 2);
INSERT INTO `gift_certificates_tags` (`gift_certificate_id`, `tag_id`) VALUES (3, 5);
INSERT INTO `gift_certificates_tags` (`gift_certificate_id`, `tag_id`) VALUES (3, 3);
INSERT INTO `gift_certificates_tags` (`gift_certificate_id`, `tag_id`) VALUES (4, 4);

