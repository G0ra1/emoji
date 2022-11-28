package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.PurchaseApplyDetailDto;
import com.netwisd.biz.asset.entity.PurchaseApplyDetail;
import com.netwisd.biz.asset.service.PurchaseApplyDetailService;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.PurchaseApplyDto;
import com.netwisd.biz.asset.vo.PurchaseApplyVo;
import com.netwisd.biz.asset.service.PurchaseApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 购置申请 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-20 10:10:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/purchaseApply" )
@Api(value = "purchaseApply", tags = "购置申请Controller")
@Slf4j
public class PurchaseApplyController {

    private final  PurchaseApplyService purchaseApplyService;

    private final PurchaseApplyDetailService purchaseApplyDetailService;

    /**
     * 分页查询购置申请
     * 没有使用参数注解，就是默认从form请求的方式
     * @param purchaseApplyDto 购置申请
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PurchaseApplyDto purchaseApplyDto) {
        Page pageVo = purchaseApplyService.list(purchaseApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询购置申请
     * @param purchaseApplyDto 购置申请
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PurchaseApplyDto purchaseApplyDto) {
        Page pageVo = purchaseApplyService.lists(purchaseApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询购置申请
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PurchaseApplyVo> get(@PathVariable("id" ) Long id) {
        PurchaseApplyVo purchaseApplyVo = purchaseApplyService.get(id);
        log.debug("查询成功");
        return Result.success(purchaseApplyVo);
    }

    /**
     * 通过ids查询购置申请列表
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @PostMapping("/getByIds" )
    public Result<List<PurchaseApplyVo>> getByIds(@Validation String ids) {
        List<PurchaseApplyVo> purchaseApplyVos = purchaseApplyService.getByIds(ids);
        log.debug("查询成功");
        return Result.success(purchaseApplyVos);
    }

    /**
     * 分页自定义查询购置申请
     * @param
     * @return
     */
    @ApiOperation(value = "详情数据分页查询", notes = "详情数据分页查询")
    @PostMapping("/detailLists" )
    public Result<Page> detailLists(@Validation @RequestBody PurchaseApplyDetailDto purchaseApplyDetailDto) {
        Page pageVo = purchaseApplyDetailService.list(purchaseApplyDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 新增购置申请
     * @param purchaseApplyDto 购置申请
     * @return Result
     */
    @ApiOperation(value = "新增购置申请", notes = "新增购置申请")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PurchaseApplyDto purchaseApplyDto) {
        Boolean result = purchaseApplyService.save(purchaseApplyDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改购置申请
     * @param purchaseApplyDto 购置申请
     * @return Result
     */
    @ApiOperation(value = "修改购置申请", notes = "修改购置申请")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PurchaseApplyDto purchaseApplyDto) {
        Boolean result = purchaseApplyService.update(purchaseApplyDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除购置申请
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除购置申请", notes = "通过id删除购置申请")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = purchaseApplyService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }




    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程id删除购置申请", notes = "通过流程id删除购置申请")
    @DeleteMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        purchaseApplyService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }


    /**
     * 通过流程实例ID查询购置申请
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例ID查询", notes = "通过流程实例ID查询")
    @GetMapping("getByProcId/{procInstId}" )
    public Result<PurchaseApplyVo> getByProcId(@PathVariable("procInstId" ) String procInstId) {
        PurchaseApplyVo purchaseApplyVo = purchaseApplyService.getByProcId(procInstId);
        log.debug("查询成功");
        return Result.success(purchaseApplyVo);
    }

    /**
     * 流程新增或修改
     * @param purchaseApplyDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<PurchaseApplyVo> procSaveOrUpdate(@RequestBody PurchaseApplyDto purchaseApplyDto) {
        return Result.success( purchaseApplyService.procSaveOrUpdate(purchaseApplyDto));
    }

    /**
     * 购置申请流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "购置申请流程结束", notes = "购置申请流程结束")
    @GetMapping("/purApplyAuditSuccess/{procInstId}" )
    public Result purApplyAuditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = purchaseApplyService.purApplyAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

}
