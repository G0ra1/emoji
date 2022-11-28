package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.dto.MaterialSignDto;
import com.netwisd.biz.asset.dto.PurchaseApplyDto;
import com.netwisd.biz.asset.dto.ScrapApplyDto;
import com.netwisd.biz.asset.service.ScrapApplyDetailService;
import com.netwisd.biz.asset.service.ScrapApplyService;
import com.netwisd.biz.asset.vo.MaterialSignVo;
import com.netwisd.biz.asset.vo.ScrapApplyVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.ScrapRegisterDto;
import com.netwisd.biz.asset.vo.ScrapRegisterVo;
import com.netwisd.biz.asset.service.ScrapRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 报废登记 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-05 10:14:01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/scrapRegister" )
@Api(value = "scrapRegister", tags = "报废登记Controller")
@Slf4j
public class ScrapRegisterController {

    private final  ScrapRegisterService scrapRegisterService;

    private final ScrapApplyService scrapApplyService;

    private final ScrapApplyDetailService scrapApplyDetailService;

    /**
     * 分页查询报废登记
     * 没有使用参数注解，就是默认从form请求的方式
     * @param scrapRegisterDto 报废登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ScrapRegisterDto scrapRegisterDto) {
        Page pageVo = scrapRegisterService.list(scrapRegisterDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询报废登记
     * @param scrapRegisterDto 报废登记
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody ScrapRegisterDto scrapRegisterDto) {
        Page pageVo = scrapRegisterService.lists(scrapRegisterDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询报废登记
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ScrapRegisterVo> get(@PathVariable("id" ) Long id) {
        ScrapRegisterVo scrapRegisterVo = scrapRegisterService.get(id);
        log.debug("查询成功");
        return Result.success(scrapRegisterVo);
    }

    /**
     * 新增报废登记
     * @param scrapRegisterDto 报废登记
     * @return Result
     */
    @ApiOperation(value = "新增报废登记", notes = "新增报废登记")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ScrapRegisterDto scrapRegisterDto) {
        scrapRegisterService.save(scrapRegisterDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改报废登记
     * @param scrapRegisterDto 报废登记
     * @return Result
     */
    @ApiOperation(value = "修改报废登记", notes = "修改报废登记")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ScrapRegisterDto scrapRegisterDto) {
        scrapRegisterService.update(scrapRegisterDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除报废登记
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除报废登记", notes = "通过id删除报废登记")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        scrapRegisterService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 流程新增或修改
     * @param scrapRegisterDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改",notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<ScrapRegisterVo> procSaveOrUpdate(@RequestBody ScrapRegisterDto scrapRegisterDto){
        return Result.success(scrapRegisterService.procSaveOrUpdate(scrapRegisterDto));
    }
    /**
     * 通过流程实例Id查询
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}")
    public Result<ScrapRegisterVo> getByProc(@PathVariable("procInstId") String procInstId){
        ScrapRegisterVo scrapRegisterVo = scrapRegisterService.getByProc(procInstId);
        log.debug("通过流程id查询成功");
        return Result.success(scrapRegisterVo);
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    @ApiOperation(value = "通过流程实例id删除", notes = "通过流程实例id删除")
    @DeleteMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        scrapRegisterService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 报废登记流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "报废登记流程结束", notes = "报废登记流程结束")
    @PostMapping("purscrapAuditSuccess/{procInstId}" )
    public Result purScrapAuditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = scrapRegisterService.procAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
    /**
     * 报废登记流程保存前
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "报废登记流程保存前", notes = "报废登记流程保存前")
    @PostMapping("/saveBefore/{procInstId}" )
    public Result saveBefore(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = scrapRegisterService.procSaveBefore(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
    /**
     * 报废登记流程保存后
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "报废申请流程保存后", notes = "报废申请流程保存后")
    @PostMapping("/saveAfter/{procInstId}" )
    public Result saveAfter(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = scrapRegisterService.procSaveAfter(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 获取待报废数据的报废申请
     * @param scrapApplyDto
     * @return Result
     */
    @ApiOperation(value = "获取待登记的购置申请", notes = "获取待登记的购置申请")
    @PostMapping("/getApplyList")
    public Result<Page> getApplyList(@RequestBody ScrapApplyDto scrapApplyDto) {
        Page pageVo = scrapApplyService.getRegisterList(scrapApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }
    /**
     * 获取待登记数据的购置申请
     * @param applyIds
     * @return Result
     */
    @ApiOperation(value = "获取报废待登记的购置申请详情", notes = "获取报废待登记的购置申请详情")
    @GetMapping("/getApplyDetailByIds/{applyIds}")
    public Result getApplyDetailByIds(@PathVariable("applyIds") String applyIds) {
        if(StringUtils.isEmpty(applyIds)){
            throw new IncloudException("请选择报废申请单");
        }
        //Page pageVo =  scrapApplyDetailService.getByApplyIds(applyIds);
        //log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(scrapApplyDetailService.getByApplyIds(applyIds));
        //return Result.success(pageVo);
    }
}
