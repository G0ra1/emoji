package com.netwisd.base.common.mdm.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 角色与岗位关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:19:56
 */
@Data
@ApiModel(value = "角色与岗位关系 Vo")
public class MdmRolePostVo extends IVo{

    /**
     * role_id
     * 角色ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="角色ID")
    private Long roleId;
    /**
     * role_code
     * 角色编码
     */
    
    @ApiModelProperty(value="角色编码")
    private String roleCode;
    /**
     * role_name
     * 角色名称
     */
    
    @ApiModelProperty(value="角色名称")
    private String roleName;
    /**
     * post_id
     * 岗位的ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="岗位的ID")
    private Long postId;
    /**
     * post_code
     * 岗位的code
     */
    
    @ApiModelProperty(value="岗位的code")
    private String postCode;
    /**
     * post_name
     * 岗位的名称
     */
    
    @ApiModelProperty(value="岗位的名称")
    private String postName;
}
