package com.netwisd.base.log.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @ApiModelProperty(value = "开始创建时间")
    private LocalDateTime startCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private LocalDateTime endCreateTime;

    @ApiModelProperty(value = "显示条目数")
    private Integer size;

}

