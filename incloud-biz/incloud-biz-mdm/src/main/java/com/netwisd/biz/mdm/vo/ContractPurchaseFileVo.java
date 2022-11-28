package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 采购合同附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-08 10:22:57
 */
@Data
@ApiModel(value = "采购合同附件 Vo")
public class ContractPurchaseFileVo extends IVo{

    /**
     * contract_id
     * 合同id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="合同id")
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
     * file_name
     * 附件名称
     */
    
    @ApiModelProperty(value="附件名称")
    private String fileName;
    /**
     * file_url
     * 附件地址
     */
    
    @ApiModelProperty(value="附件地址")
    private String fileUrl;
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
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="创建人父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    
    @ApiModelProperty(value="创建人父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="创建人父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    
    @ApiModelProperty(value="创建人父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    
    @ApiModelProperty(value="创建人父级组织全路径ID")
    private String createUserOrgFullId;
}
