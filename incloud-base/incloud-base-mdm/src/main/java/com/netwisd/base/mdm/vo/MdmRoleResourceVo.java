package com.netwisd.base.mdm.vo;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 角色与资源关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-17 17:27:09
 */
@Data
@ApiModel(value = "角色与资源关系 Vo")
public class MdmRoleResourceVo extends IVo{

    /**
     * role_id
     * 角色ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="角色ID")
    private Long roleId;
    /**
     * resource_id
     * 资源ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="资源ID")
    private Long resourceId;
    /**
     * role_code
     * 角色Code
     */
    @ApiModelProperty(value="角色Code")
    private String roleCode;
    /**
     * resource_ids
     * 资源IDs
     */
    @Valid(length = 4000)
    @ApiModelProperty(value="资源IDs")
    private String resourceIds;
}
