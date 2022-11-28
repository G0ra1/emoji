package com.netwisd.base.mdm.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
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
public class MdmRolePostUserDto extends IDto{

    public MdmRolePostUserDto(Args args){
        super(args);
    }

    /**
     * role_id
     * 角色编码
     */

    @Valid(length = 255)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="角色编码")
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
     * userIds
     * 人员相关的id
     */

    @ApiModelProperty(value="人员相关的id")
    private String userIds;

    /**
     * postIds
     * 岗位相关的id
     */

    @ApiModelProperty(value="岗位相关的id")
    private String postIds;

}
