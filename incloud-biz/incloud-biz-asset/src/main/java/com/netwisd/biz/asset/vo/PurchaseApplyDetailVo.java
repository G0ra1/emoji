package com.netwisd.biz.asset.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 购置申请详情 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-30 16:28:46
 */
@Data
@ApiModel(value = "购置申请详情 Vo")
public class PurchaseApplyDetailVo extends IVo{

    /**
     * apply_id
     * 购置申请id;购置申请id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="购置申请id;购置申请id")
    private Long applyId;
    /**
     * planall_id
     * 计划详情总表id;计划详情总表id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="计划详情总表id;计划详情总表id")
    private Long planallId;
    /**
     * route
     * 全路径
     */
    @ApiModelProperty(value="全路径")
    private String route;
    /**
     * route_name
     * 全路径名称
     */
    @ApiModelProperty(value="全路径名称")
    private String routeName;
    /**
     * classify_code
     * 物资分类编码
     */
    @ApiModelProperty(value="物资分类编码")
    private String classifyCode;
    /**
     * classify_name
     * 物资分类名称
     */
    @ApiModelProperty(value="物资分类名称")
    private String classifyName;
    /**
     * item_id
     * 物资Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="物资Id")
    private Long itemId;
    /**
     * item_code
     * 物资编码
     */
    @ApiModelProperty(value="物资编码")
    private String itemCode;
    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    private String itemName;
    /**
     * desclong
     * 物资长描述
     */
    @ApiModelProperty(value="物资长描述")
    private String desclong;
    /**
     * descshort
     * 物资短描述
     */
    @ApiModelProperty(value="物资短描述")
    private String descshort;
    /**
     * unit_code
     * 物资单位编码
     */
    @ApiModelProperty(value="物资单位编码")
    private String unitCode;
    /**
     * unit_name
     * 物资单位名称
     */
    @ApiModelProperty(value="物资单位名称")
    private String unitName;
    /**
     * specs
     * 物资规格
     */
    @ApiModelProperty(value="物资规格")
    private String specs;
    /**
     * standard
     * 物资标准
     */
    @ApiModelProperty(value="物资标准")
    private String standard;
    /**
     * material_quality
     * 物资材质
     */
    @ApiModelProperty(value="物资材质")
    private String materialQuality;
    /**
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    private String taxRate;
    /**
     * tax_amount
     * 税额
     */
    @ApiModelProperty(value="税额")
    private BigDecimal taxAmount;
    /**
     * apply_number
     * 申请数量
     */
    @ApiModelProperty(value="申请数量")
    private BigDecimal applyNumber;
    /**
     * apply_amount
     * 申请含税单价
     */
    @ApiModelProperty(value="申请含税单价")
    private BigDecimal applyAmount;
    /**
     * apply_untaxed_amount
     * 申请不含税单价
     */
    @ApiModelProperty(value="申请不含税单价")
    private BigDecimal applyUntaxedAmount;
    /**
     * apply_sum_amount
     * 申请含税总价
     */
    @ApiModelProperty(value="申请含税总价")
    private BigDecimal applySumAmount;
    /**
     * apply_sum_untaxed_amount
     * 申请不含税总价
     */
    @ApiModelProperty(value="申请不含税总价")
    private BigDecimal applySumUntaxedAmount;
    /**
     * purpose
     * 用途
     */
    @ApiModelProperty(value="用途")
    private String purpose;
    /**
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String explanation;
    /**
     * planYear
     * 计划年度
     */
    @ApiModelProperty(value="计划年度")
    private String planYear;
    /**
     * apply_code
     * 购置申请code
     */
    @ApiModelProperty(value="购置申请code")
    private String applyCode;
    /**
     * classify_id
     * 分类id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="分类id")
    private Long classifyId;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    private String itemType;
    /**
     * register_number
     * 采购登记数量
     */
    @ApiModelProperty(value="采购登记数量")
    private BigDecimal registerNumber;
    /**
     * not_register_number
     * 未采购登记数量
     */
    @ApiModelProperty(value="未采购登记数量")
    private BigDecimal notRegisterNumber;
    /**
     * register_sum_amount
     * 采购登记金额
     */
    @ApiModelProperty(value="申请含税单价")
    private BigDecimal registerSumAmount;
    /**
     * not_register_sum_amount
     * 未采购登记金额
     */
    @ApiModelProperty(value="申请含税单价")
    private BigDecimal notRegisterSumAmount;
    /**
     * acceptance_number
     * 购置验收数量
     */
    @ApiModelProperty(value="购置验收数量")
    private BigDecimal acceptanceNumber;
    /**
     * storage_number
     * 购置入库数量
     */
    @ApiModelProperty(value="购置入库数量")
    private BigDecimal storageNumber;
    /**
     * sku_code
     * sku编码
     */
    @ApiModelProperty(value="sku编码")
    private String skuCode;
    /**
     * sku_full_id
     * sku全id
     */
    @ApiModelProperty(value="sku全id")
    private String skuFullId;
    /**
     * sku_full_name
     * sku全名称
     */
    @ApiModelProperty(value="sku全名称")
    private String skuFullName;
    /**
     * apply_tax_amount
     * 申请单价税额
     */
    @ApiModelProperty(value="申请单价税额")
    private BigDecimal applyTaxAmount;
    /**
     * apply_sum_tax_amount
     * 申请总价税额
     */
    @ApiModelProperty(value="申请总价税额")
    private BigDecimal applySumTaxAmount;

    /**
     * apply_user_id
     * 申请人
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
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
     * status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    private Integer status;

}
