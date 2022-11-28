package com.netwisd.base.common.mdm.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 角色与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:44:05
 */
@Data
@ApiModel(value = "角色与用户关系 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmRoleUserDto extends IDto{

    public MdmRoleUserDto(Args args){
        super(args);
    }

    /**
     * role_id
     * 角色ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="角色ID")
    private Long roleId;

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
     * user_id
     * 用户ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * user_name
     * 用户名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="用户名称")
    private String userName;

    /**
     * user_name_ch
     * 用户中文名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="用户中文名称")
    private String userNameCh;

}
