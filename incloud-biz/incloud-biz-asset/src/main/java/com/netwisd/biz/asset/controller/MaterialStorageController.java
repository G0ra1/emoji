package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.service.AssetsDetailService;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaterialStorageDto;
import com.netwisd.biz.asset.vo.MaterialStorageVo;
import com.netwisd.biz.asset.service.MaterialStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 资产入库管理 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:44:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialStorage" )
@Api(value = "materialStorage", tags = "资产入库管理Controller")
@Slf4j
public class MaterialStorageController {

    private final MaterialStorageService materialStorageService;

    private final AssetsDetailService assetsDetailService;

    /**
     * 分页查询资产入库管理
     * 没有使用参数注解，就是默认从form请求的方式
     * @param materialStorageDto 资产入库管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaterialStorageDto materialStorageDto) {
        Page pageVo = materialStorageService.list(materialStorageDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产入库管理
     * @param materialStorageDto 资产入库管理
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaterialStorageDto materialStorageDto) {
        Page pageVo = materialStorageService.lists(materialStorageDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产入库管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialStorageVo> get(@PathVariable("id" ) Long id) {
        MaterialStorageVo materialStorageVo = materialStorageService.get(id);
        log.debug("查询成功");
        return Result.success(materialStorageVo);
    }

    /**
     * 新增资产入库管理
     * @param materialStorageDto 资产入库管理
     * @return Result
     */
    @ApiOperation(value = "新增资产入库管理", notes = "新增资产入库管理")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialStorageDto materialStorageDto) {
        materialStorageService.save(materialStorageDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改资产入库管理
     * @param materialStorageDto 资产入库管理
     * @return Result
     */
    @ApiOperation(value = "修改资产入库管理", notes = "修改资产入库管理")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialStorageDto materialStorageDto) {
        materialStorageService.update(materialStorageDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除资产入库管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资产入库管理", notes = "通过id删除资产入库管理")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        materialStorageService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id删除", notes = "通过流程实例id删除")
    @GetMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        materialStorageService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通过id查询资产入库管理
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}" )
    public Result<MaterialStorageVo> getByProc(@PathVariable("procInstId" ) String procInstId) {
        MaterialStorageVo materialStorageVo = materialStorageService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(materialStorageVo);
    }

    @ApiOperation(value = "获取待入账资产明细", notes = "获取验收待入账资产明细")
    @RequestMapping(value = "/getAssetsDetail", method = RequestMethod.POST)
    public Result getAssetsDetail(@RequestBody AssetsDetailDto assetsDetailDto) {
        //Page<AssetsDetailVo> pageVo = assetsDetailService.getListByConditionsPage(assetsDetailDto, AssetsConditions.FOR_STORAGE);
        Page<AssetsDetailVo> pageVo = materialStorageService.getAssetsDetails(assetsDetailDto);
        log.debug("queryWrapper查询数据 ---->{}",pageVo.getRecords());
        return Result.success(pageVo);
    }

}
