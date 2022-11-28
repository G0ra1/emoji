package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentAdjNewsDto;
import com.netwisd.base.portal.vo.PortalContentAdjNewsVo;
import com.netwisd.base.portal.service.PortalContentAdjNewsService;
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
 * @Description 调整 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-27 15:26:39
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentAdjNews" )
@Api(value = "portalContentAdjNews", tags = "调整 新闻通告类内容发布Controller")
@Slf4j
public class PortalContentAdjNewsController {

    private final  PortalContentAdjNewsService portalContentAdjNewsService;

    /**
     * 分页查询调整 新闻通告类内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentAdjNewsDto 调整 新闻通告类内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentAdjNewsDto portalContentAdjNewsDto) {
        Page pageVo = portalContentAdjNewsService.list(portalContentAdjNewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询调整 新闻通告类内容发布
     * @param portalContentAdjNewsDto 调整 新闻通告类内容发布
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentAdjNewsDto portalContentAdjNewsDto) {
        Page pageVo = portalContentAdjNewsService.lists(portalContentAdjNewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询调整 新闻通告类内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentAdjNewsVo> get(@PathVariable("id" ) Long id) {
        PortalContentAdjNewsVo portalContentAdjNewsVo = portalContentAdjNewsService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentAdjNewsVo);
    }

    /**
     * 新增调整 新闻通告类内容发布
     * @param portalContentAdjNewsDto 调整 新闻通告类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增调整 新闻通告类内容发布", notes = "新增调整 新闻通告类内容发布")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentAdjNewsDto portalContentAdjNewsDto) {
        Boolean result = portalContentAdjNewsService.save(portalContentAdjNewsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改调整 新闻通告类内容发布
     * @param portalContentAdjNewsDto 调整 新闻通告类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改调整 新闻通告类内容发布", notes = "修改调整 新闻通告类内容发布")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentAdjNewsDto portalContentAdjNewsDto) {
        Boolean result = portalContentAdjNewsService.update(portalContentAdjNewsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过流程实例id 查询调整 新闻通告类内容发布
     * @param procViewDto
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id 查询调整 新闻通告类内容发布", notes = "通过流程实例id 查询调整 新闻通告类内容发布")
    @PostMapping("/procView" )
    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
            PortalContentAdjNewsVo portalContentAdjNewsVo = portalContentAdjNewsService.procView(procViewDto);
        log.debug("查询成功");
        return Result.success(JacksonUtil.toJSONString(portalContentAdjNewsVo));
    }

    /**
     * 新增调整 新闻通告类内容发布
     * @param portalContentAdjNewsDto 新增调整 新闻通告类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增调整 新闻通告类内容发布", notes = "新增调整 新闻通告类内容发布")
    @PostMapping("/procSave")
    public Result<Boolean> procSave(@Validation @RequestBody PortalContentAdjNewsDto portalContentAdjNewsDto) {
        log.debug("新增调整 新闻通告类内容发布");
        return Result.success(portalContentAdjNewsService.procSave(portalContentAdjNewsDto));
    }

    /**
     * 调整 新闻通告类内容发布提交
     * @param portalContentAdjNewsDto 调整 新闻通告类内容发布提交
     * @return Result
     */
    @ApiOperation(value = "调整 新闻通告类内容发布提交", notes = "调整 新闻通告类内容发布提交")
    @PostMapping("/procSubmit")
    public Result<Boolean> procSubmit(@Validation(exclude = {
            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentAdjNewsDto portalContentAdjNewsDto) {
        log.debug("调整 新闻通告类内容发布提交");
        return Result.success(portalContentAdjNewsService.procSubmit(portalContentAdjNewsDto));
    }

    /**
     * 调整新闻通告类内容-流程删除
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = "调整新闻通告类内容-流程删除", notes = "调整新闻通告类内容-流程删除")
    @GetMapping("/procDel")
    public Result procDel(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug("调整新闻通告类内容-流程删除");
        return portalContentAdjNewsService.procDel(camundaProcinsId);
    }

    /**
     * 调整新闻通告类内容-审批完成
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = "调整新闻通告类内容-流程中止", notes = "调整新闻通告类内容-流程中止")
    @GetMapping("/procStop")
    public Result procStop(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug("调整新闻通告类内容-流程中止");
        return portalContentAdjNewsService.procStop(camundaProcinsId);
    }

    /**
     * 调整新闻通告类内容-审批完成
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = "调整新闻通告类内容-审批完成", notes = "调整新闻通告类内容-审批完成")
    @GetMapping("/auditSucceed")
    public Result auditSucceed(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug("调整新闻通告类内容-审批完成");
        return portalContentAdjNewsService.auditSucceed(camundaProcinsId);
    }
}
