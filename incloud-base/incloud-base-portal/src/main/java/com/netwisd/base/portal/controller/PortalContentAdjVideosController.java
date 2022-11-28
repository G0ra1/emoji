package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentAdjVideosDto;
import com.netwisd.base.portal.vo.PortalContentAdjVideosVo;
import com.netwisd.base.portal.service.PortalContentAdjVideosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.util.JacksonUtil;

import java.util.List;

/**
 * @Description  视频类内容发布-调整表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-31 01:42:07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentAdjVideos" )
@Api(value = "portalContentAdjVideos", tags = " 视频类内容发布-调整表Controller")
@Slf4j
public class PortalContentAdjVideosController {

    private final  PortalContentAdjVideosService portalContentAdjVideosService;

    /**
     * 分页查询 视频类内容发布-调整表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentAdjVideosDto  视频类内容发布-调整表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentAdjVideosDto portalContentAdjVideosDto) {
        Page pageVo = portalContentAdjVideosService.list(portalContentAdjVideosDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询 视频类内容发布-调整表
     * @param portalContentAdjVideosDto  视频类内容发布-调整表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List> lists(@RequestBody PortalContentAdjVideosDto portalContentAdjVideosDto) {
        List<PortalContentAdjVideosVo> portalContentAdjVideosVos = portalContentAdjVideosService.lists(portalContentAdjVideosDto);
        log.debug("查询条数:"+portalContentAdjVideosVos.size());
        return Result.success(portalContentAdjVideosVos);
    }

    /**
     * 通过id查询 视频类内容发布-调整表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentAdjVideosVo> get(@PathVariable("id" ) Long id) {
        PortalContentAdjVideosVo portalContentAdjVideosVo = portalContentAdjVideosService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentAdjVideosVo);
    }

    /**
     * 新增 视频类内容发布-调整表
     * @param portalContentAdjVideosDto  视频类内容发布-调整表
     * @return Result
     */
    @ApiOperation(value = "新增 视频类内容发布-调整表", notes = "新增 视频类内容发布-调整表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentAdjVideosDto portalContentAdjVideosDto) {
        Boolean result = portalContentAdjVideosService.save(portalContentAdjVideosDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改 视频类内容发布-调整表
     * @param portalContentAdjVideosDto  视频类内容发布-调整表
     * @return Result
     */
    @ApiOperation(value = "修改 视频类内容发布-调整表", notes = "修改 视频类内容发布-调整表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentAdjVideosDto portalContentAdjVideosDto) {
        Boolean result = portalContentAdjVideosService.update(portalContentAdjVideosDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除 视频类内容发布-调整表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除 视频类内容发布-调整表", notes = "通过id删除 视频类内容发布-调整表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentAdjVideosService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过流程实例id 查询 视频类内容发布-调整表
     * @param procViewDto
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id 查询 视频类内容发布-调整表", notes = "通过流程实例id 查询 视频类内容发布-调整表")
    @PostMapping("/procView" )
    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
            PortalContentAdjVideosVo portalContentAdjVideosVo = portalContentAdjVideosService.procView(procViewDto);
        log.debug("查询成功");
        return Result.success(JacksonUtil.toJSONString(portalContentAdjVideosVo));
    }

    /**
     * 新增 视频类内容发布-调整表
     * @param portalContentAdjVideosDto 新增 视频类内容发布-调整表
     * @return Result
     */
    @ApiOperation(value = "新增 视频类内容发布-调整表", notes = "新增 视频类内容发布-调整表")
    @PostMapping("/procSave")
    public Result<Boolean> procSave(@Validation @RequestBody PortalContentAdjVideosDto portalContentAdjVideosDto) {
        log.debug("新增 视频类内容发布-调整表");
        return Result.success(portalContentAdjVideosService.procSave(portalContentAdjVideosDto));
    }

    /**
     *  视频类内容发布-调整表提交
     * @param portalContentAdjVideosDto  视频类内容发布-调整表提交
     * @return Result
     */
    @ApiOperation(value = " 视频类内容发布-调整表提交", notes = " 视频类内容发布-调整表提交")
    @PostMapping("/procSubmit")
    public Result<Boolean> procSubmit(@Validation(exclude = {
            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentAdjVideosDto portalContentAdjVideosDto) {
        log.debug(" 视频类内容发布-调整表提交");
        return Result.success(portalContentAdjVideosService.procSubmit(portalContentAdjVideosDto));
    }
    /**
     * 视频类内容发布-调整表流程删除
     *
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = " 视频类内容发布-调整表流程删除", notes = " 视频类内容发布-调整表流程删除")
    @GetMapping("/procDel")
    public Result procDel(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug(" 视频类内容发布-调整表流程删除");
        return portalContentAdjVideosService.procDel(camundaProcinsId);
    }

    /**
     * 视频类内容发布-调整表流程中止
     *
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = " 视频类内容发布-调整表流程中止", notes = " 视频类内容发布-调整表流程中止")
    @GetMapping("/procStop")
    public Result procStop(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug(" 视频类内容发布-调整表流程中止");
        return portalContentAdjVideosService.procStop(camundaProcinsId);
    }

    /**
     * 视频类内容发布-调整表审批完成
     *
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = " 视频类内容发布-调整表审批完成", notes = " 视频类内容发布-调整表审批完成")
    @GetMapping("/auditSucceed")
    public Result auditSucceed(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug(" 视频类内容发布-调整表审批完成");
        return portalContentAdjVideosService.auditSucceed(camundaProcinsId);
    }
}
