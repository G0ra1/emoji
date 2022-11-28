package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfVarDefDto;
import com.netwisd.base.wf.vo.WfVarDefVo;
import com.netwisd.base.wf.service.procdef.WfVarDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 流程定义-变量 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 12:56:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfvardef" )
@Api(value = "wfvardef", tags = "流程定义-变量Controller")
@Slf4j
public class WfVarDefController {

    private final  WfVarDefService wfVarDefService;

    /**
     * 分页查询流程定义-变量
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfVarDefDto 流程定义-变量
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfVarDefDto wfVarDefDto) {
        Page pageVo = wfVarDefService.list(wfVarDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程定义-变量
     * @param wfVarDefDto 流程定义-变量
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfVarDefDto wfVarDefDto) {
        Page pageVo = wfVarDefService.lists(wfVarDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程定义-变量
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfVarDefVo> get(@PathVariable("id" ) Long id) {
        WfVarDefVo wfVarDefVo = wfVarDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfVarDefVo);
    }

    /**
     * 新增流程定义-变量
     * @param wfVarDefDto 流程定义-变量
     * @return Result
     */
    @ApiOperation(value = "新增流程定义-变量", notes = "新增流程定义-变量")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfVarDefDto wfVarDefDto) {
        Boolean result = wfVarDefService.save(wfVarDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程定义-变量
     * @param wfVarDefDto 流程定义-变量
     * @return Result
     */
    @ApiOperation(value = "修改流程定义-变量", notes = "修改流程定义-变量")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfVarDefDto wfVarDefDto) {
        Boolean result = wfVarDefService.update(wfVarDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程定义-变量
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程定义-变量", notes = "通过id删除流程定义-变量")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfVarDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 根据camunda流程定义id 和camunda节点id 获取所有的变量信息
     * @param camundaProcdefId camundaProcdefId
     * @param camundaNodeDefId nodeDefId
     * @return Result
     */
    @ApiOperation(value = "根据camunda流程定义id 和camunda节点id 获取所有的变量信息", notes = "根据camunda流程定义id 和camunda节点id 获取所有的变量信息")
    @GetMapping("/getVarByCamundaProcIdAndNodeDefId")
    public Result<List<WfVarDefVo>> getVarByProcDefIdAndNodeDefId(@RequestParam("camundaProcdefId")String camundaProcdefId, @RequestParam("camundaNodeDefId")String camundaNodeDefId) {
        log.debug("根据流程定义id 和节点id 获取所有的变量信息 参数：camundaProcdefId[{}]，camundaNodeDefId[{}]",camundaProcdefId, camundaNodeDefId);

        return Result.success(wfVarDefService.getVarByProcDefIdAndNodeDefId(camundaProcdefId,camundaNodeDefId,null));
    }

    /**
     * 根据camunda流程定义id 和camunda序列流id  获取所有的变量信息
     * @param camundaProcdefId camundaProcdefId
     * @param camundaSequDefId camundaSequDefId
     * @return Result
     */
    @ApiOperation(value = "根据camunda流程定义id 和camunda序列流id  获取所有的变量信息", notes = "根据camunda流程定义id 和camunda序列流id  获取所有的变量信息")
    @GetMapping("/getVarByProcDefIdAndSequDefId")
    public Result<List<WfVarDefVo>> getVarByProcDefIdAndSequDefId(@RequestParam("camundaProcdefId")String camundaProcdefId, @RequestParam("camundaSequDefId")String camundaSequDefId) {
        log.debug("根据camunda流程定义id 和camunda序列流id  获取所有的变量信息 参数：camundaProcdefId[{}]，nodeDefId[{}]",camundaProcdefId, camundaSequDefId);

        return Result.success(wfVarDefService.getVarByProcDefIdAndSequDefId(camundaProcdefId,camundaSequDefId,null));
    }

}
