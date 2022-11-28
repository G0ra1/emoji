/*
 Navicat Premium Data Transfer

 Source Server         : local8
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 127.0.0.1:3301
 Source Schema         : incloud3

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 25/08/2021 11:20:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for incloud_base_mdm_org
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_mdm_org`;
CREATE TABLE `incloud_base_mdm_org` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `org_code` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织代码',
  `org_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `org_type` int(1) NOT NULL COMMENT '组织类型',
  `parent_id` bigint(20) NOT NULL COMMENT '父级ID',
  `parent_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '父级名称',
  `org_full_id` varchar(4000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父级组织全路径ID',
  `org_full_name` varchar(4000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父级组织全路径名称',
  `level` int(11) NOT NULL COMMENT '层级',
  `sort` int(11) NOT NULL COMMENT '排序字段',
  `has_kids` int(1) NOT NULL COMMENT '是否有子集',
  `status` int(1) NOT NULL COMMENT '状态标识',
  `org_level` int(1) DEFAULT NULL COMMENT '组织层级——对应业务上的组织类型- 一级部门 二级部门之类的',
  `org_property` int(1) DEFAULT NULL COMMENT '组织性质',
  `satrap_id` bigint(20) DEFAULT NULL COMMENT '主管人ID',
  `satrap_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主管人姓名',
  `lead_time` datetime DEFAULT NULL COMMENT '筹建时间',
  `lead_approve_code` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '筹建批准文号',
  `setup_time` datetime DEFAULT NULL COMMENT '成立时间',
  `setup_appove_code` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '成立批准文号',
  `org_addr` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织地址',
  `org_region` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地级所在地区',
  `org_post` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮政编码',
  `org_phone` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话号码',
  `org_fax` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '传真号码',
  `org_duty` varchar(800) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织职责',
  `register_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册名称',
  `register_credit_code` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '统一社会代码',
  `register_legal_person_id` bigint(20) DEFAULT NULL COMMENT '法人ID',
  `register_legal_person_name` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法人名称',
  `register_register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `register_capital` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册资本',
  `register_biz_addr` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '营业地址',
  `register_biz_time` datetime DEFAULT NULL COMMENT '营业期限',
  `register_biz_scope` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '经营范围',
  `register_certificate_time` datetime DEFAULT NULL COMMENT '发证时间',
  `register_register_org` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登记单位',
  `register_scan_file_id` bigint(20) DEFAULT NULL COMMENT '扫描件',
  `register_scan_file_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扫描件地址',
  `license_code` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '许可证编码',
  `license_time` datetime DEFAULT NULL COMMENT '注册时间',
  `license_addr` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册地址',
  `license_certificate_org` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发证机关',
  `license_certificate_time` datetime DEFAULT NULL COMMENT '发证时间',
  `license_scan_file_id` bigint(20) DEFAULT NULL COMMENT '扫描件',
  `license_scan_file_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扫描件地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组织';

-- ----------------------------
-- Records of incloud_base_mdm_org
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_mdm_org` VALUES (0, 'root', 'root', 1, -1, '#', '', '', 0, 0, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `incloud_base_mdm_org` VALUES (1430348149720989698, 'a', 'a', 1, 0, 'root', NULL, NULL, 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 09:55:48', '2021-08-25 09:55:48');
INSERT INTO `incloud_base_mdm_org` VALUES (1430348328486420481, 'b', 'b', 1, 0, 'root', NULL, NULL, 1, 2, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 09:56:31', '2021-08-25 09:56:31');
INSERT INTO `incloud_base_mdm_org` VALUES (1430348644527226882, 'a1', 'a1', 1, 1430348149720989698, 'a', '1430348149720989698', 'a', 2, 1, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 09:57:46', '2021-08-25 09:57:46');
INSERT INTO `incloud_base_mdm_org` VALUES (1430348663036690434, 'a2', 'a2', 1, 1430348149720989698, 'a', '1430348149720989698', 'a', 2, 2, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 09:57:51', '2021-08-25 09:57:51');
INSERT INTO `incloud_base_mdm_org` VALUES (1430348682548592641, 'a3', 'a3', 1, 1430348149720989698, 'a', '1430348149720989698', 'a', 2, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 09:57:55', '2021-08-25 09:57:55');
INSERT INTO `incloud_base_mdm_org` VALUES (1430348802073673729, 'a31', 'a31', 1, 1430348682548592641, 'a3', '1430348682548592641,1430348149720989698', 'a3<-a', 3, 1, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 09:58:24', '2021-08-25 09:58:24');
INSERT INTO `incloud_base_mdm_org` VALUES (1430348876300271618, 'a32', 'a32', 1, 1430348682548592641, 'a3', '1430348682548592641,1430348149720989698', 'a3<-a', 3, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 09:58:41', '2021-08-25 09:58:41');
INSERT INTO `incloud_base_mdm_org` VALUES (1430349672244858882, 'd2', 'd2', 2, 1430348876300271618, 'a32', '1430348876300271618,1430348682548592641,1430348149720989698', 'a32<-a3<-a', 4, 1, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 10:01:51', '2021-08-25 10:01:51');
INSERT INTO `incloud_base_mdm_org` VALUES (1430349781233848322, 'd1', 'd1new', 2, 1430348876300271618, 'a32', '1430348876300271618,1430348682548592641,1430348149720989698', 'a32<-a3<-a', 4, 2, 1, 1, NULL, NULL, NULL, NULL, NULL, 'aaa', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 10:02:17', '2021-08-25 10:48:37');
INSERT INTO `incloud_base_mdm_org` VALUES (1430360878410125313, 'd11new', 'd11new', 2, 1430348876300271618, 'a32', '1430348876300271618,1430348682548592641,1430348149720989698', 'a32<-a3<-a', 4, 3, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 10:46:23', '2021-08-25 11:14:09');
INSERT INTO `incloud_base_mdm_org` VALUES (1430360878410125314, 'd12new', 'd12new', 2, 1430349781233848322, 'd1new', '1430349781233848322,1430348876300271618,1430348682548592641,1430348149720989698', 'd1new<-a32<-a3<-a', 5, 1, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 10:46:23', '2021-08-25 10:47:55');
INSERT INTO `incloud_base_mdm_org` VALUES (1430368341683363841, 'a33', 'a33', 1, 1430348682548592641, 'a3', '1430348682548592641,1430348149720989698', 'a3<-a', 3, 2, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 11:16:02', '2021-08-25 11:16:02');
INSERT INTO `incloud_base_mdm_org` VALUES (1430368385278959618, 'a34', 'a34', 1, 1430348682548592641, 'a3', '1430348682548592641,1430348149720989698', 'a3<-a', 3, 4, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-08-25 11:16:13', '2021-08-25 11:16:13');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
