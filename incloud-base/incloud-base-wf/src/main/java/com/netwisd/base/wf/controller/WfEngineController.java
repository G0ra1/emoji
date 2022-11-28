package com.netwisd.base.wf.controller;

import com.netwisd.base.common.vo.wf.WfTaskVo;
import com.netwisd.base.wf.service.WfEngineService;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.vo.WfNextUserVo;
import com.netwisd.base.wf.vo.WfProcessLogVo;
import com.netwisd.base.wf.vo.WfVarDefVo;
import com.netwisd.common.core.anntation.License;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 流程运行控制类
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/20 1:16 下午
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wfEngine")
@RefreshScope
@License
public class  WfEngineController {

    private final WfEngineService wfEngineService;

    /**
     * 流程保存
     *
     * @param startDto
     */
    @ApiOperation(value = "流程保存", notes = "流程保存")
    @PostMapping("/save")
    public Result save(@Validation @RequestBody WfEngineDto.StartDto startDto) {
        return Result.success(wfEngineService.save(startDto));
    }

    /**
     * 流程办理
     *
     * @param startDto
     */
    @ApiOperation(value = "流程办理", notes = "流程办理")
    @PostMapping("/handle")
    public Result<Map<String,List<WfNextUserVo>>> handle(@Validation @RequestBody WfEngineDto.StartDto startDto) {
        Map<String, List<WfNextUserVo>> handle = wfEngineService.handle(startDto);
        return Result.success(handle);
    }

    /*
    @ApiOperation(value = "启动流程",  notes = "启动流程")
    @PostMapping("/startProcess")
    public Result<WfDto> startProcess(@Validation @RequestBody WfEngineDto.StartDto startDto){
        WfDto wfDto = wfEngineService.startProcess(startDto);
        return Result.success(wfDto);
    }

    @ApiOperation(value = "获取下一步人员列表",  notes = "获取下一步人员列表")
    @PostMapping("/nextUser")
    public Result<List<WfNextUserVo>> getNextUser(@Validation @RequestBody WfEngineDto wfEngineDto){
        List<WfNextUserVo> users = wfEngineService.getNextUser(wfEngineDto);
        return Result.success(users);
    }

    @ApiOperation(value = "提交获取下一步变量",  notes = "提交获取下一步变量")
    @PostMapping("/getSubmitVariables/{taskId}")
    public Result<Set<WfVarDefVo>> getSubmitVariables(@Validation @PathVariable String taskId){
        Set<WfVarDefVo> variables = wfEngineService.getSubmitVariables(taskId);
        return Result.success(variables);
    }

    @ApiOperation(value = "驳回获取下一步变量",  notes = "驳回获取下一步变量")
    @PostMapping("/getRejectVariables/{taskId}")
    public Result<Set<String>> getRejectVariables(@Validation @PathVariable String taskId){
        Set<String> variables = wfEngineService.getRejectVariables(taskId);
        return Result.success(variables);
    }*/
    @ApiOperation(value = "流程提交", notes = "流程提交")
    @PostMapping("/submit")
    public Result<Boolean> submit(@Validation @RequestBody WfEngineDto.HandleDto handleDto) {
        Boolean result = wfEngineService.submit(handleDto);
        return Result.success(result);
    }

    @ApiOperation(value = "流程签收", notes = "流程签收")
    @PutMapping("/claim/{taskId}")
    public Result<Boolean> claim(@Validation @PathVariable String taskId) {
        Boolean result = wfEngineService.claim(taskId);
        return Result.success(result);
    }

    @ApiOperation(value = "传阅签收", notes = "传阅签收")
    @PutMapping("/claimDuplicate/{duplicateTaskID}")
    public Result<Boolean> claimDuplicate(@Validation @PathVariable Long duplicateTaskID) {
        Boolean result = wfEngineService.claimDuplicate(duplicateTaskID);
        return Result.success(result);
    }

    @ApiOperation(value = "流程加签", notes = "流程加签")
    @PutMapping("/insertNode")
    public Result<Boolean> insertNode(@Validation @RequestBody WfEngineDto.HandleDto handleDto) {
        Boolean result = wfEngineService.insertNode(handleDto);
        return Result.success(result);
    }

    @ApiOperation(value = "流程驳回", notes = "流程驳回")
    @PutMapping("/reject")
    public Result<Boolean> reject(@Validation @RequestBody WfEngineDto.HandleDto handleDto) {
        Boolean result = wfEngineService.reject(handleDto);
        return Result.success(result);
    }

    @ApiOperation(value = "流程撤回", notes = "流程撤回")
    @PutMapping("/revoke/{camundaTaskId}")
    public Result<Boolean> revoke(@Validation @PathVariable String camundaTaskId) {
        Boolean result = wfEngineService.revoke(camundaTaskId);
        return Result.success(result);
    }

    @ApiOperation(value = "流程挂起", notes = "流程挂起")
    @PutMapping("/suspendProcess")
    public Result<Boolean> suspendProcess(@Validation @RequestBody WfEngineDto.StateDto stateDto) {
        Boolean result = wfEngineService.suspendProcess(stateDto);
        return Result.success(result);
    }

    @ApiOperation(value = "流程终止", notes = "流程终止")
    @PutMapping("/endProcess")
    public Result<Boolean> endProcess(@Validation @RequestBody WfEngineDto.StateDto stateDto) {
        Boolean result = wfEngineService.endProcess(stateDto);
        return Result.success(result);
    }

