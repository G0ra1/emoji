package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaterialRefundDto;
import com.netwisd.biz.asset.vo.MaterialRefundVo;
import com.netwisd.biz.asset.service.MaterialRefundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 资产退还 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:46:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialRefund" )
@Api(value = "materialRefund", tags = "资产退还Controller")
@Slf4j
public class MaterialRefundController {

    private final  MaterialRefundService materialRefundService;

    /**
     * 分页查询资产退还
     * 没有使用参数注解，就是默认从form请求的方式
     * @param materialRefundDto 资产退还
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaterialRefundDto materialRefundDto) {
        Page pageVo = materialRefundService.list(materialRefundDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产退还
     * @param materialRefundDto 资产退还
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaterialRefundDto materialRefundDto) {
        Page pageVo = materialRefundService.lists(materialRefundDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产退还
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialRefundVo> get(@PathVariable("id" ) Long id) {
        MaterialRefundVo materialRefundVo = materialRefundService.get(id);
        log.debug("查询成功");
        return Result.success(materialRefundVo);
    }

    /**
     * 新增资产退还
     * @param materialRefundDto 资产退还
     * @return Result
     */
    @ApiOperation(value = "新增资产退还", notes = "新增资产退还")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialRefundDto materialRefundDto) {
        materialRefundService.save(materialRefundDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改资产退还
     * @param materialRefundDto 资产退还
     * @return Result
     */
    @ApiOperation(value = "修改资产退还", notes = "修改资产退还")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialRefundDto materialRefundDto) {
        materialRefundService.update(materialRefundDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除资产退还
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资产退还", notes = "通过id删除资产退还")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        materialRefundService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        materialRefundService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 根据流程实例id获取数据
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}" )
    public Result<MaterialRefundVo> getByProc(@PathVariable("procInstId" ) String procInstId) {
        MaterialRefundVo materialRefundVo = materialRefundService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(materialRefundVo);
    }

}
