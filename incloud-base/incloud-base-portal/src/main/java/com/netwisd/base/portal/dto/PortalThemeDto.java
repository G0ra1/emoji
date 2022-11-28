package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description   主题管理 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-18 23:20:45
 */
@Data
@ApiModel(value = "  主题管理 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalThemeDto extends IDto {

    public PortalThemeDto(Args args){
        super(args);
    }

    /**
     * topic_name
     * 主题名称
     */
    @ApiModelProperty(value="主题名称")
    @Valid(length = 32) 
    private String themeName;

    /**
     * is_use
     * 是否应用(0:否；1:是)
     */
    @ApiModelProperty(value="是否应用(0:否；1:是)")
    @Valid(length = 1) 
    private Integer isUse;

    /**
     * is_inside
     * 是否内置(0:否；1:是)
     */
    @ApiModelProperty(value="是否内置(0:否；1:是)")
    @Valid
    private Integer isInside;

    /**
     * topic_data
     * 主题样式数据
     */
    @ApiModelProperty(value="主题样式数据")
    @Valid
    private String themeData;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

}
