package com.netwisd.base.wf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/21 4:31 下午
 */
@Data
@AllArgsConstructor
public class RejectInfoDto {
    //流程实例ID
    private String processInstanceId;
    //流程定义ID
    private String processDefinitionId;
    //驳回节点定义ID（key）
    private String sourceTaskDefinitionKey;
    //被驳回节点定义ID（key）
    private String targetTaskDefinitionKey;
    //驳回人ID
    private String rejectUserId;
    //被驳回人ID
    private String beRejectedUserId;
}
