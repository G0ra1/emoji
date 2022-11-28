package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfProcessLogDto;
import com.netwisd.base.wf.vo.WfProcessLogVo;
import com.netwisd.base.wf.service.runtime.WfProcessLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 流程日志 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-14 17:42:43
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfprocesslog" )
@Api(value = "wfprocesslog", tags = "流程日志Controller")
@Slf4j
public class WfProcessLogController {

    private final  WfProcessLogService wfProcessLogService;

    /**
     * 分页查询流程日志
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfProcessLogDto 流程日志
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfProcessLogDto wfProcessLogDto) {
        Page pageVo = wfProcessLogService.list(wfProcessLogDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程日志
     * @param wfProcessLogDto 流程日志
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfProcessLogDto wfProcessLogDto) {
        Page pageVo = wfProcessLogService.lists(wfProcessLogDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程日志
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfProcessLogVo> get(@PathVariable("id" ) Long id) {
        WfProcessLogVo wfProcessLogVo = wfProcessLogService.get(id);
        log.debug("查询成功");
        return Result.success(wfProcessLogVo);
    }

    /**
     * 新增流程日志
     * @param wfProcessLogDto 流程日志
     * @return Result
     */
    @ApiOperation(value = "新增流程日志", notes = "新增流程日志")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfProcessLogDto wfProcessLogDto) {
        Boolean result = wfProcessLogService.save(wfProcessLogDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程日志
     * @param wfProcessLogDto 流程日志
     * @return Result
     */
    @ApiOperation(value = "修改流程日志", notes = "修改流程日志")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfProcessLogDto wfProcessLogDto) {
        Boolean result = wfProcessLogService.update(wfProcessLogDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程日志
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程日志", notes = "通过id删除流程日志")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfProcessLogService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }


    /**
     * 判断是否是驳回的草稿状态
     * @param camundaProcinsId camundaProcinsId
     * @return Result
     */
    @ApiOperation(value = "通过camundaProcinsId查询", notes = "通过camundaProcinsId查询")
    @GetMapping("/isDraftByLog" )
    public boolean isDraftByLog(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        return wfProcessLogService.isDraftByLog(camundaProcinsId);
    }

    @ApiOperation(value = "获取流程日志-驳回时使用(驳回列表)",  notes = "获取流程日志-驳回时使用(驳回列表)")
    @GetMapping("/getRejectAllToList/{camundaProcinsId}")
    public Result<List<WfProcessLogVo>> getRejectAllToList(@Validation @PathVariable("camundaProcinsId") String camundaProcinsId){
        List<WfProcessLogVo> list = wfProcessLogService.getRejectAllToList(camundaProcinsId);
        return Result.success(list);
    }

    @ApiOperation(value = "获取流程日志-驳回时使用(驳回列表)",  notes = "获取流程日志-驳回时使用(驳回列表)")
    @GetMapping("/getLogsByInsIdAndNodeId")

    public Result<List<WfProcessLogVo>> getLogsByInsIdAndNodeId(@RequestParam("camundaProcinsId") String camundaProcinsId,@RequestParam("nodeId") String nodeId){
        List<WfProcessLogVo> list = wfProcessLogService.getLogsByInsIdAndNodeId(camundaProcinsId,nodeId);
        return Result.success(list);
    }

    @ApiOperation(value = "获取流程日志-根据camundaProcinsId获取所有人员日志信息",  notes = "获取流程日志-根据camundaProcinsId获取所有人员日志信息")
    @GetMapping("/getList")
    public List<WfProcessLogVo> getList(@RequestParam("camundaProcinsId") String camundaProcinsId){
        return wfProcessLogService.getList(camundaProcinsId,true,true);
    }
}
