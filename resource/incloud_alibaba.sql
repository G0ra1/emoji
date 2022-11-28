/*
 Navicat Premium Data Transfer

 Source Server         : local8
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 127.0.0.1:3306
 Source Schema         : incloud_alibaba

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 20/04/2020 22:04:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
BEGIN;
INSERT INTO `config_info` VALUES (1, 'application-dev.yml', 'DEFAULT_GROUP', '#日志输出控制\r\nlogging:\r\n  level:\r\n    root: info\r\n    com.netwisd: debug\r\n  file: logs/${spring.application.name}.log\r\nspring:\r\n  datasource:\r\n    name: dataSource\r\n    url: jdbc:mysql://${incloud3_db_host:127.0.0.1}:${incloud3_db_port:3306}/${incloud3_db_schema:incloud3}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false\r\n    username: ${incloud3_db_user:root}\r\n    password: ${incloud3_db_pwd:root}\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    druid:\r\n      initial-size: 5\r\n      min-idle: 5\r\n      max-active: 20\r\n      max-wait: 30000\r\n      time-between-eviction-runs-millis: 60000\r\n      min-evictable-idle-time-millis: 300000\r\n      validation-query: select 1\r\n      test-while-idle: true\r\n      test-on-borrow: false\r\n      test-on-return: false\r\n      pool-prepared-statements: false\r\n      max-pool-prepared-statement-per-connection-size: 20\r\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=6000\r\n  redis:\r\n    host: ${incloud3_redis_host:10.255.0.141}\r\n    port: ${incloud3_redis_port:6379}\r\n    #cluster:\r\n      #nodes: envhost:8000,envhost:8001,envhost:8002,envhost:8003,envhost:8004,envhost:8005\r\n    password: ${incloud3_redis_pwd:Netwisd*8}\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        min-idle: 0\r\n        max-idle: 8\r\n        max-active: 8\r\n        max-wait: -1ms\r\n  rabbitmq:\r\n    addresses: ${incloud3_rabbitmq_host:127.0.0.1}:${incloud3_rabbitmq_port:5672}\r\n    username: ${incloud3_rabbitmq_username:netwisd}\r\n    password: ${incloud3_rabbitmq_password:netwisd}\r\n    virtual-host: ${incloud3_rabbitmq_virtual:/}\r\n    connection-timeout: 15000\r\n    publisher-returns: true\r\n    publisher-confirm-type: CORRELATED\r\n    listener:\r\n      simple:\r\n        acknowledge-mode: manual\r\n        concurrency: 3\r\n        max-concurrency: 10\r\n        default-requeue-rejected: false\r\n    template:\r\n      mandatory: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n    default-property-inclusion: always\r\n  mvc:\r\n    throw-exception-if-no-handler-found: true\r\n  resources:\r\n    add-mappings: false\r\n#mybatis-plus配置控制台打印完整带参数SQL语句\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n    map-underscore-to-camel-case: true\r\nhystrix:\r\n  threadpool:\r\n    default:\r\n      coreSize: 100\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 60000\r\nribbon:\r\n  ConnectTimeout: 30000\r\n  ReadTimeout: 30000\r\n  MaxAutoRetries: 0  #重试次数\r\n  MaxAutoRetriesNextServer: 0 #重试服务次数\r\n  OkToRetryOnAllOperations: true #所有请求都开启重试\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      user-info-uri: http://${incloud3_zuul_host}:${incloud3_zuul_port}/oauth/user-me\r\n      prefer-token-info: false\r\nmybatis:\r\n  mapperLocations: classpath:mapper/*.xml\r\nincloud:\r\n  database: mysql\r\n  table:\r\n    auto: update\r\n    enable: true', '751c3fae581290cecfc1f0dcbcce8404', '2020-03-25 02:08:26', '2020-04-19 02:30:07', NULL, '172.24.0.1', '', 'incloud3', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (2, 'incloud-base-zuul-dev.yml', 'DEFAULT_GROUP', 'zuul:\r\n  ignored-services: Authorization,Access-Control-Allow-Credentials, Access-Control-Allow-Origin\r\n  sensitive-headers: Cookie,Set-Cookie\r\n  routes:\r\n    oauth: #oauth\r\n      path: /oauth/**\r\n      serviceId: incloud-base-oauth\r\n    file: #file\r\n      path: /file/**\r\n      serviceId: incloud-base-file\r\n    log: #log\r\n      path: /log/**\r\n      serviceId: incloud-base-log\r\n    code: #code\r\n      path: /code/**\r\n      serviceId: incloud-common-code\r\n    demop: #demo-provider\r\n      path: /demop/**\r\n      serviceId: incloud-demo-provider\r\n    democ: #demo-consumer\r\n      path: /democ/**\r\n      serviceId: incloud-demo-consumer\r\n    user: #分级\r\n      path: /urm/**\r\n      serviceId: incloud-biz-urm\r\n  host:\r\n    connect-timeout-millis: 30000\r\n    socket-timeout-millis: 30000\r\n  add-proxy-headers: true\r\n  ribbon:\r\n    eager-load:\r\n      enabled: true', '3cffc9b8310bbc0119f9899611154c5f', '2020-03-25 02:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', '', 'incloud3', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (3, 'incloud-base-file-dev.yml', 'DEFAULT_GROUP', 'spring:\r\n  servlet:\r\n    multipart:\r\n      max-file-size: 100MB\r\n      max-request-size: 100MB\r\nfile:\r\n  local:\r\n    path: /opt/localFile\r\n#    path: ${file-path:d:/uploadFiles}\r\n    prefix: /statics\r\n    urlPrefix: /file/${file.local.prefix}\r\n    #urlPrefix: http://10.255.0.186/api-f\r\n  aliyun:\r\n    endpoint: xxx\r\n    accessKeyId: xxx\r\n    accessKeySecret: xxx\r\n    bucketName: xxx\r\n    domain: https://xxx\r\n  minio:\r\n    #url: http://10.255.0.190:9000\r\n    url: http://${incloud3_minio_host:10.255.0.190}:${incloud3_minio_port:9000}\r\n    accessKey: ${incloud3_minio_accessKey:root}\r\n    secretKey: ${incloud3_minio_secretKey:Netwisd*8}\r\n    bucketName: ${incloud3_minio_bucketName:incloud3}\r\n  enable:\r\n    storeType: MINIO\r\nincloud:\r\n  table:\r\n    package: com.netwisd.base.file.entity', '63ece01cc6e70ba574d05d51a053fa6a', '2020-03-25 02:08:26', '2020-04-19 08:31:36', NULL, '172.24.0.1', '', 'incloud3', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (4, 'incloud-demo-consumer-dev.yml', 'DEFAULT_GROUP', '#spring:\r\n#  cloud:\r\n#    alibaba:\r\n#      seata:\r\n#        tx-service-group: incloud_demo_consumer\r\nseata:\r\n  #默认为true，写是为了方便手动控制开启或关闭\r\n  enabled: true\r\n  applicationId: ${spring.application.name}\r\n  txServiceGroup: incloud_demo_consumer\r\n  enableAutoDataSourceProxy: false\r\n  useJdkProxy: false\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\r\n      namespace: ${spring.cloud.nacos.discovery.namespace}\r\n  service:\r\n    vgroupMapping:\r\n      incloud_demo_consumer: default\r\nincloud:\r\n  table:\r\n    package: com.netwisd.demo.entity', 'f7bbf81cfcb08013f220e7fa16d98a98', '2020-03-25 02:08:26', '2020-04-19 02:31:23', NULL, '172.24.0.1', '', 'incloud3', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (5, 'incloud-demo-provider-dev.yml', 'DEFAULT_GROUP', '#spring:\r\n#  cloud:\r\n#    alibaba:\r\n#      seata:\r\n#        tx-service-group: incloud_demo_provider\r\nseata:\r\n  #默认为true，写是为了方便手动控制开启或关闭\r\n  enabled: true\r\n  applicationId: ${spring.application.name}\r\n  txServiceGroup: incloud_demo_provider\r\n  enableAutoDataSourceProxy: true\r\n  useJdkProxy: false\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\r\n      namespace: ${spring.cloud.nacos.discovery.namespace}\r\n  service:\r\n    vgroupMapping:\r\n      incloud_demo_provider: default\r\nincloud:\r\n  table:\r\n    package: com.netwisd.demo.entity', '0ab11feb53c2f3445f6ef0bf2f91a18e', '2020-03-25 02:08:26', '2020-04-19 08:32:52', NULL, '172.24.0.1', '', 'incloud3', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (6, 'incloud-base-oauth-dev.yml', 'DEFAULT_GROUP', 'access_token:\r\n  store-jwt: false\r\n  jwt-signing-key: incloud3=$==+_+%0%:)(:)\r\n  add-userinfo: true', 'c8542be5f7941eae9902cd50120741e7', '2020-03-25 02:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', '', 'incloud3', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (7, 'incloud-base-route-dev.yml', 'DEFAULT_GROUP', 'zuul:\r\n  ignored-services: Authorization,Access-Control-Allow-Credentials, Access-Control-Allow-Origin\r\n  sensitive-headers: Cookie,Set-Cookie\r\n  routes:\r\n    demop: #demo-provider\r\n      path: /demopp/**\r\n      serviceId: incloud-demo-provider\r\n  host:\r\n    connect-timeout-millis: 30000\r\n    socket-timeout-millis: 30000\r\n  add-proxy-headers: true\r\n  ribbon:\r\n    eager-load:\r\n      enabled: true\r\nincloud:\r\n  table:\r\n    package: com.netwisd.base.route.entity', '1de7df018536422f4108bbce7685d63e', '2020-03-25 02:08:26', '2020-04-19 08:32:14', NULL, '172.24.0.1', '', 'incloud3', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (8, 'incloud-demo-message-dev.yml', 'DEFAULT_GROUP', 'seata:\r\n  #默认为true，写是为了方便手动控制开启或关闭\r\n  enabled: true\r\n  applicationId: ${spring.application.name}\r\n  txServiceGroup: incloud_demo_message\r\n  enableAutoDataSourceProxy: true\r\n  useJdkProxy: false\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\r\n      namespace: ${spring.cloud.nacos.discovery.namespace}\r\n  service:\r\n    vgroupMapping:\r\n      incloud_demo_message: default\r\nincloud:\r\n  table:\r\n    package: com.netwisd.demo.entity', 'a8626097db6fa5024513c01489a19d68', '2020-03-25 02:08:26', '2020-04-19 02:32:32', NULL, '172.24.0.1', '', 'incloud3', 'null', 'null', 'null', 'yaml', 'null');
COMMIT;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='增加租户字段';

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info` (
  `id` bigint(64) unsigned NOT NULL,
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin,
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
BEGIN;
INSERT INTO `his_config_info` VALUES (0, 1, 'application-dev.yml', 'DEFAULT_GROUP', '', '#日志输出控制\r\nlogging:\r\n  level:\r\n    root: info\r\n    com.netwisd: debug\r\n  file: logs/${spring.application.name}.log\r\nspring:\r\n  datasource:\r\n    name: dataSource\r\n    url: jdbc:mysql://${incloud3_db_host:127.0.0.1}:${incloud3_db_port:3306}/${incloud3_db_schema:incloud3}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false\r\n    username: ${incloud3_db_user:root}\r\n    password: ${incloud3_db_pwd:root}\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    druid:\r\n      initial-size: 5\r\n      min-idle: 5\r\n      max-active: 20\r\n      max-wait: 30000\r\n      time-between-eviction-runs-millis: 60000\r\n      min-evictable-idle-time-millis: 300000\r\n      validation-query: select 1\r\n      test-while-idle: true\r\n      test-on-borrow: false\r\n      test-on-return: false\r\n      pool-prepared-statements: false\r\n      max-pool-prepared-statement-per-connection-size: 20\r\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=6000\r\n  redis:\r\n    host: ${incloud3_redis_host:10.255.0.141}\r\n    port: ${incloud3_redis_port:6379}\r\n    #cluster:\r\n      #nodes: envhost:8000,envhost:8001,envhost:8002,envhost:8003,envhost:8004,envhost:8005\r\n    password: ${incloud3_redis_pwd:Netwisd*8}\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        min-idle: 0\r\n        max-idle: 8\r\n        max-active: 8\r\n        max-wait: -1ms\r\n  rabbitmq:\r\n    addresses: ${incloud3_rabbitmq_host:127.0.0.1}:${incloud3_rabbitmq_port:5672}\r\n    username: ${incloud3_rabbitmq_username:netwisd}\r\n    password: ${incloud3_rabbitmq_password:netwisd}\r\n    virtual-host: ${incloud3_rabbitmq_virtual:/}\r\n    connection-timeout: 15000\r\n    publisher-returns: true\r\n    publisher-confirm-type: CORRELATED\r\n    listener:\r\n      simple:\r\n        acknowledge-mode: manual\r\n        concurrency: 3\r\n        max-concurrency: 10\r\n        default-requeue-rejected: false\r\n    template:\r\n      mandatory: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n    default-property-inclusion: always\r\n  mvc:\r\n    throw-exception-if-no-handler-found: true\r\n  resources:\r\n    add-mappings: false\r\n#mybatis-plus配置控制台打印完整带参数SQL语句\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n    map-underscore-to-camel-case: true\r\nhystrix:\r\n  threadpool:\r\n    default:\r\n      coreSize: 100\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 60000\r\nribbon:\r\n  ConnectTimeout: 30000\r\n  ReadTimeout: 30000\r\n  MaxAutoRetries: 0  #重试次数\r\n  MaxAutoRetriesNextServer: 0 #重试服务次数\r\n  OkToRetryOnAllOperations: true #所有请求都开启重试\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      user-info-uri: http://${incloud3_zuul_host}:${incloud3_zuul_port}/oauth/user-me\r\n      prefer-token-info: false', 'fc19e896719fbd2f32e10329973adab3', '2020-03-25 15:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', 'I', 'incloud3');
INSERT INTO `his_config_info` VALUES (0, 2, 'incloud-base-zuul-dev.yml', 'DEFAULT_GROUP', '', 'zuul:\r\n  ignored-services: Authorization,Access-Control-Allow-Credentials, Access-Control-Allow-Origin\r\n  sensitive-headers: Cookie,Set-Cookie\r\n  routes:\r\n    oauth: #oauth\r\n      path: /oauth/**\r\n      serviceId: incloud-base-oauth\r\n    file: #file\r\n      path: /file/**\r\n      serviceId: incloud-base-file\r\n    log: #log\r\n      path: /log/**\r\n      serviceId: incloud-base-log\r\n    code: #code\r\n      path: /code/**\r\n      serviceId: incloud-common-code\r\n    demop: #demo-provider\r\n      path: /demop/**\r\n      serviceId: incloud-demo-provider\r\n    democ: #demo-consumer\r\n      path: /democ/**\r\n      serviceId: incloud-demo-consumer\r\n    user: #分级\r\n      path: /urm/**\r\n      serviceId: incloud-biz-urm\r\n  host:\r\n    connect-timeout-millis: 30000\r\n    socket-timeout-millis: 30000\r\n  add-proxy-headers: true\r\n  ribbon:\r\n    eager-load:\r\n      enabled: true', '3cffc9b8310bbc0119f9899611154c5f', '2020-03-25 15:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', 'I', 'incloud3');
INSERT INTO `his_config_info` VALUES (0, 3, 'incloud-base-file-dev.yml', 'DEFAULT_GROUP', '', 'spring:\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\nfile:\n  local:\n    path: /opt/localFile\n#    path: ${file-path:d:/uploadFiles}\n    prefix: /statics\n    urlPrefix: /file/${file.local.prefix}\n    #urlPrefix: http://10.255.0.186/api-f\n  aliyun:\n    endpoint: xxx\n    accessKeyId: xxx\n    accessKeySecret: xxx\n    bucketName: xxx\n    domain: https://xxx\n  minio:\n    #url: http://10.255.0.190:9000\n    url: http://${incloud3_minio_host:10.255.0.190}:${incloud3_minio_port:9000}\n    accessKey: ${incloud3_minio_accessKey:root}\n    secretKey: ${incloud3_minio_secretKey:Netwisd*8}\n    bucketName: ${incloud3_minio_bucketName:incloud3}\n  enable:\n    storeType: MINIO', '9b78f3062feaf2dd5229307fdd199bb0', '2020-03-25 15:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', 'I', 'incloud3');
INSERT INTO `his_config_info` VALUES (0, 4, 'incloud-demo-consumer-dev.yml', 'DEFAULT_GROUP', '', '#spring:\n#  cloud:\n#    alibaba:\n#      seata:\n#        tx-service-group: incloud_demo_consumer\nseata:\n  #默认为true，写是为了方便手动控制开启或关闭\n  enabled: true\n  applicationId: ${spring.application.name}\n  txServiceGroup: incloud_demo_consumer\n  enableAutoDataSourceProxy: false\n  useJdkProxy: false\n  registry:\n    type: nacos\n    nacos:\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\n      namespace: ${spring.cloud.nacos.discovery.namespace}\n  service:\n    vgroupMapping:\n      incloud_demo_consumer: default', 'd41b561071e6985ff4358be63fcd2e09', '2020-03-25 15:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', 'I', 'incloud3');
INSERT INTO `his_config_info` VALUES (0, 5, 'incloud-demo-provider-dev.yml', 'DEFAULT_GROUP', '', '#spring:\n#  cloud:\n#    alibaba:\n#      seata:\n#        tx-service-group: incloud_demo_provider\nseata:\n  #默认为true，写是为了方便手动控制开启或关闭\n  enabled: true\n  applicationId: ${spring.application.name}\n  txServiceGroup: incloud_demo_provider\n  enableAutoDataSourceProxy: true\n  useJdkProxy: false\n  registry:\n    type: nacos\n    nacos:\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\n      namespace: ${spring.cloud.nacos.discovery.namespace}\n  service:\n    vgroupMapping:\n      incloud_demo_provider: default', '450bc591f4d8487def4b74c1adf84b01', '2020-03-25 15:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', 'I', 'incloud3');
INSERT INTO `his_config_info` VALUES (0, 6, 'incloud-base-oauth-dev.yml', 'DEFAULT_GROUP', '', 'access_token:\r\n  store-jwt: false\r\n  jwt-signing-key: incloud3=$==+_+%0%:)(:)\r\n  add-userinfo: true', 'c8542be5f7941eae9902cd50120741e7', '2020-03-25 15:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', 'I', 'incloud3');
INSERT INTO `his_config_info` VALUES (0, 7, 'incloud-base-route-dev.yml', 'DEFAULT_GROUP', '', 'zuul:\r\n  ignored-services: \'*\'\r\n  sensitiveHeaders:\r\n  routes:\r\n    demop: #demo-provider\r\n      path: /demopp/**\r\n      serviceId: incloud-demo-provider\r\n  host:\r\n    connect-timeout-millis: 30000\r\n    socket-timeout-millis: 30000\r\n  add-proxy-headers: true\r\n  ribbon:\r\n    eager-load:\r\n      enabled: true\r\n', '19f38cc31853015e9f35d810f862127e', '2020-03-25 15:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', 'I', 'incloud3');
INSERT INTO `his_config_info` VALUES (0, 8, 'incloud-demo-message-dev.yml', 'DEFAULT_GROUP', '', 'seata:\n  #默认为true，写是为了方便手动控制开启或关闭\n  enabled: true\n  applicationId: ${spring.application.name}\n  txServiceGroup: incloud_demo_message\n  enableAutoDataSourceProxy: true\n  useJdkProxy: false\n  registry:\n    type: nacos\n    nacos:\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\n      namespace: ${spring.cloud.nacos.discovery.namespace}\n  service:\n    vgroupMapping:\n      incloud_demo_message: default', 'f22fb4e08c5f10f48770e14474a763e2', '2020-03-25 15:08:26', '2020-03-25 02:08:26', NULL, '172.21.0.1', 'I', 'incloud3');
INSERT INTO `his_config_info` VALUES (7, 9, 'incloud-base-route-dev.yml', 'DEFAULT_GROUP', '', 'zuul:\r\n  ignored-services: \'*\'\r\n  sensitiveHeaders:\r\n  routes:\r\n    demop: #demo-provider\r\n      path: /demopp/**\r\n      serviceId: incloud-demo-provider\r\n  host:\r\n    connect-timeout-millis: 30000\r\n    socket-timeout-millis: 30000\r\n  add-proxy-headers: true\r\n  ribbon:\r\n    eager-load:\r\n      enabled: true\r\n', '19f38cc31853015e9f35d810f862127e', '2020-03-25 15:09:57', '2020-03-25 02:09:58', NULL, '172.21.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (1, 10, 'application-dev.yml', 'DEFAULT_GROUP', '', '#日志输出控制\r\nlogging:\r\n  level:\r\n    root: info\r\n    com.netwisd: debug\r\n  file: logs/${spring.application.name}.log\r\nspring:\r\n  datasource:\r\n    name: dataSource\r\n    url: jdbc:mysql://${incloud3_db_host:127.0.0.1}:${incloud3_db_port:3306}/${incloud3_db_schema:incloud3}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false\r\n    username: ${incloud3_db_user:root}\r\n    password: ${incloud3_db_pwd:root}\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    druid:\r\n      initial-size: 5\r\n      min-idle: 5\r\n      max-active: 20\r\n      max-wait: 30000\r\n      time-between-eviction-runs-millis: 60000\r\n      min-evictable-idle-time-millis: 300000\r\n      validation-query: select 1\r\n      test-while-idle: true\r\n      test-on-borrow: false\r\n      test-on-return: false\r\n      pool-prepared-statements: false\r\n      max-pool-prepared-statement-per-connection-size: 20\r\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=6000\r\n  redis:\r\n    host: ${incloud3_redis_host:10.255.0.141}\r\n    port: ${incloud3_redis_port:6379}\r\n    #cluster:\r\n      #nodes: envhost:8000,envhost:8001,envhost:8002,envhost:8003,envhost:8004,envhost:8005\r\n    password: ${incloud3_redis_pwd:Netwisd*8}\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        min-idle: 0\r\n        max-idle: 8\r\n        max-active: 8\r\n        max-wait: -1ms\r\n  rabbitmq:\r\n    addresses: ${incloud3_rabbitmq_host:127.0.0.1}:${incloud3_rabbitmq_port:5672}\r\n    username: ${incloud3_rabbitmq_username:netwisd}\r\n    password: ${incloud3_rabbitmq_password:netwisd}\r\n    virtual-host: ${incloud3_rabbitmq_virtual:/}\r\n    connection-timeout: 15000\r\n    publisher-returns: true\r\n    publisher-confirm-type: CORRELATED\r\n    listener:\r\n      simple:\r\n        acknowledge-mode: manual\r\n        concurrency: 3\r\n        max-concurrency: 10\r\n        default-requeue-rejected: false\r\n    template:\r\n      mandatory: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n    default-property-inclusion: always\r\n  mvc:\r\n    throw-exception-if-no-handler-found: true\r\n  resources:\r\n    add-mappings: false\r\n#mybatis-plus配置控制台打印完整带参数SQL语句\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n    map-underscore-to-camel-case: true\r\nhystrix:\r\n  threadpool:\r\n    default:\r\n      coreSize: 100\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 60000\r\nribbon:\r\n  ConnectTimeout: 30000\r\n  ReadTimeout: 30000\r\n  MaxAutoRetries: 0  #重试次数\r\n  MaxAutoRetriesNextServer: 0 #重试服务次数\r\n  OkToRetryOnAllOperations: true #所有请求都开启重试\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      user-info-uri: http://${incloud3_zuul_host}:${incloud3_zuul_port}/oauth/user-me\r\n      prefer-token-info: false', 'fc19e896719fbd2f32e10329973adab3', '2020-04-18 15:57:48', '2020-04-18 02:57:48', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (1, 11, 'application-dev.yml', 'DEFAULT_GROUP', '', '#日志输出控制\r\nlogging:\r\n  level:\r\n    root: info\r\n    com.netwisd: debug\r\n  file: logs/${spring.application.name}.log\r\nspring:\r\n  datasource:\r\n    name: dataSource\r\n    url: jdbc:mysql://${incloud3_db_host:127.0.0.1}:${incloud3_db_port:3306}/${incloud3_db_schema:incloud3}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false\r\n    username: ${incloud3_db_user:root}\r\n    password: ${incloud3_db_pwd:root}\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    druid:\r\n      initial-size: 5\r\n      min-idle: 5\r\n      max-active: 20\r\n      max-wait: 30000\r\n      time-between-eviction-runs-millis: 60000\r\n      min-evictable-idle-time-millis: 300000\r\n      validation-query: select 1\r\n      test-while-idle: true\r\n      test-on-borrow: false\r\n      test-on-return: false\r\n      pool-prepared-statements: false\r\n      max-pool-prepared-statement-per-connection-size: 20\r\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=6000\r\n  redis:\r\n    host: ${incloud3_redis_host:10.255.0.141}\r\n    port: ${incloud3_redis_port:6379}\r\n    #cluster:\r\n      #nodes: envhost:8000,envhost:8001,envhost:8002,envhost:8003,envhost:8004,envhost:8005\r\n    password: ${incloud3_redis_pwd:Netwisd*8}\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        min-idle: 0\r\n        max-idle: 8\r\n        max-active: 8\r\n        max-wait: -1ms\r\n  rabbitmq:\r\n    addresses: ${incloud3_rabbitmq_host:127.0.0.1}:${incloud3_rabbitmq_port:5672}\r\n    username: ${incloud3_rabbitmq_username:netwisd}\r\n    password: ${incloud3_rabbitmq_password:netwisd}\r\n    virtual-host: ${incloud3_rabbitmq_virtual:/}\r\n    connection-timeout: 15000\r\n    publisher-returns: true\r\n    publisher-confirm-type: CORRELATED\r\n    listener:\r\n      simple:\r\n        acknowledge-mode: manual\r\n        concurrency: 3\r\n        max-concurrency: 10\r\n        default-requeue-rejected: false\r\n    template:\r\n      mandatory: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n    default-property-inclusion: always\r\n  mvc:\r\n    throw-exception-if-no-handler-found: true\r\n  resources:\r\n    add-mappings: false\r\n#mybatis-plus配置控制台打印完整带参数SQL语句\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n    map-underscore-to-camel-case: true\r\nhystrix:\r\n  threadpool:\r\n    default:\r\n      coreSize: 100\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 60000\r\nribbon:\r\n  ConnectTimeout: 30000\r\n  ReadTimeout: 30000\r\n  MaxAutoRetries: 0  #重试次数\r\n  MaxAutoRetriesNextServer: 0 #重试服务次数\r\n  OkToRetryOnAllOperations: true #所有请求都开启重试\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      user-info-uri: http://${incloud3_zuul_host}:${incloud3_zuul_port}/oauth/user-me\r\n      prefer-token-info: false\r\nincloud:\r\n  database: mysql\r\n  table:\r\n    auto: update\r\n    enable: true\r\n    package: com.netwisd', '479cd9928b8e20146f6573e5fd10bf60', '2020-04-19 14:35:42', '2020-04-19 01:35:42', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (4, 12, 'incloud-demo-consumer-dev.yml', 'DEFAULT_GROUP', '', '#spring:\n#  cloud:\n#    alibaba:\n#      seata:\n#        tx-service-group: incloud_demo_consumer\nseata:\n  #默认为true，写是为了方便手动控制开启或关闭\n  enabled: true\n  applicationId: ${spring.application.name}\n  txServiceGroup: incloud_demo_consumer\n  enableAutoDataSourceProxy: false\n  useJdkProxy: false\n  registry:\n    type: nacos\n    nacos:\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\n      namespace: ${spring.cloud.nacos.discovery.namespace}\n  service:\n    vgroupMapping:\n      incloud_demo_consumer: default', 'd41b561071e6985ff4358be63fcd2e09', '2020-04-19 14:36:18', '2020-04-19 01:36:18', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (4, 13, 'incloud-demo-consumer-dev.yml', 'DEFAULT_GROUP', '', '#spring:\r\n#  cloud:\r\n#    alibaba:\r\n#      seata:\r\n#        tx-service-group: incloud_demo_consumer\r\nseata:\r\n  #默认为true，写是为了方便手动控制开启或关闭\r\n  enabled: true\r\n  applicationId: ${spring.application.name}\r\n  txServiceGroup: incloud_demo_consumer\r\n  enableAutoDataSourceProxy: false\r\n  useJdkProxy: false\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\r\n      namespace: ${spring.cloud.nacos.discovery.namespace}\r\n  service:\r\n    vgroupMapping:\r\n      incloud_demo_consumer: default\r\nincloud:\r\n  database: mysql\r\n  table:\r\n    auto: update\r\n    enable: true\r\n    package: com.netwisd.demo.entity', '3f727ef30ab4fee7826667b663c35855', '2020-04-19 14:51:00', '2020-04-19 01:51:00', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (4, 14, 'incloud-demo-consumer-dev.yml', 'DEFAULT_GROUP', '', '#spring:\r\n#  cloud:\r\n#    alibaba:\r\n#      seata:\r\n#        tx-service-group: incloud_demo_consumer\r\nseata:\r\n  #默认为true，写是为了方便手动控制开启或关闭\r\n  enabled: true\r\n  applicationId: ${spring.application.name}\r\n  txServiceGroup: incloud_demo_consumer\r\n  enableAutoDataSourceProxy: false\r\n  useJdkProxy: false\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\r\n      namespace: ${spring.cloud.nacos.discovery.namespace}\r\n  service:\r\n    vgroupMapping:\r\n      incloud_demo_consumer: default\r\nmybatis-plus:\r\n  mapper-locations: classpath*:mapper/*.xml\r\nincloud:\r\n  database: mysql\r\n  table:\r\n    auto: update\r\n    enable: true\r\n    package: com.netwisd.demo.entity', '94b4adfca9e5abe1ab8477716f8eed31', '2020-04-19 14:52:10', '2020-04-19 01:52:10', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (4, 15, 'incloud-demo-consumer-dev.yml', 'DEFAULT_GROUP', '', '#spring:\r\n#  cloud:\r\n#    alibaba:\r\n#      seata:\r\n#        tx-service-group: incloud_demo_consumer\r\nseata:\r\n  #默认为true，写是为了方便手动控制开启或关闭\r\n  enabled: true\r\n  applicationId: ${spring.application.name}\r\n  txServiceGroup: incloud_demo_consumer\r\n  enableAutoDataSourceProxy: false\r\n  useJdkProxy: false\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\r\n      namespace: ${spring.cloud.nacos.discovery.namespace}\r\n  service:\r\n    vgroupMapping:\r\n      incloud_demo_consumer: default\r\nmybatis:\r\n  mapperLocations: classpath:mapper/*.xml\r\nincloud:\r\n  database: mysql\r\n  table:\r\n    auto: update\r\n    enable: true\r\n    package: com.netwisd.demo.entity', '6d21cb81d54d4618b5d45e6708007af3', '2020-04-19 15:25:30', '2020-04-19 02:25:31', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (1, 16, 'application-dev.yml', 'DEFAULT_GROUP', '', '#日志输出控制\r\nlogging:\r\n  level:\r\n    root: info\r\n    com.netwisd: debug\r\n  file: logs/${spring.application.name}.log\r\nspring:\r\n  datasource:\r\n    name: dataSource\r\n    url: jdbc:mysql://${incloud3_db_host:127.0.0.1}:${incloud3_db_port:3306}/${incloud3_db_schema:incloud3}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false\r\n    username: ${incloud3_db_user:root}\r\n    password: ${incloud3_db_pwd:root}\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    druid:\r\n      initial-size: 5\r\n      min-idle: 5\r\n      max-active: 20\r\n      max-wait: 30000\r\n      time-between-eviction-runs-millis: 60000\r\n      min-evictable-idle-time-millis: 300000\r\n      validation-query: select 1\r\n      test-while-idle: true\r\n      test-on-borrow: false\r\n      test-on-return: false\r\n      pool-prepared-statements: false\r\n      max-pool-prepared-statement-per-connection-size: 20\r\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=6000\r\n  redis:\r\n    host: ${incloud3_redis_host:10.255.0.141}\r\n    port: ${incloud3_redis_port:6379}\r\n    #cluster:\r\n      #nodes: envhost:8000,envhost:8001,envhost:8002,envhost:8003,envhost:8004,envhost:8005\r\n    password: ${incloud3_redis_pwd:Netwisd*8}\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        min-idle: 0\r\n        max-idle: 8\r\n        max-active: 8\r\n        max-wait: -1ms\r\n  rabbitmq:\r\n    addresses: ${incloud3_rabbitmq_host:127.0.0.1}:${incloud3_rabbitmq_port:5672}\r\n    username: ${incloud3_rabbitmq_username:netwisd}\r\n    password: ${incloud3_rabbitmq_password:netwisd}\r\n    virtual-host: ${incloud3_rabbitmq_virtual:/}\r\n    connection-timeout: 15000\r\n    publisher-returns: true\r\n    publisher-confirm-type: CORRELATED\r\n    listener:\r\n      simple:\r\n        acknowledge-mode: manual\r\n        concurrency: 3\r\n        max-concurrency: 10\r\n        default-requeue-rejected: false\r\n    template:\r\n      mandatory: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n    default-property-inclusion: always\r\n  mvc:\r\n    throw-exception-if-no-handler-found: true\r\n  resources:\r\n    add-mappings: false\r\n#mybatis-plus配置控制台打印完整带参数SQL语句\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n    map-underscore-to-camel-case: true\r\nhystrix:\r\n  threadpool:\r\n    default:\r\n      coreSize: 100\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 60000\r\nribbon:\r\n  ConnectTimeout: 30000\r\n  ReadTimeout: 30000\r\n  MaxAutoRetries: 0  #重试次数\r\n  MaxAutoRetriesNextServer: 0 #重试服务次数\r\n  OkToRetryOnAllOperations: true #所有请求都开启重试\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      user-info-uri: http://${incloud3_zuul_host}:${incloud3_zuul_port}/oauth/user-me\r\n      prefer-token-info: false', 'fc19e896719fbd2f32e10329973adab3', '2020-04-19 15:27:39', '2020-04-19 02:27:40', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (1, 17, 'application-dev.yml', 'DEFAULT_GROUP', '', '#日志输出控制\r\nlogging:\r\n  level:\r\n    root: info\r\n    com.netwisd: debug\r\n  file: logs/${spring.application.name}.log\r\nspring:\r\n  datasource:\r\n    name: dataSource\r\n    url: jdbc:mysql://${incloud3_db_host:127.0.0.1}:${incloud3_db_port:3306}/${incloud3_db_schema:incloud3}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false\r\n    username: ${incloud3_db_user:root}\r\n    password: ${incloud3_db_pwd:root}\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    druid:\r\n      initial-size: 5\r\n      min-idle: 5\r\n      max-active: 20\r\n      max-wait: 30000\r\n      time-between-eviction-runs-millis: 60000\r\n      min-evictable-idle-time-millis: 300000\r\n      validation-query: select 1\r\n      test-while-idle: true\r\n      test-on-borrow: false\r\n      test-on-return: false\r\n      pool-prepared-statements: false\r\n      max-pool-prepared-statement-per-connection-size: 20\r\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=6000\r\n  redis:\r\n    host: ${incloud3_redis_host:10.255.0.141}\r\n    port: ${incloud3_redis_port:6379}\r\n    #cluster:\r\n      #nodes: envhost:8000,envhost:8001,envhost:8002,envhost:8003,envhost:8004,envhost:8005\r\n    password: ${incloud3_redis_pwd:Netwisd*8}\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        min-idle: 0\r\n        max-idle: 8\r\n        max-active: 8\r\n        max-wait: -1ms\r\n  rabbitmq:\r\n    addresses: ${incloud3_rabbitmq_host:127.0.0.1}:${incloud3_rabbitmq_port:5672}\r\n    username: ${incloud3_rabbitmq_username:netwisd}\r\n    password: ${incloud3_rabbitmq_password:netwisd}\r\n    virtual-host: ${incloud3_rabbitmq_virtual:/}\r\n    connection-timeout: 15000\r\n    publisher-returns: true\r\n    publisher-confirm-type: CORRELATED\r\n    listener:\r\n      simple:\r\n        acknowledge-mode: manual\r\n        concurrency: 3\r\n        max-concurrency: 10\r\n        default-requeue-rejected: false\r\n    template:\r\n      mandatory: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n    default-property-inclusion: always\r\n  mvc:\r\n    throw-exception-if-no-handler-found: true\r\n  resources:\r\n    add-mappings: false\r\n#mybatis-plus配置控制台打印完整带参数SQL语句\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n    map-underscore-to-camel-case: true\r\nhystrix:\r\n  threadpool:\r\n    default:\r\n      coreSize: 100\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 60000\r\nribbon:\r\n  ConnectTimeout: 30000\r\n  ReadTimeout: 30000\r\n  MaxAutoRetries: 0  #重试次数\r\n  MaxAutoRetriesNextServer: 0 #重试服务次数\r\n  OkToRetryOnAllOperations: true #所有请求都开启重试\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      user-info-uri: http://${incloud3_zuul_host}:${incloud3_zuul_port}/oauth/user-me\r\n      prefer-token-info: false\r\nincloud:\r\n  database: mysql\r\n  table:\r\n    auto: update\r\n    enable: true\r\n    package: com.netwisd.**.entity', '7e0e34dcaa659942e4aaebcef61c4c64', '2020-04-19 15:29:27', '2020-04-19 02:29:27', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (4, 18, 'incloud-demo-consumer-dev.yml', 'DEFAULT_GROUP', '', '#spring:\r\n#  cloud:\r\n#    alibaba:\r\n#      seata:\r\n#        tx-service-group: incloud_demo_consumer\r\nseata:\r\n  #默认为true，写是为了方便手动控制开启或关闭\r\n  enabled: true\r\n  applicationId: ${spring.application.name}\r\n  txServiceGroup: incloud_demo_consumer\r\n  enableAutoDataSourceProxy: false\r\n  useJdkProxy: false\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\r\n      namespace: ${spring.cloud.nacos.discovery.namespace}\r\n  service:\r\n    vgroupMapping:\r\n      incloud_demo_consumer: default\r\nmybatis:\r\n  mapperLocations: classpath:mapper/*.xml\r\n', 'b41bc480751aa2e52df2716071b6cf94', '2020-04-19 15:29:52', '2020-04-19 02:29:52', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (1, 19, 'application-dev.yml', 'DEFAULT_GROUP', '', '#日志输出控制\r\nlogging:\r\n  level:\r\n    root: info\r\n    com.netwisd: debug\r\n  file: logs/${spring.application.name}.log\r\nspring:\r\n  datasource:\r\n    name: dataSource\r\n    url: jdbc:mysql://${incloud3_db_host:127.0.0.1}:${incloud3_db_port:3306}/${incloud3_db_schema:incloud3}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false\r\n    username: ${incloud3_db_user:root}\r\n    password: ${incloud3_db_pwd:root}\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    druid:\r\n      initial-size: 5\r\n      min-idle: 5\r\n      max-active: 20\r\n      max-wait: 30000\r\n      time-between-eviction-runs-millis: 60000\r\n      min-evictable-idle-time-millis: 300000\r\n      validation-query: select 1\r\n      test-while-idle: true\r\n      test-on-borrow: false\r\n      test-on-return: false\r\n      pool-prepared-statements: false\r\n      max-pool-prepared-statement-per-connection-size: 20\r\n      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=6000\r\n  redis:\r\n    host: ${incloud3_redis_host:10.255.0.141}\r\n    port: ${incloud3_redis_port:6379}\r\n    #cluster:\r\n      #nodes: envhost:8000,envhost:8001,envhost:8002,envhost:8003,envhost:8004,envhost:8005\r\n    password: ${incloud3_redis_pwd:Netwisd*8}\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        min-idle: 0\r\n        max-idle: 8\r\n        max-active: 8\r\n        max-wait: -1ms\r\n  rabbitmq:\r\n    addresses: ${incloud3_rabbitmq_host:127.0.0.1}:${incloud3_rabbitmq_port:5672}\r\n    username: ${incloud3_rabbitmq_username:netwisd}\r\n    password: ${incloud3_rabbitmq_password:netwisd}\r\n    virtual-host: ${incloud3_rabbitmq_virtual:/}\r\n    connection-timeout: 15000\r\n    publisher-returns: true\r\n    publisher-confirm-type: CORRELATED\r\n    listener:\r\n      simple:\r\n        acknowledge-mode: manual\r\n        concurrency: 3\r\n        max-concurrency: 10\r\n        default-requeue-rejected: false\r\n    template:\r\n      mandatory: true\r\n  jackson:\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    time-zone: GMT+8\r\n    default-property-inclusion: always\r\n  mvc:\r\n    throw-exception-if-no-handler-found: true\r\n  resources:\r\n    add-mappings: false\r\n#mybatis-plus配置控制台打印完整带参数SQL语句\r\nmybatis-plus:\r\n  configuration:\r\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl\r\n    map-underscore-to-camel-case: true\r\nhystrix:\r\n  threadpool:\r\n    default:\r\n      coreSize: 100\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 60000\r\nribbon:\r\n  ConnectTimeout: 30000\r\n  ReadTimeout: 30000\r\n  MaxAutoRetries: 0  #重试次数\r\n  MaxAutoRetriesNextServer: 0 #重试服务次数\r\n  OkToRetryOnAllOperations: true #所有请求都开启重试\r\nsecurity:\r\n  oauth2:\r\n    resource:\r\n      user-info-uri: http://${incloud3_zuul_host}:${incloud3_zuul_port}/oauth/user-me\r\n      prefer-token-info: false\r\nincloud:\r\n  database: mysql\r\n  table:\r\n    auto: update\r\n    enable: true', '96a296fb7c9c8d32520432f199ba5059', '2020-04-19 15:30:06', '2020-04-19 02:30:07', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (4, 20, 'incloud-demo-consumer-dev.yml', 'DEFAULT_GROUP', '', '#spring:\r\n#  cloud:\r\n#    alibaba:\r\n#      seata:\r\n#        tx-service-group: incloud_demo_consumer\r\nseata:\r\n  #默认为true，写是为了方便手动控制开启或关闭\r\n  enabled: true\r\n  applicationId: ${spring.application.name}\r\n  txServiceGroup: incloud_demo_consumer\r\n  enableAutoDataSourceProxy: false\r\n  useJdkProxy: false\r\n  registry:\r\n    type: nacos\r\n    nacos:\r\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\r\n      namespace: ${spring.cloud.nacos.discovery.namespace}\r\n  service:\r\n    vgroupMapping:\r\n      incloud_demo_consumer: default\r\nincloud:\r\n  table:\r\n    package: com.netwisd.**.entity', '358e078d18a965e56b2ad8cfa01b4788', '2020-04-19 15:31:23', '2020-04-19 02:31:23', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (8, 21, 'incloud-demo-message-dev.yml', 'DEFAULT_GROUP', '', 'seata:\n  #默认为true，写是为了方便手动控制开启或关闭\n  enabled: true\n  applicationId: ${spring.application.name}\n  txServiceGroup: incloud_demo_message\n  enableAutoDataSourceProxy: true\n  useJdkProxy: false\n  registry:\n    type: nacos\n    nacos:\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\n      namespace: ${spring.cloud.nacos.discovery.namespace}\n  service:\n    vgroupMapping:\n      incloud_demo_message: default', 'f22fb4e08c5f10f48770e14474a763e2', '2020-04-19 15:32:32', '2020-04-19 02:32:32', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (3, 22, 'incloud-base-file-dev.yml', 'DEFAULT_GROUP', '', 'spring:\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\nfile:\n  local:\n    path: /opt/localFile\n#    path: ${file-path:d:/uploadFiles}\n    prefix: /statics\n    urlPrefix: /file/${file.local.prefix}\n    #urlPrefix: http://10.255.0.186/api-f\n  aliyun:\n    endpoint: xxx\n    accessKeyId: xxx\n    accessKeySecret: xxx\n    bucketName: xxx\n    domain: https://xxx\n  minio:\n    #url: http://10.255.0.190:9000\n    url: http://${incloud3_minio_host:10.255.0.190}:${incloud3_minio_port:9000}\n    accessKey: ${incloud3_minio_accessKey:root}\n    secretKey: ${incloud3_minio_secretKey:Netwisd*8}\n    bucketName: ${incloud3_minio_bucketName:incloud3}\n  enable:\n    storeType: MINIO', '9b78f3062feaf2dd5229307fdd199bb0', '2020-04-19 21:31:36', '2020-04-19 08:31:36', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (7, 23, 'incloud-base-route-dev.yml', 'DEFAULT_GROUP', '', 'zuul:\r\n  ignored-services: Authorization,Access-Control-Allow-Credentials, Access-Control-Allow-Origin\r\n  sensitive-headers: Cookie,Set-Cookie\r\n  routes:\r\n    demop: #demo-provider\r\n      path: /demopp/**\r\n      serviceId: incloud-demo-provider\r\n  host:\r\n    connect-timeout-millis: 30000\r\n    socket-timeout-millis: 30000\r\n  add-proxy-headers: true\r\n  ribbon:\r\n    eager-load:\r\n      enabled: true\r\n', 'dc0d621db082f18facf4ca7158811a04', '2020-04-19 21:32:13', '2020-04-19 08:32:14', NULL, '172.24.0.1', 'U', 'incloud3');
INSERT INTO `his_config_info` VALUES (5, 24, 'incloud-demo-provider-dev.yml', 'DEFAULT_GROUP', '', '#spring:\n#  cloud:\n#    alibaba:\n#      seata:\n#        tx-service-group: incloud_demo_provider\nseata:\n  #默认为true，写是为了方便手动控制开启或关闭\n  enabled: true\n  applicationId: ${spring.application.name}\n  txServiceGroup: incloud_demo_provider\n  enableAutoDataSourceProxy: true\n  useJdkProxy: false\n  registry:\n    type: nacos\n    nacos:\n      serverAddr: ${spring.cloud.nacos.discovery.server-addr}\n      namespace: ${spring.cloud.nacos.discovery.namespace}\n  service:\n    vgroupMapping:\n      incloud_demo_provider: default', '450bc591f4d8487def4b74c1adf84b01', '2020-04-19 21:32:51', '2020-04-19 08:32:52', NULL, '172.24.0.1', 'U', 'incloud3');
COMMIT;

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `role` varchar(50) NOT NULL,
  `resource` varchar(512) NOT NULL,
  `action` varchar(8) NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
BEGIN;
INSERT INTO `tenant_info` VALUES (1, '1', 'incloud3', 'incloud3', 'incloud3', 'nacos', 1585120084949, 1585120084949);
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
