package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.vo.wf.WfProcessVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfProcessDto;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description 流程实例 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 13:49:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfprocess" )
@Api(value = "wfprocess", tags = "流程实例Controller")
@Slf4j
public class WfProcessController {

    private final  WfProcessService wfProcessService;

    /**
     * 分页查询流程实例
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfProcessDto 流程实例
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfProcessDto wfProcessDto) {
        Page pageVo = wfProcessService.list(wfProcessDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程实例
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfProcessVo> get(@PathVariable("id" ) Long id) {
        WfProcessVo wfProcessVo = wfProcessService.get(id);
        log.debug("查询成功");
        return Result.success(wfProcessVo);
    }


    /**
     * 通过id删除流程实例
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程实例", notes = "通过id删除流程实例")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfProcessService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    @ApiOperation(value = "新建测试", notes = "新建测试")
    @PostMapping("/save" )
    public Result<Boolean> save(@RequestBody WfProcessDto wfProcessDto) {
        Boolean save = wfProcessService.save(wfProcessDto);
        return Result.success(save);
    }

}
