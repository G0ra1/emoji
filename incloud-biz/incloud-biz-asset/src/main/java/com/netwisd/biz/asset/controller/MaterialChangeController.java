package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialChangeDetailDto;
import com.netwisd.biz.asset.entity.MaterialChangeDetail;
import com.netwisd.biz.asset.vo.MaterialChangeDetailVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaterialChangeDto;
import com.netwisd.biz.asset.vo.MaterialChangeVo;
import com.netwisd.biz.asset.service.MaterialChangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 资产信息变更 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:11:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialChange" )
@Api(value = "materialChange", tags = "资产信息变更Controller")
@Slf4j
public class MaterialChangeController {

    private final  MaterialChangeService materialChangeService;

    /**
     * 分页查询资产信息变更
     * 没有使用参数注解，就是默认从form请求的方式
     * @param materialChangeDto 资产信息变更
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaterialChangeDto materialChangeDto) {
        Page pageVo = materialChangeService.list(materialChangeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产信息变更
     * @param materialChangeDto 资产信息变更
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaterialChangeDto materialChangeDto) {
        Page pageVo = materialChangeService.lists(materialChangeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产信息变更
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialChangeVo> get(@PathVariable("id" ) Long id) {
        MaterialChangeVo materialChangeVo = materialChangeService.get(id);
        log.debug("查询成功");
        return Result.success(materialChangeVo);
    }

    /**
     * 新增资产信息变更
     * @param materialChangeDto 资产信息变更
     * @return Result
     */
    @ApiOperation(value = "新增资产信息变更", notes = "新增资产信息变更")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialChangeDto materialChangeDto) {
        materialChangeService.save(materialChangeDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改资产信息变更
     * @param materialChangeDto 资产信息变更
     * @return Result
     */
    @ApiOperation(value = "修改资产信息变更", notes = "修改资产信息变更")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialChangeDto materialChangeDto) {
        materialChangeService.update(materialChangeDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除资产信息变更
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资产信息变更", notes = "通过id删除资产信息变更")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        materialChangeService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程id删除购置申请", notes = "通过流程id删除购置申请")
    @DeleteMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        materialChangeService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }


    /**
     * 通过流程实例ID查询购置申请
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例ID查询", notes = "通过流程实例ID查询")
    @GetMapping("/getByProcId/{procInstId}" )
    public Result<MaterialChangeVo> getByProcId(@PathVariable("procInstId" ) String procInstId) {
        MaterialChangeVo changeVo = materialChangeService.getByProcId(procInstId);
        log.debug("查询成功");
        return Result.success(changeVo);
    }

    /**
     * 流程新增或修改
     * @param materialChangeDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<MaterialChangeVo> procSaveOrUpdate(@RequestBody MaterialChangeDto materialChangeDto) {
        return Result.success( materialChangeService.procSaveOrUpdate(materialChangeDto));
    }

    /**
     * 资产信息变更保存前
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置采购保存前", notes = "购置采购保存前")
    @GetMapping("/procSaveBefore/{procInstId}" )
    public Result procSaveBefore(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialChangeService.procSaveBefore(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }


    /**
     * 资产信息变更保存后
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置采购保存后", notes = "购置采购保存后")
    @GetMapping("/procSaveAfter/{procInstId}" )
    public Result procSaveAfter(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialChangeService.procSaveAfter(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 资产信息变更流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置验收流程结束", notes = "购置验收流程结束")
    @GetMapping("/procAuditSuccess/{procInstId}" )
    public Result procAuditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialChangeService.procAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 获取领用资产
     * @param materialChangeDetailDto
     * @return Result
     */
    @ApiOperation(value = "获取待变更的领用资产", notes = "获取待变更的领用资产")
    @PostMapping("/getDetailList" )
    public Result<List<MaterialChangeDetailVo>> getDetailList(@RequestBody MaterialChangeDetailDto materialChangeDetailDto) {
        List<MaterialChangeDetailVo> detailVoList = materialChangeService.getDetailList(materialChangeDetailDto);
        log.debug("处理成功");
        return Result.success(detailVoList);
    }

}
