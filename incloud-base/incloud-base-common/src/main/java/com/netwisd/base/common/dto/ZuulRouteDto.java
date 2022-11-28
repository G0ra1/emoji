package com.netwisd.base.common.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 动态路由 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-03-08 10:59:22
 */
@Data
@ApiModel(value = "动态路由 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ZuulRouteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * 主键
     */
    @ApiModelProperty(value="主键"  )
    private String id;

    /**
     * path
     * 
     */
    @ApiModelProperty( value="path" )
    private String path;

    /**
     * serviceId
     * 
     */
    @ApiModelProperty( value="serviceId" )
    private String serviceid;

    /**
     * url
     * 
     */
    @ApiModelProperty( value="url" )
    private String url;

    /**
     * strip_prefix
     * 
     */
    @ApiModelProperty( value="strip_prefix" )
    private Boolean stripPrefix;

    /**
     * retryable
     * 
     */
    @ApiModelProperty( value="retryable" )
    private Boolean retryable;

    /**
     * apiName
     * 
     */
    @ApiModelProperty( value="apiName" )
    private String apiname;

    /**
     * enabled
     * 
     */
    @ApiModelProperty( value="enabled" )
    private Boolean enabled;

    /**
     * page对象，用于页面传参，给个默认值
     * 一般传current（默认1），size（默认10）
     */
    @ApiModelProperty( value="page" )
    public Page page = new Page();

}
