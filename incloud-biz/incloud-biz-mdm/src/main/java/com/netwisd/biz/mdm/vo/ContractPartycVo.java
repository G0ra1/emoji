package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 丙方 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-14 15:34:03
 */
@Data
@ApiModel(value = "丙方 Vo")
public class ContractPartycVo extends IVo{

    /**
     * contract_id
     * 合同id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="合同id")
    private Long contractId;
    /**
     * contract_code
     * 合同编号
     */
    
    @ApiModelProperty(value="合同编号")
    private String contractCode;
    /**
     * partyc_id
     * 丙方id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="丙方id")
    private Long partycId;
    /**
     * partyc_code
     * 丙方编号
     */
    
    @ApiModelProperty(value="丙方编号")
    private String partycCode;
    /**
     * partyc_name
     * 丙方名称
     */
    
    @ApiModelProperty(value="丙方名称")
    private String partycName;
    /**
     * partyc_qualification
     * 丙方资质
     */
    
    @ApiModelProperty(value="丙方资质")
    private String partycQualification;
    /**
     * contract_type
     * 合同类型（0,采购合同;1,销售合同）
     */
    
    @ApiModelProperty(value="合同类型（0,采购合同;1,销售合同）")
    private Integer contractType;
    /**
     * create_user_id
     * 创建人ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    
    @ApiModelProperty(value="创建人名称")
    private String createUserName;
    /**
     * parent_org_id
     * 父级机构ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="父级机构ID")
    private Long parentOrgId;
    /**
     * parent_org_name
     * 父级机构名称
     */
    
    @ApiModelProperty(value="父级机构名称")
    private String parentOrgName;
    /**
     * parent_dept_id
     * 父级部门ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="父级部门ID")
    private Long parentDeptId;
    /**
     * parent_dept_name
     * 父级部门名称
     */
    
    @ApiModelProperty(value="父级部门名称")
    private String parentDeptName;
    /**
     * org_full_id
     * 父级组织全路径ID
     */
    
    @ApiModelProperty(value="父级组织全路径ID")
    private String orgFullId;

    List<ContractPartycContactVo> partycContactList;
}
