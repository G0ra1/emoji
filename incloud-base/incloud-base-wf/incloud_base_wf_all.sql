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

 Date: 10/11/2020 17:25:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ge_bytearray`;
CREATE TABLE `incloud_base_wf_act_ge_bytearray` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` int(11) DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`) USING BTREE,
  KEY `ACT_IDX_BYTEARRAY_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_BYTEARRAY_RM_TIME` (`REMOVAL_TIME_`) USING BTREE,
  KEY `ACT_IDX_BYTEARRAY_NAME` (`NAME_`) USING BTREE,
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `incloud_base_wf_act_re_deployment` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ge_bytearray
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ge_property`;
CREATE TABLE `incloud_base_wf_act_ge_property` (
  `NAME_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ge_property
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_act_ge_property` VALUES ('deployment.lock', '0', 1);
INSERT INTO `incloud_base_wf_act_ge_property` VALUES ('history.cleanup.job.lock', '0', 1);
INSERT INTO `incloud_base_wf_act_ge_property` VALUES ('historyLevel', '3', 1);
INSERT INTO `incloud_base_wf_act_ge_property` VALUES ('next.dbid', '1', 1);
INSERT INTO `incloud_base_wf_act_ge_property` VALUES ('schema.history', 'create(fox)', 1);
INSERT INTO `incloud_base_wf_act_ge_property` VALUES ('schema.version', 'fox', 1);
INSERT INTO `incloud_base_wf_act_ge_property` VALUES ('startup.lock', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ge_schema_log
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ge_schema_log`;
CREATE TABLE `incloud_base_wf_act_ge_schema_log` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TIMESTAMP_` datetime DEFAULT NULL,
  `VERSION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ge_schema_log
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_act_ge_schema_log` VALUES ('0', '2020-11-06 16:33:02', '7.12.0');
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_actinst`;
CREATE TABLE `incloud_base_wf_act_hi_actinst` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PARENT_ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CALL_CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `ACT_INST_STATE_` int(11) DEFAULT NULL,
  `SEQUENCE_COUNTER_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ACTINST_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_COMP` (`EXECUTION_ID_`,`ACT_ID_`,`END_TIME_`,`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_STATS` (`PROC_DEF_ID_`,`PROC_INST_ID_`,`ACT_ID_`,`END_TIME_`,`ACT_INST_STATE_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_PROC_DEF_KEY` (`PROC_DEF_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_AI_PDEFID_END_TIME` (`PROC_DEF_ID_`,`END_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_ACT_INST_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_actinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_attachment`;
CREATE TABLE `incloud_base_wf_act_hi_attachment` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ATTACHMENT_CONTENT` (`CONTENT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ATTACHMENT_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ATTACHMENT_PROCINST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ATTACHMENT_TASK` (`TASK_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_ATTACHMENT_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_attachment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_batch
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_batch`;
CREATE TABLE `incloud_base_wf_act_hi_batch` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TOTAL_JOBS_` int(11) DEFAULT NULL,
  `JOBS_PER_SEED_` int(11) DEFAULT NULL,
  `INVOCATIONS_PER_JOB_` int(11) DEFAULT NULL,
  `SEED_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `MONITOR_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BATCH_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_HI_BAT_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_batch
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_caseactinst
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_caseactinst`;
CREATE TABLE `incloud_base_wf_act_hi_caseactinst` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PARENT_ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CASE_ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CALL_CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_ACT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_ACT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `STATE_` int(11) DEFAULT NULL,
  `REQUIRED_` tinyint(1) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_CAS_A_I_CREATE` (`CREATE_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_CAS_A_I_END` (`END_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_CAS_A_I_COMP` (`CASE_ACT_ID_`,`END_TIME_`,`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_CAS_A_I_CASEINST` (`CASE_INST_ID_`,`CASE_ACT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_CAS_A_I_TENANT_ID` (`TENANT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_caseactinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_caseinst
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_caseinst`;
CREATE TABLE `incloud_base_wf_act_hi_caseinst` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CREATE_TIME_` datetime NOT NULL,
  `CLOSE_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `STATE_` int(11) DEFAULT NULL,
  `CREATE_USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SUPER_CASE_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE KEY `CASE_INST_ID_` (`CASE_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_CAS_I_CLOSE` (`CLOSE_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_CAS_I_BUSKEY` (`BUSINESS_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_CAS_I_TENANT_ID` (`TENANT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_caseinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_comment`;
CREATE TABLE `incloud_base_wf_act_hi_comment` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_COMMENT_TASK` (`TASK_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_COMMENT_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_COMMENT_PROCINST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_COMMENT_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_comment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_dec_in
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_dec_in`;
CREATE TABLE `incloud_base_wf_act_hi_dec_in` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DEC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CLAUSE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CLAUSE_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `VAR_TYPE_` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_IN_INST` (`DEC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_IN_CLAUSE` (`DEC_INST_ID_`,`CLAUSE_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_IN_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_IN_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_dec_in
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_dec_out
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_dec_out`;
CREATE TABLE `incloud_base_wf_act_hi_dec_out` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DEC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `CLAUSE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CLAUSE_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RULE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RULE_ORDER_` int(11) DEFAULT NULL,
  `VAR_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `VAR_TYPE_` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_OUT_INST` (`DEC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_OUT_RULE` (`RULE_ORDER_`,`CLAUSE_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_OUT_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_OUT_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_dec_out
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_decinst
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_decinst`;
CREATE TABLE `incloud_base_wf_act_hi_decinst` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DEC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DEC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DEC_DEF_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EVAL_TIME_` datetime NOT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  `COLLECT_VALUE_` double DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_DEC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DEC_REQ_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DEC_REQ_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_ID` (`DEC_DEF_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_KEY` (`DEC_DEF_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_PI` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_CI` (`CASE_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_ACT` (`ACT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_ACT_INST` (`ACT_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_TIME` (`EVAL_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_ROOT_ID` (`ROOT_DEC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_REQ_ID` (`DEC_REQ_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_REQ_KEY` (`DEC_REQ_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DEC_INST_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_decinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_detail`;
CREATE TABLE `incloud_base_wf_act_hi_detail` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `VAR_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SEQUENCE_COUNTER_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `OPERATION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_CASE_INST` (`CASE_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_CASE_EXEC` (`CASE_EXECUTION_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_PROC_DEF_KEY` (`PROC_DEF_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_BYTEAR` (`BYTEARRAY_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_RM_TIME` (`REMOVAL_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_TASK_BYTEAR` (`BYTEARRAY_ID_`,`TASK_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_DETAIL_VAR_INST_ID` (`VAR_INST_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_ext_task_log
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_ext_task_log`;
CREATE TABLE `incloud_base_wf_act_hi_ext_task_log` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TIMESTAMP_` timestamp NOT NULL,
  `EXT_TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `TOPIC_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `WORKER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` bigint(20) NOT NULL DEFAULT '0',
  `ERROR_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ERROR_DETAILS_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `STATE_` int(11) DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_HI_EXT_TASK_LOG_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_HI_EXT_TASK_LOG_PROCINST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_HI_EXT_TASK_LOG_PROCDEF` (`PROC_DEF_ID_`) USING BTREE,
  KEY `ACT_HI_EXT_TASK_LOG_PROC_DEF_KEY` (`PROC_DEF_KEY_`) USING BTREE,
  KEY `ACT_HI_EXT_TASK_LOG_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_EXTTASKLOG_ERRORDET` (`ERROR_DETAILS_ID_`) USING BTREE,
  KEY `ACT_HI_EXT_TASK_LOG_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_ext_task_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_identitylink`;
CREATE TABLE `incloud_base_wf_act_hi_identitylink` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TIMESTAMP_` timestamp NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `OPERATION_TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNER_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LNK_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LNK_GROUP` (`GROUP_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LNK_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LNK_PROC_DEF_KEY` (`PROC_DEF_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LINK_TASK` (`TASK_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LINK_RM_TIME` (`REMOVAL_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_IDENT_LNK_TIMESTAMP` (`TIMESTAMP_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_identitylink
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_incident
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_incident`;
CREATE TABLE `incloud_base_wf_act_hi_incident` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp NOT NULL,
  `END_TIME_` timestamp NULL DEFAULT NULL,
  `INCIDENT_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `INCIDENT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ACTIVITY_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CAUSE_INCIDENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_CAUSE_INCIDENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `HISTORY_CONFIGURATION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `INCIDENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_INCIDENT_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_INCIDENT_PROC_DEF_KEY` (`PROC_DEF_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_INCIDENT_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_INCIDENT_PROCINST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_INCIDENT_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_incident
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_job_log
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_job_log`;
CREATE TABLE `incloud_base_wf_act_hi_job_log` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TIMESTAMP_` datetime NOT NULL,
  `JOB_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `JOB_DUEDATE_` datetime DEFAULT NULL,
  `JOB_RETRIES_` int(11) DEFAULT NULL,
  `JOB_PRIORITY_` bigint(20) NOT NULL DEFAULT '0',
  `JOB_EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `JOB_EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `JOB_STATE_` int(11) DEFAULT NULL,
  `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `JOB_DEF_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `JOB_DEF_CONFIGURATION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SEQUENCE_COUNTER_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_JOB_LOG_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_JOB_LOG_PROCINST` (`PROCESS_INSTANCE_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_JOB_LOG_PROCDEF` (`PROCESS_DEF_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_JOB_LOG_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_JOB_LOG_JOB_DEF_ID` (`JOB_DEF_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_JOB_LOG_PROC_DEF_KEY` (`PROCESS_DEF_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_JOB_LOG_EX_STACK` (`JOB_EXCEPTION_STACK_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_JOB_LOG_RM_TIME` (`REMOVAL_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_JOB_LOG_JOB_CONF` (`JOB_DEF_CONFIGURATION_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_job_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_op_log
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_op_log`;
CREATE TABLE `incloud_base_wf_act_hi_op_log` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `JOB_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BATCH_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP_` timestamp NOT NULL,
  `OPERATION_TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `OPERATION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ENTITY_TYPE_` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROPERTY_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ORG_VALUE_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NEW_VALUE_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  `CATEGORY_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXTERNAL_TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ANNOTATION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_OP_LOG_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_OP_LOG_PROCINST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_OP_LOG_PROCDEF` (`PROC_DEF_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_OP_LOG_TASK` (`TASK_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_OP_LOG_RM_TIME` (`REMOVAL_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_OP_LOG_TIMESTAMP` (`TIMESTAMP_`) USING BTREE,
  KEY `ACT_IDX_HI_OP_LOG_USER_ID` (`USER_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_OP_LOG_OP_TYPE` (`OPERATION_TYPE_`) USING BTREE,
  KEY `ACT_IDX_HI_OP_LOG_ENTITY_TYPE` (`ENTITY_TYPE_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_op_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_procinst`;
CREATE TABLE `incloud_base_wf_act_hi_procinst` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SUPER_CASE_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `STATE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_PRO_INST_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_PRO_INST_PROC_DEF_KEY` (`PROC_DEF_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_PRO_INST_PROC_TIME` (`START_TIME_`,`END_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_PI_PDEFID_END_TIME` (`PROC_DEF_ID_`,`END_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_PRO_INST_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_PRO_INST_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_procinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_taskinst`;
CREATE TABLE `incloud_base_wf_act_hi_taskinst` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `FOLLOW_UP_DATE_` datetime DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_TASKINST_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_TASK_INST_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_TASK_INST_PROC_DEF_KEY` (`PROC_DEF_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_TASKINST_PROCINST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_TASKINSTID_PROCINST` (`ID_`,`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_TASK_INST_RM_TIME` (`REMOVAL_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_TASK_INST_START` (`START_TIME_`) USING BTREE,
  KEY `ACT_IDX_HI_TASK_INST_END` (`END_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_taskinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_hi_varinst`;
CREATE TABLE `incloud_base_wf_act_hi_varinst` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `STATE_` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REMOVAL_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_HI_VARINST_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`) USING BTREE,
  KEY `ACT_IDX_HI_CASEVAR_CASE_INST` (`CASE_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_VAR_INST_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_VAR_INST_PROC_DEF_KEY` (`PROC_DEF_KEY_`) USING BTREE,
  KEY `ACT_IDX_HI_VARINST_BYTEAR` (`BYTEARRAY_ID_`) USING BTREE,
  KEY `ACT_IDX_HI_VARINST_RM_TIME` (`REMOVAL_TIME_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_hi_varinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_id_group
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_id_group`;
CREATE TABLE `incloud_base_wf_act_id_group` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_id_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_id_info
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_id_info`;
CREATE TABLE `incloud_base_wf_act_id_info` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_id_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_id_membership`;
CREATE TABLE `incloud_base_wf_act_id_membership` (
  `USER_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`) USING BTREE,
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `incloud_base_wf_act_id_group` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `incloud_base_wf_act_id_user` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_id_membership
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_id_tenant
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_id_tenant`;
CREATE TABLE `incloud_base_wf_act_id_tenant` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_id_tenant
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_id_tenant_member
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_id_tenant_member`;
CREATE TABLE `incloud_base_wf_act_id_tenant_member` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `USER_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `GROUP_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE KEY `ACT_UNIQ_TENANT_MEMB_USER` (`TENANT_ID_`,`USER_ID_`) USING BTREE,
  UNIQUE KEY `ACT_UNIQ_TENANT_MEMB_GROUP` (`TENANT_ID_`,`GROUP_ID_`) USING BTREE,
  KEY `ACT_FK_TENANT_MEMB_USER` (`USER_ID_`) USING BTREE,
  KEY `ACT_FK_TENANT_MEMB_GROUP` (`GROUP_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_TENANT_MEMB` FOREIGN KEY (`TENANT_ID_`) REFERENCES `incloud_base_wf_act_id_tenant` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TENANT_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `incloud_base_wf_act_id_group` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TENANT_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `incloud_base_wf_act_id_user` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_id_tenant_member
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_id_user
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_id_user`;
CREATE TABLE `incloud_base_wf_act_id_user` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SALT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `LOCK_EXP_TIME_` datetime DEFAULT NULL,
  `ATTEMPTS_` int(11) DEFAULT NULL,
  `PICTURE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_id_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_re_case_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_re_case_def`;
CREATE TABLE `incloud_base_wf_act_re_case_def` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `HISTORY_TTL_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_CASE_DEF_TENANT_ID` (`TENANT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_re_case_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_re_decision_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_re_decision_def`;
CREATE TABLE `incloud_base_wf_act_re_decision_def` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DEC_REQ_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DEC_REQ_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `HISTORY_TTL_` int(11) DEFAULT NULL,
  `VERSION_TAG_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_DEC_DEF_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_DEC_DEF_REQ_ID` (`DEC_REQ_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_DEC_REQ` FOREIGN KEY (`DEC_REQ_ID_`) REFERENCES `incloud_base_wf_act_re_decision_req_def` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_re_decision_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_re_decision_req_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_re_decision_req_def`;
CREATE TABLE `incloud_base_wf_act_re_decision_req_def` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_DEC_REQ_DEF_TENANT_ID` (`TENANT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_re_decision_req_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_re_deployment`;
CREATE TABLE `incloud_base_wf_act_re_deployment` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DEPLOY_TIME_` datetime DEFAULT NULL,
  `SOURCE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_DEPLOYMENT_NAME` (`NAME_`) USING BTREE,
  KEY `ACT_IDX_DEPLOYMENT_TENANT_ID` (`TENANT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_re_deployment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_re_procdef`;
CREATE TABLE `incloud_base_wf_act_re_procdef` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `VERSION_TAG_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `HISTORY_TTL_` int(11) DEFAULT NULL,
  `STARTABLE_` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_PROCDEF_DEPLOYMENT_ID` (`DEPLOYMENT_ID_`) USING BTREE,
  KEY `ACT_IDX_PROCDEF_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_PROCDEF_VER_TAG` (`VERSION_TAG_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_re_procdef
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_authorization
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_authorization`;
CREATE TABLE `incloud_base_wf_act_ru_authorization` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NOT NULL,
  `TYPE_` int(11) NOT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_TYPE_` int(11) NOT NULL,
  `RESOURCE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PERMS_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE KEY `ACT_UNIQ_AUTH_USER` (`USER_ID_`,`TYPE_`,`RESOURCE_TYPE_`,`RESOURCE_ID_`) USING BTREE,
  UNIQUE KEY `ACT_UNIQ_AUTH_GROUP` (`GROUP_ID_`,`TYPE_`,`RESOURCE_TYPE_`,`RESOURCE_ID_`) USING BTREE,
  KEY `ACT_IDX_AUTH_GROUP_ID` (`GROUP_ID_`) USING BTREE,
  KEY `ACT_IDX_AUTH_RESOURCE_ID` (`RESOURCE_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_authorization
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_batch
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_batch`;
CREATE TABLE `incloud_base_wf_act_ru_batch` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TOTAL_JOBS_` int(11) DEFAULT NULL,
  `JOBS_CREATED_` int(11) DEFAULT NULL,
  `JOBS_PER_SEED_` int(11) DEFAULT NULL,
  `INVOCATIONS_PER_JOB_` int(11) DEFAULT NULL,
  `SEED_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BATCH_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `MONITOR_JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CONFIGURATION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_BATCH_SEED_JOB_DEF` (`SEED_JOB_DEF_ID_`) USING BTREE,
  KEY `ACT_IDX_BATCH_MONITOR_JOB_DEF` (`MONITOR_JOB_DEF_ID_`) USING BTREE,
  KEY `ACT_IDX_BATCH_JOB_DEF` (`BATCH_JOB_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_BATCH_JOB_DEF` FOREIGN KEY (`BATCH_JOB_DEF_ID_`) REFERENCES `incloud_base_wf_act_ru_jobdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_BATCH_MONITOR_JOB_DEF` FOREIGN KEY (`MONITOR_JOB_DEF_ID_`) REFERENCES `incloud_base_wf_act_ru_jobdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_BATCH_SEED_JOB_DEF` FOREIGN KEY (`SEED_JOB_DEF_ID_`) REFERENCES `incloud_base_wf_act_ru_jobdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_batch
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_event_subscr`;
CREATE TABLE `incloud_base_wf_act_ru_event_subscr` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` datetime NOT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`) USING BTREE,
  KEY `ACT_IDX_EVENT_SUBSCR_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`) USING BTREE,
  KEY `ACT_IDX_EVENT_SUBSCR_EVT_NAME` (`EVENT_NAME_`) USING BTREE,
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_event_subscr
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_execution`;
CREATE TABLE `incloud_base_wf_act_ru_execution` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SUPER_CASE_EXEC_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `SEQUENCE_COUNTER_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_EXEC_ROOT_PI` (`ROOT_PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`) USING BTREE,
  KEY `ACT_IDX_EXEC_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`) USING BTREE,
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`) USING BTREE,
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `incloud_base_wf_act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_execution
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_ext_task
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_ext_task`;
CREATE TABLE `incloud_base_wf_act_ru_ext_task` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NOT NULL,
  `WORKER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TOPIC_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `ERROR_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ERROR_DETAILS_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `LOCK_EXP_TIME_` datetime DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_EXT_TASK_TOPIC` (`TOPIC_NAME_`) USING BTREE,
  KEY `ACT_IDX_EXT_TASK_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_EXT_TASK_PRIORITY` (`PRIORITY_`) USING BTREE,
  KEY `ACT_IDX_EXT_TASK_ERR_DETAILS` (`ERROR_DETAILS_ID_`) USING BTREE,
  KEY `ACT_IDX_EXT_TASK_EXEC` (`EXECUTION_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_EXT_TASK_ERROR_DETAILS` FOREIGN KEY (`ERROR_DETAILS_ID_`) REFERENCES `incloud_base_wf_act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_EXT_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_ext_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_filter
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_filter`;
CREATE TABLE `incloud_base_wf_act_ru_filter` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NOT NULL,
  `RESOURCE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `QUERY_` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROPERTIES_` longtext CHARACTER SET utf8 COLLATE utf8_bin,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_filter
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_identitylink`;
CREATE TABLE `incloud_base_wf_act_ru_identitylink` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`) USING BTREE,
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`) USING BTREE,
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`) USING BTREE,
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `incloud_base_wf_act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `incloud_base_wf_act_ru_task` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_identitylink
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_incident
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_incident`;
CREATE TABLE `incloud_base_wf_act_ru_incident` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NOT NULL,
  `INCIDENT_TIMESTAMP_` datetime NOT NULL,
  `INCIDENT_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `INCIDENT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CAUSE_INCIDENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ROOT_CAUSE_INCIDENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_INC_CONFIGURATION` (`CONFIGURATION_`) USING BTREE,
  KEY `ACT_IDX_INC_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_INC_JOB_DEF` (`JOB_DEF_ID_`) USING BTREE,
  KEY `ACT_IDX_INC_CAUSEINCID` (`CAUSE_INCIDENT_ID_`) USING BTREE,
  KEY `ACT_IDX_INC_EXID` (`EXECUTION_ID_`) USING BTREE,
  KEY `ACT_IDX_INC_PROCDEFID` (`PROC_DEF_ID_`) USING BTREE,
  KEY `ACT_IDX_INC_PROCINSTID` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_IDX_INC_ROOTCAUSEINCID` (`ROOT_CAUSE_INCIDENT_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_INC_CAUSE` FOREIGN KEY (`CAUSE_INCIDENT_ID_`) REFERENCES `incloud_base_wf_act_ru_incident` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_INC_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_INC_JOB_DEF` FOREIGN KEY (`JOB_DEF_ID_`) REFERENCES `incloud_base_wf_act_ru_jobdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_INC_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `incloud_base_wf_act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_INC_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_INC_RCAUSE` FOREIGN KEY (`ROOT_CAUSE_INCIDENT_ID_`) REFERENCES `incloud_base_wf_act_ru_incident` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_incident
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_job`;
CREATE TABLE `incloud_base_wf_act_ru_job` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` datetime DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `REPEAT_OFFSET_` bigint(20) DEFAULT '0',
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) NOT NULL DEFAULT '1',
  `JOB_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` bigint(20) NOT NULL DEFAULT '0',
  `SEQUENCE_COUNTER_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_JOB_EXECUTION_ID` (`EXECUTION_ID_`) USING BTREE,
  KEY `ACT_IDX_JOB_HANDLER` (`HANDLER_TYPE_`(100),`HANDLER_CFG_`(155)) USING BTREE,
  KEY `ACT_IDX_JOB_PROCINST` (`PROCESS_INSTANCE_ID_`) USING BTREE,
  KEY `ACT_IDX_JOB_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_JOB_JOB_DEF_ID` (`JOB_DEF_ID_`) USING BTREE,
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`) USING BTREE,
  KEY `ACT_IDX_JOB_HANDLER_TYPE` (`HANDLER_TYPE_`) USING BTREE,
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `incloud_base_wf_act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_job
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_jobdef
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_jobdef`;
CREATE TABLE `incloud_base_wf_act_ru_jobdef` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `JOB_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `JOB_CONFIGURATION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `JOB_PRIORITY_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_JOBDEF_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_IDX_JOBDEF_PROC_DEF_ID` (`PROC_DEF_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_jobdef
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_meter_log
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_meter_log`;
CREATE TABLE `incloud_base_wf_act_ru_meter_log` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REPORTER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` bigint(20) DEFAULT NULL,
  `TIMESTAMP_` datetime DEFAULT NULL,
  `MILLISECONDS_` bigint(20) DEFAULT '0',
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_METER_LOG_MS` (`MILLISECONDS_`) USING BTREE,
  KEY `ACT_IDX_METER_LOG_NAME_MS` (`NAME_`,`MILLISECONDS_`) USING BTREE,
  KEY `ACT_IDX_METER_LOG_REPORT` (`NAME_`,`REPORTER_`,`MILLISECONDS_`) USING BTREE,
  KEY `ACT_IDX_METER_LOG_TIME` (`TIMESTAMP_`) USING BTREE,
  KEY `ACT_IDX_METER_LOG` (`NAME_`,`TIMESTAMP_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_meter_log
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f550681-22fd-11eb-b5f9-1e10b4a24087', 'activity-instance-start', '127.0.0.1$default', 0, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f550682-22fd-11eb-b5f9-1e10b4a24087', 'job-acquired-failure', '127.0.0.1$default', 0, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f550683-22fd-11eb-b5f9-1e10b4a24087', 'job-locked-exclusive', '127.0.0.1$default', 0, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f550684-22fd-11eb-b5f9-1e10b4a24087', 'job-execution-rejected', '127.0.0.1$default', 0, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f550685-22fd-11eb-b5f9-1e10b4a24087', 'executed-decision-elements', '127.0.0.1$default', 0, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f550686-22fd-11eb-b5f9-1e10b4a24087', 'activity-instance-end', '127.0.0.1$default', 0, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f550687-22fd-11eb-b5f9-1e10b4a24087', 'job-successful', '127.0.0.1$default', 0, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f550688-22fd-11eb-b5f9-1e10b4a24087', 'job-acquired-success', '127.0.0.1$default', 0, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f550689-22fd-11eb-b5f9-1e10b4a24087', 'job-acquisition-attempt', '127.0.0.1$default', 5, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('5f55068a-22fd-11eb-b5f9-1e10b4a24087', 'job-failed', '127.0.0.1$default', 0, '2020-11-10 10:35:19', 1604975718638);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b5674927-22fc-11eb-b5f9-1e10b4a24087', 'activity-instance-start', '127.0.0.1$default', 6, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b5674928-22fc-11eb-b5f9-1e10b4a24087', 'job-acquired-failure', '127.0.0.1$default', 0, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b5674929-22fc-11eb-b5f9-1e10b4a24087', 'job-locked-exclusive', '127.0.0.1$default', 0, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b567492a-22fc-11eb-b5f9-1e10b4a24087', 'job-execution-rejected', '127.0.0.1$default', 0, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b567492b-22fc-11eb-b5f9-1e10b4a24087', 'executed-decision-elements', '127.0.0.1$default', 0, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b567492c-22fc-11eb-b5f9-1e10b4a24087', 'activity-instance-end', '127.0.0.1$default', 5, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b567492d-22fc-11eb-b5f9-1e10b4a24087', 'job-successful', '127.0.0.1$default', 0, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b567492e-22fc-11eb-b5f9-1e10b4a24087', 'job-acquired-success', '127.0.0.1$default', 0, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b567492f-22fc-11eb-b5f9-1e10b4a24087', 'job-acquisition-attempt', '127.0.0.1$default', 15, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('b5674930-22fc-11eb-b5f9-1e10b4a24087', 'job-failed', '127.0.0.1$default', 0, '2020-11-10 10:30:34', 1604975433545);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188d4-232f-11eb-8f37-1e10b4a24087', 'activity-instance-start', '127.0.0.1$default', 0, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188d5-232f-11eb-8f37-1e10b4a24087', 'job-acquired-failure', '127.0.0.1$default', 0, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188d6-232f-11eb-8f37-1e10b4a24087', 'job-locked-exclusive', '127.0.0.1$default', 0, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188d7-232f-11eb-8f37-1e10b4a24087', 'job-execution-rejected', '127.0.0.1$default', 0, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188d8-232f-11eb-8f37-1e10b4a24087', 'executed-decision-elements', '127.0.0.1$default', 0, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188d9-232f-11eb-8f37-1e10b4a24087', 'activity-instance-end', '127.0.0.1$default', 0, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188da-232f-11eb-8f37-1e10b4a24087', 'job-successful', '127.0.0.1$default', 0, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188db-232f-11eb-8f37-1e10b4a24087', 'job-acquired-success', '127.0.0.1$default', 0, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188dc-232f-11eb-8f37-1e10b4a24087', 'job-acquisition-attempt', '127.0.0.1$default', 9, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('bbc188dd-232f-11eb-8f37-1e10b4a24087', 'job-failed', '127.0.0.1$default', 0, '2020-11-10 16:35:49', 1604997348535);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc87a-2331-11eb-8ad0-1e10b4a24087', 'activity-instance-start', '192.168.124.25$default', 0, '2020-11-10 16:51:42', 1604998301971);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc87b-2331-11eb-8ad0-1e10b4a24087', 'job-acquired-failure', '192.168.124.25$default', 0, '2020-11-10 16:51:42', 1604998301971);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc87c-2331-11eb-8ad0-1e10b4a24087', 'job-locked-exclusive', '192.168.124.25$default', 0, '2020-11-10 16:51:42', 1604998301971);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc87d-2331-11eb-8ad0-1e10b4a24087', 'job-execution-rejected', '192.168.124.25$default', 0, '2020-11-10 16:51:42', 1604998301971);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc87e-2331-11eb-8ad0-1e10b4a24087', 'executed-decision-elements', '192.168.124.25$default', 0, '2020-11-10 16:51:42', 1604998301971);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc87f-2331-11eb-8ad0-1e10b4a24087', 'activity-instance-end', '192.168.124.25$default', 0, '2020-11-10 16:51:42', 1604998301971);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc880-2331-11eb-8ad0-1e10b4a24087', 'job-successful', '192.168.124.25$default', 0, '2020-11-10 16:51:42', 1604998301971);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc881-2331-11eb-8ad0-1e10b4a24087', 'job-acquired-success', '192.168.124.25$default', 0, '2020-11-10 16:51:42', 1604998301971);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc882-2331-11eb-8ad0-1e10b4a24087', 'job-acquisition-attempt', '192.168.124.25$default', 18, '2020-11-10 16:51:42', 1604998301971);
INSERT INTO `incloud_base_wf_act_ru_meter_log` VALUES ('f40cc883-2331-11eb-8ad0-1e10b4a24087', 'job-failed', '192.168.124.25$default', 0, '2020-11-10 16:51:42', 1604998301971);
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_task`;
CREATE TABLE `incloud_base_wf_act_ru_task` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `FOLLOW_UP_DATE_` datetime DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`) USING BTREE,
  KEY `ACT_IDX_TASK_ASSIGNEE` (`ASSIGNEE_`) USING BTREE,
  KEY `ACT_IDX_TASK_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`) USING BTREE,
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `incloud_base_wf_act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_act_ru_variable`;
CREATE TABLE `incloud_base_wf_act_ru_variable` (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CASE_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `VAR_SCOPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `SEQUENCE_COUNTER_` bigint(20) DEFAULT NULL,
  `IS_CONCURRENT_LOCAL_` tinyint(4) DEFAULT NULL,
  `TENANT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE KEY `ACT_UNIQ_VARIABLE` (`VAR_SCOPE_`,`NAME_`) USING BTREE,
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`) USING BTREE,
  KEY `ACT_IDX_VARIABLE_TENANT_ID` (`TENANT_ID_`) USING BTREE,
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`) USING BTREE,
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`) USING BTREE,
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `incloud_base_wf_act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `incloud_base_wf_act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of incloud_base_wf_act_ru_variable
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_button
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_button`;
CREATE TABLE `incloud_base_wf_button` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `button_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `button_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????code',
  `is_enable` int(1) NOT NULL COMMENT '????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='$??????????????????';

-- ----------------------------
-- Records of incloud_base_wf_button
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_button` VALUES (1311229012470771714, '??????', 'save', 1, '2020-09-30 16:58:53', '2020-09-30 16:58:53');
INSERT INTO `incloud_base_wf_button` VALUES (1311229068133380098, '??????', 'submit', 1, '2020-09-30 16:59:06', '2020-09-30 16:59:06');
INSERT INTO `incloud_base_wf_button` VALUES (1311229114136506370, '??????', 'suspend_process', 1, '2020-09-30 16:59:17', '2020-09-30 16:59:17');
INSERT INTO `incloud_base_wf_button` VALUES (1311229162828181506, '??????', 'end_process', 1, '2020-09-30 16:59:29', '2020-09-30 16:59:29');
INSERT INTO `incloud_base_wf_button` VALUES (1311229223544926210, '??????', 'activate_process', 1, '2020-09-30 16:59:43', '2020-09-30 16:59:43');
INSERT INTO `incloud_base_wf_button` VALUES (1311229278771326977, '??????', 'delete_process', 1, '2020-09-30 16:59:56', '2020-09-30 16:59:56');
INSERT INTO `incloud_base_wf_button` VALUES (1311229320986996738, '??????', 'reject', 1, '2020-09-30 17:00:06', '2020-09-30 17:00:06');
INSERT INTO `incloud_base_wf_button` VALUES (1323797882276417537, '??????', 'countersign', 1, '2020-11-04 09:23:05', '2020-11-04 09:23:05');
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_button_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_button_def`;
CREATE TABLE `incloud_base_wf_button_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `button_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????code',
  `button_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????name',
  `node_def_id` bigint(20) DEFAULT NULL COMMENT '????????????Id',
  `procdef_id` bigint(20) DEFAULT NULL COMMENT '????????????id',
  `is_enable` int(1) DEFAULT NULL COMMENT '????????????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_node_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `button_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????code',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????-??????';

-- ----------------------------
-- Records of incloud_base_wf_button_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_comm_language
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_comm_language`;
CREATE TABLE `incloud_base_wf_comm_language` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????id',
  `create_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `use_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????id',
  `use_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `is_general` int(1) DEFAULT NULL COMMENT '??????????????? ?????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='??????????????????';

-- ----------------------------
-- Records of incloud_base_wf_comm_language
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_delegation
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_delegation`;
CREATE TABLE `incloud_base_wf_delegation` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `procdef_type_id` bigint(20) NOT NULL COMMENT '????????????id',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `delegation_person_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????id',
  `delegation_person_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `designate_person_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????id',
  `designate_person_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `is_activation` int(1) NOT NULL COMMENT '???????????? 0??? 1???',
  `delegation_start_time` datetime NOT NULL COMMENT '??????????????????',
  `delegation_end_time` datetime NOT NULL COMMENT '??????????????????',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_delegation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_done_task
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_done_task`;
CREATE TABLE `incloud_base_wf_done_task` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_procdef_version` int(2) NOT NULL COMMENT 'camunda??????????????????',
  `camunda_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda??????ID',
  `camunda_exection_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda exection??????ID',
  `camunda_act_ins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????id',
  `procins_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procins_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `node_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????key???ID',
  `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `node_type` int(2) NOT NULL COMMENT '????????????',
  `decision` int(1) NOT NULL COMMENT '??????????????????',
  `is_agree` int(1) NOT NULL COMMENT '????????????',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `ownner` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `assignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `assignee_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `priority` int(2) DEFAULT NULL COMMENT '???????????????',
  `due_date` datetime DEFAULT NULL COMMENT '??????????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `procdef_type_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `cliam_time` datetime DEFAULT NULL COMMENT '????????????',
  `state` int(2) DEFAULT NULL COMMENT '????????????',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `apply_time` datetime DEFAULT NULL COMMENT '????????????',
  `starter_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `starter_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `starter_org_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_org_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `biz_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????key',
  `form_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????key',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `module_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????code',
  `module_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `form_id` bigint(20) NOT NULL COMMENT '????????????id',
  `form_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `is_clone` int(1) DEFAULT NULL COMMENT '??????clone',
  `is_call_activity` int(1) NOT NULL COMMENT '???????????????',
  `camunda_parent_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda ???????????????ID',
  `parent_procins_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID',
  `child_log_procins_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `parent_log_procins_id` bigint(20) DEFAULT NULL COMMENT '?????????????????????',
  `camunda_child_log_procins_id` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `camunda_parent_log_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `current_activity_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????ID',
  `current_activity_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `current_activity_assignee` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `current_activity_assignee_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????????????????',
  `be_cloned_from_camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????clone??????????????????clone???????????????ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_done_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_duplicate_response
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_duplicate_response`;
CREATE TABLE `incloud_base_wf_duplicate_response` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `in_duplicate_task_id` bigint(20) NOT NULL COMMENT '???????????????id',
  `ownner` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????id',
  `ownner_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `out_duplicate_task_id` bigint(20) DEFAULT NULL COMMENT '???????????????id',
  `assignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????id',
  `assignee_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_duplicate_response
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_event
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_event`;
CREATE TABLE `incloud_base_wf_event` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `event_type` int(1) NOT NULL COMMENT '????????????',
  `listener_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????ID',
  `listener_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `listener_impl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????rest???bean',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `select_sign` int(1) NOT NULL COMMENT '??????????????????',
  `select_must` int(1) DEFAULT NULL COMMENT '??????????????????',
  `procdef_type_id` bigint(20) NOT NULL COMMENT '????????????id',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `default_trig_val` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `node_event_type` int(1) NOT NULL COMMENT '?????????????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_event
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_event` VALUES (1310392923866533889, 1, 'processStateDelegate', '??????????????????', 'com.netwisd.base.wf.event.delegate.ProcessStateDelegate', NULL, 1, 1, 0, '????????????', 'end', 0, '2020-09-28 09:36:34', '2020-09-28 09:36:34');
INSERT INTO `incloud_base_wf_event` VALUES (1310393531046563841, 0, 'wfTodoTaskListener', '??????????????????', 'com.netwisd.base.wf.event.listener.WfTodoTaskListener', NULL, 1, 1, 0, '????????????', 'create', 12, '2020-09-28 09:38:58', '2020-09-28 09:38:58');
INSERT INTO `incloud_base_wf_event` VALUES (1310394709394329601, 1, 'variableClearDelegate', '????????????????????????', 'com.netwisd.base.wf.event.delegate.VariableClearDelegate', NULL, 0, 1, 0, '????????????', 'take', 3, '2020-09-28 09:43:39', '2020-09-28 09:43:39');
INSERT INTO `incloud_base_wf_event` VALUES (1310853701895720962, 1, 'endDelegate', '?????????????????????', 'com.netwisd.base.wf.event.listener.EndDelegate', NULL, 0, NULL, 1311230514199375873, '????????????', 'end_process', 0, '2020-09-29 16:07:32', '2020-09-29 16:07:32');
INSERT INTO `incloud_base_wf_event` VALUES (1311230339821187074, 0, 'deleteWfTodoTaskListener', '????????????????????????', 'com.netwisd.base.wf.event.listener.DeleteWfTodoTaskListener', NULL, 1, 1, 0, '????????????', 'delete', 12, '2020-09-30 17:04:09', '2020-09-30 17:04:09');
INSERT INTO `incloud_base_wf_event` VALUES (1318820114346319874, 1, 'processStartDelegate', '???????????????????????????', 'com.netwisd.base.wf.event.delegate.ProcessStartDelegate', NULL, 1, 1, 0, '????????????', 'start', 0, '2020-10-21 15:43:12', '2020-10-21 15:43:12');
INSERT INTO `incloud_base_wf_event` VALUES (1321647311671005185, 1, 'callActivityEndDelegate', '?????????????????????', 'com.netwisd.base.wf.event.delegate.CallActivityEndDelegate', NULL, 1, 1, 0, '????????????', 'end', 15, '2020-10-29 10:57:29', '2020-10-29 10:57:29');
INSERT INTO `incloud_base_wf_event` VALUES (1321725465810964481, 1, 'callActivityStartDelegate', '???????????????????????????', 'com.netwisd.base.wf.event.delegate.CallActivityStartDelegate', NULL, 1, 1, 0, '????????????', 'start', 15, '2020-10-29 16:08:02', '2020-10-29 16:08:02');
INSERT INTO `incloud_base_wf_event` VALUES (1326082497033953281, 0, 'currentActSthListener', '??????????????????????????????', 'com.netwisd.base.wf.event.listener.CurrentActSthListener', NULL, 1, 1, 0, '????????????', 'complete', 2, '2020-11-10 16:41:19', '2020-11-10 16:41:19');
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_event_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_event_def`;
CREATE TABLE `incloud_base_wf_event_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `event_type` int(1) NOT NULL COMMENT '????????????',
  `event_bind_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `event_id` bigint(20) NOT NULL COMMENT '??????????????????id',
  `event_submit_sign` int(1) DEFAULT NULL COMMENT '?????????????????????????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `node_def_id` bigint(20) DEFAULT NULL COMMENT '????????????ID',
  `event_type_sign` int(1) NOT NULL COMMENT '????????????????????????(???????????????????????????????????????????????????)',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_node_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_event_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_event_msg
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_event_msg`;
CREATE TABLE `incloud_base_wf_event_msg` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `event_type` int(2) NOT NULL COMMENT '??????????????????',
  `event_handle_type` int(2) NOT NULL COMMENT '??????????????????',
  `node_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????id',
  `node_type` int(2) NOT NULL COMMENT '????????????',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????',
  `is_read` int(1) NOT NULL COMMENT '????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????????????????';

-- ----------------------------
-- Records of incloud_base_wf_event_msg
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_event_param
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_event_param`;
CREATE TABLE `incloud_base_wf_event_param` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `event_id` bigint(20) NOT NULL COMMENT '??????ID',
  `param_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `param_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `param_defalut_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????',
  `param_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????id',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????????????????';

-- ----------------------------
-- Records of incloud_base_wf_event_param
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_event_param` VALUES (1311255530118860802, 1310394709394329601, 'expression', 'variableExpression', 'wfDecisionInstances,wfUnDecisionInstances', 'variableExpression', '2020-09-30 18:44:15', '2020-09-30 18:44:15');
INSERT INTO `incloud_base_wf_event_param` VALUES (1326093469252354051, 1311230339821187074, 'expression', 'variableExpression', 'wfDecisionInstances,wfUnDecisionInstances', 'variableExpression', '2020-11-10 17:24:55', '2020-11-10 17:24:55');
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_event_param_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_event_param_def`;
CREATE TABLE `incloud_base_wf_event_param_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `event_def_id` bigint(20) NOT NULL COMMENT '??????def id',
  `param_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `param_id` bigint(20) NOT NULL COMMENT '????????????id',
  `param_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_node_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `node_def_id` bigint(20) DEFAULT NULL COMMENT '????????????ID',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????????????????';

-- ----------------------------
-- Records of incloud_base_wf_event_param_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_expre
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_expre`;
CREATE TABLE `incloud_base_wf_expre` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `procdef_type_id` bigint(50) NOT NULL COMMENT '????????????Id',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `expre_type` int(1) NOT NULL COMMENT '???????????????',
  `expre_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `expre_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????',
  `expre_return_type` int(2) NOT NULL COMMENT '?????????????????????',
  `expre_return_generics` int(2) DEFAULT NULL COMMENT '?????????????????????',
  `expre_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='???????????????';

-- ----------------------------
-- Records of incloud_base_wf_expre
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_expre` VALUES (1311238105499545602, 1311230514199375873, '????????????', 1, '????????????????????????', 'wfConditionExpression.invokeFormMethod()', 5, NULL, NULL, '2020-09-30 17:35:01', '2020-09-30 17:35:01');
INSERT INTO `incloud_base_wf_expre` VALUES (1323138468217360386, 0, '????????????', 0, '?????????????????????', 'wfUserExpression.getUserByDeptId()', -1, NULL, NULL, '2020-11-02 13:42:48', '2020-11-02 13:42:48');
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_expre_param
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_expre_param`;
CREATE TABLE `incloud_base_wf_expre_param` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `param_type` int(1) NOT NULL COMMENT '????????????',
  `param_var_type` int(2) NOT NULL COMMENT '????????????',
  `param_var_generics` int(2) DEFAULT NULL COMMENT '????????????',
  `param_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????ID',
  `param_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `expre_id` bigint(20) NOT NULL COMMENT '?????????ID',
  `param_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `sequence_num` int(10) DEFAULT NULL COMMENT '??????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='?????????????????????';

-- ----------------------------
-- Records of incloud_base_wf_expre_param
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_expre_param` VALUES (1311238105503739906, 2, 12, NULL, 'param', 'param', 1311238105499545602, NULL, 1, '2020-09-30 17:35:01', '2020-09-30 17:35:01');
INSERT INTO `incloud_base_wf_expre_param` VALUES (1323138468221554690, 1, 5, NULL, 'deptIds', '??????Id', 1323138468217360386, '??????Id', 1, '2020-11-02 13:42:48', '2020-11-02 13:42:48');
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_expre_sequ_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_expre_sequ_def`;
CREATE TABLE `incloud_base_wf_expre_sequ_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `expre_id` bigint(20) NOT NULL COMMENT '?????????id',
  `expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `sequ_def_id` bigint(20) NOT NULL COMMENT 'def?????????ID',
  `camunda_sequ_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????ID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda????????????key',
  `procdef_id` bigint(20) DEFAULT NULL COMMENT '????????????id',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????-?????????-?????????';

-- ----------------------------
-- Records of incloud_base_wf_expre_sequ_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_expre_sequ_param_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_expre_sequ_param_def`;
CREATE TABLE `incloud_base_wf_expre_sequ_param_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `expre_param_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID',
  `expre_param_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????????????????????????????????????????',
  `expre_param_source` int(1) DEFAULT NULL COMMENT '????????????',
  `expre_sequ_def_id` bigint(20) NOT NULL COMMENT '????????? ?????????def id',
  `camunda_sequ_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????ID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda????????????key',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????-?????????-?????????-??????';

-- ----------------------------
-- Records of incloud_base_wf_expre_sequ_param_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_expre_user_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_expre_user_def`;
CREATE TABLE `incloud_base_wf_expre_user_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `node_def_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `node_type` int(2) NOT NULL COMMENT '????????????',
  `expre_id` bigint(20) NOT NULL COMMENT '?????????id',
  `expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `expression_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_node_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `biz_type` int(2) DEFAULT NULL COMMENT '??????????????????',
  `biz_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????????????????id',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='?????????????????????';

-- ----------------------------
-- Records of incloud_base_wf_expre_user_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_expre_user_param_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_expre_user_param_def`;
CREATE TABLE `incloud_base_wf_expre_user_param_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `node_def_id` bigint(20) NOT NULL COMMENT '??????ID',
  `node_type` int(2) NOT NULL COMMENT '????????????',
  `expre_param_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID',
  `expre_param_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????????????????????????????????????????',
  `expre_param_source` int(1) DEFAULT NULL COMMENT '????????????',
  `expre_user_def_id` bigint(20) DEFAULT NULL COMMENT '?????????defID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_node_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `expre_param_var_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='???????????????????????????';

-- ----------------------------
-- Records of incloud_base_wf_expre_user_param_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_form
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_form`;
CREATE TABLE `incloud_base_wf_form` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `form_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????Key',
  `form_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `form_save_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????url',
  `form_update_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????url',
  `form_view_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????url',
  `module_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????code',
  `module_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `data_source_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????',
  `is_enable` int(1) NOT NULL COMMENT '????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_form
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_form` VALUES (1311231242926141442, 'jiekuan', '??????', '/urm/loan/procSave', '/urm/loan/procSubmit', '/urm/loan/procView', 'expense', '??????', '1290548150490107905', 'superAdmin', 1, '2020-09-30 17:07:44', '2020-09-30 17:07:44');
INSERT INTO `incloud_base_wf_form` VALUES (1323167527345328129, 'expense_capital_loan', '??????', '/urm/loan/procSave', '/urm/loan/procSubmit', '/urm/loan/procView', 'expense', '??????', '1290548150490107905', '??????', 1, '2020-11-02 15:38:16', '2020-11-02 15:38:16');
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_form_button
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_form_button`;
CREATE TABLE `incloud_base_wf_form_button` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `form_id` bigint(20) NOT NULL,
  `button_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_form_button
-- ----------------------------
BEGIN;
INSERT INTO `incloud_base_wf_form_button` VALUES (1325990898568495105, 1311231242926141442, 1311229012470771714, '2020-11-10 10:37:21', '2020-11-10 10:37:21');
INSERT INTO `incloud_base_wf_form_button` VALUES (1325990898593660929, 1311231242926141442, 1311229068133380098, '2020-11-10 10:37:21', '2020-11-10 10:37:21');
INSERT INTO `incloud_base_wf_form_button` VALUES (1325990898602049538, 1311231242926141442, 1311229114136506370, '2020-11-10 10:37:21', '2020-11-10 10:37:21');
INSERT INTO `incloud_base_wf_form_button` VALUES (1325990898602049539, 1311231242926141442, 1311229162828181506, '2020-11-10 10:37:21', '2020-11-10 10:37:21');
INSERT INTO `incloud_base_wf_form_button` VALUES (1325990898602049540, 1311231242926141442, 1311229223544926210, '2020-11-10 10:37:21', '2020-11-10 10:37:21');
INSERT INTO `incloud_base_wf_form_button` VALUES (1325990898606243842, 1311231242926141442, 1311229278771326977, '2020-11-10 10:37:21', '2020-11-10 10:37:21');
INSERT INTO `incloud_base_wf_form_button` VALUES (1325990898610438146, 1311231242926141442, 1311229320986996738, '2020-11-10 10:37:21', '2020-11-10 10:37:21');
INSERT INTO `incloud_base_wf_form_button` VALUES (1325990898614632449, 1311231242926141442, 1323797882276417537, '2020-11-10 10:37:21', '2020-11-10 10:37:21');
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_form_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_form_def`;
CREATE TABLE `incloud_base_wf_form_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `form_id` bigint(100) NOT NULL COMMENT '??????ID',
  `form_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `node_def_id` bigint(20) DEFAULT NULL COMMENT '????????????ID',
  `camunda_node_def_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda????????????ID',
  `camunda_sequ_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda???????????????id',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='??????????????????';

-- ----------------------------
-- Records of incloud_base_wf_form_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_form_var
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_form_var`;
CREATE TABLE `incloud_base_wf_form_var` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `var_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????var',
  `column_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????',
  `var_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????? ?????????????????????',
  `var_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `java_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Java??????',
  `biz_table` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `biz_table_alias` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `form_id` bigint(20) NOT NULL COMMENT '??????ID',
  `is_null` int(1) NOT NULL COMMENT '????????????',
  `is_pri` int(1) NOT NULL COMMENT '????????????',
  `column_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `null_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='??????????????????';

-- ----------------------------
-- Records of incloud_base_wf_form_var
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_form_var_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_form_var_def`;
CREATE TABLE `incloud_base_wf_form_var_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `form_var_id` bigint(20) NOT NULL COMMENT '????????????id ',
  `form_id` bigint(20) NOT NULL COMMENT '??????ID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_node_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `node_def_id` bigint(20) NOT NULL COMMENT '??????ID',
  `var_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????? (??????code)',
  `power_code` int(1) NOT NULL COMMENT '??????????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????????????????';

-- ----------------------------
-- Records of incloud_base_wf_form_var_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_my_in_duplicate_task
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_my_in_duplicate_task`;
CREATE TABLE `incloud_base_wf_my_in_duplicate_task` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_procdef_version` int(2) NOT NULL COMMENT 'camunda??????????????????',
  `camunda_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID??????????????????????????????',
  `camunda_node_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key??????????????????????????????',
  `camunda_node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????name??????????????????????????????',
  `camunda_node_type` int(2) NOT NULL COMMENT '?????????????????????????????????',
  `procins_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procins_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `ownner` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `ownner_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `assignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `assignee_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `procdef_type_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `cliam_time` datetime DEFAULT NULL COMMENT '????????????',
  `state` int(2) DEFAULT NULL COMMENT '??????',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `apply_time` datetime DEFAULT NULL COMMENT '????????????',
  `starter_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `starter_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `starter_org_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_org_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `biz_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????key',
  `is_duplicated` int(1) DEFAULT NULL COMMENT '????????????',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `out_duplicate_task_id` bigint(20) NOT NULL COMMENT '???????????????id',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `form_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????key',
  `is_call_activity` int(1) NOT NULL COMMENT '???????????????',
  `form_id` bigint(20) NOT NULL COMMENT '????????????id',
  `form_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `module_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????code',
  `module_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `is_clone` int(1) DEFAULT NULL COMMENT '??????clone',
  `camunda_parent_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda ???????????????ID',
  `parent_procins_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID',
  `child_log_procins_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `parent_log_procins_id` bigint(20) DEFAULT NULL COMMENT '?????????????????????',
  `camunda_child_log_procins_id` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `camunda_parent_log_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `is_cloned_by_camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????clone??????????????????clone???????????????ID',
  `current_activity_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????ID',
  `current_activity_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `current_activity_assignee` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `current_activity_assignee_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='???????????????????????????';

-- ----------------------------
-- Records of incloud_base_wf_my_in_duplicate_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_my_out_duplicate_task
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_my_out_duplicate_task`;
CREATE TABLE `incloud_base_wf_my_out_duplicate_task` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_procdef_version` int(2) NOT NULL COMMENT 'camunda??????????????????',
  `camunda_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID??????????????????????????????',
  `camunda_node_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key??????????????????????????????',
  `camunda_node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????name??????????????????????????????',
  `camunda_node_type` int(2) NOT NULL COMMENT '?????????????????????????????????',
  `procins_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procins_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `assignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `procdef_type_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `state` int(2) DEFAULT NULL COMMENT '??????',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `apply_time` datetime DEFAULT NULL COMMENT '????????????',
  `starter_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `starter_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `starter_org_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_org_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `biz_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????key',
  `assignee_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `form_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????key',
  `is_call_activity` int(1) NOT NULL COMMENT '???????????????',
  `form_id` bigint(20) NOT NULL COMMENT '????????????id',
  `form_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `module_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????code',
  `module_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `is_clone` int(1) DEFAULT NULL COMMENT '??????clone',
  `camunda_parent_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda ???????????????ID',
  `parent_procins_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID',
  `child_log_procins_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `parent_log_procins_id` bigint(20) DEFAULT NULL COMMENT '?????????????????????',
  `camunda_child_log_procins_id` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `camunda_parent_log_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `is_cloned_by_camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????clone??????????????????clone???????????????ID',
  `current_activity_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????ID',
  `current_activity_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `current_activity_assignee` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `current_activity_assignee_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='??????????????????';

-- ----------------------------
-- Records of incloud_base_wf_my_out_duplicate_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_node_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_node_def`;
CREATE TABLE `incloud_base_wf_node_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `camunda_node_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda??????ID',
  `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `node_type` int(2) NOT NULL COMMENT '????????????',
  `due_date` double(3,1) DEFAULT NULL COMMENT '????????????',
  `follow_up_date` double(3,1) DEFAULT NULL COMMENT '????????????',
  `priority` int(2) DEFAULT NULL COMMENT '???????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `is_multi_task` int(1) DEFAULT NULL COMMENT '??????????????????',
  `select_rule` int(1) DEFAULT NULL COMMENT '????????????',
  `batch_rule` int(1) DEFAULT NULL COMMENT '??????????????????',
  `cancel_rule` int(1) DEFAULT NULL COMMENT '??????????????????',
  `return_rule` int(1) DEFAULT NULL COMMENT '??????????????????',
  `passing_rate` decimal(10,2) DEFAULT NULL COMMENT '???????????????',
  `passing_handle` int(1) DEFAULT NULL COMMENT '????????????????????????',
  `unpassing_handle` int(1) DEFAULT NULL COMMENT '???????????????????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `camunda_parent_node_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????nodeId',
  `is_look_over` int(1) DEFAULT NULL COMMENT '??????????????? ???????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????-????????????';

-- ----------------------------
-- Records of incloud_base_wf_node_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_node_detail_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_node_detail_def`;
CREATE TABLE `incloud_base_wf_node_detail_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `select_rule` int(1) NOT NULL COMMENT '????????????',
  `batch_rule` int(1) NOT NULL COMMENT '??????????????????',
  `cancel_rule` int(1) NOT NULL COMMENT '??????????????????',
  `return_rule` int(1) NOT NULL COMMENT '??????????????????',
  `passing_rate` decimal(10,2) DEFAULT NULL COMMENT '???????????????',
  `passing_handle` int(1) DEFAULT NULL COMMENT '????????????????????????',
  `unpassing_handle` int(1) DEFAULT NULL COMMENT '???????????????????????????',
  `node_def_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????-????????????-detail';

-- ----------------------------
-- Records of incloud_base_wf_node_detail_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_proc_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_proc_def`;
CREATE TABLE `incloud_base_wf_proc_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????ID',
  `procdef_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????key??????????????????',
  `procdef_version` int(2) NOT NULL COMMENT '????????????',
  `deployment_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????ID',
  `resource_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `suspention_state` int(1) NOT NULL COMMENT '????????????',
  `tenant_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????ID',
  `version_tag` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `startable` int(1) NOT NULL COMMENT '????????????',
  `deploy_time` datetime NOT NULL COMMENT '????????????',
  `data_source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `current_version` int(11) NOT NULL COMMENT '????????????',
  `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????id',
  `create_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `procdef_type_id` bigint(50) NOT NULL COMMENT '????????????ID',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `remind_sign` int(1) DEFAULT NULL COMMENT '??????????????????',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `form_id` bigint(20) NOT NULL COMMENT '????????????id',
  `form_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `module_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????code',
  `module_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `form_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????key',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `is_clone` int(1) DEFAULT NULL COMMENT '??????clone',
  `camunda_parent_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda ???????????????ID',
  `parent_procdef_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID',
  `child_log_procdef_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????????????????',
  `camunda_child_log_procdef_id` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `is_cloned_by_camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????clone??????????????????clone???????????????ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_proc_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_proc_def_rel
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_proc_def_rel`;
CREATE TABLE `incloud_base_wf_proc_def_rel` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `main_camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????id',
  `child_camunda_procdef_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????key',
  `child_procdef_version` int(2) NOT NULL COMMENT '????????????',
  `main_camunda_node_def_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `main_node_def_id` bigint(20) DEFAULT NULL COMMENT '????????????Id',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `main_procdef_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'main_procdef_name',
  `main_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'main_procdef_name',
  `child_camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????id',
  `child_procdef_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????????????????',
  `child_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `main_node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='???????????????????????????????????????';

-- ----------------------------
-- Records of incloud_base_wf_proc_def_rel
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_procdef_type
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_procdef_type`;
CREATE TABLE `incloud_base_wf_procdef_type` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `procdef_type_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `is_enable` int(2) DEFAULT NULL COMMENT '????????????',
  `parent_id` bigint(20) NOT NULL COMMENT '????????????id',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_procdef_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_process
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_process`;
CREATE TABLE `incloud_base_wf_process` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_procdef_version` int(2) NOT NULL COMMENT 'camunda??????????????????',
  `camunda_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `procins_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `biz_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????key',
  `start_time` datetime DEFAULT NULL COMMENT '??????????????????',
  `end_time` datetime DEFAULT NULL COMMENT '??????????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `procdef_type_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `apply_time` datetime DEFAULT NULL COMMENT '????????????',
  `starter_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `starter_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `starter_org_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_org_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `state` int(2) NOT NULL COMMENT '????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `is_clone` int(1) DEFAULT NULL COMMENT '??????clone',
  `camunda_parent_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda ???????????????ID',
  `parent_procins_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID',
  `module_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????code',
  `module_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `form_id` bigint(20) NOT NULL COMMENT '????????????id',
  `form_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `form_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????key',
  `is_call_activity` int(1) NOT NULL COMMENT '???????????????',
  `child_log_procins_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `parent_log_procins_id` bigint(20) DEFAULT NULL COMMENT '?????????????????????',
  `camunda_child_log_procins_id` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `camunda_parent_log_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `camunda_call_activity_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda callActivity??????ID',
  `current_activity_id` varchar(50) DEFAULT NULL COMMENT '????????????ID',
  `current_activity_name` varchar(255) DEFAULT NULL COMMENT '??????????????????',
  `current_activity_assignee` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `current_activity_assignee_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????????????????',
  `be_cloned_from_camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????clone??????????????????clone???????????????ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_process
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_process_log
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_process_log`;
CREATE TABLE `incloud_base_wf_process_log` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `node_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????ID',
  `node_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `node_type` int(2) DEFAULT NULL COMMENT '????????????',
  `target_node_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????ID',
  `target_node_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `target_node_type` int(2) DEFAULT NULL COMMENT '??????????????????',
  `user_id` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????ID',
  `user_name` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `dept_id` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????ID',
  `dept_name` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `org_id` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????ID',
  `org_name` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `start_time` datetime NOT NULL COMMENT '????????????',
  `end_time` datetime DEFAULT NULL COMMENT '????????????',
  `type` int(2) DEFAULT NULL COMMENT '????????????',
  `decision` int(1) DEFAULT NULL COMMENT '??????????????????',
  `is_agree` int(1) DEFAULT NULL COMMENT '????????????',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `camunda_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `form_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????Key',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `camunda_call_activity_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda callActivity??????ID',
  `module_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????code',
  `module_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_procdef_version` int(2) NOT NULL COMMENT 'camunda??????????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `procdef_type_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `procins_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `camunda_parent_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda ???????????????ID',
  `parent_procins_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID',
  `procins_id` bigint(20) NOT NULL COMMENT '????????????ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_process_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_process_log_data
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_process_log_data`;
CREATE TABLE `incloud_base_wf_process_log_data` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `process_log_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `biz_data` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '????????????Json',
  `camunda_task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `camunda_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='??????????????????';

-- ----------------------------
-- Records of incloud_base_wf_process_log_data
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_sequ_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_sequ_def`;
CREATE TABLE `incloud_base_wf_sequ_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `camunda_sequ_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda?????????ID',
  `sequ_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `expression` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `expression_name` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `camunda_parent_node_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????nodeId',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????-?????????';

-- ----------------------------
-- Records of incloud_base_wf_sequ_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_sequ_event_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_sequ_event_def`;
CREATE TABLE `incloud_base_wf_sequ_event_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `event_bind_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `event_id` bigint(20) NOT NULL COMMENT '??????????????????id',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `sequ_def_id` bigint(20) DEFAULT NULL COMMENT '????????????ID',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_sequ_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????-?????????-??????';

-- ----------------------------
-- Records of incloud_base_wf_sequ_event_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_sequ_event_param_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_sequ_event_param_def`;
CREATE TABLE `incloud_base_wf_sequ_event_param_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `event_def_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `param_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `param_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????',
  `param_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `param_defalut_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_sequ_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda??????ID',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????-?????????-??????-??????';

-- ----------------------------
-- Records of incloud_base_wf_sequ_event_param_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_sys
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_sys`;
CREATE TABLE `incloud_base_wf_sys` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `sys_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????',
  `sys_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????code',
  `is_enable` int(1) NOT NULL COMMENT '????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='?????????';

-- ----------------------------
-- Records of incloud_base_wf_sys
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_todo_task
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_todo_task`;
CREATE TABLE `incloud_base_wf_todo_task` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `camunda_procdef_version` int(2) NOT NULL COMMENT 'camunda??????????????????',
  `camunda_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda??????ID',
  `camunda_exection_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda exection??????ID',
  `camunda_act_ins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????id',
  `procins_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procins_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `node_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????key???ID',
  `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `node_type` int(2) NOT NULL COMMENT '????????????',
  `decision` int(1) DEFAULT NULL COMMENT '??????????????????',
  `is_agree` int(1) DEFAULT NULL COMMENT '????????????',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '????????????',
  `ownner` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `assignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `assignee_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `priority` int(2) DEFAULT NULL COMMENT '???????????????',
  `due_date` datetime DEFAULT NULL COMMENT '??????????????????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `procdef_type_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `procdef_type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????',
  `cliam_time` datetime DEFAULT NULL COMMENT '????????????',
  `state` int(2) DEFAULT NULL COMMENT '????????????',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `apply_time` datetime DEFAULT NULL COMMENT '????????????',
  `starter_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `starter_dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `starter_org_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????ID',
  `starter_org_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????????????????',
  `biz_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????key',
  `candidates` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '???????????????',
  `is_draft` int(1) NOT NULL COMMENT '??????????????????',
  `form_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????????key',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `is_call_activity` int(1) NOT NULL COMMENT '???????????????',
  `form_id` bigint(20) NOT NULL COMMENT '????????????id',
  `form_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????????????????',
  `module_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????code',
  `module_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????????????',
  `is_clone` int(1) DEFAULT NULL COMMENT '??????clone',
  `camunda_parent_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda ???????????????ID',
  `parent_procins_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID',
  `child_log_procins_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '?????????????????????',
  `parent_log_procins_id` bigint(20) DEFAULT NULL COMMENT '?????????????????????',
  `camunda_child_log_procins_id` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  `camunda_parent_log_procins_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda?????????????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????';

-- ----------------------------
-- Records of incloud_base_wf_todo_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_base_wf_var_def
-- ----------------------------
DROP TABLE IF EXISTS `incloud_base_wf_var_def`;
CREATE TABLE `incloud_base_wf_var_def` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `procdef_id` bigint(20) NOT NULL COMMENT '????????????ID',
  `var_id` bigint(20) NOT NULL COMMENT '????????????id',
  `form_id` bigint(20) NOT NULL COMMENT '??????ID',
  `action_scope` int(1) NOT NULL COMMENT '????????? 0?????? 1??????',
  `node_def_id` bigint(20) DEFAULT NULL COMMENT '????????????ID',
  `sequ_def_id` bigint(20) DEFAULT NULL COMMENT '???????????????id',
  `camunda_node_def_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda????????????ID',
  `camunda_sequ_def_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'camunda???????????????id',
  `camunda_procdef_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????ID',
  `camunda_procdef_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'camunda????????????key',
  `form_var_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????????????????id(????????????)',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='????????????-??????';

-- ----------------------------
-- Records of incloud_base_wf_var_def
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for incloud_common_db_ds
-- ----------------------------
DROP TABLE IF EXISTS `incloud_common_db_ds`;
CREATE TABLE `incloud_common_db_ds` (
  `id` bigint(20) NOT NULL COMMENT '??????',
  `pool_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????',
  `type` int(1) NOT NULL COMMENT '??????1???mysql 2???oracle',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '?????????',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??????',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url',
  `is_enable` int(1) NOT NULL COMMENT '????????????',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '??????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='?????????';

-- ----------------------------
-- Records of incloud_common_db_ds
-- ----------------------------
BEGIN;
INSERT INTO `incloud_common_db_ds` VALUES (1290548150490107905, 'incloud3', 1, 'root', 'Netwisd*8', 'jdbc:mysql://localhost:3301/incloud3?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false', 1, 'incloud3', '2020-08-04 15:20:31', '2020-08-04 15:20:31');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
