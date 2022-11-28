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
 * @Description 角色与岗位关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:19:56
 */
@Data
@ApiModel(value = "角色与岗位关系 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmRolePostDto extends IDto{

    public MdmRolePostDto(Args args){
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
     * post_id
     * 岗位ref的ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="岗位的ID")
    private Long postId;

    /**
     * post_code
     * 岗位的code
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="岗位的code")
    private String postCode;

    /**
     * post_name
     * 岗位的名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="岗位的名称")
    private String postName;

}
