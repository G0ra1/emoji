package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.constants.AssetsConditions;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.service.AssetsDetailService;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.AssetsDto;
import com.netwisd.biz.asset.vo.AssetsVo;
import com.netwisd.biz.asset.service.AssetsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.poi.hpsf.Decimal;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 资产台账 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-24 16:12:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/assets" )
@Api(value = "assets", tags = "资产台账Controller")
@Slf4j
public class AssetsController {

    private final AssetsService assetsService;

    //private final AssetsSkuService assetsSkuService;

    private final AssetsDetailService assetsDetailService;

    /**
     * 分页查询资产台账
     * 没有使用参数注解，就是默认从form请求的方式
     * @param assetsDto 资产台账
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody AssetsDto assetsDto) {
        Page pageVo = assetsService.list(assetsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产台账
     * @param assetsDto 资产台账
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody AssetsDto assetsDto) {
        Page pageVo = assetsService.lists(assetsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产台账
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<AssetsVo> get(@PathVariable("id" ) Long id) {
        AssetsVo assetsVo = assetsService.get(id);
        log.debug("查询成功");
        return Result.success(assetsVo);
    }

    @ApiOperation(value = "获取库存大于0的资产", notes = "获取库存大于0的资产")
    @RequestMapping(value = "/getAssets", method = RequestMethod.POST)
    public Result getAssets(@RequestBody AssetsDto assetsDto) {
        Page<AssetsVo> pageVo = assetsService.getListByConditionsPage(assetsDto, AssetsConditions.STOCK,AssetsConditions.DELIVERY);
        log.debug("查询成功");
        return Result.success(pageVo);
    }

    @ApiOperation(value = "获取库存大于0的资产明细", notes = "获取库存大于0的资产明细")
    @RequestMapping(value = "/getAssetsDetail", method = RequestMethod.POST)
    public Result getAssetsDetail(@RequestBody AssetsDetailDto assetsDetailDto) {
        Page<AssetsDetailVo> pageVo = assetsDetailService.getListByConditionsPage(assetsDetailDto, AssetsConditions.STOCK);
        log.debug("查询成功");
        return Result.success(pageVo);
    }

   /* @ApiOperation(value = "获取资产sku信息", notes = "获取资产sku信息")
    @RequestMapping(value = "/getAssetsSku", method = RequestMethod.POST)
    public Result getAssetsSku(@RequestBody AssetsSkuDto assetsSkuDto) {
        Page<AssetsDetailVo> pageVo = assetsSkuService.getAssetsSku(assetsSkuDto);
        log.debug("查询成功");
        return Result.success(pageVo);
    }*/

    
}
