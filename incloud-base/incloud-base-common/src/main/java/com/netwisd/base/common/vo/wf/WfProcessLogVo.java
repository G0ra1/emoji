package com.netwisd.base.common.vo.wf;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 流程日志 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-14 17:42:43
 */
@Data
@ApiModel(value = "流程日志 Vo")
public class WfProcessLogVo extends IVo{
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @ApiModelProperty(value="camunda流程实例ID")
    private String camundaProcinsId;
    /**
     * camunda_task_id
     * camunda任务ID
     */
    @ApiModelProperty(value="camunda任务ID")
    private String camundaTaskId;
    /**
     * camunda_act_ins_id
     * camunda节点实例ID
     */
    @ApiModelProperty(value="camunda节点实例ID")
    private String camundaActInsId;
    /**
     * process_id
     * 流程实例ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程实例ID")
    private Long processId;
    /**
     * node_id
     * 节点ID
     */
    @ApiModelProperty(value="节点ID")
    private String nodeId;
    /**
     * node_name
     * 节点名称
     */
    @ApiModelProperty(value="节点名称")
    private String nodeName;
    /**
     * target_
     * 目标节点ID
     */
    @ApiModelProperty(value="目标节点ID")
    private String targetNodeId;
    /**
     * target_node_name
     * 目标节点名称
     */
    @ApiModelProperty(value="目标节点名称")
    private String targetNodeName;
    /**
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    private Integer nodeType;
    /**
     * target_node_type
     * 目标节点类型
     */
    @ApiModelProperty(value="目标节点类型")
    private Integer targetNodeType;
    /**
     * user_name
     * 用户名账号
     */
    @ApiModelProperty(value="用户名账号")
    private String userName;
    /**
     * user_name_ch
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    private String userNameCh;
    /**
     * dept_id
     * 部门ID
     */
    @ApiModelProperty(value="部门ID")
    private String deptId;
    /**
     * dept_name
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    private String deptName;
    /**
     * org_id
     * 机构ID
     */
    @ApiModelProperty(value="机构ID")
    private String orgId;
    /**
     * org_name
     * 机构名称
     */
    @ApiModelProperty(value="机构名称")
    private String orgName;
    /**
     * start_time
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private LocalDateTime startTime;
    /**
     * end_time
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private LocalDateTime endTime;
    /**
     * type
     * 类型
     */
    @ApiModelProperty(value="类型")
    private Integer type;
    /**
     * decision
     * 流程决策标识
     */
    @ApiModelProperty(value="流程决策标识")
    private String decision;
    /**
     * isAgree
     * 是否同意
     */
    @ApiModelProperty(value="是否同意")
    private Integer isAgree;
    /**
     * description
     * 意见
     */
    @ApiModelProperty(value="意见")
    private String description;
}
