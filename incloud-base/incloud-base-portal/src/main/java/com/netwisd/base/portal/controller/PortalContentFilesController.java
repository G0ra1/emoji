package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentPicturesDto;
import com.netwisd.common.core.anntation.IncludeAnntation;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentFilesDto;
import com.netwisd.base.portal.vo.PortalContentFilesVo;
import com.netwisd.base.portal.service.PortalContentFilesService;
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
 * @Description 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-18 14:46:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentFiles" )
@Api(value = "portalContentFiles", tags = "文件下载类型内容发布Controller")
@Slf4j
public class PortalContentFilesController {

    private final  PortalContentFilesService portalContentFilesService;

    /**
     * 分页查询文件下载类型内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentFilesDto 文件下载类型内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentFilesDto portalContentFilesDto) {
        Page pageVo = portalContentFilesService.list(portalContentFilesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询文件下载类型内容发布
     * @param portalContentFilesDto 文件下载类型内容发布
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentFilesDto portalContentFilesDto) {
        Page pageVo = portalContentFilesService.lists(portalContentFilesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询文件下载类型内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentFilesVo> get(@PathVariable("id" ) Long id) {
        PortalContentFilesVo portalContentFilesVo = portalContentFilesService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentFilesVo);
    }

    /**
     * 新增文件下载类型内容发布
     * @param portalContentFilesDto 文件下载类型内容发布
     * @return Result
     */
    @ApiOperation(value = "新增文件下载类型内容发布", notes = "新增文件下载类型内容发布")
    @PostMapping
    public Result<Boolean> save(@RequestBody PortalContentFilesDto portalContentFilesDto) {
        Boolean result = portalContentFilesService.save(portalContentFilesDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改文件下载类型内容发布
     * @param portalContentFilesDto 文件下载类型内容发布
     * @return Result
     */
    @ApiOperation(value = "修改文件下载类型内容发布", notes = "修改文件下载类型内容发布")
    @PutMapping
    public Result<Boolean> update(@RequestBody PortalContentFilesDto portalContentFilesDto) {
        Boolean result = portalContentFilesService.update(portalContentFilesDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 删除文件下载类型内容发布
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "删除文件下载类型内容发布", notes = "删除文件下载类型内容发布")
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable("ids") String ids) {
        Boolean result = portalContentFilesService.delete(ids);
        log.debug("文件下载类型内容发布成功");
        return Result.success(result);
    }

    /**
     * 展示使用，分页查询
     * @param portalContentFilesDto 文件下载类型内容
     * @return
     */
    @ApiOperation(value = "展示使用，分页查询", notes = "展示使用，分页查询")
    @PostMapping("/listForShow" )
    public Result<Page> listForShow(@RequestBody PortalContentFilesDto portalContentFilesDto) {
        Page pageVo = portalContentFilesService.listForShow(portalContentFilesDto);
        return Result.success(pageVo);
    }

//    /**
//     * 通过流程实例id 查询文件下载类型内容发布
//     * @param procViewDto
//     * @return Result
//     */
//    @ApiOperation(value = "通过流程实例id 查询文件下载类型内容发布", notes = "通过流程实例id 查询文件下载类型内容发布")
//    @PostMapping("/procView" )
//    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
//            PortalContentFilesVo portalContentFilesVo = portalContentFilesService.procView(procViewDto);
//        log.debug("查询成功");
//        return Result.success(JacksonUtil.toJSONString(portalContentFilesVo));
//    }
//
//    /**
//     * 新增文件下载类型内容发布
//     * @param portalContentFilesDto 新增文件下载类型内容发布
//     * @return Result
//     */
//    @ApiOperation(value = "新增文件下载类型内容发布", notes = "新增文件下载类型内容发布")
//    @PostMapping("/procSave")
//    public Result<Boolean> procSave(@RequestBody PortalContentFilesDto portalContentFilesDto) {
//        log.debug("新增文件下载类型内容发布");
//        return Result.success(portalContentFilesService.procSave(portalContentFilesDto));
//    }
//
//    /**
//     * 文件下载类型内容发布提交
//     * @param portalContentFilesDto 文件下载类型内容发布提交
//     * @return Result
//     */
//    @ApiOperation(value = "文件下载类型内容发布提交", notes = "文件下载类型内容发布提交")
//    @PostMapping("/procSubmit")
//    public Result<Boolean> procSubmit(@Validation(exclude = {
//            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentFilesDto portalContentFilesDto) {
//        log.debug("文件下载类型内容发布提交");
//        return Result.success(portalContentFilesService.procSubmit(portalContentFilesDto));
//    }
//
//    /**
//     * 文件下载类型内容-流程删除
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "文件下载类型内容-流程删除", notes = "文件下载类型内容-流程删除")
//    @GetMapping("/procDel")
//    public Result procDel(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("文件下载类型内容-流程删除");
//        return portalContentFilesService.procDel(camundaProcinsId);
//    }
//
//    /**
//     * 文件下载类型内容-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "文件下载类型内容-流程中止", notes = "文件下载类型内容-流程中止")
//    @GetMapping("/procStop")
//    public Result procStop(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("文件下载类型内容-流程中止");
//        return portalContentFilesService.procStop(camundaProcinsId);
//    }
//
//    /**
//     * 文件下载类型内容-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "文件下载类型内容-审批完成", notes = "文件下载类型内容-审批完成")
//    @GetMapping("/auditSucceed")
//    public Result auditSucceed(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("文件下载类型内容-审批完成");
//        return portalContentFilesService.auditSucceed(camundaProcinsId);
//    }
}
