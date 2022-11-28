package com.netwisd.base.wf.starter.event;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/3/19 14:38
 */
@Data
public class ExecutionEntity implements Serializable {
    protected String processDefinitionId;
    protected String activityId;
    protected String activityName;
    protected String processInstanceId;
    protected String bizTag;
    /*protected String formKey;*/
}
