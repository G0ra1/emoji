package com.netwisd.base.wf.starter.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/30 12:41:00
 */
@Data
@NoArgsConstructor
public class WfDto extends IDto {

    public WfDto (Args args){
        super(args);
    }

    //工作流相关用的字段
    @ApiModelProperty(value="工作流——流程相关对象")
    private WfEngineDto.StartDto startDto;

    @ApiModelProperty(value="工作流——流程相关对象")
    private WfEngineDto.HandleDto handleDto;

    //业务相关用的字段
    @ApiModelProperty(value="工作流——流程定义key")
    private String camundaProcdefKey;

    @ApiModelProperty(value="工作流——流程定义id")
    private String camundaProcdefId;

    @ApiModelProperty(value="工作流——流程实例id")
    private String camundaProcinsId;

    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="工作流——流程分类ID")
    private Long procdefTypeId;

    @ApiModelProperty(value="工作流——流程分类名称")
    private String procdefTypeName;

    @ApiModelProperty(value="工作流——流程版本")
    private Integer procdefVersion;

    @ApiModelProperty(value="工作流——流程定义名称")
    private String procdefName;

    @ApiModelProperty(value="工作流——流程实例标题")
    private String processName;

    @ApiModelProperty(value="工作流——申请原因")
    private String reason;

    @ApiModelProperty(value="工作流——业务key")
    private String bizKey;

    /*@ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;*/

    @ApiModelProperty(value="工作流——流程任务ID")
    private String camundaTaskId;

    @ApiModelProperty(value="工作流——业务数据是否改变标识，用户标识工作流是否需要调用业务接口")
    @Valid
    private Boolean isChange;

    @ApiModelProperty(value="优先级")
    private String bizPriority;

    @ApiModelProperty(value="审批状态")
    private Integer auditStatus;
}