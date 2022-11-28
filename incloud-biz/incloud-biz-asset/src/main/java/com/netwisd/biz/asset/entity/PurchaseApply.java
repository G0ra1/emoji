package com.netwisd.biz.asset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.wf.starter.entitiy.WfEntity;
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
 * @Description $购置申请 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-20 10:10:21
 */
@Data
@Table(value = "incloud_biz_asset_purchase_apply",comment = "购置申请")
@TableName("incloud_biz_asset_purchase_apply")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "购置申请 Entity")
public class PurchaseApply extends WfEntity<PurchaseApply> {

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
     * item_type
     * 申请类型
     */
    @ApiModelProperty(value="申请类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请类型")
    private String itemType;
    /**
     * item_type_name
     * 申请类型名称
     */
    @ApiModelProperty(value="申请类型名称")
    @TableField(value="item_type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请类型名称")
    private String itemTypeName;
    /**
     * status
     * 采购状态
     */
    @ApiModelProperty(value="采购状态")
    @TableField(value="status")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "采购状态")
    private String status;
    /**
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    @TableField(value="explanation")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "说明")
    private String explanation;
    /**
     * plan_year
     * 计划年度
     */
    @ApiModelProperty(value="计划年度")
    @TableField(value="plan_year")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "计划年度")
    private String planYear;
    /**
     * file_ids
     * 附件ids
     */
    @ApiModelProperty(value="附件ids")
    @TableField(value="file_ids")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "附件ids")
    private String fileIds;
    /**
     * not_register_amount
     * 未采购金额
     */
    @ApiModelProperty(value="未采购金额")
    @TableField(value="not_register_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "未采购金额")
    private BigDecimal notRegisterAmount;
    /**
     * register_number
     * 采购登记数量
     */
    @ApiModelProperty(value="采购登记数量")
    @TableField(value="register_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "采购登记数量")
    private BigDecimal registerNumber;
    /**
     * not_register_number
     * 未采购登记数量
     */
    @ApiModelProperty(value="未采购登记数量")
    @TableField(value="not_register_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未采购登记数量")
    private BigDecimal notRegisterNumber;

}