    @ApiOperation(value = "流程激活", notes = "流程激活")
    @PutMapping("/activateProcess")
    public Result<Boolean> activateProcess(@Validation @RequestBody WfEngineDto.StateDto stateDto) {
        Boolean result = wfEngineService.activateProcess(stateDto);
        return Result.success(result);
    }

    @ApiOperation(value = "流程删除-执行事件", notes = "流程删除执行事件")
    @PostMapping("/deleteProcess")
    public Result<Boolean> deleteProcess(@Validation @RequestBody WfEngineDto.StateDto stateDto) {
        Boolean result = wfEngineService.deleteProcess(stateDto);
        return Result.success(result);
    }

    @ApiOperation(value = "只删除流程", notes = "只删除流程")
    @PostMapping("/deleteOnlyProcess")
    public Result<Boolean> deleteOnlyProcess(@Validation @RequestBody WfEngineDto.StateDto stateDto) {
        Boolean result = wfEngineService.deleteOnlyProcess(stateDto);
        return Result.success(result);
    }

    @ApiOperation(value = "获取流程日志-驳回时使用", notes = "获取流程日志-驳回时使用")
    @GetMapping("/getProcessLog/{camundaProcinsId}")
    public Result<List<WfProcessLogVo>> getProcessLog(@Validation @PathVariable("camundaProcinsId") String camundaProcinsId) {
        List<WfProcessLogVo> list = wfEngineService.getList(camundaProcinsId);
        return Result.success(list);
    }

    @ApiOperation(value = "获取流程日志", notes = "获取流程日志")
    @GetMapping("/getAllProcessLog/{camundaProcinsId}")
    public Result<List<WfProcessLogVo>> getAllProcessLog(@PathVariable("camundaProcinsId") String camundaProcinsId) {
        List<WfProcessLogVo> list = wfEngineService.getAllList(camundaProcinsId, null);
        return Result.success(list);
    }

    @ApiOperation(value = "获取子流程或外部流程日志", notes = "获取子流程或外部流程日志")
    @GetMapping("/getchildProcessLogActInsId")
    public Result<List<WfProcessLogVo>> getchildProcessLogActInsId(@RequestParam("camundaCurrentActInsId") String camundaCurrentActInsId) {
        List<WfProcessLogVo> list = wfEngineService.getchildProcessLogActInsId(camundaCurrentActInsId);
        return Result.success(list);
    }

    /*@ApiOperation(value = "获取流程日志",  notes = "获取流程日志")
    @GetMapping("/getRejectAllList/{camundaProcinsId}")
    public Result<List<WfProcessLogVo>> getRejectAllList(@Validation @PathVariable("camundaProcinsId") String camundaProcinsId){
        List<WfProcessLogVo> list = wfEngineService.getList(camundaProcinsId);
        return Result.success(list);
    }*/

    @ApiOperation(value = "根据流程实例ID获取任务信息", notes = "根据流程实例ID获取任务信息")
    @GetMapping("/getTaskByProcInsId/{camundaProcInsId}")
    public Result getTaskByProcInsId(@Validation @PathVariable("camundaProcInsId") String camundaProcInsId) {
        return wfEngineService.getTaskByProcInsId(camundaProcInsId);
    }

    @ApiOperation(value = "被传阅人意见回复", notes = "被传阅人意见回复")
    @PutMapping("/replyDescription/{duplicateTaskID}/{description}")
    public Result<Boolean> replyDescription(@Validation @PathVariable Long duplicateTaskID, @PathVariable String description) {
        Boolean result = wfEngineService.replyDescription(duplicateTaskID, description);
        return Result.success(result);
    }

    @ApiOperation(value = "转办给某人", notes = "转办给某人")
    @PostMapping("/toSb")
    @SneakyThrows
    Result toSb(@Validation @RequestBody List<WfEngineDto.ToSbDto> toSbDtos) {
        return wfEngineService.toSb(toSbDtos);
    }

    @ApiOperation(value = "查询表单数据以及流程定义信息", notes = "查询表单数据以及流程定义信息")
    @GetMapping("/getFormInfo")
    @SneakyThrows
    Result getFormInfo(@RequestParam("taskType") String taskType, @RequestParam("id") String id) {
        return Result.success(wfEngineService.getFormInfo(taskType, id));
    }

    @ApiOperation(value = "收到的知会签收", notes = "收到的知会签收")
    @PutMapping("/claimReceiveNotify/{notifyId}")
    public Result<Boolean> claimReceiveNotify(@Validation @PathVariable String notifyId) {
        Boolean result = wfEngineService.claimReceiveNotify(notifyId);
        return Result.success(result);
    }

    @ApiOperation(value = "收到的知会签收", notes = "收到的知会签收")
    @PutMapping("/handleNotifyOp/{id}/{op}")
    public Result<Boolean> handleNotifyOp(@PathVariable("id") String id, @PathVariable("op") String op) {
        wfEngineService.handleNotifyOp(id, op);
        return Result.success();
    }

    @ApiOperation(value = "流程催办", notes = "流程催办")
    @PutMapping("/handleUrge/{camundaProcInsId}")
    public Result handleUrge(@PathVariable("camundaProcInsId") String camundaProcInsId) {
        wfEngineService.handleUrge(camundaProcInsId);
        return Result.success();
    }

}
