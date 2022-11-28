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
 * @Description $购置申请详情 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-30 16:28:46
 */
@Data
@Table(value = "incloud_biz_asset_purchase_apply_detail",comment = "购置申请详情")
@TableName("incloud_biz_asset_purchase_apply_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "购置申请详情 Entity")
public class PurchaseApplyDetail extends IModel<PurchaseApplyDetail> {

    /**
     * apply_id
     * 购置申请id;购置申请id
     */
    @ApiModelProperty(value="购置申请id;购置申请id")
    @TableField(value="apply_id")
    @Column(type = DataType.BIGINT, length = 20, fkTableName = "incloud_biz_asset_purchase_apply" ,fkFieldName = "id" , isNull = true, comment = "购置申请id;购置申请id")
    private Long applyId;
    /**
     * planall_id
     * 计划详情总表id;计划详情总表id
     */
    @ApiModelProperty(value="计划详情总表id;计划详情总表id")
    @TableField(value="planall_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "计划详情总表id;计划详情总表id")
    private Long planallId;
    /**
     * route
     * 全路径
     */
    @ApiModelProperty(value="全路径")
    @TableField(value="route")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "全路径")
    private String route;
    /**
     * route_name
     * 全路径名称
     */
    @ApiModelProperty(value="全路径名称")
    @TableField(value="route_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "全路径名称")
    private String routeName;
    /**
     * classify_code
     * 物资分类编码
     */
    @ApiModelProperty(value="物资分类编码")
    @TableField(value="classify_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类编码")
    private String classifyCode;
    /**
     * classify_name
     * 物资分类名称
     */
    @ApiModelProperty(value="物资分类名称")
    @TableField(value="classify_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类名称")
    private String classifyName;
    /**
     * item_id
     * 物资Id
     */
    @ApiModelProperty(value="物资Id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "物资Id")
    private Long itemId;
    /**
     * item_code
     * 物资编码
     */
    @ApiModelProperty(value="物资编码")
    @TableField(value="item_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资编码")
    private String itemCode;
    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    @TableField(value="item_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资名称")
    private String itemName;
    /**
     * desclong
     * 物资长描述
     */
    @ApiModelProperty(value="物资长描述")
    @TableField(value="desclong")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "物资长描述")
    private String desclong;
    /**
     * descshort
     * 物资短描述
     */
    @ApiModelProperty(value="物资短描述")
    @TableField(value="descshort")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资短描述")
    private String descshort;
    /**
     * unit_code
     * 物资单位编码
     */
    @ApiModelProperty(value="物资单位编码")
    @TableField(value="unit_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资单位编码")
    private String unitCode;
    /**
     * unit_name
     * 物资单位名称
     */
    @ApiModelProperty(value="物资单位名称")
    @TableField(value="unit_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资单位名称")
    private String unitName;
    /**
     * specs
     * 物资规格
     */
    @ApiModelProperty(value="物资规格")
    @TableField(value="specs")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资规格")
    private String specs;
    /**
     * standard
     * 物资标准
     */
    @ApiModelProperty(value="物资标准")
    @TableField(value="standard")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资标准")
    private String standard;
    /**
     * material_quality
     * 物资材质
     */
    @ApiModelProperty(value="物资材质")
    @TableField(value="material_quality")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资材质")
    private String materialQuality;
    /**
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    @TableField(value="tax_rate")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "税率")
    private String taxRate;
    /**
     * tax_amount
     * 税额
     */
    @ApiModelProperty(value="税额")
    @TableField(value="tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "税额")
    private BigDecimal taxAmount;
    /**
     * apply_number
     * 申请数量
     */
    @ApiModelProperty(value="申请数量")
    @TableField(value="apply_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "申请数量")
    private BigDecimal applyNumber;
    /**
     * apply_amount
     * 申请含税单价
     */
    @ApiModelProperty(value="申请含税单价")
    @TableField(value="apply_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "申请含税单价")
    private BigDecimal applyAmount;
    /**
     * apply_untaxed_amount
     * 申请不含税单价
     */
    @ApiModelProperty(value="申请不含税单价")
    @TableField(value="apply_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "申请不含税单价")
    private BigDecimal applyUntaxedAmount;
    /**
     * apply_sum_amount
     * 申请含税总价
     */
    @ApiModelProperty(value="申请含税总价")
    @TableField(value="apply_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "申请含税总价")
    private BigDecimal applySumAmount;
    /**
     * apply_sum_untaxed_amount
     * 申请不含税总价
     */
    @ApiModelProperty(value="申请不含税总价")
    @TableField(value="apply_sum_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "申请不含税总价")
    private BigDecimal applySumUntaxedAmount;
    /**
     * purpose
     * 用途
     */
    @ApiModelProperty(value="用途")
    @TableField(value="purpose")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "用途")
    private String purpose;
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
     * apply_code
     * 购置申请code
     */
    @ApiModelProperty(value="购置申请code")
    @TableField(value="apply_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "购置申请code")
    private String applyCode;
    /**
     * classify_id
     * 分类id
     */
    @ApiModelProperty(value="分类id")
    @TableField(value="classify_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "分类id")
    private Long classifyId;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;
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
    /**
     * register_sum_amount
     * 采购登记金额
     */
    @ApiModelProperty(value="采购登记金额")
    @TableField(value="register_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "采购登记金额")
    private BigDecimal registerSumAmount;
    /**
     * not_register_sum_amount
     * 未采购登记金额
     */
    @ApiModelProperty(value="未采购登记金额")
    @TableField(value="not_register_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "未采购登记金额")
    private BigDecimal notRegisterSumAmount;
    /**
     * acceptance_number
     * 购置验收数量
     */
    @ApiModelProperty(value="购置验收数量")
    @TableField(value="acceptance_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "购置验收数量")
    private BigDecimal acceptanceNumber;
    /**
     * storage_number
     * 购置入库数量
     */
    @ApiModelProperty(value="购置入库数量")
    @TableField(value="storage_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "购置入库数量")
    private BigDecimal storageNumber;
    /**
     * sku_code
     * sku编码
     */
    @ApiModelProperty(value="sku编码")
    @TableField(value="sku_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "sku编码")
    private String skuCode;
    /**
     * sku_full_id
     * sku全id
     */
    @ApiModelProperty(value="sku全id")
    @TableField(value="sku_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "sku全id")
    private String skuFullId;
    /**
     * sku_full_name
     * sku全名称
     */
    @ApiModelProperty(value="sku全名称")
    @TableField(value="sku_full_name")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "sku全名称")
    private String skuFullName;
    /**
     * apply_tax_amount
     * 申请单价税额
     */
    @ApiModelProperty(value="申请单价税额")
    @TableField(value="apply_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 ,  isNull = true, comment = "申请单价税额")
    private BigDecimal applyTaxAmount;
    /**
     * apply_sum_tax_amount
     * 申请总价税额
     */
    @ApiModelProperty(value="申请总价税额")
    @TableField(value="apply_sum_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 ,  isNull = true, comment = "申请总价税额")
    private BigDecimal applySumTaxAmount;
    /**
     * apply_user_id
     * 申请人
     */
    @ApiModelProperty(value="申请人")
    @TableField(value="apply_user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "申请人")
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
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "申请人机构")
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
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "申请人部门")
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
     * status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "审批状态")
    private Integer status;

}
