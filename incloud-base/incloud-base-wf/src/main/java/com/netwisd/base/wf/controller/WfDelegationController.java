package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfDelegationDto;
import com.netwisd.base.wf.vo.WfDelegationVo;
import com.netwisd.base.wf.service.other.WfDelegationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 委办维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 14:45:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfdelegation" )
@Api(value = "wfdelegation", tags = "委办维护Controller")
@Slf4j
public class WfDelegationController {

    private final  WfDelegationService wfDelegationService;

    /**
     * 分页查询委办维护
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfDelegationDto 委办维护
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfDelegationDto wfDelegationDto) {
        Page pageVo = wfDelegationService.list(wfDelegationDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页查询委办维护 -管理员
     * @param wfDelegationDto 委办维护
     * @return
     */
    @ApiOperation(value = "分页查询委办维护 -管理员", notes = "分页查询委办维护 -管理员")
    @PostMapping("/delegationListForAd" )
    public Result<Page> delegationListForAd(@RequestBody WfDelegationDto wfDelegationDto) {
        Page pageVo = wfDelegationService.delegationListForAd(wfDelegationDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询委办维护
     * @param wfDelegationDto 委办维护
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfDelegationDto wfDelegationDto) {
        Page pageVo = wfDelegationService.lists(wfDelegationDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询委办维护
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfDelegationVo> get(@PathVariable("id" ) Long id) {
        WfDelegationVo wfDelegationVo = wfDelegationService.get(id);
        log.debug("查询成功");
        return Result.success(wfDelegationVo);
    }

    /**
     * 新增委办维护
     * @param wfDelegationDto 委办维护
     * @return Result
     */
    @ApiOperation(value = "新增委办维护", notes = "新增委办维护")
    @PostMapping("save")
    public Result<Boolean> save(@Validation @RequestBody WfDelegationDto wfDelegationDto) {
        Boolean result = wfDelegationService.save(wfDelegationDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改委办维护
     * @param wfDelegationDto 委办维护
     * @return Result
     */
    @ApiOperation(value = "修改委办维护", notes = "修改委办维护")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfDelegationDto wfDelegationDto) {
        Boolean result = wfDelegationService.update(wfDelegationDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除委办维护
     * @param id id
     * @return Result
     */
//    @ApiOperation(value = "通过id删除委办维护", notes = "通过id删除委办维护")
//    @DeleteMapping("/{id}" )
//    public Result<Boolean> delete(@PathVariable Long id) {
//        Boolean result = wfDelegationService.delete(id);
//        log.debug("删除成功");
//        return Result.success(result);
//    }

    /**
     * 批量删除
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = wfDelegationService.deleteBatchIds(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过多个id查询委办维护
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过多个id查询委办维护", notes = "通过多个id查询委办维护")
    @GetMapping("/multipId/{id}" )
    public Result<List<WfDelegationVo>> get(@PathVariable("id" ) Long ... id) {
        List<Long> ids = new ArrayList<>();
        for (Long aLong : id) {
            ids.add(aLong);
        }
        List<WfDelegationVo> wfDelegationVoList = wfDelegationService.selectBatchIds(ids);
        log.debug("查询成功");
        return Result.success(wfDelegationVoList);
    }

}
