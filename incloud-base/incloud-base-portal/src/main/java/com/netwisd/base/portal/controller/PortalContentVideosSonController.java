package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentVideosSonVo;
import com.netwisd.base.portal.service.PortalContentVideosSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description  视频类型内容发布子表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-05 21:48:10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentVideosSon" )
@Api(value = "portalContentVideosSon", tags = " 视频类型内容发布子表Controller")
@Slf4j
public class PortalContentVideosSonController {

    private final  PortalContentVideosSonService portalContentVideosSonService;

    /**
     * 分页查询 视频类型内容发布子表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentVideosSonDto  视频类型内容发布子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentVideosSonDto portalContentVideosSonDto) {
        Page pageVo = portalContentVideosSonService.list(portalContentVideosSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询 视频类型内容发布子表
     * @param portalContentVideosSonDto  视频类型内容发布子表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List> lists(@RequestBody PortalContentVideosSonDto portalContentVideosSonDto) {
        List<PortalContentVideosSonVo> portalContentVideosSonVoList = portalContentVideosSonService.lists(portalContentVideosSonDto);
        log.debug("查询条数:"+portalContentVideosSonVoList.size());
        return Result.success(portalContentVideosSonVoList);
    }

    /**
     * 通过id查询 视频类型内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentVideosSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentVideosSonVo portalContentVideosSonVo = portalContentVideosSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentVideosSonVo);
    }

    /**
     * 新增 视频类型内容发布子表
     * @param portalContentVideosSonDto  视频类型内容发布子表
     * @return Result
     */
    @ApiOperation(value = "新增 视频类型内容发布子表", notes = "新增 视频类型内容发布子表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentVideosSonDto portalContentVideosSonDto) {
        Boolean result = portalContentVideosSonService.save(portalContentVideosSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改 视频类型内容发布子表
     * @param portalContentVideosSonDto  视频类型内容发布子表
     * @return Result
     */
    @ApiOperation(value = "修改 视频类型内容发布子表", notes = "修改 视频类型内容发布子表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentVideosSonDto portalContentVideosSonDto) {
        Boolean result = portalContentVideosSonService.update(portalContentVideosSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除 视频类型内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除 视频类型内容发布子表", notes = "通过id删除 视频类型内容发布子表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentVideosSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
    /**
     * 修改点击量
     *
     * @param id
     * @return Result
     */
    @ApiOperation(value = " 修改点击量", notes = " 修改点击量")
    @GetMapping("/upHits")
    public Result<Boolean> upHits(@RequestParam("id") Long id) {
        Boolean result = portalContentVideosSonService.upHits(id);
        log.debug("点击量+1");
        return Result.success(result);
    }

}
