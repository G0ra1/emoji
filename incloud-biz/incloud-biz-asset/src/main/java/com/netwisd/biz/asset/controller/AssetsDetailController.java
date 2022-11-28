package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.biz.asset.service.AssetsDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 资产明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-26 14:25:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/assetsDetail" )
@Api(value = "assetsDetail", tags = "资产台账明细Controller")
@Slf4j
public class AssetsDetailController {

    private final  AssetsDetailService assetsDetailService;

    /**
     * 分页查询资产明细
     * 没有使用参数注解，就是默认从form请求的方式
     * @param assetsDetailDto 资产明细
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody AssetsDetailDto assetsDetailDto) {
        Page pageVo = assetsDetailService.list(assetsDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产明细
     * @param assetsDetailDto 资产明细
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody AssetsDetailDto assetsDetailDto) {
        Page pageVo = assetsDetailService.lists(assetsDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产明细
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<AssetsDetailVo> get(@PathVariable("id" ) Long id) {
        AssetsDetailVo assetsDetailVo = assetsDetailService.get(id);
        log.debug("查询成功");
        return Result.success(assetsDetailVo);
    }

}
