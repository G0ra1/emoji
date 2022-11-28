package com.netwisd.base.wf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/28 12:08 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WfProcinsDto implements Serializable {
    private String camundaProcessInstanceId;
    private String taskId;
    private String targetActivityId;
    private String targetAssignee;
}
