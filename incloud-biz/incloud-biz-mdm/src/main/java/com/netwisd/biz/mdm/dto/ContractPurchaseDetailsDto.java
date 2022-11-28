package com.netwisd.biz.mdm.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * @Description 采购合同清单 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:49:25
 */
@Data
@ApiModel(value = "采购合同清单 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContractPurchaseDetailsDto extends IDto{

    public ContractPurchaseDetailsDto(Args args){
        super(args);
    }

    /**
     * material_id
     * 物料id
     */
    @ApiModelProperty(value="物料id")
    
    private String materialId;

    /**
     * contract_id
     * 合同ID
     */
    @ApiModelProperty(value="合同ID")
    
    private String contractId;

    /**
     *  contract_code
     * 合同编码
     */
    @ApiModelProperty(value="合同编码")

    private String contractCode;

    /**
     * contract_name
     * 合同名称
     */
    @ApiModelProperty(value="合同名称")
    
    private String contractName;

    /**
     * material_code
     * 物料编码
     */
    @ApiModelProperty(value="物料编码")
    
    private String materialCode;

    /**
     * material_name
     * 物料名称
     */
    @ApiModelProperty(value="物料名称")
    
    private String materialName;

    /**
     * specification
     * 规格型号
     */
    @ApiModelProperty(value="规格型号")
    
    private String specification;

    /**
     * unit
     * 单位
     */
    @ApiModelProperty(value="单位")
    
    private String unit;

    /**
     * plan_quantity
     * 合同数量
     */
    @ApiModelProperty(value="合同数量")
    
    private String planQuantity;

    /**
     * texture
     * 材质
     */
    @ApiModelProperty(value="材质")
    
    private String texture;

    /**
     * price
     * 含税单价
     */
    @ApiModelProperty(value="含税单价")
    
    private BigDecimal price;

    /**
     * total_price
     * 含税总价
     */
    @ApiModelProperty(value="含税总价")
    
    private BigDecimal totalPrice;

    /**
     * cost
     * 不含税总价
     */
    @ApiModelProperty(value="不含税总价")
    
    private BigDecimal cost;

    /**
     * tax
     * 总税金
     */
    @ApiModelProperty(value="总税金")
    
    private BigDecimal tax;

    /**
     * invoice_type
     * 发票类型 0：增值税专用发票，1：增值税普通发票
     */
    @ApiModelProperty(value="发票类型 0：增值税专用发票，1：增值税普通发票")
    
    private String invoiceType;

    /**
     * tax_rate
     * 税率，1-100的正整数
     */
    @ApiModelProperty(value="税率，1-100的正整数")
    
    private String taxRate;

    /**
     * explain
     * 报价备注
     */
    @ApiModelProperty(value="报价备注")
    
    private String priExplain;

    /**
     * org_id
     * 组织ID
     */
    @ApiModelProperty(value="组织ID")
    
    private String orgId;

    /**
     * org_name
     * 组织名称
     */
    @ApiModelProperty(value="组织名称")
    
    private String orgName;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

    /**
     * external_id
     * 外部ID
     */
    @ApiModelProperty(value="外部ID")
    
    private String externalId;

    /**
     * technical_norms
     * 技术标准
     */
    @ApiModelProperty(value="技术标准")
    
    private String technicalNorms;

    /**
     * quality_level
     * 质保等级
     */
    @ApiModelProperty(value="质保等级")
    
    private String qualityLevel;

    /**
     * demand_num
     * 计划编号
     */
    @ApiModelProperty(value="计划编号")
    
    private String demandNum;

    /**
     * part
     * 使用部位
     */
    @ApiModelProperty(value="使用部位")
    
    private String part;

    /**
     *  data_source_id
     * 数据源id
     */
    @ApiModelProperty(value="数据源id")
    
    private String  dataSourceId;

    /**
     * project_id
     * 项目ID
     */
    @ApiModelProperty(value="项目ID")
    private String projectId;
    /**
     * project_code
     * 项目编码
     */
    @ApiModelProperty(value="项目编码")
    private String projectCode;
    /**
     * project_name
     * 项目名称
     */
    @ApiModelProperty(value="项目名称")
    private String projectName;

    /**
     * sourceProjectId
     * 数据源项目ID
     */
    @ApiModelProperty(value="数据源项目ID")
    private String sourceProjectId;

    /**
     * sourceOrgId
     * 数据源组织ID
     */
    @ApiModelProperty(value="数据源组织ID")
    private String sourceOrgId;

}
