package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.dto.MaterialWithdrawalDto;
import com.netwisd.biz.asset.service.AssetsDetailService;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.biz.asset.vo.MaterialAcceptResultVo;
import com.netwisd.biz.asset.vo.MaterialWithdrawalVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.ScrapApplyDto;
import com.netwisd.biz.asset.vo.ScrapApplyVo;
import com.netwisd.biz.asset.service.ScrapApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 报废申请 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-03 14:54:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/scrapApply" )
@Api(value = "scrapApply", tags = "报废申请Controller")
@Slf4j
public class ScrapApplyController {

    private final  ScrapApplyService scrapApplyService;

    private final AssetsDetailService assetsDetailService;

    /**
     * 分页查询报废申请
     * 没有使用参数注解，就是默认从form请求的方式
     * @param scrapApplyDto 报废申请
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ScrapApplyDto scrapApplyDto) {
        Page pageVo = scrapApplyService.list(scrapApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询报废申请
     * @param scrapApplyDto 报废申请
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody ScrapApplyDto scrapApplyDto) {
        Page pageVo = scrapApplyService.lists(scrapApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询报废申请
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ScrapApplyVo> get(@PathVariable("id" ) Long id) {
        ScrapApplyVo scrapApplyVo = scrapApplyService.get(id);
        log.debug("查询成功");
        return Result.success(scrapApplyVo);
    }

    /**
     * 新增报废申请
     * @param scrapApplyDto 报废申请
     * @return Result
     */
    @ApiOperation(value = "新增报废申请", notes = "新增报废申请")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ScrapApplyDto scrapApplyDto) {
        scrapApplyService.save(scrapApplyDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改报废申请
     * @param scrapApplyDto 报废申请
     * @return Result
     */
    @ApiOperation(value = "修改报废申请", notes = "修改报废申请")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ScrapApplyDto scrapApplyDto) {
        scrapApplyService.update(scrapApplyDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除报废申请
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除报废申请", notes = "通过id删除报废申请")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        scrapApplyService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }
    /**
     * 获取待报废物资资产明细
     * @param assetsDetailDto
     * @return Result
     */
    @ApiOperation(value = "获取待退库物资资产明细",notes = "获取待退库物资资产明细")
    @PostMapping("/getScrapDetail")
    public Result<Page> getScrapDetail(@RequestBody AssetsDetailDto assetsDetailDto){
        Page<AssetsDetailVo> pageVo =  assetsDetailService.getScrapDetail(assetsDetailDto);
        log.debug("queryWrapper查询数据 ---->{}",pageVo.getRecords());
        return Result.success(pageVo);
    }
    /**
     * 流程中新增或修改
     * @param scrapApplyDto
     * @return Result
     */
    @ApiOperation(value = "流程中新增或修改",notes = "获取待退库物资资产明细")
    @PostMapping("/procSaveOrUpdate")
    public Result<ScrapApplyVo> procSaveOrUpdate(@RequestBody ScrapApplyDto scrapApplyDto){
        return Result.success(scrapApplyService.procSaveOrUpdate(scrapApplyDto));
    }

    /**
     * 通过流程实例Id查询
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}")
    public Result<ScrapApplyVo> getByProc(@PathVariable("procInstId") String procInstId){
        ScrapApplyVo scrapApplyVo = scrapApplyService.getByProc(procInstId);
        log.debug("通过流程id查询成功");
        return Result.success(scrapApplyVo);
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    @ApiOperation(value = "通过流程实例id删除", notes = "通过流程实例id删除")
    @DeleteMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        scrapApplyService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }
    /**
     * 报废申请流程保存前
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "报废申请流程保存前", notes = "报废申请流程保存前")
    @PostMapping("/saveBefore/{procInstId}" )
    public Result saveBefore(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = scrapApplyService.procSaveBefore(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
    /**
     * 报废申请流程保存后
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "报废申请流程保存后", notes = "报废申请流程保存后")
    @PostMapping("/saveAfter/{procInstId}" )
    public Result saveAfter(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = scrapApplyService.procSaveAfter(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
    /**
     * 购置申请流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "报废申请流程结束", notes = "报废申请流程结束")
    @PostMapping("purscrapAuditSuccess/{procInstId}" )
    public Result purScrapAuditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = scrapApplyService.procAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
}
