package com.netwisd.base.log.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.bean.BeanUtil;
import com.netwisd.base.log.dto.ApplicationLogDto;
import com.netwisd.base.log.dto.SystemLogDto;
import com.netwisd.base.log.service.ApplicationLogService;
import com.netwisd.base.log.service.SystemLogService;
import com.netwisd.base.log.util.EasyExcelUtils;
import com.netwisd.base.log.vo.SystemLogExportVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.es.config.Page;
import com.netwisd.common.es.vo.ElasticVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "Log", tags = "日志Controller")
public class LogController {

    @Autowired
    private SystemLogService systemLogService;

    @Autowired
    private ApplicationLogService applicationLogService;

    @PostMapping("/sysLog/page")
    @ApiOperation(value = "系统操作日志分页查询")
    public Result<Page<ElasticVo>> sysLogPage(@RequestBody SystemLogDto systemLogDto) {
        return Result.success(systemLogService.searchSystemLogPage(systemLogDto));
    }

    @PostMapping("/applicationLog/list")
    @ApiOperation(value = "系统应用日志查询")
    public Result<List<ElasticVo>> applicationLog(@RequestBody ApplicationLogDto applicationLogDto) {
        return Result.success(applicationLogService.searchApplicationLog(applicationLogDto));
    }

    @GetMapping("/sysLog/export")
    @ApiOperation(value = "系统操作日志导出")
    public void sysLogExport(@RequestParam(value = "appName", required = false) String appName,
                             @RequestParam(value = "keyWords", required = false) String keyWords,
                             @RequestParam(value = "logType", required = false) String logType,
                             @RequestParam(value = "operateType", required = false) String operateType,
                             @RequestParam(value = "requestType", required = false) String requestType,
                             @RequestParam(value = "startCreateTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startCreateTime,
                             @RequestParam(value = "endCreateTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endCreateTime,
                             @RequestParam(value = "startExecTime", required = false) Long startExecTime,
                             @RequestParam(value = "endExecTime", required = false) Long endExecTime,
                             HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.HSSF);
        SystemLogDto systemLogDto = new SystemLogDto();
        systemLogDto.setAppName(appName);
        systemLogDto.setKeyWords(keyWords);
        systemLogDto.setLogType(logType);
        systemLogDto.setOperateType(operateType);
        systemLogDto.setRequestType(requestType);
        systemLogDto.setStartCreateTime(startCreateTime);
        systemLogDto.setEndCreateTime(endCreateTime);
        systemLogDto.setStartExecTime(startExecTime);
        systemLogDto.setEndExecTime(endExecTime);
        List<SystemLogExportVo> result = systemLogService.searchSystemLog(systemLogDto).stream().map(x -> BeanUtil.fillBeanWithMap(x.getData(), new SystemLogExportVo(), true)).collect(Collectors.toList());
        EasyExcelUtils.exportExcel(result, SystemLogExportVo.class, "系统操作日志", exportParams, response);
    }
}
