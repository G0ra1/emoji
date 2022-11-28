package com.netwisd.biz.asset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $资产调配 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-08 14:30:02
 */
@Data
@Table(value = "incloud_biz_asset_material_deploy",comment = "资产调配")
@TableName("incloud_biz_asset_material_deploy")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产调配 Entity")
public class MaterialDeploy extends IModel<MaterialDeploy> {

    /**
     * code
     * 编号
     */
    @ApiModelProperty(value="编号")
    @TableField(value="code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "编号")
    private String code;
    /**
     * create_user_id
     * 创建人ID
     */
    @ApiModelProperty(value="创建人ID")
    @TableField(value="create_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    @TableField(value="create_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人名称")
    private String createUserName;
    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @ApiModelProperty(value="创建人父级机构ID")
    @TableField(value="create_user_parent_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    @TableField(value="create_user_parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @ApiModelProperty(value="创建人父级部门ID")
    @TableField(value="create_user_parent_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    @TableField(value="create_user_parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    @TableField(value="create_user_org_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "创建人父级组织全路径ID")
    private String createUserOrgFullId;
    /**
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    @TableField(value="explanation")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "说明")
    private Long explanation;
    /**
     * audit_success_tiem
     * 审批通过时间
     */
    @ApiModelProperty(value="审批通过时间")
    @TableField(value="audit_success_tiem")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "审批通过时间")
    private LocalDateTime auditSuccessTiem;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    @TableField(value="camunda_procdef_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    @TableField(value="camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @ApiModelProperty(value="camunda流程实例ID")
    @TableField(value="camunda_procins_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda流程实例ID")
    private String camundaProcinsId;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @ApiModelProperty(value="流程分类ID")
    @TableField(value="procdef_type_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "流程分类ID")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    @TableField(value="procdef_type_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "流程分类名称")
    private String procdefTypeName;
    /**
     * procdef_version
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    @TableField(value="procdef_version")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "流程版本")
    private Integer procdefVersion;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    @TableField(value="procdef_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "流程定义名称")
    private String procdefName;
    /**
     * process_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    @TableField(value="process_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "流程实例名称")
    private String processName;
    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    @TableField(value="reason")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "事由")
    private String reason;
    /**
     * biz_key
     * 工作流—业务key
     */
    @ApiModelProperty(value="工作流—业务key")
    @TableField(value="biz_key")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "工作流—业务key")
    private String bizKey;
    /**
     * camunda_task_id
     * camunda流程任务ID
     */
    @ApiModelProperty(value="camunda流程任务ID")
    @TableField(value="camunda_task_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "camunda流程任务ID")
    private String camundaTaskId;
    /**
     * biz_priority
     * 任务优先级
     */
    @ApiModelProperty(value="任务优先级")
    @TableField(value="biz_priority")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "任务优先级")
    private String bizPriority;
    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    @TableField(value="audit_status")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "审批状态")
    private Integer auditStatus;
    /**
     * lend_unit_id
     * 借出单位id
     */
    @ApiModelProperty(value="借出单位id")
    @TableField(value="lend_unit_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "借出单位id")
    private Long lendUnitId;
    /**
     * lend_unit
     * 借出单位
     */
    @ApiModelProperty(value="借出单位")
    @TableField(value="lend_unit")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "借出单位")
    private String lendUnit;
    /**
     * lend_user_id
     * 借出负责人id
     */
    @ApiModelProperty(value="借出负责人id")
    @TableField(value="lend_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "借出负责人id")
    private Long lendUserId;
    /**
     * lend_user
     * 借出负责人
     */
    @ApiModelProperty(value="借出负责人")
    @TableField(value="lend_user")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "借出负责人")
    private String lendUser;
    /**
     * need_dept_id
     * 需用部门id
     */
    @ApiModelProperty(value="需用部门id")
    @TableField(value="need_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "需用部门id")
    private Long needDeptId;
    /**
     * need_dept
     * 需用部门
     */
    @ApiModelProperty(value="需用部门")
    @TableField(value="need_dept")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "需用部门")
    private String needDept;
    /**
     * borrow_state
     * 借用状态
     */
    @ApiModelProperty(value="借用状态")
    @TableField(value="borrow_state")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "借用状态")
    private String borrowState;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "申请时间")
    private LocalDateTime applyTime;
    /**
     * apply_user_id
     * 申请人ID
     */
    @ApiModelProperty(value="申请人ID")
    @TableField(value="apply_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人ID")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人名称
     */
    @ApiModelProperty(value="申请人名称")
    @TableField(value="apply_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人名称")
    private String applyUserName;
    /**
     * apply_user_org_id
     * 申请人机构ID
     */
    @ApiModelProperty(value="申请人机构ID")
    @TableField(value="apply_user_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人机构ID")
    private Long applyUserOrgId;
    /**
     * apply_user_org_name
     * 申请人机构名称
     */
    @ApiModelProperty(value="申请人机构名称")
    @TableField(value="apply_user_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人机构名称")
    private String applyUserOrgName;
    /**
     * apply_user_dept_id
     * 申请人部门ID
     */
    @ApiModelProperty(value="申请人部门ID")
    @TableField(value="apply_user_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人部门ID")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="申请人部门名称")
    @TableField(value="apply_user_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人部门名称")
    private String applyUserDeptName;
    /**
     * type
     * 类型
     */
    @ApiModelProperty(value="类型")
    @TableField(value="type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "类型")
    private Integer type;
    /**
     * type_name
     * 类型名称
     */
    @ApiModelProperty(value="类型名称")
    @TableField(value="type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "类型名称")
    private String typeName;
    /**
     * delivery_number
     * 出库数量
     */
    @ApiModelProperty(value="出库数量")
    @TableField(value="delivery_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "出库数量")
    private BigDecimal deliveryNumber;
    /**
     * not_delivery_number
     * 未出库数量
     */
    @ApiModelProperty(value="未出库数量")
    @TableField(value="not_delivery_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未出库数量")
    private BigDecimal notDeliveryNumber;
    /**
     * delivery_amount
     * 出库金额
     */
    @ApiModelProperty(value="出库金额")
    @TableField(value="delivery_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "出库金额")
    private BigDecimal deliveryAmount;
    /**
     * not_delivery_amount
     * 未出库金额
     */
    @ApiModelProperty(value="未出库金额")
    @TableField(value="not_delivery_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "未出库金额")
    private BigDecimal notDeliveryAmount;

}
