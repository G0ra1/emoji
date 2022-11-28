package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.MaterialAccept;
import com.netwisd.biz.asset.entity.MaterialAcceptAssets;
import com.netwisd.biz.asset.service.MaterialAcceptService;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.service.MaterialSignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 签收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialSign" )
@Api(value = "materialSign", tags = "资产签收Controller")
@Slf4j
public class MaterialSignController {

    private final  MaterialSignService materialSignService;

    private final MaterialAcceptService materialAcceptService;

    /**
     * 分页查询签收
     * 没有使用参数注解，就是默认从form请求的方式
     * @param materialSignDto 签收
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaterialSignDto materialSignDto) {
        Page pageVo = materialSignService.list(materialSignDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询签收
     * @param materialSignDto 签收
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaterialSignDto materialSignDto) {
        Page pageVo = materialSignService.lists(materialSignDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询签收
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialSignVo> get(@PathVariable("id" ) Long id) {
        MaterialSignVo materialSignVo = materialSignService.get(id);
        log.debug("查询成功");
        return Result.success(materialSignVo);
    }

    /**
     * 新增签收
     * @param materialSignDto 签收
     * @return Result
     */
    @ApiOperation(value = "新增签收", notes = "新增签收")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialSignDto materialSignDto) {
      Boolean result =  materialSignService.save(materialSignDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改签收
     * @param materialSignDto 签收
     * @return Result
     */
    @ApiOperation(value = "修改签收", notes = "修改签收")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialSignDto materialSignDto) {
        materialSignService.update(materialSignDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除签收
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除签收", notes = "通过id删除签收")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        materialSignService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }
    /**
     * 流程新增或修改
     * @param materialSignDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改",notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<MaterialSignVo> procSaveOrUpdate(@RequestBody MaterialSignDto materialSignDto){
        return Result.success(materialSignService.procSaveOrUpdate(materialSignDto));
    }

    /**
     * 通过流程实例Id查询
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}")
    public Result<MaterialSignVo> getByProc(@PathVariable ("procInstId") String procInstId) {
        MaterialSignVo materialSignVo = materialSignService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(materialSignVo);
    }
    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    @ApiOperation(value = "通过流程实例id删除", notes = "通过流程实例id删除")
    @DeleteMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        materialSignService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }
    /**
     * 签收流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "签收流程结束", notes = "签收流程结束")
    @PostMapping("purSignAuditSuccess/{procInstId}" )
    public Result purSignAuditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialSignService.procAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
    /**
     * 获取待签收的物资单号
     * @param materialSignDto
     * @return Result
     */
    @ApiOperation(value = "获取待签收的物资单号", notes = "获取待签收的物资单号")
    @PostMapping("/getAcceptList")
    public Result getAcceptList(@RequestBody MaterialSignDto materialSignDto) {
        if(ObjectUtils.isEmpty(materialSignDto.getDistributeType())){
            return Result.failed("参数中不存在type");
        }
        return Result.success(materialSignService.getAcceptList(materialSignDto));
    }
    /**
     * 获取待签收的明细
     * @param materialSignDto
     * @return Result
     */
//    @ApiOperation(value = "获取待签收的物资明细", notes = "获取待签收的物资明细")
//    @PostMapping("/getSignDetailList")
//    public Result<List<AssetsDetailVo>> getSignDetailList(@Validation @RequestBody MaterialSignDto materialSignDto) {
//        Long sourceId = materialSignDto.getSourceId();
//        //String sourceIds =  materialSignDto.getSourceIds();
//        if(ObjectUtils.isEmpty(sourceId)){
//            return Result.failed("来源单不能为空!!");
//        }
//        if(ObjectUtils.isEmpty(materialSignDto.getDistributeType())){
//            return Result.failed("请选择派发类型!!");
//        }
//        List<AssetsDetailVo> assetsVoList = materialSignService.getSignDetailList(materialSignDto);
//        //log.debug("查询条数:"+pageVo.getTotal());
//        return Result.success(assetsVoList);
//    }

    /**
     * 获取待签收的明细
     * @param materialSignDto
     * @return Result
     */
    @ApiOperation(value = "获取待签收的物资明细", notes = "获取待签收的物资明细")
    @PostMapping("/getSignDetailList")
    public Result<List<MaterialSignDetailVo>> getDetailList(@RequestBody MaterialSignDto materialSignDto) {
        List<MaterialSignDetailVo> assetsVoList = new ArrayList<>();
        if(ObjectUtils.isEmpty(materialSignDto.getDistributeType())){
            return Result.success(Result.failed("请选择派发类型"));
        }
        Long sourceId = materialSignDto.getSourceId();
        if(ObjectUtils.isEmpty(sourceId)){
            return Result.failed("来源单不能为空!!");
        }else{
            assetsVoList = materialSignService.getDetailList(materialSignDto);
        }
        log.debug("查询条数:"+assetsVoList.size());
        return Result.success(assetsVoList);
    }

    /**
     * 获取待派发单据
     * @param signDto
     * @return
     */
    @ApiOperation(value = "获取待派发单据", notes = "获取待派发单据")
    @PostMapping("/getOrders")
    public Result getOrders(@Validation @RequestBody MaterialSignDto signDto) {
        //log.debug("查询成功");
        return Result.success(materialSignService.getOrders(signDto));
    }


    /**
     * 购置申请流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "11111", notes = "11111")
    @GetMapping("/saveBefore/{procInstId}" )
    public Result saveBefore(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialSignService.procSaveBefore(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 购置申请流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "22222", notes = "22222")
    @GetMapping("/saveAfter/{procInstId}" )
    public Result saveAfter(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialSignService.procSaveAfter(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
}
