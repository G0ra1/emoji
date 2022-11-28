package com.netwisd.biz.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.entity.Project;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.mdm.dto.ProjectDto;
import com.netwisd.biz.mdm.vo.ProjectVo;
import com.netwisd.biz.mdm.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 项目 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 14:30:06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project" )
@Api(value = "project", tags = "项目Controller")
@Slf4j
public class ProjectController {

    private final  ProjectService projectService;
    private final  MdmMqService mdmMqService;

    /**
     * 分页查询项目
     * 没有使用参数注解，就是默认从form请求的方式
     * @param projectDto 项目
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ProjectDto projectDto) {
        Page pageVo = projectService.list(projectDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询项目
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ProjectVo> get(@PathVariable("id" ) Long id) {
        ProjectVo projectVo = projectService.get(id);
        log.debug("查询成功");
        return Result.success(projectVo);
    }

    /**
     * 新增项目
     * @param projectDto 项目
     * @return Result
     */
    @ApiOperation(value = "新增项目", notes = "新增项目")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ProjectDto projectDto) {
        Boolean result = projectService.save(projectDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改项目
     * @param projectDto 项目
     * @return Result
     */
    @ApiOperation(value = "修改项目", notes = "修改项目")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ProjectDto projectDto) {
        Boolean result = projectService.update(projectDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除项目
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除项目", notes = "通过id删除项目")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = projectService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 数据同步
     * @param projectDtoList
     * @return
     */
    @ApiOperation(value = "数据同步", notes = "数据同步")
    @PostMapping("/saveList" )
    public Result<String> saveList(@Validation @RequestBody List<ProjectDto> projectDtoList){
        projectService.saveList(projectDtoList);
        log.debug("同步成功");
        return  Result.success("同步成功");
    }

    /**
     * 删除
     * @param projectDtoList
     * @return
     */
    @ApiOperation(value = "删除", notes = "删除")
    @PostMapping("/delList" )
    public Result<String> delList(@RequestBody List<ProjectDto> projectDtoList){
        projectService.delList(projectDtoList);
        log.debug("删除成功");
        return  Result.success("删除成功");
    }

    /**
     * 全量数据推送mq
     * @return
     */
    @ApiOperation(value = "全量数据推送mq", notes = "全量数据推送mq")
    @PostMapping("outList")
    public Result<String> outList(ProjectDto projectDto){
        mdmMqService.syncMqForProject(projectDto);
        log.debug("推送成功");
        return Result.success("");
    }

}
