package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentHisBannersDto;
import com.netwisd.base.portal.vo.PortalContentHisBannersVo;
import com.netwisd.base.portal.service.PortalContentHisBannersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description banner类内容-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-30 03:09:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentHisBanners" )
@Api(value = "portalContentHisBanners", tags = "banner类内容-历史表Controller")
@Slf4j
public class PortalContentHisBannersController {

    private final  PortalContentHisBannersService portalContentHisBannersService;

    /**
     * 分页查询banner类内容-历史表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentHisBannersDto banner类内容-历史表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentHisBannersDto portalContentHisBannersDto) {
        Page pageVo = portalContentHisBannersService.list(portalContentHisBannersDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询banner类内容-历史表
     * @param portalContentHisBannersDto banner类内容-历史表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List> lists(@RequestBody PortalContentHisBannersDto portalContentHisBannersDto) {
        List<PortalContentHisBannersVo> portalContentHisBannersVoList = portalContentHisBannersService.lists(portalContentHisBannersDto);
        log.debug("查询条数:"+portalContentHisBannersVoList.size());
        return Result.success(portalContentHisBannersVoList);
    }

    /**
     * 通过id查询banner类内容-历史表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentHisBannersVo> get(@PathVariable("id" ) Long id) {
        PortalContentHisBannersVo portalContentHisBannersVo = portalContentHisBannersService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentHisBannersVo);
    }

    /**
     * 新增banner类内容-历史表
     * @param portalContentHisBannersDto banner类内容-历史表
     * @return Result
     */
    @ApiOperation(value = "新增banner类内容-历史表", notes = "新增banner类内容-历史表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentHisBannersDto portalContentHisBannersDto) {
        Boolean result = portalContentHisBannersService.save(portalContentHisBannersDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改banner类内容-历史表
     * @param portalContentHisBannersDto banner类内容-历史表
     * @return Result
     */
    @ApiOperation(value = "修改banner类内容-历史表", notes = "修改banner类内容-历史表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentHisBannersDto portalContentHisBannersDto) {
        Boolean result = portalContentHisBannersService.update(portalContentHisBannersDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除banner类内容-历史表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除banner类内容-历史表", notes = "通过id删除banner类内容-历史表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentHisBannersService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
