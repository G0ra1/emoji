package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentDoneTasksDto;
import com.netwisd.base.portal.vo.PortalContentDoneTasksVo;
import com.netwisd.base.portal.service.PortalContentDoneTasksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 任务集成类内容-已办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 18:17:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentDoneTasks" )
@Api(value = "portalContentDoneTasks", tags = "任务集成类内容-已办Controller")
@Slf4j
public class PortalContentDoneTasksController {

    private final  PortalContentDoneTasksService portalContentDoneTasksService;

    /**
     * 分页查询任务集成类内容-已办
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentDoneTasksDto 任务集成类内容-已办
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentDoneTasksDto portalContentDoneTasksDto) {
        Page pageVo = portalContentDoneTasksService.list(portalContentDoneTasksDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询任务集成类内容-已办
     * @param portalContentDoneTasksDto 任务集成类内容-已办
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody PortalContentDoneTasksDto portalContentDoneTasksDto) {
        List<PortalContentDoneTasksVo> list = portalContentDoneTasksService.lists(portalContentDoneTasksDto);
        return Result.success(list);
    }

    /**
     * 通过id查询任务集成类内容-已办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentDoneTasksVo> get(@PathVariable("id" ) Long id) {
        PortalContentDoneTasksVo portalContentDoneTasksVo = portalContentDoneTasksService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentDoneTasksVo);
    }

    /**
     * 新增任务集成类内容-已办
     * @param portalContentDoneTasksDto 任务集成类内容-已办
     * @return Result
     */
    @ApiOperation(value = "新增任务集成类内容-已办", notes = "新增任务集成类内容-已办")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentDoneTasksDto portalContentDoneTasksDto) {
        Boolean result = portalContentDoneTasksService.save(portalContentDoneTasksDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改任务集成类内容-已办
     * @param portalContentDoneTasksDto 任务集成类内容-已办
     * @return Result
     */
    @ApiOperation(value = "修改任务集成类内容-已办", notes = "修改任务集成类内容-已办")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentDoneTasksDto portalContentDoneTasksDto) {
        Boolean result = portalContentDoneTasksService.update(portalContentDoneTasksDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除任务集成类内容-已办
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除任务集成类内容-已办", notes = "通过id删除任务集成类内容-已办")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentDoneTasksService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
