package com.netwisd.biz.mdm.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * @Description 采购合同详情数据传送 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:33:08
 */
@Data
@ApiModel(value = "采购合同详情数据传送 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContractPurchaseDetailTransmit {
    private String id;
    private String extdata;
    private String materialid;
    private String materialcode;
    private String materialname;
    private String specification;
    private String unit;
    private String planquantity;
    private String texture;
    private String price;
    private String totalprice;
    private String invoicetype;
    private String taxrate;
    private String tax;
    private String cost;
    private String explain;
    private String contractid;
    private String contractname;
    private String projectid;
    private String projectname;
    private String orgnizationid;
    private String orgname;
    private String remark;
    private String externalid;
    private String technicalnorms;
    private String qualitylevel;
    private String demandnum;
    private String part;


    public ContractPurchaseDetailsDto toPurchaseDetailDto() throws java.text.ParseException{
        ContractPurchaseDetailsDto dto=new ContractPurchaseDetailsDto();
        dto.setDataSourceId(this.getId());
        dto.setMaterialId(this.getMaterialid());
        dto.setMaterialCode(this.getMaterialcode());
        dto.setMaterialName(this.getMaterialname());
        dto.setContractName(this.getContractname());
        dto.setSpecification(this.getSpecification());
        dto.setUnit(this.getUnit());
        dto.setPlanQuantity(this.getPlanquantity());
        dto.setTexture(this.getTexture());
        if(StringUtils.isNotEmpty(this.getPrice()))
            dto.setPrice(new BigDecimal(this.getPrice()));
        if(StringUtils.isNotEmpty(this.getTotalprice()))
            dto.setTotalPrice(new BigDecimal(this.getTotalprice()));
        dto.setInvoiceType(this.getInvoicetype());
        dto.setTaxRate(this.getTaxrate());
        if(StringUtils.isNotEmpty(this.getTax()))
            dto.setTax(new BigDecimal(this.getTax()));
        if(StringUtils.isNotEmpty(this.getCost()))
            dto.setCost(new BigDecimal(this.getCost()));
        dto.setSourceOrgId(this.getOrgnizationid());
        dto.setOrgName(this.getOrgname());
        dto.setSourceProjectId(this.getProjectid());
        dto.setProjectName(this.getProjectname());
        dto.setRemark(this.getRemark());
        dto.setExternalId(this.getExternalid());
        dto.setQualityLevel(this.getQualitylevel());
        dto.setDemandNum(this.getDemandnum());
        dto.setPart(this.getPart());
        return dto;
    }


}
