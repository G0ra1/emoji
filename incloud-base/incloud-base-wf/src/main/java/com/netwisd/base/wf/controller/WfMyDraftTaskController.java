package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfDoneTaskDto;
import com.netwisd.base.wf.service.runtime.WfDoneTaskService;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 处理中心-我发起的流程 功能描述...
 * @author 云数网讯 XHL
 * @date 2022-03-10 11:20:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfmydrafttask" )
@Api(value = "wfmydrafttask", tags = "我发起的流程Controller")
@Slf4j
public class WfMyDraftTaskController {

    private final  WfDoneTaskService wfDoneTaskService;

    /**
     * 分页查询 我发起的流程
     * @param wfDoneTaskDto 我发起的流程
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfDoneTaskDto wfDoneTaskDto) {
        Page pageVo = wfDoneTaskService.list(wfDoneTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

}
