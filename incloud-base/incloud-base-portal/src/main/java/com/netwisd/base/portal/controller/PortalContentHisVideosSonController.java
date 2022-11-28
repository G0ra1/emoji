package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentHisVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentHisVideosSonVo;
import com.netwisd.base.portal.service.PortalContentHisVideosSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description  视频类型内容发布-历史表子表  功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 14:08:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentHisVideosSon" )
@Api(value = "portalContentHisVideosSon", tags = " 视频类型内容发布-历史表子表 Controller")
@Slf4j
public class PortalContentHisVideosSonController {

    private final  PortalContentHisVideosSonService portalContentHisVideosSonService;

    /**
     * 分页查询 视频类型内容发布-历史表子表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentHisVideosSonDto  视频类型内容发布-历史表子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentHisVideosSonDto portalContentHisVideosSonDto) {
        Page pageVo = portalContentHisVideosSonService.list(portalContentHisVideosSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询 视频类型内容发布-历史表子表
     * @param portalContentHisVideosSonDto  视频类型内容发布-历史表子表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentHisVideosSonDto portalContentHisVideosSonDto) {
        Page pageVo = portalContentHisVideosSonService.lists(portalContentHisVideosSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询 视频类型内容发布-历史表子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentHisVideosSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentHisVideosSonVo portalContentHisVideosSonVo = portalContentHisVideosSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentHisVideosSonVo);
    }

    /**
     * 新增 视频类型内容发布-历史表子表
     * @param portalContentHisVideosSonDto  视频类型内容发布-历史表子表
     * @return Result
     */
    @ApiOperation(value = "新增 视频类型内容发布-历史表子表 ", notes = "新增 视频类型内容发布-历史表子表 ")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentHisVideosSonDto portalContentHisVideosSonDto) {
        Boolean result = portalContentHisVideosSonService.save(portalContentHisVideosSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改 视频类型内容发布-历史表子表
     * @param portalContentHisVideosSonDto  视频类型内容发布-历史表子表
     * @return Result
     */
    @ApiOperation(value = "修改 视频类型内容发布-历史表子表 ", notes = "修改 视频类型内容发布-历史表子表 ")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentHisVideosSonDto portalContentHisVideosSonDto) {
        Boolean result = portalContentHisVideosSonService.update(portalContentHisVideosSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除 视频类型内容发布-历史表子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除 视频类型内容发布-历史表子表 ", notes = "通过id删除 视频类型内容发布-历史表子表 ")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentHisVideosSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
