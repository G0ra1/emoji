package com.netwisd.base.wf.dto;

import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 流程定义和子流程定义关系表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-10-21 11:22:02
 */
@Data
@ApiModel(value = "流程定义和子流程定义关系表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfProcDefRelDto extends IDto{
    /**
     * main_camunda_procdef_id
     * 主流程id
     */
    @ApiModelProperty(value="主流程id")
    private String mainCamundaProcdefId;

    /**
     * main_procdef_name
     * 主流程定义名称
     */
    @ApiModelProperty(value="主流程定义名称")
    private String mainProcdefName;

    /**
     * main_procdef_id
     * 主流程定义名称
     */
    @ApiModelProperty(value="主流程定义名称")
    private Long mainProcdefId;

    /**
     * child_camunda_procdef_key
     * 子流程key
     */
    @ApiModelProperty(value="子流程key")
    private String childCamundaProcdefKey;

    /**
     * child_procdef_version
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    private Integer childProcdefVersion;

    /**
     * child_camunda_procdef_id
     * 子主流程定义id
     */
    @ApiModelProperty(value="子主流程定义id")
    private String childCamundaProcdefId;

    /**
     * child_procdef_name
     * 子主流程定义名称
     */
    @ApiModelProperty(value="子主流程定义名称")
    private String childProcdefName;

    /**
     * child_procdef_id
     * 子流程定义名称
     */
    @ApiModelProperty(value="子流程定义名称")
    private Long childProcdefId;

    /**
     * main_camunda_node_def_id
     * 主流程节点定义id
     */
    @ApiModelProperty(value="main_camunda_node_def_id")
    private String mainCamundaNodeDefId;

    /**
     * main_node_def_id
     * 节点定义Id
     */
    @ApiModelProperty(value="节点定义Id")
    private Long mainNodeDefId;

    /**
     * main_node_name
     * 主节点名称
     */
    @ApiModelProperty(value="主节点名称")
    private String mainNodeName;

}
