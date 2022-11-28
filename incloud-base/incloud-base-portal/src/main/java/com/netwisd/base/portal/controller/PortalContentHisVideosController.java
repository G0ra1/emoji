package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentHisVideosDto;
import com.netwisd.base.portal.vo.PortalContentHisVideosVo;
import com.netwisd.base.portal.service.PortalContentHisVideosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description  视频类内容发布-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-31 02:45:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentHisVideos" )
@Api(value = "portalContentHisVideos", tags = " 视频类内容发布-历史表Controller")
@Slf4j
public class PortalContentHisVideosController {

    private final  PortalContentHisVideosService portalContentHisVideosService;

    /**
     * 分页查询 视频类内容发布-历史表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentHisVideosDto  视频类内容发布-历史表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentHisVideosDto portalContentHisVideosDto) {
        Page pageVo = portalContentHisVideosService.list(portalContentHisVideosDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询 视频类内容发布-历史表
     * @param portalContentHisVideosDto  视频类内容发布-历史表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentHisVideosDto portalContentHisVideosDto) {
        Page pageVo = portalContentHisVideosService.lists(portalContentHisVideosDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询 视频类内容发布-历史表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentHisVideosVo> get(@PathVariable("id" ) Long id) {
        PortalContentHisVideosVo portalContentHisVideosVo = portalContentHisVideosService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentHisVideosVo);
    }

    /**
     * 新增 视频类内容发布-历史表
     * @param portalContentHisVideosDto  视频类内容发布-历史表
     * @return Result
     */
    @ApiOperation(value = "新增 视频类内容发布-历史表", notes = "新增 视频类内容发布-历史表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentHisVideosDto portalContentHisVideosDto) {
        Boolean result = portalContentHisVideosService.save(portalContentHisVideosDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改 视频类内容发布-历史表
     * @param portalContentHisVideosDto  视频类内容发布-历史表
     * @return Result
     */
    @ApiOperation(value = "修改 视频类内容发布-历史表", notes = "修改 视频类内容发布-历史表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentHisVideosDto portalContentHisVideosDto) {
        Boolean result = portalContentHisVideosService.update(portalContentHisVideosDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除 视频类内容发布-历史表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除 视频类内容发布-历史表", notes = "通过id删除 视频类内容发布-历史表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentHisVideosService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
