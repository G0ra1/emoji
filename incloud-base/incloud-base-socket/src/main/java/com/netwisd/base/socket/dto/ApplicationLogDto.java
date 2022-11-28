package com.netwisd.base.socket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationLogDto {

    @ApiModelProperty(value = "搜索关键字")
    private String keyword;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "日志IP地址")
    private String host;

    @ApiModelProperty(value = "日志级别")
    private String level;

    @ApiModelProperty(value = "是否开启实时推送")
    private boolean isOpen;

}

