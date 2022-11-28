package com.netwisd.base.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
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
 * @Description 子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Data
@ApiModel(value = "子系统 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmSysDto extends IDto{

    public MdmSysDto(Args args){
        super(args);
    }

    /**
     * sys_code
     * 系统code
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="系统code")
    private String sysCode;

    /**
     * sys_name
     * 系统名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="系统名称")
    private String sysName;

    /**
     * sys_icon
     * 图标
     */
    
    
    @ApiModelProperty(value="图标")
    private String sysIcon;

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
