package com.netwisd.base.mdm.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
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
 * @Description 角色与资源关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-17 17:27:09
 */
@Data
@ApiModel(value = "角色与资源关系 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmRoleResourceDto extends IDto{

    public MdmRoleResourceDto(Args args){
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
     * resource_id
     * 资源ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
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
    @ApiModelProperty(value="资源IDs")
    private String resourceIds;

}
