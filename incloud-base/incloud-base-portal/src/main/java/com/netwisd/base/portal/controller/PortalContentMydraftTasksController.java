package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentMydraftTasksDto;
import com.netwisd.base.portal.vo.PortalContentMydraftTasksVo;
import com.netwisd.base.portal.service.PortalContentMydraftTasksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 任务集成类-我发起的任务 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 15:27:43
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentMydraftTasks" )
@Api(value = "portalContentMydraftTasks", tags = "任务集成类-我发起的任务Controller")
@Slf4j
public class PortalContentMydraftTasksController {

    private final  PortalContentMydraftTasksService portalContentMydraftTasksService;

    /**
     * 分页查询任务集成类-我发起的任务
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentMydraftTasksDto 任务集成类-我发起的任务
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentMydraftTasksDto portalContentMydraftTasksDto) {
        Page pageVo = portalContentMydraftTasksService.list(portalContentMydraftTasksDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询任务集成类-我发起的任务
     * @param portalContentMydraftTasksDto 任务集成类-我发起的任务
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody PortalContentMydraftTasksDto portalContentMydraftTasksDto) {
        List<PortalContentMydraftTasksVo> list = portalContentMydraftTasksService.lists(portalContentMydraftTasksDto);
        return Result.success(list);
    }

    /**
     * 通过id查询任务集成类-我发起的任务
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentMydraftTasksVo> get(@PathVariable("id" ) Long id) {
        PortalContentMydraftTasksVo portalContentMydraftTasksVo = portalContentMydraftTasksService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentMydraftTasksVo);
    }

    /**
     * 新增任务集成类-我发起的任务
     * @param portalContentMydraftTasksDto 任务集成类-我发起的任务
     * @return Result
     */
    @ApiOperation(value = "新增任务集成类-我发起的任务", notes = "新增任务集成类-我发起的任务")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentMydraftTasksDto portalContentMydraftTasksDto) {
        Boolean result = portalContentMydraftTasksService.save(portalContentMydraftTasksDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改任务集成类-我发起的任务
     * @param portalContentMydraftTasksDto 任务集成类-我发起的任务
     * @return Result
     */
    @ApiOperation(value = "修改任务集成类-我发起的任务", notes = "修改任务集成类-我发起的任务")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentMydraftTasksDto portalContentMydraftTasksDto) {
        Boolean result = portalContentMydraftTasksService.update(portalContentMydraftTasksDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除任务集成类-我发起的任务
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除任务集成类-我发起的任务", notes = "通过id删除任务集成类-我发起的任务")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentMydraftTasksService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
