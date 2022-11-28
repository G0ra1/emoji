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
 * @Description app安装包更新记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-01-06 10:16:32
 */
@Data
@ApiModel(value = "app安装包更新记录表 Vo")
public class PortalAppUpdateMsgVo extends IVo{

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
