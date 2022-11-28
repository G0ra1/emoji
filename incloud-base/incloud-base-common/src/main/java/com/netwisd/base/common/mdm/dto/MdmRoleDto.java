package com.netwisd.base.common.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 角色 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 18:05:58
 */
@Data
@ApiModel(value = "角色 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmRoleDto extends IDto{

    public MdmRoleDto(Args args){
        super(args);
    }

    /**
     * sys_id
     * 子系统ID
     */
//    @Valid(length = 500)
//    @ApiModelProperty(value="子系统ID")
//    private String sysId;

    /**
     * role_code
     * 角色编码
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="角色编码")
    private String roleCode;

    /**
     * role_name
     * 角色名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="角色名称")
    private String roleName;

    /**
     * role_type
     * 角色类型
     */
    
    @Valid(length = 11)
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
    
    @Valid(length = 2)
    @ApiModelProperty(value="状态标识")
    private Integer status;

}
