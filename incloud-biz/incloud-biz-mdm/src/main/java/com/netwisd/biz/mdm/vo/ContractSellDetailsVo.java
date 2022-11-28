package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 销售合同清单 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:09:22
 */
@Data
@ApiModel(value = "销售合同清单 Vo")
public class ContractSellDetailsVo extends IVo{

    /**
     * material_id
     * 物料id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="物料id")
    private Long materialId;
    /**
     * contract_id
     * 合同ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="合同ID")
    private Long contractId;
    /**
     * contract_code
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
    private BigDecimal taxRate;
}
