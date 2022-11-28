package com.netwisd.base.wf.dto;

import com.netwisd.common.core.data.IValidation;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/29 9:38 下午
 */
@Data
public class VariableDto implements Serializable, IValidation {

/*    private String wf_local_desription;
    private String wf_local_decision;
    private String wf_assignee;
    private String wf_assigneeList;*/
    private String taskId;
    private String userId;
    private String delegateUserId;
    private Map<String,Object> innerVariable;
}
