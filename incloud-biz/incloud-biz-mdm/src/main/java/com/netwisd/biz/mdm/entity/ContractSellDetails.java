package com.netwisd.biz.mdm.entity;

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

/**
 * @Description $销售合同清单 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:09:22
 */
@Data
@Table(value = "incloud_biz_mdm_contract_sell_details",comment = "销售合同清单")
@TableName("incloud_biz_mdm_contract_sell_details")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "销售合同清单 Entity")
public class ContractSellDetails extends IModel<ContractSellDetails> {

    /**
     * material_id
     * 物料id
     */
    @ApiModelProperty(value="物料id")
    @TableField(value="material_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "物料id")
    private Long materialId;
    /**
     * contract_id
     * 合同ID
     */
    @ApiModelProperty(value="合同ID")
    @TableField(value="contract_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "合同ID")
    private Long contractId;
    /**
     * contract_code
     * 合同编码
     */
    @ApiModelProperty(value="合同编码")
    @TableField(value="contract_code")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "合同编码")
    private String contractCode;
    /**
     * contract_name
     * 合同名称
     */
    @ApiModelProperty(value="合同名称")
    @TableField(value="contract_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同名称")
    private String contractName;
    /**
     * material_code
     * 物料编码
     */
    @ApiModelProperty(value="物料编码")
    @TableField(value="material_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物料编码")
    private String materialCode;
    /**
     * material_name
     * 物料名称
     */
    @ApiModelProperty(value="物料名称")
    @TableField(value="material_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物料名称")
    private String materialName;
    /**
     * specification
     * 规格型号
     */
    @ApiModelProperty(value="规格型号")
    @TableField(value="specification")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "规格型号")
    private String specification;
    /**
     * unit
     * 单位
     */
    @ApiModelProperty(value="单位")
    @TableField(value="unit")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "单位")
    private String unit;
    /**
     * plan_quantity
     * 合同数量
     */
    @ApiModelProperty(value="合同数量")
    @TableField(value="plan_quantity")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同数量")
    private String planQuantity;
    /**
     * texture
     * 材质
     */
    @ApiModelProperty(value="材质")
    @TableField(value="texture")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "材质")
    private String texture;
    /**
     * price
     * 含税单价
     */
    @ApiModelProperty(value="含税单价")
    @TableField(value="price")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "含税单价")
    private BigDecimal price;
    /**
     * total_price
     * 含税总价
     */
    @ApiModelProperty(value="含税总价")
    @TableField(value="total_price")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "含税总价")
    private BigDecimal totalPrice;
    /**
     * cost
     * 不含税总价
     */
    @ApiModelProperty(value="不含税总价")
    @TableField(value="cost")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "不含税总价")
    private BigDecimal cost;
    /**
     * tax
     * 总税金
     */
    @ApiModelProperty(value="总税金")
    @TableField(value="tax")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "总税金")
    private BigDecimal tax;
    /**
     * invoice_type
     * 发票类型 0：增值税专用发票，1：增值税普通发票
     */
    @ApiModelProperty(value="发票类型 0：增值税专用发票，1：增值税普通发票")
    @TableField(value="invoice_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "发票类型 0：增值税专用发票，1：增值税普通发票")
    private String invoiceType;
    /**
     * tax_rate
     * 税率，1-100的正整数
     */
    @ApiModelProperty(value="税率，1-100的正整数")
    @TableField(value="tax_rate")
    @Column(type = DataType.DECIMAL, length = 3, precision = 2 , isNull = true, comment = "税率，1-100的正整数")
    private BigDecimal taxRate;
}
