/*
 Navicat Premium Data Transfer

 Source Server         : local8
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 127.0.0.1:3306
 Source Schema         : incloud3

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 20/04/2020 22:05:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for incloud_base_file_info
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_file_info`;
CREATE TABLE `incloud_base_file_info` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `file_name` varchar(100) NOT NULL COMMENT '原始文件名',
  `file_is_img` int(1) DEFAULT NULL COMMENT '是否是图片,0:是，1:不是',
  `file_content_type` varchar(255) DEFAULT NULL COMMENT '文件的真实类型',
  `file_source` varchar(50) NOT NULL COMMENT '文件来源，业务来源',
  `file_store_type` varchar(10) NOT NULL COMMENT '文件存储方式；1.本地；2aliyun；3.minio',
  `file_md5_code` varchar(32) NOT NULL COMMENT 'md5文件唯一标识',
  `file_bucket_name` varchar(50) DEFAULT NULL COMMENT 'minio和aliyun时会用到',
  `file_size` bigint(20) NOT NULL COMMENT '文件大小',
  `file_path` varchar(255) DEFAULT NULL COMMENT '本地存储时使用，真实路径',
  `file_url` varchar(255) DEFAULT NULL COMMENT '本地存储时使用，访问路径',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人用户id',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件存储';

-- ----------------------------
-- Records of incloud_base_file_info
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_file_info` VALUES (1252223699671642113, '数据库备份.key', 1, 'application/octet-stream', 'test', 'MINIO', '0979e45634bdd33a55cba091558cd457', 'incloud3', 402963, NULL, NULL, 'admin', '超级管理员', '2020-04-20 21:12:50', '2020-04-20 21:12:50');
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_zuul_route
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_zuul_route`;
CREATE TABLE `incloud_base_zuul_route` (
  `id` varchar(20) NOT NULL,
  `path` varchar(20) NOT NULL,
  `serviceId` varchar(30) DEFAULT NULL,
  `url` varchar(30) DEFAULT NULL,
  `strip_prefix` bit(1) DEFAULT NULL,
  `retryable` bit(1) DEFAULT NULL,
  `apiName` varchar(20) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of incloud_base_zuul_route
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_zuul_route` VALUES ('message', '/message/**', NULL, 'http://192.168.3.46:6000', b'1', NULL, NULL, b'1');
INSERT INTO `incloud_base_zuul_route` VALUES ('test', '/test/**', NULL, 'http://192.168.3.46:7000', b'1', NULL, NULL, b'1');
COMMIT;

-- ----------------------------
-- Table structure for incloud_demo_app
-- ----------------------------
DROP TABLE IF EXISTS `incloud_demo_app`;
CREATE TABLE `incloud_demo_app` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `app_name` varchar(50) DEFAULT NULL COMMENT 'app名称',
  `app_age` int(3) DEFAULT NULL COMMENT 'app年龄',
  `app_time` datetime DEFAULT NULL COMMENT 'app日期',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='demo_app测试类';

-- ----------------------------
-- Table structure for incloud_demo_consumer
-- ----------------------------
DROP TABLE IF EXISTS `incloud_demo_consumer`;
CREATE TABLE `incloud_demo_consumer` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `date_name` varchar(30) NOT NULL COMMENT '名称',
  `date_type` int(3) DEFAULT NULL COMMENT '数据类型',
  `create_date` datetime DEFAULT NULL COMMENT 'create_date',
  `update_date` datetime DEFAULT NULL COMMENT 'update_date',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务调用方 Entity';

-- ----------------------------
-- Table structure for incloud_demo_provider
-- ----------------------------
DROP TABLE IF EXISTS `incloud_demo_provider`;
CREATE TABLE `incloud_demo_provider` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `incloud_remark` varchar(50) NOT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT 'create_date',
  `update_date` datetime DEFAULT NULL COMMENT 'update_date',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务提供方 Entity';

SET FOREIGN_KEY_CHECKS = 1;
