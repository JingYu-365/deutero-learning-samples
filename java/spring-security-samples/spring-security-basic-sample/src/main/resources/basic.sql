/*
 Navicat Premium Data Transfer

 Source Server         : LOCAL
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : study

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 31/01/2021 21:16:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `parentid` bigint(20) DEFAULT NULL,
  `permission` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES (1, '系统管理', '', 0, 'menu:system');
INSERT INTO `menu` VALUES (2, '用户管理', '', 0, 'menu:user');
COMMIT;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
BEGIN;
INSERT INTO `persistent_logins` VALUES ('laba', 'KBs+fIpL1zSSwinrgxhrXA==', 'epxWp0TytfELLl9C8ruleA==', '2021-01-31 20:54:24');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, '管理员');
INSERT INTO `role` VALUES (2, '普通用户');
COMMIT;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `mid` bigint(20) DEFAULT NULL,
  `rid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
BEGIN;
INSERT INTO `role_menu` VALUES (1, 1);
INSERT INTO `role_menu` VALUES (2, 1);
INSERT INTO `role_menu` VALUES (2, 2);
COMMIT;

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
  `uid` bigint(20) DEFAULT NULL,
  `rid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_user
-- ----------------------------
BEGIN;
INSERT INTO `role_user` VALUES (1, 1);
INSERT INTO `role_user` VALUES (2, 2);
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES (1, 'laba', 'laba');
INSERT INTO `users` VALUES (2, 'root', 'root');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
