package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfCommLanguageDto;
import com.netwisd.base.wf.vo.WfCommLanguageVo;
import com.netwisd.base.wf.service.other.WfCommLanguageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 审批时常用语 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-17 14:45:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfcommlanguage" )
@Api(value = "wfcommlanguage", tags = "审批时常用语Controller")
@Slf4j
public class WfCommLanguageController {

    private final  WfCommLanguageService wfCommLanguageService;

    /**
     * 分页查询审批时常用语
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfCommLanguageDto 审批时常用语
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfCommLanguageDto wfCommLanguageDto) {
        Page pageVo = wfCommLanguageService.list(wfCommLanguageDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询审批时常用语
     * @param wfCommLanguageDto 审批时常用语
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfCommLanguageDto wfCommLanguageDto) {
        Page pageVo = wfCommLanguageService.lists(wfCommLanguageDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询审批时常用语
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfCommLanguageVo> get(@PathVariable("id" ) Long id) {
        WfCommLanguageVo wfCommLanguageVo = wfCommLanguageService.get(id);
        log.debug("查询成功");
        return Result.success(wfCommLanguageVo);
    }

    /**
     * 新增审批时常用语
     * @param wfCommLanguageDto 审批时常用语
     * @return Result
     */
    @ApiOperation(value = "新增审批时常用语", notes = "新增审批时常用语")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfCommLanguageDto wfCommLanguageDto) {
        Boolean result = wfCommLanguageService.save(wfCommLanguageDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改审批时常用语
     * @param wfCommLanguageDto 审批时常用语
     * @return Result
     */
    @ApiOperation(value = "修改审批时常用语", notes = "修改审批时常用语")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfCommLanguageDto wfCommLanguageDto) {
        Boolean result = wfCommLanguageService.update(wfCommLanguageDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除审批时常用语
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除审批时常用语", notes = "通过id删除审批时常用语")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfCommLanguageService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
