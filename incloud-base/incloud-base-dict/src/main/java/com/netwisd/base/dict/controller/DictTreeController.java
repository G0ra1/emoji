package com.netwisd.base.dict.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.dict.service.DictTreeService;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.anntation.IncludeAnntation;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.log.annotation.SysLog;
import com.netwisd.common.log.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/dictTree")
@Api(value = "DictTree", tags = "字典树形Controller")
public class DictTreeController {

    private final DictTreeService dictTreeService;

    @SysLog(value = "树形字典分页查询")
    @PostMapping("/page")
    @ApiOperation(value = "分页查询")
    public Result<IPage> page(@RequestBody DictTreeDto dictTreeDto) {
        return Result.success(dictTreeService.queryPageList(dictTreeDto));
    }

    @SysLog(value = "获取树形子集")
    @GetMapping("/childList/{parentCode}")
    @ApiOperation(value = "获取树形子集")
    public Result<List<DictTreeVo>> childList(@PathVariable(name = "parentCode") String parentCode) {
        return Result.success(dictTreeService.childListByParentCode(parentCode));
    }

    @SysLog(value = "树形字典查询")
    @GetMapping("/list")
    @ApiOperation(value = "查询")
    public Result<List<DictTreeVo>> list(DictTreeDto dictTreeDto) {
        return Result.success(dictTreeService.list(dictTreeDto));
    }

    @SysLog(value = "树形字典新增", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping
    @ApiOperation(value = "新增")
    public Result add(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL), include = @IncludeAnntation(vars = {"id", "dictCode", "dictName"}))
                      @RequestBody DictTreeDto dictTreeDto) {
        return Result.success(dictTreeService.add(dictTreeDto));
    }

    @SysLog(value = "树形字典编辑", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @PutMapping
    @ApiOperation(value = "编辑")
    public Result edit(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL), include = @IncludeAnntation(vars = {"id"}))
                       @RequestBody DictTreeDto dictTreeDto) {
        return Result.success(dictTreeService.edit(dictTreeDto));
    }

    @SysLog(value = "树形字典获取详情")
    @GetMapping("/detailId/{id}")
    @ApiOperation(value = "获取详情")
    public Result<DictTreeVo> getDictTreeById(@PathVariable(name = "id") Long id) {
        return Result.success(dictTreeService.getDictTree(id, null));
    }

    @SysLog(value = "树形字典获取详情")
    @GetMapping("/detailCode/{code}")
    @ApiOperation(value = "获取详情")
    public Result<DictTreeVo> getDictTreeByCode(@PathVariable(name = "code") String code) {
        return Result.success(dictTreeService.getDictTree(null, code));
    }

    @SysLog(value = "树形字典删除", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除")
    public Result del(@PathVariable(name = "ids") String ids) {
        return Result.success(dictTreeService.del(ids));
    }

}
