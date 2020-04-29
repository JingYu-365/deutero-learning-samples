-- ----------------------------
-- Table structure for resume
-- ----------------------------
DROP TABLE IF EXISTS `resume`;

CREATE TABLE `resume` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`sex` varchar(255) DEFAULT NULL,
`phone` varchar(255) DEFAULT NULL,
`address` varchar(255) DEFAULT NULL,
`education` varchar(255) DEFAULT NULL,
`state` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;