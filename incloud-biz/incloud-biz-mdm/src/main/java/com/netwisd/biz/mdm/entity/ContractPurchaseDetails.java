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
 * @Description $采购合同清单 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:49:25
 */
@Data
@Table(value = "incloud_biz_mdm_contract_purchase_details",comment = "采购合同清单")
@TableName("incloud_biz_mdm_contract_purchase_details")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "采购合同清单 Entity")
public class ContractPurchaseDetails extends IModel<ContractPurchaseDetails> {

    /**
     * material_id
     * 物料id
     */
    @ApiModelProperty(value="物料id")
    @TableField(value="material_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "物料id")
    private String materialId;
    /**
     * contract_id
     * 合同ID
     */
    @ApiModelProperty(value="合同ID")
    @TableField(value="contract_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "合同ID")
    private String contractId;
    /**
     *  contract_code
     * 合同编码
     */
    @ApiModelProperty(value="合同编码")
    @TableField(value=" contract_code")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "合同编码")
    private String  contractCode;
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
    @Column(type = DataType.VARCHAR, length = 1000,  isNull = true, comment = "规格型号")
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
    @Column(type = DataType.DECIMAL, length = 20, precision = 2 , isNull = true, comment = "含税单价")
    private BigDecimal price;
    /**
     * total_price
     * 含税总价
     */
    @ApiModelProperty(value="含税总价")
    @TableField(value="total_price")
    @Column(type = DataType.DECIMAL, length = 20, precision = 2 , isNull = true, comment = "含税总价")
    private BigDecimal totalPrice;
    /**
     * cost
     * 不含税总价
     */
    @ApiModelProperty(value="不含税总价")
    @TableField(value="cost")
    @Column(type = DataType.DECIMAL, length = 20, precision = 2 , isNull = true, comment = "不含税总价")
    private BigDecimal cost;
    /**
     * tax
     * 总税金
     */
    @ApiModelProperty(value="总税金")
    @TableField(value="tax")
    @Column(type = DataType.DECIMAL, length = 20, precision = 2 , isNull = true, comment = "总税金")
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
    @Column(type = DataType.VARCHAR, length = 3, isNull = true, comment = "税率，1-100的正整数")
    private String taxRate;
    /**
     * pri_explain
     * 报价备注
     */
    @ApiModelProperty(value="报价备注")
    @TableField(value="pri_explain")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "报价备注")
    private String priExplain;
    /**
     * org_id
     * 组织ID
     */
    @ApiModelProperty(value="组织ID")
    @TableField(value="org_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "组织ID")
    private String orgId;
    /**
     * org_code
     * 组织编码
     */
    @ApiModelProperty(value="组织编码")
    @TableField(value="org_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "组织编码")
    private String orgCode;
    /**
     * org_name
     * 组织名称
     */
    @ApiModelProperty(value="组织名称")
    @TableField(value="org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "组织名称")
    private String orgName;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 1000,  isNull = true, comment = "备注")
    private String remark;
    /**
     * external_id
     * 外部ID
     */
    @ApiModelProperty(value="外部ID")
    @TableField(value="external_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "外部ID")
    private String externalId;
    /**
     * technical_norms
     * 技术标准
     */
    @ApiModelProperty(value="技术标准")
    @TableField(value="technical_norms")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "技术标准")
    private String technicalNorms;
    /**
     * quality_level
     * 质保等级
     */
    @ApiModelProperty(value="质保等级")
    @TableField(value="quality_level")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "质保等级")
    private String qualityLevel;
    /**
     * demand_num
     * 计划编号
     */
    @ApiModelProperty(value="计划编号")
    @TableField(value="demand_num")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "计划编号")
    private String demandNum;
    /**
     * part
     * 使用部位
     */
    @ApiModelProperty(value="使用部位")
    @TableField(value="part")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "使用部位")
    private String part;
    /**
     *  data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    @TableField(value=" data_source_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "数据源id")
    private String  dataSourceId;

    /**
     * project_id
     * 项目ID
     */
    @ApiModelProperty(value="项目ID")
    @TableField(value="project_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "项目ID")
    private String projectId;

    /**
     * project_code
     * 项目编码
     */
    @ApiModelProperty(value="项目编码")
    @TableField(value="project_code")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "项目编码")
    private String projectCode;
    /**
     * project_name
     * 项目名称
     */
    @ApiModelProperty(value="项目名称")
    @TableField(value="project_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目名称")
    private String projectName;

    /**
     * source_project_id
     * 数据源项目ID
     */
    @ApiModelProperty(value="数据源项目ID")
    @TableField(value="source_project_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "数据源项目ID")
    private String sourceProjectId;
    /**
     * source_org_id
     * 数据源组织ID
     */
    @ApiModelProperty(value="数据源组织ID")
    @TableField(value="source_org_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "数据源组织ID")
    private String sourceOrgId;
}
