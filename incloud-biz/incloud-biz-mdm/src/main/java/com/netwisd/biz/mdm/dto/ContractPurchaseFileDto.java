package com.netwisd.biz.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 采购合同附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-08 10:22:57
 */
@Data
@ApiModel(value = "采购合同附件 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContractPurchaseFileDto extends IDto{

    public ContractPurchaseFileDto(Args args){
        super(args);
    }

    /**
     * contract_id
     * 合同id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
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
