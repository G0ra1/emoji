package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentAdjFilesDto;
import com.netwisd.base.portal.vo.PortalContentAdjFilesVo;
import com.netwisd.base.portal.service.PortalContentAdjFilesService;
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

/**
 * @Description 调整 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:20:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentAdjFiles" )
@Api(value = "portalContentAdjFiles", tags = "调整 文件下载类型内容发布Controller")
@Slf4j
public class PortalContentAdjFilesController {

    private final  PortalContentAdjFilesService portalContentAdjFilesService;

    /**
     * 分页查询调整 文件下载类型内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentAdjFilesDto 调整 文件下载类型内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentAdjFilesDto portalContentAdjFilesDto) {
        Page pageVo = portalContentAdjFilesService.list(portalContentAdjFilesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询调整 文件下载类型内容发布
     * @param portalContentAdjFilesDto 调整 文件下载类型内容发布
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentAdjFilesDto portalContentAdjFilesDto) {
        Page pageVo = portalContentAdjFilesService.lists(portalContentAdjFilesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询调整 文件下载类型内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentAdjFilesVo> get(@PathVariable("id" ) Long id) {
        PortalContentAdjFilesVo portalContentAdjFilesVo = portalContentAdjFilesService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentAdjFilesVo);
    }

    /**
     * 新增调整 文件下载类型内容发布
     * @param portalContentAdjFilesDto 调整 文件下载类型内容发布
     * @return Result
     */
    @ApiOperation(value = "新增调整 文件下载类型内容发布", notes = "新增调整 文件下载类型内容发布")
    @PostMapping
    public Result<Boolean> save(@RequestBody PortalContentAdjFilesDto portalContentAdjFilesDto) {
        Boolean result = portalContentAdjFilesService.save(portalContentAdjFilesDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改调整 文件下载类型内容发布
     * @param portalContentAdjFilesDto 调整 文件下载类型内容发布
     * @return Result
     */
    @ApiOperation(value = "修改调整 文件下载类型内容发布", notes = "修改调整 文件下载类型内容发布")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentAdjFilesDto portalContentAdjFilesDto) {
        Boolean result = portalContentAdjFilesService.update(portalContentAdjFilesDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过流程实例id 查询调整 文件下载类型内容发布
     * @param procViewDto
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id 查询调整 文件下载类型内容发布", notes = "通过流程实例id 查询调整 文件下载类型内容发布")
    @PostMapping("/procView" )
    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
            PortalContentAdjFilesVo portalContentAdjFilesVo = portalContentAdjFilesService.procView(procViewDto);
        log.debug("查询成功");
        return Result.success(JacksonUtil.toJSONString(portalContentAdjFilesVo));
    }

    /**
     * 新增调整 文件下载类型内容发布
     * @param portalContentAdjFilesDto 新增调整 文件下载类型内容发布
     * @return Result
     */
    @ApiOperation(value = "新增调整 文件下载类型内容发布", notes = "新增调整 文件下载类型内容发布")
    @PostMapping("/procSave")
    public Result<Boolean> procSave(@Validation @RequestBody PortalContentAdjFilesDto portalContentAdjFilesDto) {
        log.debug("新增调整 文件下载类型内容发布");
        return Result.success(portalContentAdjFilesService.procSave(portalContentAdjFilesDto));
    }

    /**
     * 调整 文件下载类型内容发布提交
     * @param portalContentAdjFilesDto 调整 文件下载类型内容发布提交
     * @return Result
     */
    @ApiOperation(value = "调整 文件下载类型内容发布提交", notes = "调整 文件下载类型内容发布提交")
    @PostMapping("/procSubmit")
    public Result<Boolean> procSubmit(@Validation(exclude = {
            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentAdjFilesDto portalContentAdjFilesDto) {
        log.debug("调整 文件下载类型内容发布提交");
        return Result.success(portalContentAdjFilesService.procSubmit(portalContentAdjFilesDto));
    }

    /**
     * 调整 文件下载类型内容发布-流程删除
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = "调整 文件下载类型内容发布-流程删除", notes = "调整 文件下载类型内容发布-流程删除")
    @GetMapping("/procDel")
    public Result procDel(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug("调整 文件下载类型内容发布-流程删除");
        return portalContentAdjFilesService.procDel(camundaProcinsId);
    }

    /**
     * 调整 文件下载类型内容发布-审批完成
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = "调整 文件下载类型内容发布-流程中止", notes = "调整 文件下载类型内容发布-流程中止")
    @GetMapping("/procStop")
    public Result procStop(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug("调整 文件下载类型内容发布-流程中止");
        return portalContentAdjFilesService.procStop(camundaProcinsId);
    }

    /**
     * 调整 文件下载类型内容发布-审批完成
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = "调整 文件下载类型内容发布-审批完成", notes = "调整 文件下载类型内容发布-审批完成")
    @GetMapping("/auditSucceed")
    public Result auditSucceed(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug("调整 文件下载类型内容发布-审批完成");
        return portalContentAdjFilesService.auditSucceed(camundaProcinsId);
    }
}
