package com.netwisd.base.log.dto;

import com.netwisd.common.es.config.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class SystemLogDto {

    @ApiModelProperty(value = "搜索关键字")
    private String keyWords;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "日志类型")
    private String logType;

    @ApiModelProperty(value = "操作类型")
    private String operateType;

    @ApiModelProperty(value = "请求方式")
    private String requestType;

    @ApiModelProperty(value = "开始创建时间")
    private LocalDateTime startCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private LocalDateTime endCreateTime;

    @ApiModelProperty(value = "开始执行秒数")
    private Long startExecTime;

    @ApiModelProperty(value = "结束执行秒数")
    private Long endExecTime;

    private Page page = new Page();
}
