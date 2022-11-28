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
/**
 * @Description 采购信息登记详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-06-14 10:39:26
 */
@Data
@Map("incloud_biz_asset_purchase_register_detail")
@ApiModel(value = "采购信息登记详情 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PurchaseRegisterDetailDto extends IDto{

    public PurchaseRegisterDetailDto(Args args){
        super(args);
    }
    /**
     * apply_id
     * 购置申请id;购置申请id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="购置申请id;购置申请id")
    private Long applyId;
    /**
     * apply_code
     * 购置申请编号
     */
    @ApiModelProperty(value="购置申请编号")
    private String applyCode;
    /**
     * apply_detail_id
     * 购置申请详情id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="购置申请详情id")
    private Long applyDetailId;
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
     * 物资分类编码;物资分类编码
     */
    @ApiModelProperty(value="物资分类编码;物资分类编码")
    private String classifyCode;
    /**
     * classify_name
     * 物资分类名称;物资分类名称
     */
    @ApiModelProperty(value="物资分类名称;物资分类名称")
    private String classifyName;
    /**
     * item_code
     * 物资编码;物资编码
     */
    @ApiModelProperty(value="物资编码;物资编码")
    private String itemCode;
    /**
     * item_name
     * 物资名称;物资名称
     */
    @ApiModelProperty(value="物资名称;物资名称")
    private String itemName;
    /**
     * desclong
     * 物资长描述;物资长描述
     */
    @ApiModelProperty(value="物资长描述;物资长描述")
    private String desclong;
    /**
     * descshort
     * 物资短描述;物资短描述
     */
    @ApiModelProperty(value="物资短描述;物资短描述")
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
     * tax_rate
     * 申请税率
     */
    @ApiModelProperty(value="申请税率")
    private BigDecimal taxRate;
    /**
     * tax_amount
     * 申请税额
     */
    @ApiModelProperty(value="申请税额")
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
     * register_tax_rate
     * 采购税率
     */
    @ApiModelProperty(value="采购税率")
    private BigDecimal registerTaxRate;
    /**
     * register_tax_amount
     * 采购税额
     */
    @ApiModelProperty(value="采购税额")
    private BigDecimal registerTaxAmount;
    /**
     * register_number
     * 采购数量
     */
    @ApiModelProperty(value="采购数量")
    private BigDecimal registerNumber;
    /**
     * register_amount
     * 采购含税单价
     */
    @ApiModelProperty(value="采购含税单价")
    private BigDecimal registerAmount;
    /**
     * register_untaxed_amount
     * 采购不含税单价
     */
    @ApiModelProperty(value="采购不含税单价")
    private BigDecimal registerUntaxedAmount;
    /**
     * register_sum_amount
     * 采购含税总价
     */
    @ApiModelProperty(value="采购含税总价")
    private BigDecimal registerSumAmount;
    /**
     * register_sum_untaxed_amount
     * 采购不含税总价
     */
    @ApiModelProperty(value="采购不含税总价")
    private BigDecimal registerSumUntaxedAmount;
    /**
     * classify_id
     * 分类id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="分类id")
    private Long classifyId;
    /**
     * item_type
     * 物资类型;物资类型
     */
    @ApiModelProperty(value="物资类型;物资类型")
    private String itemType;
    /**
     * require_time
     * 要求到货时间
     */
    @ApiModelProperty(value="要求到货时间")
    private LocalDateTime requireTime;
    /**
     * supplier_name
     * 供应商
     */
    @ApiModelProperty(value="供应商")
    private String supplierName;
    /**
     * contract_code
     * 合同号
     */
    @ApiModelProperty(value="合同号")
    private String contractCode;
    /**
     * plan_time
     * 预计到货时间
     */
    @ApiModelProperty(value="预计到货时间")
    private LocalDateTime planTime;
    /**
     * purchase_type
     * 物资购置类型
     */
    @ApiModelProperty(value="物资购置类型")
    private Integer purchaseType;
    /**
     * register_id
     * 采购登记id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_asset_purchase_register" ,field = "id")
    @ApiModelProperty(value="采购登记id")
    private Long registerId;
    /**
     * register_assets_id
     * 采购登记明细id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="采购登记明细id")
    private Long registerAssetsId;
    /**
     * register_sum_tax_amount
     * 采购总税额
     */
    @ApiModelProperty(value="采购总税额")
    private BigDecimal registerSumTaxAmount;
    /**
     * sku_code
     * sku属性
     */
    @ApiModelProperty(value="sku属性")
    private String skuCode;
    /**
     * sku_full_id
     * sku全路径id
     */
    @ApiModelProperty(value="sku全路径id")
    private String skuFullId;
    /**
     * sku_full_name
     * sku全路径名称
     */
    @ApiModelProperty(value="sku全路径名称")
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
     * item_id
     * 物资Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="物资Id")
    private Long itemId;
    /**
     * supplier_id
     * 供应商id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="供应商id")
    private Long supplierId;
    /**
     * contract_id
     * 合同id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="合同id")
    private Long contractId;


}
