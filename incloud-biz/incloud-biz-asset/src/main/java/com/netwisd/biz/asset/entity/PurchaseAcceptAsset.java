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
 * @Description $验收明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-14 10:39:29
 */
@Data
@Table(value = "incloud_biz_asset_purchase_accept_asset",comment = "验收明细")
@TableName("incloud_biz_asset_purchase_accept_asset")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "验收明细 Entity")
public class PurchaseAcceptAsset extends IModel<PurchaseAcceptAsset> {

    /**
     * acceptance_id
     * 验收id
     */
    @ApiModelProperty(value="验收id")
    @TableField(value="acceptance_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_purchase_accept" ,fkFieldName = "id" , isNull = true, comment = "验收id")
    private Long acceptanceId;
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
     * purchase_type
     * 物资购置类型
     */
    @ApiModelProperty(value="物资购置类型")
    @TableField(value="purchase_type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "物资购置类型")
    private Integer purchaseType;
    /**
     * sku_code
     * sku属性
     */
    @ApiModelProperty(value="sku属性")
    @TableField(value="sku_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "sku属性")
    private String skuCode;
    /**
     * sku_full_id
     * sku全路径id
     */
    @ApiModelProperty(value="sku全路径id")
    @TableField(value="sku_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "sku全路径id")
    private String skuFullId;
    /**
     * sku_full_name
     * sku全路径名称
     */
    @ApiModelProperty(value="sku全路径名称")
    @TableField(value="sku_full_name")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "sku全路径名称")
    private String skuFullName;
    /**
     * register_id
     * 采购登记Id
     */
    @ApiModelProperty(value="采购登记Id")
    @TableField(value="register_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "采购登记Id")
    private Long registerId;
    /**
     * register_code
     * 采购登记编号
     */
    @ApiModelProperty(value="采购登记编号")
    @TableField(value="register_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "采购登记编号")
    private String registerCode;
    /**
     * register_number
     * 采购数量
     */
    @ApiModelProperty(value="采购数量")
    @TableField(value="register_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "采购数量")
    private BigDecimal registerNumber;
    /**
     * acceptance_tax_rate
     * 验收税率
     */
    @ApiModelProperty(value="验收税率")
    @TableField(value="acceptance_tax_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "验收税率")
    private BigDecimal acceptanceTaxRate;
    /**
     * acceptance_amount
     * 验收单价
     */
    @ApiModelProperty(value="验收单价")
    @TableField(value="acceptance_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "验收单价")
    private BigDecimal acceptanceAmount;
    /**
     * acceptance_untaxed_amount
     * 验收单价-未税
     */
    @ApiModelProperty(value="验收单价-未税")
    @TableField(value="acceptance_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "验收单价-未税")
    private BigDecimal acceptanceUntaxedAmount;
    /**
     * acceptance_tax_amount
     * 验收单价-税额
     */
    @ApiModelProperty(value="验收单价-税额")
    @TableField(value="acceptance_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "验收单价-税额")
    private BigDecimal acceptanceTaxAmount;
    /**
     * acceptance_sum_amount
     * 验收总价
     */
    @ApiModelProperty(value="验收总价")
    @TableField(value="acceptance_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "验收总价")
    private BigDecimal acceptanceSumAmount;
    /**
     * acceptance_sum_untaxed_amount
     * 验收总价-未税
     */
    @ApiModelProperty(value="验收总价-未税")
    @TableField(value="acceptance_sum_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "验收总价-未税")
    private BigDecimal acceptanceSumUntaxedAmount;
    /**
     * acceptance_sum_tax_amount
     * 验收总价-税额
     */
    @ApiModelProperty(value="验收总价-税额")
    @TableField(value="acceptance_sum_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "验收总价-税额")
    private BigDecimal acceptanceSumTaxAmount;
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
     * acceptance_code
     * 验收编码
     */
    @ApiModelProperty(value="验收编码")
    @TableField(value="acceptance_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "验收编码")
    private String acceptanceCode;
    /**
     * register_result_id
     * 购置结果表id
     */
    @ApiModelProperty(value="购置结果表id")
    @TableField(value="register_result_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "购置结果表id")
    private Long registerResultId;
    /**
     * item_id
     * 物资Id
     */
    @ApiModelProperty(value="物资Id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资Id")
    private Long itemId;
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

    /**
     * exterior_check
     * 外观检查
     */
    @ApiModelProperty(value="外观检查")
    @TableField(value="exterior_check")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "外观检查")
    private String exteriorCheck;
    /**
     * quality_acceptance
     * 质量验收
     */
    @ApiModelProperty(value="质量验收")
    @TableField(value="quality_acceptance")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "质量验收")
    private String qualityAcceptance;
    /**
     * document
     * 文件资料
     */
    @ApiModelProperty(value="文件资料")
    @TableField(value="document")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "文件资料")
    private String document;
    /**
     * status
     * 验收状态
     */
    @ApiModelProperty(value="验收状态")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "验收状态")
    private Integer status;
    /**
     * file_ids
     * 附件ids
     */
    @ApiModelProperty(value="附件ids")
    @TableField(value="file_ids")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "附件ids")
    private String fileIds;
}
