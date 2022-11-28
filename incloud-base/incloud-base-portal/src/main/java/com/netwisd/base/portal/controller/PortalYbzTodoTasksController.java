package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.vo.PortalYbzDoneTasksVo;
import com.netwisd.base.portal.vo.PortalYbzTodoTasksVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalYbzTodoTasksDto;
import com.netwisd.base.portal.service.PortalYbzTodoTasksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description 集成友报账-待办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:57:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalYbzTodoTasks" )
@Api(value = "portalYbzTodoTasks", tags = "集成友报账-待办Controller")
@Slf4j
public class PortalYbzTodoTasksController {

    private final  PortalYbzTodoTasksService portalYbzTodoTasksService;

    /**
     * 分页查询集成友报账-待办
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalYbzTodoTasksDto 集成友报账-待办
     * @return
     */
    @ApiOperation(value = "分页查询集成友报账-待办", notes = "分页查询集成友报账-待办")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalYbzTodoTasksDto portalYbzTodoTasksDto) {
        Page pageVo = portalYbzTodoTasksService.list(portalYbzTodoTasksDto);
        log.debug("分页查询集成友报账-待办，查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 集合查询集成友报账-待办
     * @param portalYbzTodoTasksDto 集成友报账-待办
     * @return
     */
    @ApiOperation(value = "集合查询集成友报账-待办", notes = "集合查询集成友报账-待办")
    @PostMapping("/lists" )
    public Result<List<PortalYbzTodoTasksVo>> lists(@RequestBody PortalYbzTodoTasksDto portalYbzTodoTasksDto) {
        List<PortalYbzTodoTasksVo> ybzTodoTasksVos = portalYbzTodoTasksService.lists(portalYbzTodoTasksDto);
        log.debug("集合查询集成友报账-待办，查询条数:"+ybzTodoTasksVos.size());
        return Result.success(ybzTodoTasksVos);
    }

    /**
     * 通过id查询集成友报账-待办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalYbzTodoTasksVo> get(@PathVariable("id" ) Long id) {
        PortalYbzTodoTasksVo portalYbzTodoTasksVo = portalYbzTodoTasksService.get(id);
        log.debug("查询成功");
        return Result.success(portalYbzTodoTasksVo);
    }

    /**
     * 通过友报账唯一标识删除集成友报账-待办
     * @param ybzId ybzId
     * @return Result
     */
    @ApiOperation(value = "通过友报账唯一标识删除集成友报账-待办", notes = "通过友报账唯一标识删除集成友报账-待办")
    @DeleteMapping("/deleteByYbzId/{id}" )
    public Result<Boolean> deleteByYbzId(@PathVariable String ybzId) {
        Boolean result = portalYbzTodoTasksService.deleteByYbzId(ybzId);
        log.debug("删除成功");
        return Result.success(result);
    }

}
