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
 * @Description app安装包更新记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-01-06 10:16:32
 */
@Data
@ApiModel(value = "app安装包更新记录表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalAppUpdateMsgDto extends IDto{

    public PortalAppUpdateMsgDto(Args args){
        super(args);
    }
    /**
     * app_id
     * appid
     */
    @ApiModelProperty(value="appid")
    private String appId;

    /**
     * app_name
     * app名称
     */
    @ApiModelProperty(value="app名称")
    private String appName;

    /**
     * app_type
     * app渠道 
     */
    @ApiModelProperty(value="app渠道 ")
    private String appType;

    /**
     * app_version
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private String appVersion;

    /**
     * download_url
     * 下载地址
     */
    @ApiModelProperty(value="下载地址")
    private String downloadUrl;

    /**
     * update_log
     * 版本变更说明
     */
    @ApiModelProperty(value="版本变更说明")
    private String updateLog;

    /**
     * update_install
     * 是否强制升级 0 否 1 是
     */
    @ApiModelProperty(value="是否强制升级 0 否 1 是")
    private Integer updateInstall;

}
