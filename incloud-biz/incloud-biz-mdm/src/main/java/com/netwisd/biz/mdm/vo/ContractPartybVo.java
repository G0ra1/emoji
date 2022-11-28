package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 合同乙方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-30 11:09:06
 */
@Data
@ApiModel(value = "合同乙方 Vo")
public class ContractPartybVo extends IVo{

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
     * partyb_id
     * 乙方id
     */
    @ApiModelProperty(value="乙方id")
    private Long partybId;
    /**
     * partyb_code
     * 乙方编号
     */
    @ApiModelProperty(value="乙方编号")
    private String partybCode;
    /**
     * partyb_name
     * 乙方名称
     */
    @ApiModelProperty(value="乙方名称")
    private String partybName;
    /**
     * partyb_qualification
     * 乙方资质
     */
    @ApiModelProperty(value="乙方资质")
    private String partybQualification;
    /**
     * contract_type
     * 合同类型（0,采购合同;1,销售合同）
     */
    @ApiModelProperty(value="合同类型（0,采购合同;1,销售合同）")
    private Integer contractType;
    List<ContractPartybContactVo> partybContactList;
}
