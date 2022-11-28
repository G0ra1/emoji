package com.netwisd.base.common.mdm.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 角色与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:44:05
 */
@Data
@ApiModel(value = "角色与用户关系 Vo")
public class MdmRoleUserVo extends IVo{

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
     * user_id
     * 用户ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="用户ID")
    private Long userId;
    /**
     * user_name
     * 用户名称
     */
    
    @ApiModelProperty(value="用户名称")
    private String userName;
    /**
     * user_name_ch
     * 用户中文名称
     */
    
    @ApiModelProperty(value="用户中文名称")
    private String userNameCh;

    /**
     * orgId
     * 机构id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="orgId")
    private Long orgId;
}
