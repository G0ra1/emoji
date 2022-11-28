package com.netwisd.base.dict.controller;

import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.dict.service.DictTreeVersionService;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/dictTreeVersion")
@Api(value = "DictTreeVersion", tags = "字典树形版本Controller")
public class DictTreeVersionController {

    private final DictTreeVersionService dictTreeVersionService;

    @SysLog(value = "字典版本查询")
    @GetMapping("/list")
    @ApiOperation(value = "字典版本查询")
    public Result<List<DictTreeVo>> list(@RequestParam(value = "dictTreeId", required = false) String dictTreeId) {
        return Result.success(dictTreeVersionService.list(Long.valueOf(dictTreeId)));
    }

    @SysLog(value = "字典版本删除")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "字典版本删除")
    public Result delDictVersion(@PathVariable(name = "ids") String ids) {
        dictTreeVersionService.delDictVersion(ids);
        return Result.success();
    }

    @SysLog(value = "切换版本")
    @GetMapping("/settingMainVersion")
    @ApiOperation(value = "切换版本")
    public Result settingMainVersion(@RequestParam(value = "id") String id) {
        dictTreeVersionService.settingMainVersion(id);
        return Result.success();
    }

}
