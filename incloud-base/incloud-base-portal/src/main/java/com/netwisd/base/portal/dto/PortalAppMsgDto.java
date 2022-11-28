package com.netwisd.base.portal.dto;

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
 * @Description 应用市场app信息表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-12-23 15:07:50
 */
@Data
@ApiModel(value = "应用市场app信息表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalAppMsgDto extends IDto{

    public PortalAppMsgDto(Args args){
        super(args);
    }
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
