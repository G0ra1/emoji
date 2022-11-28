package com.netwisd.biz.asset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.wf.starter.entitiy.WfEntity;
import com.netwisd.common.core.util.IdTypeDeserializer;
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
 * @Description $资产领用 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Data
@Table(value = "incloud_biz_asset_material_accept",comment = "资产领用")
@TableName("incloud_biz_asset_material_accept")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产领用 Entity")
public class MaterialAccept extends WfEntity<MaterialAccept> {

    /**
     * code
     * 编号
     */
    @ApiModelProperty(value="编号")
    @TableField(value="code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "编号")
    private String code;
    /**
     * apply_user_id
     * 申请人
     */
    @ApiModelProperty(value="申请人")
    @TableField(value="apply_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人
     */
    @ApiModelProperty(value="申请人")
    @TableField(value="apply_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人")
    private String applyUserName;
    /**
     * apply_user_org_id
     * 申请人机构
     */
    @ApiModelProperty(value="申请人机构")
    @TableField(value="apply_user_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人机构")
    private Long applyUserOrgId;
    /**
     * apply_user_org_name
     * 申请人机构
     */
    @ApiModelProperty(value="申请人机构")
    @TableField(value="apply_user_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人机构")
    private String applyUserOrgName;
    /**
     * apply_user_dept_id
     * 申请人部门
     */
    @ApiModelProperty(value="申请人部门")
    @TableField(value="apply_user_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人部门")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门
     */
    @ApiModelProperty(value="申请人部门")
    @TableField(value="apply_user_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人部门")
    private String applyUserDeptName;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "申请时间")
    private LocalDateTime applyTime;
    /**
     * sum_total_amount
     * 合计金额
     */
    @ApiModelProperty(value="合计金额")
    @TableField(value="sum_total_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "合计金额")
    private BigDecimal sumTotalAmount;
    /**
     * sum_total_untaxed_amount
     * 合计金额-未税
     */
    @ApiModelProperty(value="合计金额-未税")
    @TableField(value="sum_total_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "合计金额-未税")
    private BigDecimal sumTotalUntaxedAmount;
    /**
     * sum_total_tax_amount
     * 合计金额-税额
     */
    @ApiModelProperty(value="合计金额-税额")
    @TableField(value="sum_total_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "合计金额-税额")
    private BigDecimal sumTotalTaxAmount;
    /**
     * sum_total_number
     * 合计申请数量
     */
    @ApiModelProperty(value="合计申请数量")
    @TableField(value="sum_total_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "合计申请数量")
    private BigDecimal sumTotalNumber;
    /**
     * audit_success_tiem
     * 审批通过时间
     */
    @ApiModelProperty(value="审批通过时间")
    @TableField(value="audit_success_tiem")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "审批通过时间")
    private LocalDateTime auditSuccessTiem;
    /**
     * camunda_task_id
     * camunda流程任务ID
     */
    @ApiModelProperty(value="camunda流程任务ID")
    @TableField(value="camunda_task_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "camunda流程任务ID")
    private String camundaTaskId;

    /**
     * file_ids
     * 附件ids
     */
    @ApiModelProperty(value="附件ids")
    @TableField(value="file_ids")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "附件ids")
    private String fileIds;
    /**
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    @TableField(value="explanation")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "说明")
    private String explanation;

    /**
     * task_inst_id
     * 任务实例Id
     */
    @ApiModelProperty(value="任务实例Id")
    @TableField(value="task_inst_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "任务实例Id")
    private Long taskInstId;
    /**
     * parent_task_node_id
     * 父任务节点Id
     */
    @ApiModelProperty(value="父任务节点Id")
    @TableField(value="parent_task_node_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "父任务节点Id")
    private Long parentTaskNodeId;
    /**
     * task_id
     * 当前节点Id
     */
    @ApiModelProperty(value="当前节点Id")
    @TableField(value="task_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "当前节点Id")
    private Long taskId;
    /**
     * distribute_number
     * 派发数量
     */
    @ApiModelProperty(value="派发数量")
    @TableField(value="distribute_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "派发数量")
    private BigDecimal distributeNumber;
    /**
     * not_distribute_number
     * 未派发数量
     */
    @ApiModelProperty(value="未派发数量")
    @TableField(value="not_distribute_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未派发数量")
    private BigDecimal notDistributeNumber;
    /**
     * sign_number
     * 签收数量
     */
    @ApiModelProperty(value="签收数量")
    @TableField(value="sign_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "签收数量")
    private BigDecimal signNumber;
    /**
     * not_sign_number
     * 未签收数量
     */
    @ApiModelProperty(value="未签收数量")
    @TableField(value="not_sign_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未签收数量")
    private BigDecimal notSignNumber;

    /**
     * asset_user_id
     * 使用人/资产所属人
     */
    @ApiModelProperty(value="使用人/资产所属人")
    @TableField(value="asset_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "使用人")
    private Long assetUserId;
    /**
     * asset_user_name
     * 申请人
     */
    @ApiModelProperty(value="使用人/资产所属人")
    @TableField(value="asset_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "使用人")
    private String assetUserName;
    /**
     * asset_dept_id
     * 使用人部门
     */
    @ApiModelProperty(value="使用人部门")
    @TableField(value="asset_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "使用人部门")
    private Long assetDeptId;
    /**
     * asset_dept_name
     * 使用人部门
     */
    @ApiModelProperty(value="使用人部门")
    @TableField(value="asset_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "使用人部门")
    private String assetDeptName;
    /**
     * asset_org_id
     * 使用人单位
     */
    @ApiModelProperty(value="使用人单位")
    @TableField(value="asset_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "使用人单位")
    private Long assetOrgId;
    /**
     * asset_org_name
     * 使用人单位
     */
    @ApiModelProperty(value="使用人单位")
    @TableField(value="asset_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "使用人单位")
    private String assetOrgName;
}
