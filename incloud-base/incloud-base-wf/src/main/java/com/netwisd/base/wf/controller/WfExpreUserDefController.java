package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.entity.WfExpreUserDef;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfExpreUserDefDto;
import com.netwisd.base.wf.vo.WfExpreUserDefVo;
import com.netwisd.base.wf.service.procdef.WfExpreUserDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:48:32
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfexpreuserdef" )
@Api(value = "wfexpreuserdef", tags = "人员表达式定义Controller")
@Slf4j
public class WfExpreUserDefController {

    private final  WfExpreUserDefService wfExpreUserDefService;

    /**
     * 分页查询人员表达式定义
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfExpreUserDefDto 人员表达式定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfExpreUserDefDto wfExpreUserDefDto) {
        Page pageVo = wfExpreUserDefService.list(wfExpreUserDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询人员表达式定义
     * @param wfExpreUserDefDto 人员表达式定义
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfExpreUserDefDto wfExpreUserDefDto) {
        Page pageVo = wfExpreUserDefService.lists(wfExpreUserDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询人员表达式定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfExpreUserDefVo> get(@PathVariable("id" ) Long id) {
        WfExpreUserDefVo wfExpreUserDefVo = wfExpreUserDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfExpreUserDefVo);
    }

    /**
     * 新增人员表达式定义
     * @param wfExpreUserDefDto 人员表达式定义
     * @return Result
     */
    @ApiOperation(value = "新增人员表达式定义", notes = "新增人员表达式定义")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfExpreUserDefDto wfExpreUserDefDto) {
        Boolean result = wfExpreUserDefService.save(wfExpreUserDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改人员表达式定义
     * @param wfExpreUserDefDto 人员表达式定义
     * @return Result
     */
    @ApiOperation(value = "修改人员表达式定义", notes = "修改人员表达式定义")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfExpreUserDefDto wfExpreUserDefDto) {
        Boolean result = wfExpreUserDefService.update(wfExpreUserDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除人员表达式定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除人员表达式定义", notes = "通过id删除人员表达式定义")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfExpreUserDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 根据camunda流程定义id 和camunda节点id 获取所有的表达式信息
     * @param camundaProcdefId camundaProcdefId
     * @param camundaNodeDefId nodeDefId
     * @return Result
     */
    @ApiOperation(value = "根据流程定义id 和节点id 获取所有的表达式信息", notes = "根据流程定义id 和节点id 获取所有的表达式信息")
    @GetMapping("/getExpreByCamundaProcIdAndNodeDefId")
    public Result<List<WfExpreUserDef>> getExpreByProcDefIdAndNodeDefId(@RequestParam("camundaProcdefId")String camundaProcdefId, @RequestParam("camundaNodeDefId")String camundaNodeDefId) {
        log.debug("根据camunda流程定义id 和camunda节点id 获取所有的表达式信息 参数：camundaProcdefId[{}]，camundaNodeDefId[{}]",camundaProcdefId, camundaNodeDefId);

        return Result.success(wfExpreUserDefService.getExpreByProcDefIdAndNodeDefId(camundaProcdefId,camundaNodeDefId));
    }

}
