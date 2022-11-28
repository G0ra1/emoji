package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentUnreadTasksDto;
import com.netwisd.base.portal.vo.PortalContentUnreadTasksVo;
import com.netwisd.base.portal.service.PortalContentUnreadTasksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 任务集成类-待阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 17:09:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentUnreadTasks" )
@Api(value = "portalContentUnreadTasks", tags = "任务集成类-待阅Controller")
@Slf4j
public class PortalContentUnreadTasksController {

    private final  PortalContentUnreadTasksService portalContentUnreadTasksService;

    /**
     * 分页查询任务集成类-待阅
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentUnreadTasksDto 任务集成类-待阅
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentUnreadTasksDto portalContentUnreadTasksDto) {
        Page pageVo = portalContentUnreadTasksService.list(portalContentUnreadTasksDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询任务集成类-待阅
     * @param portalContentUnreadTasksDto 任务集成类-待阅
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody PortalContentUnreadTasksDto portalContentUnreadTasksDto) {
        List<PortalContentUnreadTasksVo> list = portalContentUnreadTasksService.lists(portalContentUnreadTasksDto);
        return Result.success(list);
    }

    /**
     * 通过id查询任务集成类-待阅
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentUnreadTasksVo> get(@PathVariable("id" ) Long id) {
        PortalContentUnreadTasksVo portalContentUnreadTasksVo = portalContentUnreadTasksService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentUnreadTasksVo);
    }

    /**
     * 新增任务集成类-待阅
     * @param portalContentUnreadTasksDto 任务集成类-待阅
     * @return Result
     */
    @ApiOperation(value = "新增任务集成类-待阅", notes = "新增任务集成类-待阅")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentUnreadTasksDto portalContentUnreadTasksDto) {
        Boolean result = portalContentUnreadTasksService.save(portalContentUnreadTasksDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改任务集成类-待阅
     * @param portalContentUnreadTasksDto 任务集成类-待阅
     * @return Result
     */
    @ApiOperation(value = "修改任务集成类-待阅", notes = "修改任务集成类-待阅")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentUnreadTasksDto portalContentUnreadTasksDto) {
        Boolean result = portalContentUnreadTasksService.update(portalContentUnreadTasksDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除任务集成类-待阅
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除任务集成类-待阅", notes = "通过id删除任务集成类-待阅")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentUnreadTasksService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
