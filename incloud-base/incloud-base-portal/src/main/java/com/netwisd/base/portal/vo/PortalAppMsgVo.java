package com.netwisd.base.portal.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 应用市场app信息表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-12-23 15:07:50
 */
@Data
@ApiModel(value = "应用市场app信息表 Vo")
public class PortalAppMsgVo extends IVo{

    /**
     * bundle_id
     * 
     */
    
    @ApiModelProperty(value="bundle_id")
    private String bundleId;
    /**
     * version
     * 
     */
    
    @ApiModelProperty(value="version")
    private String version;
    /**
     * platform
     * 
     */
    
    @ApiModelProperty(value="platform")
    private String platform;
    /**
     * market_name
     * 
     */
    
    @ApiModelProperty(value="market_name")
    private String marketName;
    /**
     * state
     * 
     */
    
    @ApiModelProperty(value="state")
    private String state;
    /**
     * url
     *
     */

    @ApiModelProperty(value="url")
    private String url;
}
