package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfDuplicateResponseDto;
import com.netwisd.base.wf.vo.WfDuplicateResponseVo;
import com.netwisd.base.wf.service.runtime.WfDuplicateResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 传阅回复
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 22:12:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfduplicateresponse" )
@Api(value = "wfduplicateresponse", tags = "传阅回复Controller")
@Slf4j
public class WfDuplicateResponseController {

    private final  WfDuplicateResponseService wfDuplicateResponseService;

    /**
     * 分页查询传阅回复
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfDuplicateResponseDto 传阅回复
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfDuplicateResponseDto wfDuplicateResponseDto) {
        Page pageVo = wfDuplicateResponseService.list(wfDuplicateResponseDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询传阅回复
     * @param wfDuplicateResponseDto 传阅回复
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfDuplicateResponseDto wfDuplicateResponseDto) {
        Page pageVo = wfDuplicateResponseService.lists(wfDuplicateResponseDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询传阅回复
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfDuplicateResponseVo> get(@PathVariable("id" ) Long id) {
        WfDuplicateResponseVo wfDuplicateResponseVo = wfDuplicateResponseService.get(id);
        log.debug("查询成功");
        return Result.success(wfDuplicateResponseVo);
    }

    /**
     * 新增传阅回复
     * @param wfDuplicateResponseDto 传阅回复
     * @return Result
     */
    @ApiOperation(value = "新增我发起的传阅", notes = "新增我发起的传阅")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfDuplicateResponseDto wfDuplicateResponseDto) {
        Boolean result = wfDuplicateResponseService.save(wfDuplicateResponseDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改传阅回复
     * @param wfDuplicateResponseDto 传阅回复
     * @return Result
     */
    @ApiOperation(value = "修改传阅回复", notes = "修改传阅回复")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfDuplicateResponseDto wfDuplicateResponseDto) {
        Boolean result = wfDuplicateResponseService.update(wfDuplicateResponseDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除传阅回复
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除传阅回复", notes = "通过id删除传阅回复")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfDuplicateResponseService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
