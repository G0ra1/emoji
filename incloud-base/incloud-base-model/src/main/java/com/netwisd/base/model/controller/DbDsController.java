package com.netwisd.base.model.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.RsaUtil;
import com.netwisd.base.model.dto.DbDsDto;
import com.netwisd.base.model.service.DbDsService;
import com.netwisd.base.model.vo.DbDsVo;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Api(value = "dbds", tags = "数据源Controller")
@Slf4j
@RestController
@AllArgsConstructor
@RefreshScope
@RequestMapping("/dbds")
public class DbDsController {

    private final DbDsService dbDsService;

    @ApiOperation(value = "新增数据源", notes = "新增数据源")
    @PostMapping
    public Result saveDbDs(@Validation(exclude = @ExcludeAnntation(vars = {"description"}))
                           @RequestBody DbDsDto dbDsDto) {
        return Result.success(dbDsService.saveDbDs(dbDsDto));
    }

    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    public Result<DbDsVo> getDbDs(@PathVariable("id") Long id) {
        return Result.success(dbDsService.getDbDs(id));
    }

    @PostMapping("/check")
    @ApiOperation("测试连接")
    public Result check(@Validation @RequestBody DbDsDto dbDsDto) {
        dbDsDto.setPassword(RsaUtil.handleSecret(dbDsDto.getPassword()));
        return Result.success(dbDsService.check(dbDsDto));
    }

    @ApiOperation(value = "修改数据源", notes = "修改数据源")
    @PutMapping
    public Result updateDbDs(@Validation @RequestBody DbDsDto dbDsDto) {
        return Result.success(dbDsService.updateDbDs(dbDsDto));
    }

    @ApiOperation(value = "通过id删除数据源", notes = "通过id删除数据源")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(dbDsService.deleteDbDs(id));
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/page")
    public Result<Page> page(@RequestBody DbDsDto dbDsDto) {
        return Result.success(dbDsService.page(dbDsDto));
    }

    @ApiOperation(value = "数据源列表", notes = "数据源列表")
    @GetMapping("/list")
    public Result<List> list(DbDsDto dbDsDto) {
        return Result.success(dbDsService.lists(dbDsDto));
    }

    @GetMapping("/currentDs")
    @ApiOperation("获取当前所有数据源")
    public Result<Set<String>> currentDs() {
        Set<String> set = dbDsService.currentDs();
        return Result.success(set);
    }
}
