DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint` (
  `id` varchar(255) NOT NULL,
  `complaint` varchar(255) NOT NULL,
  `company` varchar(255) DEFAULT NULL,
  `closed` boolean,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `complaint_comment`;
CREATE TABLE `complaint_comment` (
  `comment_id` varchar(255) NOT NULL,
  `complaint_id` varchar(255) NOT NULL,
  `comment_message` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `when_date` datetime not null,
  PRIMARY KEY (`comment_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
