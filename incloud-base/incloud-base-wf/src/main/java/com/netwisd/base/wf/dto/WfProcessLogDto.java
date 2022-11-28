package com.netwisd.base.wf.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * @Description 流程日志 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-14 17:42:43
 */
@Data
@ApiModel(value = "流程日志 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfProcessLogDto extends IDto{
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
     * camunda_procdef_version
     * camunda流程定义版本
     */
    @ApiModelProperty(value="camunda流程定义版本")
    private Integer camundaProcdefVersion;
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @ApiModelProperty(value="camunda流程实例ID")
    @Valid(length = 64)
    private String camundaProcinsId;
    /**
     * camunda_task_id
     * camunda任务ID
     */
    @ApiModelProperty(value="camunda任务ID")
    private String camundaTaskId;
    /**
     * camunda_parent_act_ins_id
     * camunda父节点实例ID
     */
    @ApiModelProperty(value="camunda父节点实例ID")
    private String camundaParentActInsId;
    /**
     * camunda_current_act_ins_id
     * camunda当前节点实例ID
     */
    @ApiModelProperty(value="camunda当前节点实例ID")
    private String camundaCurrentActInsId;
    /**
     * camunda_call_activity_def_id
     * camunda callActivity定义ID
     */
    @ApiModelProperty(value="camunda callActivity定义ID")
    private String camundaCallActivityDefId;
    /**
     * camunda_parent_proc_ins_id
     * camunda 父流程实例ID
     */
    @ApiModelProperty(value="camunda 父流程实例ID")
    private String camundaParentProcinsId;
    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefId;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    private String procdefName;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @ApiModelProperty(value="流程分类ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    private String procdefTypeName;
    /**
     * process_id
     * 流程实例ID
     */
    @ApiModelProperty(value="流程实例ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procinsId;
    /**
     * procins_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    private String procinsName;
    /**
     * parent_proc_ins_id
     * 父流程实例ID
     */
    @ApiModelProperty(value="父流程实例ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long parentProcinsId;
    /**
     * node_id
     * 节点ID
     */
    @ApiModelProperty(value="节点ID")
    @Valid(length = 50) 
    private String nodeId;
    /**
     * node_name
     * 节点名称
     */
    @ApiModelProperty(value="节点名称")
    @Valid(length = 100) 
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
    @Valid(length = 2)
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
    @Valid(length = 0) 
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
    private Integer decision;
    /**
     * isAgree
     * 是否同意
     */
    @ApiModelProperty(value="是否同意")
    @Valid
    private Integer isAgree;
    /**
     * description
     * 意见
     */
    @ApiModelProperty(value="意见")
    private String description;
    /**
     * claim_status
     * 签收状态
     */
    @ApiModelProperty(value="签收状态")
    private Integer claimStatus;
    /**
     * claim_time
     * 签收时间
     */
    @ApiModelProperty(value="签收时间")
    private LocalDateTime claimTime;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;
}