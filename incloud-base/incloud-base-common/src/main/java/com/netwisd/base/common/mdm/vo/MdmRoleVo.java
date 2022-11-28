package com.netwisd.base.common.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 角色 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 18:05:58
 */
@Data
@ApiModel(value = "角色 Vo")
public class MdmRoleVo extends IVo{

    /**
     * sys_id
     * 子系统ID
     */
//    @ApiModelProperty(value="子系统ID")
//    private String sysId;
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
     * role_type
     * 角色类型
     */
    
    @ApiModelProperty(value="角色类型")
    private Integer roleType;
    /**
     * sort
     * 排序字段
     */
    
    @ApiModelProperty(value="排序字段")
    private Integer sort;
    /**
     * status
     * 状态标识
     */
    
    @ApiModelProperty(value="状态标识")
    private Integer status;

    /**
     * rolePosts
     * 角色和岗位关系
     */
    @ApiModelProperty(value="角色和岗位关系")
    private List<MdmRolePostVo> rolePosts;

    /**
     * roleUsers
     * 角色和用户关系
     */
    @ApiModelProperty(value="角色和用户关系")
    private List<MdmRoleUserVo> roleUsers;
}
