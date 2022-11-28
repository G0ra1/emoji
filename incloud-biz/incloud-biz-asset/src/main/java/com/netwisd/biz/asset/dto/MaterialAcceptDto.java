package com.netwisd.biz.asset.dto;

import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
import com.netwisd.biz.asset.dto.MaterialAcceptDetailDto;
import com.netwisd.biz.asset.dto.MaterialAcceptAssetsDto;
/**
 * @Description 资产领用 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Data
@Map("incloud_biz_asset_material_accept")
@ApiModel(value = "资产领用 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MaterialAcceptDto extends WfDto {

    public MaterialAcceptDto(Args args){
        super(args);
    }
    /**
     * code
     * 编号
     */
    @ApiModelProperty(value="编号")
    private String code;
    /**
     * apply_user_id
     * 申请人
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人
     */
    @ApiModelProperty(value="申请人")
    private String applyUserName;
    /**
     * apply_user_org_id
     * 申请人机构
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人机构")
    private Long applyUserOrgId;
    /**
     * apply_user_org_name
     * 申请人机构
     */
    @ApiModelProperty(value="申请人机构")
    private String applyUserOrgName;
    /**
     * apply_user_dept_id
     * 申请人部门
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人部门")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门
     */
    @ApiModelProperty(value="申请人部门")
    private String applyUserDeptName;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;
    /**
     * sum_total_amount
     * 合计金额
     */
    @ApiModelProperty(value="合计金额")
    private BigDecimal sumTotalAmount;
    /**
     * sum_total_untaxed_amount
     * 合计金额-未税
     */
    @ApiModelProperty(value="合计金额-未税")
    private BigDecimal sumTotalUntaxedAmount;
    /**
     * sum_total_tax_amount
     * 合计金额-税额
     */
    @ApiModelProperty(value="合计金额-税额")
    private BigDecimal sumTotalTaxAmount;
    /**
     * sum_total_number
     * 合计数量
     */
    @ApiModelProperty(value="合计数量")
    private BigDecimal sumTotalNumber;
    /**
     * audit_success_tiem
     * 审批通过时间
     */
    @ApiModelProperty(value="审批通过时间")
    private LocalDateTime auditSuccessTiem;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @ApiModelProperty(value="camunda流程实例ID")
    private String camundaProcinsId;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="流程分类ID")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    private String procdefTypeName;
    /**
     * procdef_version
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    private Integer procdefVersion;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    private String procdefName;
    /**
     * process_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    private String processName;
    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    private String reason;
    /**
     * reason
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String explanation;
    /**
     * biz_key
     * 工作流—业务key
     */
    @ApiModelProperty(value="工作流—业务key")
    private String bizKey;
    /**
     * camunda_task_id
     * camunda流程任务ID
     */
    @ApiModelProperty(value="camunda流程任务ID")
    private String camundaTaskId;
    /**
     * biz_priority
     * 任务优先级
     */
    @ApiModelProperty(value="任务优先级")
    private String bizPriority;
    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    private Integer auditStatus;

    /**
     * task_inst_id
     * 任务实例Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="任务实例Id")
    private Long taskInstId;

    /**
     * parent_task_node_id
     * 父任务节点Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="父任务节点Id")
    private Long parentTaskNodeId;
    /**
     * task_id
     * 当前任务节点id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="当前任务节点id")
    private Long taskId;
    /**
     * distribute_number
     * 派发数量
     */
    @ApiModelProperty(value="派发数量")
    private BigDecimal distributeNumber;
    /**
     * not_distribute_number
     * 未派发数量
     */
    @ApiModelProperty(value="派发数量")
    private BigDecimal notDistributeNumber;
    /**
     * sign_number
     * 签收数量
     */
    @ApiModelProperty(value="签收数量")
    private BigDecimal signNumber;
    /**
     * not_sign_number
     * 未签收数量
     */
    @ApiModelProperty(value="未签收数量")
    private BigDecimal notSignNumber;
    /**
     * file_ids
     * 附件ids
     */
    @ApiModelProperty(value="附件ids")
    private String fileIds;

    /**
     * asset_user_id
     * 资产所属人（使用人）
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产所属人")
    private Long assetUserId;
    /**
     * asset_user_name
     * 资产所属人（使用人）
     */
    @ApiModelProperty(value="资产所属人名称")
    private String assetUserName;
    /**
     * asset_dept_id
     * 使用人单位
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="使用人部门")
    private Long assetDeptId;
    /**
     * asset_dept_name
     * 使用人单位
     */
    @ApiModelProperty(value="使用人部门")
    private String assetDeptName;
    /**
     * asset_org_id
     * 使用人单位
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="使用人单位")
    private Long assetOrgId;
    /**
     * asset_org_name
     * 使用人单位
     */
    @ApiModelProperty(value="使用人单位")
    private String assetOrgName;

    @ApiModelProperty(value="materialAcceptDetailList")
    private List<MaterialAcceptDetailDto> materialAcceptDetailList;
    @ApiModelProperty(value="materialAcceptAssetsList")
    private List<MaterialAcceptAssetsDto> materialAcceptAssetsList;

    /**
     * 指定字段模糊查询
     */
    @ApiModelProperty(value="查询条件")
    private String searchCondition;

}
