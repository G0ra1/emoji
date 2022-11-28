package com.netwisd.base.common.constants;

/**
 * 消息定义
 */
public interface Msg {
	//数据库配置的消息
	String ROUTE_QUEUE = "route.queue";
	String ROUTE_EXCHANGE = "route.exchange";
	String ROUTE_TYPE = "direct";
	String ROUTE_KEY = "route.queue";

	//网关修改备份状态,其他都用rabbitmq默认的
	String DB_FLAG_QUEUE = "db.flag.queue";

	String ROUTE_ADD = "add";
	String ROUTE_UPDATE = "update";
	String ROUTE_DEL = "del";

	//数据库配置的消息
	String DB_BACKUP_QUEUE = "db.backup.queue";
	String DB_BACKUP_EXCHANGE = "db.backup.exchange";
	String DB_BACKUP_TYPE = "topic";
	String DB_BACKUP_KEY = "backup.*";

	//工作流流程实例消息
	String WF_PROCESS_QUEUE = "wf.process.queue";
	String WF_PROCESS_EXCHANGE = "wf.process.exchange";
	String WF_PROCESS_TYPE = "topic";
	String WF_PROCESS_KEY = "del.*";
}
