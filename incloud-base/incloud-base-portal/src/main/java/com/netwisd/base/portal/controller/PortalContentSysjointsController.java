package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentPicturesDto;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentSysjointsDto;
import com.netwisd.base.portal.vo.PortalContentSysjointsVo;
import com.netwisd.base.portal.service.PortalContentSysjointsService;
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
 * @Description 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:18:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentSysjoints" )
@Api(value = "portalContentSysjoints", tags = "系统集成类内容发布Controller")
@Slf4j
public class PortalContentSysjointsController {

    private final  PortalContentSysjointsService portalContentSysjointsService;

    /**
     * 分页查询系统集成类内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentSysjointsDto 系统集成类内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentSysjointsDto portalContentSysjointsDto) {
        Page pageVo = portalContentSysjointsService.list(portalContentSysjointsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询系统集成类内容发布
     * @param portalContentSysjointsDto 系统集成类内容发布
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentSysjointsDto portalContentSysjointsDto) {
        Page pageVo = portalContentSysjointsService.lists(portalContentSysjointsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询系统集成类内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentSysjointsVo> get(@PathVariable("id" ) Long id) {
        PortalContentSysjointsVo portalContentSysjointsVo = portalContentSysjointsService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentSysjointsVo);
    }

    /**
     * 新增系统集成类内容发布
     * @param portalContentSysjointsDto 系统集成类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增系统集成类内容发布", notes = "新增系统集成类内容发布")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentSysjointsDto portalContentSysjointsDto) {
        Boolean result = portalContentSysjointsService.save(portalContentSysjointsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改系统集成类内容发布
     * @param portalContentSysjointsDto 系统集成类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改系统集成类内容发布", notes = "修改系统集成类内容发布")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentSysjointsDto portalContentSysjointsDto) {
        Boolean result = portalContentSysjointsService.update(portalContentSysjointsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 删除系统集成类内容发布
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "删除系统集成类内容发布", notes = "删除系统集成类内容发布")
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable("ids") String ids) {
        Boolean result = portalContentSysjointsService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 展示使用，分页查询
     * @param portalContentSysjointsDto 系统集成类内容发布
     * @return
     */
    @ApiOperation(value = "展示使用，分页查询", notes = "展示使用，分页查询")
    @PostMapping("/listForShow" )
    public Result<Page> listForShow(@RequestBody PortalContentSysjointsDto portalContentSysjointsDto) {
        Page pageVo = portalContentSysjointsService.listForShow(portalContentSysjointsDto);
        return Result.success(pageVo);
    }

//    /**
//     * 通过流程实例id 查询系统集成类内容发布
//     * @param procViewDto
//     * @return Result
//     */
//    @ApiOperation(value = "通过流程实例id 查询系统集成类内容发布", notes = "通过流程实例id 查询系统集成类内容发布")
//    @PostMapping("/procView" )
//    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
//            PortalContentSysjointsVo portalContentSysjointsVo = portalContentSysjointsService.procView(procViewDto);
//        log.debug("查询成功");
//        return Result.success(JacksonUtil.toJSONString(portalContentSysjointsVo));
//    }
//
//    /**
//     * 新增系统集成类内容发布
//     * @param portalContentSysjointsDto 新增系统集成类内容发布
//     * @return Result
//     */
//    @ApiOperation(value = "新增系统集成类内容发布", notes = "新增系统集成类内容发布")
//    @PostMapping("/procSave")
//    public Result<Boolean> procSave(@Validation @RequestBody PortalContentSysjointsDto portalContentSysjointsDto) {
//        log.debug("新增系统集成类内容发布");
//        return Result.success(portalContentSysjointsService.procSave(portalContentSysjointsDto));
//    }
//
//    /**
//     * 系统集成类内容发布提交
//     * @param portalContentSysjointsDto 系统集成类内容发布提交
//     * @return Result
//     */
//    @ApiOperation(value = "系统集成类内容发布提交", notes = "系统集成类内容发布提交")
//    @PostMapping("/procSubmit")
//    public Result<Boolean> procSubmit(@Validation(exclude = {
//            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentSysjointsDto portalContentSysjointsDto) {
//        log.debug("系统集成类内容发布提交");
//        return Result.success(portalContentSysjointsService.procSubmit(portalContentSysjointsDto));
//    }
//
//    /**
//     * 调整 系统集成类内容发布-流程删除
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "调整 系统集成类内容发布-流程删除", notes = "调整 系统集成类内容发布-流程删除")
//    @GetMapping("/procDel")
//    public Result procDel(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("调整 系统集成类内容发布-流程删除");
//        return portalContentSysjointsService.procDel(camundaProcinsId);
//    }
//
//    /**
//     * 调整 系统集成类内容发布-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "调整 系统集成类内容发布-流程中止", notes = "调整 系统集成类内容发布-流程中止")
//    @GetMapping("/procStop")
//    public Result procStop(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("调整 系统集成类内容发布-流程中止");
//        return portalContentSysjointsService.procStop(camundaProcinsId);
//    }
//
//    /**
//     * 调整 系统集成类内容发布-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "调整 系统集成类内容发布-审批完成", notes = "调整 系统集成类内容发布-审批完成")
//    @GetMapping("/auditSucceed")
//    public Result auditSucceed(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("调整 系统集成类内容发布-审批完成");
//        return portalContentSysjointsService.auditSucceed(camundaProcinsId);
//    }
}
