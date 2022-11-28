package com.netwisd.base.dict.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.base.dict.dto.DictDto;
import com.netwisd.base.dict.dto.DictItemDto;
import com.netwisd.base.dict.service.DictService;
import com.netwisd.base.dict.vo.DictItemVo;
import com.netwisd.base.dict.vo.DictVo;
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
@Api(value = "Dict", tags = "字典Controller")
public class DictController {

    private final DictService dictService;

    @SysLog(value = "字典分页查询")
    @PostMapping("/dictType/page")
    @ApiOperation(value = "分页查询")
    public Result<IPage> page(@RequestBody DictDto dictDto) {
        return Result.success(dictService.queryPageList(dictDto));
    }

    @SysLog(value = "字典新增", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping("/dictType")
    @ApiOperation(value = "新增")
    public Result addDictType(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL), include = @IncludeAnntation(vars = {"dictCode", "dictName"}))
                              @RequestBody DictDto dictDto) {
        return Result.success(dictService.addDictType(dictDto));
    }

    @SysLog(value = "字典编辑", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @PutMapping("/dictType")
    @ApiOperation(value = "编辑")
    public Result editDictType(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL), include = @IncludeAnntation(vars = {"id"}))
                               @RequestBody DictDto dictDto) {
        return Result.success(dictService.editDictType(dictDto));
    }

    @SysLog(value = "字典获取详情")
    @GetMapping("/dictType/detailId/{id}")
    @ApiOperation(value = "获取详情")
    public Result<DictVo> getDictTypeById(@PathVariable(name = "id") Long id) {
        return Result.success(dictService.getDictType(id, null));
    }

    @SysLog(value = "字典获取详情")
    @GetMapping("/dictType/detailCode/{code}")
    @ApiOperation(value = "获取详情")
    public Result<DictVo> getDictTypeByCode(@PathVariable(name = "code") String code) {
        return Result.success(dictService.getDictType(null, code));
    }

    @SysLog(value = "字典删除", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/dictType/{ids}")
    @ApiOperation(value = "删除")
    public Result delDictType(@PathVariable(name = "ids") String ids) {
        return Result.success(dictService.delDictType(ids));
    }

    @SysLog(value = "字典项查询")
    @PostMapping("/dictItem/page")
    @ApiOperation(value = "字典项列表查询")
    public Result<IPage> page(@RequestBody DictItemDto dictItemDto) {
        return Result.success(dictService.dictItemPage(dictItemDto));
    }

    @SysLog(value = "字典项查询")
    @GetMapping("/dictItem/list")
    @ApiOperation(value = "字典项列表查询")
    public Result<List<DictItemVo>> list(DictItemDto dictItemDto) {
        return Result.success(dictService.dictItemList(dictItemDto));
    }

    @SysLog(value = "字典项新增", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping("/dictItem")
    @ApiOperation(value = "新增字典项")
    public Result addDictItem(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL), include = @IncludeAnntation(vars = {"dictItemCode", "dictItemName"}))
                              @RequestBody DictItemDto dictItemDto) {
        return Result.success(dictService.addDictItem(dictItemDto));
    }

    @SysLog(value = "字典项编辑", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @PutMapping("/dictItem")
    @ApiOperation(value = "编辑")
    public Result editDictItem(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL), include = @IncludeAnntation(vars = {"id"}))
                               @RequestBody DictItemDto dictItemDto) {
        return Result.success(dictService.editDictItem(dictItemDto));
    }

    @SysLog(value = "字典项详情")
    @GetMapping("/dictItem/detailId/{id}")
    @ApiOperation(value = "详情")
    public Result<DictItemVo> getDictItemById(@PathVariable(name = "id") Long id) {
        return Result.success(dictService.getDictItem(id, null));
    }

    @SysLog(value = "字典项详情")
    @GetMapping("/dictItem/detailCode/{code}")
    @ApiOperation(value = "详情")
    public Result<DictItemVo> getDictItemByCode(@PathVariable(name = "code") String code) {
        return Result.success(dictService.getDictItem(null, code));
    }

    @SysLog(value = "字典项删除", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/dictItem/{ids}")
    @ApiOperation(value = "删除")
    public Result delDictItem(@PathVariable(name = "ids") String ids) {
        return Result.success(dictService.delDictItem(ids));
    }

}
