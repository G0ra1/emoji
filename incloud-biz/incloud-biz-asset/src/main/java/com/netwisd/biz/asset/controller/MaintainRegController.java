package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.vo.MaintainPlanVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaintainRegDto;
import com.netwisd.biz.asset.vo.MaintainRegVo;
import com.netwisd.biz.asset.service.MaintainRegService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 维修登记 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-28 17:12:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/maintainReg" )
@Api(value = "maintainReg", tags = "维修登记Controller")
@Slf4j
public class MaintainRegController {

    private final  MaintainRegService maintainRegService;

    /**
     * 分页查询维修登记
     * 没有使用参数注解，就是默认从form请求的方式
     * @param maintainRegDto 维修登记
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaintainRegDto maintainRegDto) {
        Page pageVo = maintainRegService.list(maintainRegDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询维修登记
     * @param maintainRegDto 维修登记
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaintainRegDto maintainRegDto) {
        Page pageVo = maintainRegService.lists(maintainRegDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询维修登记
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaintainRegVo> get(@PathVariable("id" ) Long id) {
        MaintainRegVo maintainRegVo = maintainRegService.get(id);
        log.debug("查询成功");
        return Result.success(maintainRegVo);
    }

    /**
     * 新增维修登记
     * @param maintainRegDto 维修登记
     * @return Result
     */
    @ApiOperation(value = "新增维修登记", notes = "新增维修登记")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaintainRegDto maintainRegDto) {
        maintainRegService.save(maintainRegDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改维修登记
     * @param maintainRegDto 维修登记
     * @return Result
     */
    @ApiOperation(value = "修改维修登记", notes = "修改维修登记")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaintainRegDto maintainRegDto) {
        maintainRegService.update(maintainRegDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除维修登记
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除维修登记", notes = "通过id删除维修登记")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        maintainRegService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }
    /**
     * 通过流程实例id查询
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询维修登记详情",notes = "通过流程实例id查询维修登记详情")
    @GetMapping("/proc/{procInstId}")
    public Result<MaintainRegVo> getByProc(@PathVariable("procInstId") String procInstId){
        MaintainRegVo maintainRegVo = maintainRegService.getByProc(procInstId);
        log.debug("查询成功");
        return Result.success(maintainRegVo);
    }

}
