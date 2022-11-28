package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentTodoTasksDto;
import com.netwisd.base.portal.vo.PortalContentTodoTasksVo;
import com.netwisd.base.portal.service.PortalContentTodoTasksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 任务集成类内容-待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 17:07:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentTodoTasks" )
@Api(value = "portalContentTodoTasks", tags = "任务集成类内容-待办Controller")
@Slf4j
public class PortalContentTodoTasksController {

    private final  PortalContentTodoTasksService portalContentTodoTasksService;

    /**
     * 分页查询任务集成类内容-待办
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentTodoTasksDto 任务集成类内容-待办
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentTodoTasksDto portalContentTodoTasksDto) {
        Page pageVo = portalContentTodoTasksService.list(portalContentTodoTasksDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询任务集成类内容-待办
     * @param portalContentTodoTasksDto 任务集成类内容-待办
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody PortalContentTodoTasksDto portalContentTodoTasksDto) {
        List<PortalContentTodoTasksVo> list = portalContentTodoTasksService.lists(portalContentTodoTasksDto);
        return Result.success(list);
    }

    /**
     * 通过id查询任务集成类内容-待办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentTodoTasksVo> get(@PathVariable("id" ) Long id) {
        PortalContentTodoTasksVo portalContentTodoTasksVo = portalContentTodoTasksService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentTodoTasksVo);
    }

    /**
     * 新增任务集成类内容-待办
     * @param portalContentTodoTasksDto 任务集成类内容-待办
     * @return Result
     */
    @ApiOperation(value = "新增任务集成类内容-待办", notes = "新增任务集成类内容-待办")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentTodoTasksDto portalContentTodoTasksDto) {
        Boolean result = portalContentTodoTasksService.save(portalContentTodoTasksDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改任务集成类内容-待办
     * @param portalContentTodoTasksDto 任务集成类内容-待办
     * @return Result
     */
    @ApiOperation(value = "修改任务集成类内容-待办", notes = "修改任务集成类内容-待办")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentTodoTasksDto portalContentTodoTasksDto) {
        Boolean result = portalContentTodoTasksService.update(portalContentTodoTasksDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除任务集成类内容-待办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除任务集成类内容-待办", notes = "通过id删除任务集成类内容-待办")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentTodoTasksService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
