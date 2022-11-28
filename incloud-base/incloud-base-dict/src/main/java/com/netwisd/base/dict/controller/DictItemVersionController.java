package com.netwisd.base.dict.controller;

import com.netwisd.base.dict.service.DictItemVersionService;
import com.netwisd.base.dict.vo.DictItemVo;
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
@RequestMapping("/dictItemVersion")
@Api(value = "DictItemVersion", tags = "字典项目版本Controller")
public class DictItemVersionController {

    private final DictItemVersionService dictItemVersionService;

    @SysLog(value = "字典版本查询")
    @GetMapping("/list")
    @ApiOperation(value = "字典版本查询")
    public Result<List<DictItemVo>> list(@RequestParam(value = "dictItemId", required = false) String dictItemId) {
        return Result.success(dictItemVersionService.list(Long.valueOf(dictItemId)));
    }

    @SysLog(value = "字典版本删除")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "字典版本删除")
    public Result delDictVersion(@PathVariable(name = "ids") String ids) {
        dictItemVersionService.delDictItemVersion(ids);
        return Result.success();
    }

    @SysLog(value = "切换版本")
    @GetMapping("/settingMainVersion")
    @ApiOperation(value = "切换版本")
    public Result settingMainVersion(@RequestParam(value = "id") String id) {
        dictItemVersionService.settingMainVersion(id);
        return Result.success();
    }

}
