package com.netwisd.base.wf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 流程定义-序列流-表达式 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 18:59:28
 */
@Data
@ApiModel(value = "流程定义-序列流-表达式 Vo")
public class WfExpreSequDefVo extends IVo{
    /**
     * expre_id
     * 表达式id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="表达式id")
    private Long expreId;
    /**
     * expression
     * 表达式的值
     */
    @ApiModelProperty(value="表达式的值")
    private String expression;
    /**
     * sequ_def_id
     * def序列流ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="def序列流ID")
    private Long sequDefId;
    /**
     * camunda_sequ_id
     * camunda序列流ID
     */
    @ApiModelProperty(value="camunda序列流ID")
    private String camundaSequId;
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * procdef_id
     * 流程定义id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程定义id")
    private Long procdefId;
}
