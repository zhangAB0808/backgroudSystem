/*
Navicat MySQL Data Transfer

Source Server         : Mysql08
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : zhang

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2022-05-17 11:02:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `value` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('user', 'el-icon-user', 'icon');
INSERT INTO `sys_dict` VALUES ('house', 'el-icon-house', 'icon');
INSERT INTO `sys_dict` VALUES ('menu', 'el-icon-menu', 'icon');
INSERT INTO `sys_dict` VALUES ('s-custom', 'el-icon-s-custom', 'icon');
INSERT INTO `sys_dict` VALUES ('s-grid', 'el-icon-s-grid', 'icon');
INSERT INTO `sys_dict` VALUES ('document', 'el-icon-document', 'icon');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT '0',
  `enable` tinyint(1) DEFAULT '1',
  `md5` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES ('12', '3.jpg', 'jpg', '153', 'http://localhost:8081/file/c51c3223c3d9403fab1117c671eba726.jpg', '0', '1', '2324496ad81d3c52ce0ffa4636660602');
INSERT INTO `sys_file` VALUES ('14', '1.jpg', 'jpg', '94', 'http://localhost:8081/file/6e521b9a5771481ba911deeaab73b046.jpg', '0', '1', '9be8cd345ebfbf5469338f2a24a5311a');
INSERT INTO `sys_file` VALUES ('16', '2.jpg', 'jpg', '94', 'http://localhost:8081/file/c92586461f00479dbef85339e09bf059.jpg', '0', '1', 'd7070fc7996c5bcdb28172697fa2b764');
INSERT INTO `sys_file` VALUES ('18', '4.jpg', 'jpg', '153', 'http://localhost:8081/file/7a0cca17810e4a16894b823eed825629.jpg', '0', '1', '0840e27d068e819d374410d63212a4ae');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('10', '主页', '/home', 'el-icon-house', null, null);
INSERT INTO `sys_menu` VALUES ('11', '系统管理', '', 'el-icon-s-grid', null, null);
INSERT INTO `sys_menu` VALUES ('12', '用户管理', '/user', 'el-icon-user', null, '11');
INSERT INTO `sys_menu` VALUES ('13', '文件管理', '/file', 'el-icon-document', null, '11');
INSERT INTO `sys_menu` VALUES ('14', '角色管理', '/role', 'el-icon-s-custom', null, '11');
INSERT INTO `sys_menu` VALUES ('15', '菜单管理', '/menu', 'el-icon-menu', null, '11');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `flag` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', '管理员', 'ROLE_ADMIN');
INSERT INTO `sys_role` VALUES ('2', '普通用户', '普通用户', 'ROLE_USER');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `menu_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`menu_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('10', '1');
INSERT INTO `sys_role_menu` VALUES ('10', '2');
INSERT INTO `sys_role_menu` VALUES ('11', '1');
INSERT INTO `sys_role_menu` VALUES ('11', '2');
INSERT INTO `sys_role_menu` VALUES ('12', '1');
INSERT INTO `sys_role_menu` VALUES ('13', '1');
INSERT INTO `sys_role_menu` VALUES ('13', '2');
INSERT INTO `sys_role_menu` VALUES ('14', '1');
INSERT INTO `sys_role_menu` VALUES ('15', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nickname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `avatar_url` varchar(562) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '普通用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'admin', '管理员', 'admin@qq.com', '18438909039', '河南郑州', '2022-05-01 21:10:21', 'https://img0.baidu.com/it/u=550251718,3136611345&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '管理员');
INSERT INTO `sys_user` VALUES ('3', 'tang', '123', '唐僧', 'tang@qq.com', '15628465462', '东土大唐', '2021-02-02 20:58:29', 'http://localhost:8081/file/2e8a12c9c54d4827a85c8f6995577032.jpg', '普通用户');
INSERT INTO `sys_user` VALUES ('6', 'sun', '147', '孙悟空', 'sun@qq.com', '15648492365', '花果山水帘洞', '2021-10-04 17:05:58', 'http://localhost:8081/file/c92586461f00479dbef85339e09bf059.jpg', '普通用户');
INSERT INTO `sys_user` VALUES ('7', 'zhu', '100', '猪八戒', 'zhu@qq.com', '15634956325', '高老庄', '2022-08-10 16:28:10', 'http://localhost:8081/file/c51c3223c3d9403fab1117c671eba726.jpg', '普通用户');
INSERT INTO `sys_user` VALUES ('8', 'sha', '110', '沙和尚', 'sha@qq.com', '13256475659', '流沙河', '2022-05-10 16:32:08', 'http://localhost:8081/file/1ef10ec157de432c9922cbab1c1fa0b2.jpg', '普通用户');
INSERT INTO `sys_user` VALUES ('9', 'zhang', '0808', 'klaus', '3317306971@qq.com', '18438909039', '河南省南阳市', '2022-05-13 15:41:34', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.sxycpw.com%2Fuploads%2Fallimg%2Fc201116%2F160551521D4040-1930I.jpg&refer=http%3A%2F%2Fwww.sxycpw.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1655032254&t=5e765a288799901f2fce9fbf39b20998', '管理员');
