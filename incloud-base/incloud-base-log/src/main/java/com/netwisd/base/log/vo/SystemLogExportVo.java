package com.netwisd.base.log.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SystemLogExportVo {

    @Excel(name = "应用名称", needMerge = true, orderNum = "0", width = 25)
    private String appName;

    @Excel(name = "日志类型", needMerge = true, orderNum = "1", width = 25)
    private String logTypeVo;

    @Excel(name = "操作类型", needMerge = true, orderNum = "2", width = 25)
    private String operateTypeVo;

    @Excel(name = "内容", needMerge = true, orderNum = "3", width = 25)
    private String content;

    @Excel(name = "请求IP", needMerge = true, orderNum = "4", width = 25)
    private String remoteAddr;

    @Excel(name = "浏览器类型", needMerge = true, orderNum = "5", width = 25)
    private String userAgent;

    @Excel(name = "请求URL", needMerge = true, orderNum = "6", width = 25)
    private String requestUri;

    @Excel(name = "请求方法", needMerge = true, orderNum = "7", width = 25)
    private String method;

    @Excel(name = "请求类型", needMerge = true, orderNum = "8", width = 25)
    private String requestType;

    @Excel(name = "参数", needMerge = true, orderNum = "9", width = 25)
    private String params;

    @Excel(name = "执行时间", needMerge = true, orderNum = "10", width = 25)
    private BigDecimal execTime;

    @Excel(name = "操作人", needMerge = true, orderNum = "11", width = 25)
    private String createUserName;

    @Excel(name = "操作时间", needMerge = true, orderNum = "12", width = 25)
    private String createTime;
}
