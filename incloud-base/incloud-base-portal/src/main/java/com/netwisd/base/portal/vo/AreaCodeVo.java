package com.netwisd.base.portal.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 地区编码 功能描述...
 * @author 云数网讯 lihongyu@netwisd.com
 * @date 2020-11-30 17:19:05
 */
@Data
@ApiModel(value = "地区编码 Vo")
public class AreaCodeVo extends IVo{

    /**
     * province_name
     * 省份
     */
    @ApiModelProperty(value="省份")
    private String provinceName;
    /**
     * city_name
     * 市
     */
    @ApiModelProperty(value="市")
    private String cityName;
    /**
     * area_code
     * 地区编码
     */
    @ApiModelProperty(value="地区编码")
    private String areaCode;
}
