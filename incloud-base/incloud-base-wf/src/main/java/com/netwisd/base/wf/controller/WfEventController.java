package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfEventDto;
import com.netwisd.base.wf.vo.WfEventVo;
import com.netwisd.base.wf.service.procdef.WfEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 事件维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 11:37:15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfevent" )
@Api(value = "wfevent", tags = "事件维护Controller")
@Slf4j
public class WfEventController {

    private final  WfEventService wfEventService;

    /**
     * 分页查询事件维护
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfEventDto 事件维护
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfEventDto wfEventDto) {
        Page pageVo = wfEventService.list(wfEventDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询事件维护
     * @param wfEventDto 事件维护
     * @return
     */
    @ApiOperation(value = "不分页 带子表数据接口", notes = "不分页 带子表数据接口")
    @PostMapping("/lists" )
    public Result<List<WfEventVo>> lists(@RequestBody WfEventDto wfEventDto) {
        List<WfEventVo> wfEventVoList = wfEventService.lists(wfEventDto);
        log.debug("查询条数:"+wfEventVoList.size());
        return Result.success(wfEventVoList);
    }

    /**
     * 通过id查询按钮维护
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfEventVo> get(@PathVariable("id" ) Long id) {
        WfEventVo wfEventVo = wfEventService.get(id);
        log.debug("查询成功");
        return Result.success(wfEventVo);
    }

    /**
     * 新事件维护
     * @param wfEventDto 事件维护
     * @return Result
     */
    @ApiOperation(value = "新增事件维护", notes = "新增事件维护")
    @PostMapping("/save")
    public Result<Boolean> save(@Validation @RequestBody WfEventDto wfEventDto) {
        Boolean result = wfEventService.save(wfEventDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改事件维护
     * @param wfEventDto 事件维护
     * @return Result
     */
    @ApiOperation(value = "修改事件维护", notes = "修改事件维护")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfEventDto wfEventDto) {
        Boolean result = wfEventService.update(wfEventDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除事件维护
     * @param ids
     * @return Result
     */
    @ApiOperation(value = "通过id删除事件维护", notes = "通过id删除事件维护")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = wfEventService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过多个id查询按钮维护
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过多个id查询按钮维护", notes = "通过多个id查询按钮维护")
    @GetMapping("/multipId/{id}" )
    public Result<List<WfEventVo>> get(@PathVariable("id" ) Long ... id) {
        List<Long> ids = new ArrayList<>();
        for (Long aLong : id) {
            ids.add(aLong);
        }
        List<WfEventVo> wfEventVoList = wfEventService.selectBatchIds(ids);
        log.debug("查询成功");
        return Result.success(wfEventVoList);
    }
}
