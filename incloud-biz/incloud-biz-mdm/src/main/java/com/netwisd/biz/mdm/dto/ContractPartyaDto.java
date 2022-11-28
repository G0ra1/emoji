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

import java.util.List;

/**
 * @Description 甲方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:34:54
 */
@Data
@ApiModel(value = "甲方 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContractPartyaDto extends IDto{

    public ContractPartyaDto(Args args){
        super(args);
    }

    /**
     * contract_id
     * 合同id
     */
    @ApiModelProperty(value="合同id")
    
    private Long contractId;

    /**
     * contract_code
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    
    private String contractCode;

    /**
     * partya_id
     * 甲方id
     */
    @ApiModelProperty(value="甲方id")
    
    private Long partyaId;

    /**
     * partya_code
     * 甲方编号
     */
    @ApiModelProperty(value="甲方编号")
    
    private String partyaCode;

    /**
     * partya_name
     * 甲方名称
     */
    @ApiModelProperty(value="甲方名称")
    
    private String partyaName;
    /**
     * partya_qualification
     * 甲方资质
     */
    @ApiModelProperty(value="甲方资质")
    private String partyaQualification;

    /**
     * contract_type
     * 合同类型（0,采购合同;1,销售合同）
     */
    @ApiModelProperty(value="合同类型（0,采购合同;1,销售合同）")
    
    private Integer contractType;

    private List<ContractPartyaContactDto> partyaContactList;

}
