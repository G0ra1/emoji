package com.netwisd.base.common.task.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class SysJobDto {

    @ApiModelProperty(value = "子系统")
    private String bizSysCodes;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务组名：DEFAULT、SYSTEM")
    private String jobGroup;

    @ApiModelProperty(value = "目标类型")
    private String invokeTargetType;

    @ApiModelProperty(value = "调用目标")
    private String invokeTarget;

    @ApiModelProperty(value = "cron执行表达式")
    private String cronExpression;

    @ApiModelProperty(value = "补偿策略-1、1、2")
    private Integer misfirePolicy;

    @ApiModelProperty(value = "并发执行0=允许,1=禁止")
    private Integer concurrent;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "调度过期天数清除")
    private Integer jobExpiredDayClean;

    @ApiModelProperty(value = "调度日志过期天数清除")
    private Integer jobLogExpiredDayClean;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "任务状态（0正常 1暂停）")
    private Integer status;

    private Long jobLogId;

    public Long logStartTime;

    public Long logEndTime;

}