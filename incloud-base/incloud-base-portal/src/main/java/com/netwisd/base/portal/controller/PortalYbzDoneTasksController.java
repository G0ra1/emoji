package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalYbzDoneTasksDto;
import com.netwisd.base.portal.vo.PortalYbzDoneTasksVo;
import com.netwisd.base.portal.service.PortalYbzDoneTasksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 集成友报账-已办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:59:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalYbzDoneTasks" )
@Api(value = "portalYbzDoneTasks", tags = "集成友报账-已办Controller")
@Slf4j
public class PortalYbzDoneTasksController {

    private final  PortalYbzDoneTasksService portalYbzDoneTasksService;

    /**
     * 分页查询集成友报账-已办
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalYbzDoneTasksDto 集成友报账-已办
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalYbzDoneTasksDto portalYbzDoneTasksDto) {
        Page pageVo = portalYbzDoneTasksService.list(portalYbzDoneTasksDto);
        log.debug("分页查询集成友报账-已办，查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 集合查询集成友报账-已办
     * @param portalYbzDoneTasksDto 集成友报账-已办
     * @return
     */
    @ApiOperation(value = "集合查询集成友报账-已办", notes = "集合查询集成友报账-已办")
    @PostMapping("/lists" )
    public Result<List<PortalYbzDoneTasksVo>> lists(@RequestBody PortalYbzDoneTasksDto portalYbzDoneTasksDto) {
        List<PortalYbzDoneTasksVo> pageVo = portalYbzDoneTasksService.lists(portalYbzDoneTasksDto);
        log.debug("集合查询集成友报账-已办，查询条数:"+pageVo.size());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询集成友报账-已办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalYbzDoneTasksVo> get(@PathVariable("id" ) Long id) {
        PortalYbzDoneTasksVo portalYbzDoneTasksVo = portalYbzDoneTasksService.get(id);
        log.debug("查询成功");
        return Result.success(portalYbzDoneTasksVo);
    }

}
