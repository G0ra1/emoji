package com.netwisd.base.wf.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IValidation;
import lombok.Data;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/23 1:13 下午
 */
@Data
public class WfTestValidateDtoDetail implements IValidation {
    @Valid(nullMsg = "任务ID不能为空！！！")
    private String taskId;
    private String userId;
    private String delegateUserId;
    private Map<String,Object> innerVariable;
}
