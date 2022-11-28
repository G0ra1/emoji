package com.netwisd.base.mdm.dto;

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
 * @Description 角色对应的功能权限子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-23 19:12:14
 */
@Data
@ApiModel(value = "角色对应的功能权限子系统 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmRoleSysDto extends IDto{

    public MdmRoleSysDto(Args args){
        super(args);
    }

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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="角色id")
    private Long roleId;

    /**
     * sys_id
     * 子系统id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="子系统id")
    private Long sysId;

}
