package com.netwisd.base.portal.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description   主题管理 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-18 23:20:45
 */
@Data
@ApiModel(value = "  主题管理 Vo")
public class PortalThemeVo extends IVo{

    /**
     * topic_name
     * 主题名称
     */
    @ApiModelProperty(value="主题名称")
    private String themeName;
    /**
     * is_use
     * 是否应用(0:否；1:是)
     */
    @ApiModelProperty(value="是否应用(0:否；1:是)")
    private Integer isUse;
    /**
     * is_inside
     * 是否内置(0:否；1:是)
     */
    @ApiModelProperty(value="是否内置(0:否；1:是)")
    private Integer isInside;
    /**
     * topic_data
     * 主题样式数据
     */
    @ApiModelProperty(value="主题样式数据")
    private String themeData;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
}
