package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentHisPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentHisPicturesSonVo;
import com.netwisd.base.portal.service.PortalContentHisPicturesSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 历史 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:15:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentHisPicturesSon" )
@Api(value = "portalContentHisPicturesSon", tags = "历史 图片轮播类内容发布子表Controller")
@Slf4j
public class PortalContentHisPicturesSonController {

    private final  PortalContentHisPicturesSonService portalContentHisPicturesSonService;

    /**
     * 分页查询历史 图片轮播类内容发布子表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentHisPicturesSonDto 历史 图片轮播类内容发布子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentHisPicturesSonDto portalContentHisPicturesSonDto) {
        Page pageVo = portalContentHisPicturesSonService.list(portalContentHisPicturesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询历史 图片轮播类内容发布子表
     * @param portalContentHisPicturesSonDto 历史 图片轮播类内容发布子表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentHisPicturesSonDto portalContentHisPicturesSonDto) {
        Page pageVo = portalContentHisPicturesSonService.lists(portalContentHisPicturesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询历史 图片轮播类内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentHisPicturesSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentHisPicturesSonVo portalContentHisPicturesSonVo = portalContentHisPicturesSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentHisPicturesSonVo);
    }

    /**
     * 新增历史 图片轮播类内容发布子表
     * @param portalContentHisPicturesSonDto 历史 图片轮播类内容发布子表
     * @return Result
     */
    @ApiOperation(value = "新增历史 图片轮播类内容发布子表", notes = "新增历史 图片轮播类内容发布子表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentHisPicturesSonDto portalContentHisPicturesSonDto) {
        Boolean result = portalContentHisPicturesSonService.save(portalContentHisPicturesSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改历史 图片轮播类内容发布子表
     * @param portalContentHisPicturesSonDto 历史 图片轮播类内容发布子表
     * @return Result
     */
    @ApiOperation(value = "修改历史 图片轮播类内容发布子表", notes = "修改历史 图片轮播类内容发布子表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentHisPicturesSonDto portalContentHisPicturesSonDto) {
        Boolean result = portalContentHisPicturesSonService.update(portalContentHisPicturesSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除历史 图片轮播类内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除历史 图片轮播类内容发布子表", notes = "通过id删除历史 图片轮播类内容发布子表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentHisPicturesSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
