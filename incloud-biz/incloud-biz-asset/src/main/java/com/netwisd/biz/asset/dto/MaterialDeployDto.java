package com.netwisd.biz.asset.dto;

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
import com.netwisd.biz.asset.dto.MaterialDeployDetailDto;
/**
 * @Description 资产调配 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-08 14:30:02
 */
@Data
@Map("incloud_biz_asset_material_deploy")
@ApiModel(value = "资产调配 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MaterialDeployDto extends IDto{

    public MaterialDeployDto(Args args){
        super(args);
    }
    /**
     * code
     * 编号
     */
    @ApiModelProperty(value="编号")
    private String code;
    /**
     * create_user_id
     * 创建人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    private String createUserName;
    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    private String createUserOrgFullId;
    /**
     * explanation
     * 说明
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="说明")
    private Long explanation;
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
     * lend_unit_id
     * 借出单位id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="借出单位id")
    private Long lendUnitId;
    /**
     * lend_unit
     * 借出单位
     */
    @ApiModelProperty(value="借出单位")
    private String lendUnit;
    /**
     * lend_user_id
     * 借出负责人id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="借出负责人id")
    private Long lendUserId;
    /**
     * lend_user
     * 借出负责人
     */
    @ApiModelProperty(value="借出负责人")
    private String lendUser;
    /**
     * need_dept_id
     * 需用部门id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="需用部门id")
    private Long needDeptId;
    /**
     * need_dept
     * 需用部门
     */
    @ApiModelProperty(value="需用部门")
    private String needDept;
    /**
     * borrow_state
     * 借用状态
     */
    @ApiModelProperty(value="借用状态")
    private String borrowState;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;
    /**
     * apply_user_id
     * 申请人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人ID")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人名称
     */
    @ApiModelProperty(value="申请人名称")
    private String applyUserName;
    /**
     * apply_user_org_id
     * 申请人机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人机构ID")
    private Long applyUserOrgId;
    /**
     * apply_user_org_name
     * 申请人机构名称
     */
    @ApiModelProperty(value="申请人机构名称")
    private String applyUserOrgName;
    /**
     * apply_user_dept_id
     * 申请人部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人部门ID")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="申请人部门名称")
    private String applyUserDeptName;
    /**
     * type
     * 类型
     */
    @ApiModelProperty(value="类型")
    private Integer type;
    /**
     * type_name
     * 类型名称
     */
    @ApiModelProperty(value="类型名称")
    private String typeName;
    /**
     * delivery_number
     * 出库数量
     */
    @ApiModelProperty(value="出库数量")
    private BigDecimal deliveryNumber;
    /**
     * not_delivery_number
     * 未出库数量
     */
    @ApiModelProperty(value="未出库数量")
    private BigDecimal notDeliveryNumber;
    /**
     * delivery_amount
     * 出库金额
     */
    @ApiModelProperty(value="出库金额")
    private BigDecimal deliveryAmount;
    /**
     * not_delivery_amount
     * 未出库金额
     */
    @ApiModelProperty(value="未出库金额")
    private BigDecimal notDeliveryAmount;

    @ApiModelProperty(value="调配详情")
    private List<MaterialDeployDetailDto> detailList;

}
