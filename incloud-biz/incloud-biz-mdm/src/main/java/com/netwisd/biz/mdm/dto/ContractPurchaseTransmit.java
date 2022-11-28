package com.netwisd.biz.mdm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netwisd.base.common.data.IDto;
import com.netwisd.biz.mdm.constants.ContractType;
import com.netwisd.biz.mdm.entity.ContractPartya;
import com.netwisd.biz.mdm.entity.ContractPurchaseDetails;
import com.netwisd.biz.mdm.entity.ContractPurchaseExecution;
import com.netwisd.common.core.constants.Args;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Description 采购合同数据传送 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:33:08
 */
@Data
@ApiModel(value = "采购合同数据传送 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContractPurchaseTransmit{

    private String contractid;
    private String contractcode;
    private String contractname;
    private String type;
    private String state;
    private String contractprice;
    private String buyerid;
    private String buyername;
    private String supplierid;
    private String suppliername;
    private String contractdate;
    private String projectid;
    private String projectname;
    private String content;
    private String employeeid;
    private String employeename;
    private String objtype;
    private String orgid;
    private String orgname;
    private String orgcode;
    private String subject;
    private String createdate;
    private String updatedate;
    private String attachments;
    private String filename;
    private String fileurl;
    private String contractcategory;
    private String purchasetype;
    private String executeprice;
    private String approvaldate;
    private String framecontractid;
    private String projecttype;
    private String sourcecontractid;
    private String addround;
    private String signtype;
    private String executionscopeids;
    private String executionscopenames;
    private String executionscopecodes;


    public ContractPurchaseDto toContractPurchaseDto() throws java.text.ParseException{
        ContractPurchaseDto dto=new ContractPurchaseDto();
        dto.setDataSourceId(this.getContractid());
        dto.setContractCode(this.getContractcode());
        dto.setContractName(this.getContractname());
        dto.setType(this.getType());
        dto.setState(this.getState());
        if(StringUtils.isNotEmpty(this.getContractprice()))
            dto.setContractPrice(new BigDecimal(this.getContractprice()));
        if(StringUtils.isNotEmpty(this.getContractdate())){
            dto.setContractDate(new Date(Long.parseLong(this.getContractdate())*1000));
        }
        dto.setContractCategory(this.getContractcategory());
        dto.setSourceProjectId(this.getProjectid());
        dto.setProjectName(this.getProjectname());
        dto.setContent(this.getContent());
        dto.setEmployeeId(this.getEmployeeid());
        dto.setEmployeeName(this.getEmployeename());
        dto.setObjType(this.getObjtype());
        dto.setSourceOrgId(this.getOrgid());
        dto.setSourceOrgName(this.getOrgname());
        dto.setSourceOrgCode(this.getOrgcode());
        dto.setSubject(this.getSubject());
        if(StringUtils.isNotEmpty(this.getCreatedate()))
            dto.setCreateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(this.getCreatedate())*1000),  TimeZone.getDefault().toZoneId()));
        if(StringUtils.isNotEmpty(this.getUpdatedate()))
            dto.setUpdateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(this.getUpdatedate())*1000),  TimeZone.getDefault().toZoneId()));
        //附件
        dto.setFilename(this.getFilename());
        dto.setFileurl(this.getFileurl());

        //甲乙方
        dto.setPartyaId(this.getBuyerid());
        dto.setPartyaName(this.getBuyername());
        dto.setPartybId(this.getSupplierid());
        dto.setPartybName(this.getSuppliername());

        dto.setPurchaseType(this.getPurchasetype());
        if(StringUtils.isNotEmpty(this.getExecuteprice()))
            dto.setExecutePrice(new BigDecimal(this.getExecuteprice()));
        if(StringUtils.isNotEmpty(this.getApprovaldate())){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dto.setApprovalDate(simpleDateFormat.parse(this.getApprovaldate()));
        }
        dto.setFramecontractId(this.getFramecontractid());
        dto.setProjectType(this.getProjecttype());
        dto.setSourcecontractId(this.getSourcecontractid());
        dto.setAddround(this.getAddround());
        dto.setSigntype(this.getSigntype());
        dto.setExecutionscopeids(this.getExecutionscopeids());
        dto.setExecutionscopecodes(this.getExecutionscopecodes());
        dto.setExecutionscopenames(this.getExecutionscopenames());
        return dto;
    }

}
