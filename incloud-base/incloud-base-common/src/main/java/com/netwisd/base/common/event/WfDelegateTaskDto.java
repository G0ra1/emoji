package com.netwisd.base.common.event;

import com.netwisd.base.common.constants.SuspensionState;
import com.netwisd.base.common.constants.TaskState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 委托任务
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/5 1:42 下午
 */
@Data
public class WfDelegateTaskDto implements Serializable {
    public static final String DELETE_REASON_COMPLETED = "completed";
    public static final String DELETE_REASON_DELETED   = "deleted";

    private static final long serialVersionUID = 1L;

    protected String id;
    protected int revision;

    //任务拥有人
    protected String owner;

    //流程受理人，取自于IdentityLinkEntity做了处理
    protected String assignee;

    //父任务
    protected String parentTaskId;

    //任务名称
    protected String name;

    //任务备注
    protected String description;

    //任务创建时间
    protected Date createTime;
    protected Date dueDate;
    protected Date followUpDate;
    protected int suspensionState = SuspensionState.ACTIVE.getStateCode();
    protected TaskState lifecycleState = TaskState.STATE_INIT;
    protected String tenantId;

    protected boolean isIdentityLinksInitialized = false;

    // 执行ID
    protected String executionId;

    //流程实例ID
    protected String processInstanceId;

    //流程定义ID
    protected String processDefinitionId;

    // 任务定义KEY,也就是活动的id
    protected String taskDefinitionKey;

    protected boolean isDeleted;
    protected String deleteReason;

    protected String eventName;
}
