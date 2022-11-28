package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaintainApplyDto;
import com.netwisd.biz.asset.vo.MaintainApplyVo;
import com.netwisd.biz.asset.service.MaintainApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 维修申请 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-27 10:28:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/maintainApply" )
@Api(value = "maintainApply", tags = "维修申请Controller")
@Slf4j
public class MaintainApplyController {

    private final  MaintainApplyService maintainApplyService;

    /**
     * 分页查询维修申请
     * 没有使用参数注解，就是默认从form请求的方式
     * @param maintainApplyDto 维修申请
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaintainApplyDto maintainApplyDto) {
        Page pageVo = maintainApplyService.list(maintainApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询维修申请
     * @param maintainApplyDto 维修申请
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaintainApplyDto maintainApplyDto) {
        Page pageVo = maintainApplyService.lists(maintainApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询维修申请
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaintainApplyVo> get(@PathVariable("id" ) Long id) {
        MaintainApplyVo maintainApplyVo = maintainApplyService.get(id);
        log.debug("查询成功");
        return Result.success(maintainApplyVo);
    }

    /**
     * 新增维修申请
     * @param maintainApplyDto 维修申请
     * @return Result
     */
    @ApiOperation(value = "新增维修申请", notes = "新增维修申请")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaintainApplyDto maintainApplyDto) {
        maintainApplyService.save(maintainApplyDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改维修申请
     * @param maintainApplyDto 维修申请
     * @return Result
     */
    @ApiOperation(value = "修改维修申请", notes = "修改维修申请")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaintainApplyDto maintainApplyDto) {
        maintainApplyService.update(maintainApplyDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除维修申请
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除维修申请", notes = "通过id删除维修申请")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        maintainApplyService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }
    /**
     * 通过流程实例id查询资产维修申请
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询",notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}")
    public Result<MaintainApplyVo> getByProc(@PathVariable("procInstId") String procInstId){
        MaintainApplyVo maintainApplyVo = maintainApplyService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(maintainApplyVo);
    }
}
