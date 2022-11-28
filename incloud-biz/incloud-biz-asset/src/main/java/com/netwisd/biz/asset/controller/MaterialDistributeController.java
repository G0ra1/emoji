package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.service.MaterialDistributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 资产派发 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:46:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialDistribute" )
@Api(value = "materialDistribute", tags = "资产派发Controller")
@Slf4j
public class MaterialDistributeController {

    private final  MaterialDistributeService materialDistributeService;

    /**
     * 分页查询资产派发
     * 没有使用参数注解，就是默认从form请求的方式
     * @param materialDistributeDto 资产派发
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaterialDistributeDto materialDistributeDto) {
        Page pageVo = materialDistributeService.list(materialDistributeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产派发
     * @param materialDistributeDto 资产派发
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaterialDistributeDto materialDistributeDto) {
        Page pageVo = materialDistributeService.lists(materialDistributeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产派发
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialDistributeVo> get(@PathVariable("id" ) Long id) {
        MaterialDistributeVo materialDistributeVo = materialDistributeService.get(id);
        log.debug("查询成功");
        return Result.success(materialDistributeVo);
    }

    /**
     * 新增资产派发
     * @param materialDistributeDto 资产派发
     * @return Result
     */
    @ApiOperation(value = "新增资产派发", notes = "新增资产派发")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialDistributeDto materialDistributeDto) {
        materialDistributeService.save(materialDistributeDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改资产派发
     * @param materialDistributeDto 资产派发
     * @return Result
     */
    @ApiOperation(value = "修改资产派发", notes = "修改资产派发")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialDistributeDto materialDistributeDto) {
        materialDistributeService.update(materialDistributeDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除资产派发
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资产派发", notes = "通过id删除资产派发")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = materialDistributeService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id删除", notes = "通过流程实例id删除")
    @DeleteMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialDistributeService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通过id查询资产入库管理
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/getByProcId/{procInstId}" )
    public Result<MaterialDistributeVo> getByProcId(@PathVariable("procInstId" ) String procInstId) {
        MaterialDistributeVo materialDistributeVo = materialDistributeService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(materialDistributeVo);
    }

    /**
     * 流程新增或修改
     * @param materialDistributeDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result procSaveOrUpdate(@Validation @RequestBody MaterialDistributeDto materialDistributeDto) {
        Boolean result = materialDistributeService.procSaveOrUpdate(materialDistributeDto);
        log.debug("流程新增或修改成功");
        return Result.success();
    }

    /**
     * 根据派发明细查找需要派发的可用物资
     * @param assetsDetailDto 必传assets_id
     * @return
     */
    @ApiOperation(value = "根据派发明细查找需要派发的可用物资", notes = "根据派发明细查找需要派发的可用物资")
    @PostMapping("/gerDetailByAssetsId")
    public Result<Page<MaterialDistributeDetailVo>> gerDetailByAssetsId(@Validation @RequestBody AssetsDetailDto assetsDetailDto) {
        if(ObjectUtils.isEmpty(assetsDetailDto.getAssetsId())){
            return Result.failed("参数中不存在assetsId");
        }
        Page<MaterialDistributeDetailVo> page = materialDistributeService.gerDetailByAssetsId(assetsDetailDto);
        log.debug("查询成功");
        return Result.success(page);
    }

    /**
     * 获取待派发单据
     * @param distributeDto
     * @return
     */
    @ApiOperation(value = "获取待派发单据", notes = "获取待派发单据")
    @PostMapping("/getOrders")
    public Result getOrders(@Validation @RequestBody MaterialDistributeDto distributeDto) {
        if(ObjectUtils.isEmpty(distributeDto.getDistributeType())){
            return Result.failed("参数中不存type");
        }
        //log.debug("查询成功");
        return Result.success(materialDistributeService.getOrders(distributeDto));
    }

    /**
     * 获取待派发的的明细
     * @param distributeDto
     * @return Result
     */
    @ApiOperation(value = "获取待派发的的明细", notes = "获取待派发的的明细")
    @PostMapping("/getAssetsList")
    public Result<List<MaterialDistributeAssetsVo>> getAssetsList(@Validation @RequestBody MaterialDistributeDto distributeDto) {
        Long sourceId = distributeDto.getSourceId();
        String sourceIds =  distributeDto.getSourceIds();
        if(ObjectUtils.isEmpty(sourceId) && ObjectUtils.isEmpty(sourceIds)){
            return Result.failed("来源单不能为空!!");
        }
        if(ObjectUtils.isEmpty(distributeDto.getDistributeType())){
            return Result.failed("请选择派发类型!!");
        }
        List<MaterialDistributeAssetsVo> assetsVoList = materialDistributeService.getAssetsList(distributeDto);
        //log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(assetsVoList);
    }

    /**
     * 根据选择的派发明细生成待派发的详情
     * @param assetsDto
     * @return Result
     */
    @ApiOperation(value = "根据选择的派发明细生成待派发的详情", notes = "根据选择的派发明细生成待派发的详情")
    @PostMapping("/getDetailByAssets")
    public Result<List<MaterialDistributeDetailVo>> getDetailByAssets(@Validation @RequestBody MaterialDistributeAssetsDto assetsDto) {
        List<MaterialDistributeDetailVo> detailVoList = materialDistributeService.getDetailByAssets(assetsDto);
        //log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(detailVoList);
    }



    /**
     * 根据选择的派发明细生成待派发的详情
     * @param assetsDtoList
     * @return Result
     */
    @ApiOperation(value = "根据选择的派发明细生成待派发的详情", notes = "根据选择的派发明细生成待派发的详情")
    @PostMapping("/getDetailByAssetsList")
    public Result<List<MaterialDistributeDetailVo>> getDetailByAssetsList(@Validation @RequestBody List<MaterialDistributeAssetsDto> assetsDtoList) {
        List<MaterialDistributeDetailVo> detailVoList = materialDistributeService.getDetailByAssetsList(assetsDtoList);
        //log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(detailVoList);
    }


    /**
     * 购置申请流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "11111", notes = "11111")
    @GetMapping("/saveBefore/{procInstId}" )
    public Result saveBefore(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialDistributeService.procSaveBefore(procInstId);
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
        Boolean result = materialDistributeService.procSaveAfter(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 购置申请流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "33333", notes = "33333")
    @GetMapping("/auditSuccess/{procInstId}" )
    public Result auditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialDistributeService.procAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }


}
