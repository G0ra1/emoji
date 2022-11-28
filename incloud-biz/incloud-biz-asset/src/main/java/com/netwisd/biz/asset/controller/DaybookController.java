package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.constants.ItemTypeEnum;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.DaybookDto;
import com.netwisd.biz.asset.vo.DaybookVo;
import com.netwisd.biz.asset.service.DaybookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 资产流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:19:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/daybook" )
@Api(value = "daybook", tags = "资产流水表Controller")
@Slf4j
public class DaybookController {

    private final  DaybookService daybookService;

    /**
     * 分页查询资产流水表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param daybookDto 资产流水表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody DaybookDto daybookDto) {
        Page pageVo = daybookService.list(daybookDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产流水表
     * @param daybookDto 资产流水表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody DaybookDto daybookDto) {
        Page pageVo = daybookService.lists(daybookDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产流水表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<DaybookVo> get(@PathVariable("id" ) Long id) {
        DaybookVo daybookVo = daybookService.get(id);
        log.debug("查询成功");
        return Result.success(daybookVo);
    }

    /**
     * 通过资产详情id查询资产流水表
     * @param assetsDetailDto
     * @return Result
     */
    @ApiOperation(value = "通过资产详情id查询", notes = "通过资产详情id查询")
    @PostMapping("/getByAssets" )
    public Result<Page> getByAssets(@RequestBody AssetsDetailDto assetsDetailDto) {
        Page<DaybookVo> page = daybookService.getByAssets(assetsDetailDto);
        log.debug("查询成功");
        return Result.success(page);
    }

    /**
     * 获取类型
     * @param type
     * @return Result
     */
    @ApiOperation(value = "通过资产详情id查询", notes = "通过资产详情id查询")
    @GetMapping("/getType/{type}" )
    public Result<String> getType(@PathVariable("type" ) Integer type) {
        String result = "/";
        for (DayBookType value : DayBookType.values()) {
            if(value.code == type){
                result = value.name;
            }
        }
        return Result.success(result);
    }
}
