package com.netwisd.base.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 角色对应的功能权限子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-23 19:12:14
 */
@Data
@ApiModel(value = "角色对应的功能权限子系统 Vo")
public class MdmRoleSysVo extends IVo{

    /**
     * role_code
     * 角色code
     */
    
    @ApiModelProperty(value="角色code")
    private String roleCode;
    /**
     * role_id
     * 角色id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="角色id")
    private Long roleId;
    /**
     * sys_id
     * 子系统id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="子系统id")
    private Long sysId;
}
