package com.netwisd.base.wf.dto;

import com.netwisd.common.core.anntation.Valid;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "bpmn xml 对象")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeploymentBpmnDto {
    /**
     * BPMN xml字符串
     */
    @Valid
    private String data;
    /**
     * 流程定义id
     */
    private String procDefId;

    /**
     * 是否是创建的新版本
     */
    private Integer isNewVer = 0;

    /**
     * 是否使用当前版本
     */
    private Integer isCurrentVer = 1;
}
