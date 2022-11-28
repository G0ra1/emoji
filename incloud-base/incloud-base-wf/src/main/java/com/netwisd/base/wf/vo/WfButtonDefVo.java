package com.netwisd.base.wf.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 流程定义-按钮 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 22:26:53
 */
@Data
@ApiModel(value = "流程定义-按钮 Vo")
public class WfButtonDefVo extends IVo{
    @ApiModelProperty(value="按钮code")
    private String buttonCode;

    /**
     * button_name
     * 按钮name
     */
    @ApiModelProperty(value="按钮name")
    private String buttonName;
    /**
     * node_def_id
     * 节点定义Id
     */
    @ApiModelProperty(value="节点定义Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long nodeDefId;
    /**
     * procdef_id
     * 流程定义id
     */
    @ApiModelProperty(value="流程定义id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long procdefId;
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
     * camunda_node_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    private String camundaNodeDefId;
}
