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
 * @Description $采购登记结果表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-14 10:39:32
 */
@Data
@Table(value = "incloud_biz_asset_purchase_register_result",comment = "采购登记结果表")
@TableName("incloud_biz_asset_purchase_register_result")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "采购登记结果表 Entity")
public class PurchaseRegisterResult extends IModel<PurchaseRegisterResult> {

    /**
    * classify_id
    * 分类id
    */
    @ApiModelProperty(value="分类id")
    @TableField(value="classify_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "分类id")
    private Long classifyId;

    /**
    * classify_code
    * 分类编码
    */
    @ApiModelProperty(value="分类编码")
    @TableField(value="classify_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类编码")
    private String classifyCode;

    /**
    * classify_name
    * 分类名称
    */
    @ApiModelProperty(value="分类名称")
    @TableField(value="classify_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类名称")
    private String classifyName;

    /**
    * item_type
    * 物资类型
    */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;

    /**
    * route
    * 物资分类全路径
    */
    @ApiModelProperty(value="物资分类全路径")
    @TableField(value="route")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类全路径")
    private String route;

    /**
    * route_name
    * 物资分类全路径名称
    */
    @ApiModelProperty(value="物资分类全路径名称")
    @TableField(value="route_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类全路径名称")
    private String routeName;

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
    * material_quality
    * 物资材质
    */
    @ApiModelProperty(value="物资材质")
    @TableField(value="material_quality")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资材质")
    private String materialQuality;

    /**
    * standard
    * 物资标准
    */
    @ApiModelProperty(value="物资标准")
    @TableField(value="standard")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资标准")
    private String standard;

    /**
    * specs
    * 物资规格
    */
    @ApiModelProperty(value="物资规格")
    @TableField(value="specs")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资规格")
    private String specs;

    /**
    * supplier_name
    * 供应商
    */
    @ApiModelProperty(value="供应商")
    @TableField(value="supplier_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "供应商")
    private String supplierName;

    /**
    * contract_code
    * 合同号
    */
    @ApiModelProperty(value="合同号")
    @TableField(value="contract_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同号")
    private String contractCode;

    /**
    * acceptance_number
    * 验收数量
    */
    @ApiModelProperty(value="验收数量")
    @TableField(value="acceptance_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "验收数量")
    private BigDecimal acceptanceNumber;

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
    * reason
    * 事由
    */
    @ApiModelProperty(value="事由")
    @TableField(value="reason")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "事由")
    private String reason;

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
    * register_id
    * 采购id
    */
    @ApiModelProperty(value="采购id")
    @TableField(value="register_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "采购id")
    private Long registerId;

    /**
    * register_code
    * 采购编码
    */
    @ApiModelProperty(value="采购编码")
    @TableField(value="register_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "采购编码")
    private String registerCode;

    /**
    * register_tax_rate
    * 登记税率
    */
    @ApiModelProperty(value="登记税率")
    @TableField(value="register_tax_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "登记税率")
    private BigDecimal registerTaxRate;

    /**
    * register_number
    * 登记数量
    */
    @ApiModelProperty(value="登记数量")
    @TableField(value="register_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "登记数量")
    private BigDecimal registerNumber;

    /**
    * register_amount
    * 登记单价
    */
    @ApiModelProperty(value="登记单价")
    @TableField(value="register_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "登记单价")
    private BigDecimal registerAmount;

    /**
    * register_untaxed_amount
    * 登记单价-未税
    */
    @ApiModelProperty(value="登记单价-未税")
    @TableField(value="register_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "登记单价-未税")
    private BigDecimal registerUntaxedAmount;

    /**
    * register_tax_amount
    * 登记单价-税额
    */
    @ApiModelProperty(value="登记单价-税额")
    @TableField(value="register_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "登记单价-税额")
    private BigDecimal registerTaxAmount;

    /**
    * register_sum_amount
    * 登记总价
    */
    @ApiModelProperty(value="登记总价")
    @TableField(value="register_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "登记总价")
    private BigDecimal registerSumAmount;

    /**
    * register_sum_untaxed_amount
    * 登记总价-未税
    */
    @ApiModelProperty(value="登记总价-未税")
    @TableField(value="register_sum_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "登记总价-未税")
    private BigDecimal registerSumUntaxedAmount;

    /**
    * register_sum_tax_amount
    * 登记总价-税额
    */
    @ApiModelProperty(value="登记总价-税额")
    @TableField(value="register_sum_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "登记总价-税额")
    private BigDecimal registerSumTaxAmount;

    /**
    * register_assets_id
    * 采购明细id
    */
    @ApiModelProperty(value="采购明细id")
    @TableField(value="register_assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "采购明细id")
    private Long registerAssetsId;

    /**
    * not_acceptance_number
    * 未验收数量
    */
    @ApiModelProperty(value="未验收数量")
    @TableField(value="not_acceptance_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未验收数量")
    private BigDecimal notAcceptanceNumber;

    /**
    * storage_number
    * 入库数量
    */
    @ApiModelProperty(value="入库数量")
    @TableField(value="storage_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "入库数量")
    private BigDecimal storageNumber;

    /**
    * not_storage_number
    * 未入库数量
    */
    @ApiModelProperty(value="未入库数量")
    @TableField(value="not_storage_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未入库数量")
    private BigDecimal notStorageNumber;

    /**
    * purchase_type
    * 物资购置类型
    */
    @ApiModelProperty(value="物资购置类型")
    @TableField(value="purchase_type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "物资购置类型")
    private Integer purchaseType;

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
    * sku_codes
    * sku属性
    */
    @ApiModelProperty(value="sku属性")
    @TableField(value="sku_codes")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "sku属性")
    private String skuCodes;

    /**
    * item_id
    * 物资Id
    */
    @ApiModelProperty(value="物资Id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资Id")
    private Long itemId;

    /**
    * register_detail_id
    * 采购详情id
    */
    @ApiModelProperty(value="采购详情id")
    @TableField(value="register_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "采购详情id")
    private Long registerDetailId;

    /**
    * supplier_id
    * 供应商id
    */
    @ApiModelProperty(value="供应商id")
    @TableField(value="supplier_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "供应商id")
    private Long supplierId;

    /**
    * contract_id
    * 合同id
    */
    @ApiModelProperty(value="合同id")
    @TableField(value="contract_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "合同id")
    private Long contractId;

}
