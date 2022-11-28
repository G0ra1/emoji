package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentPicturesDto;
import com.netwisd.base.portal.vo.PortalContentPicturesVo;
import com.netwisd.base.portal.service.PortalContentPicturesService;
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
 * @Description 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentPictures" )
@Api(value = "portalContentPictures", tags = "图片轮播类内容发布Controller")
@Slf4j
public class PortalContentPicturesController {

    private final  PortalContentPicturesService portalContentPicturesService;

    /**
     * 分页查询图片轮播类内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentPicturesDto 图片轮播类内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentPicturesDto portalContentPicturesDto) {
        Page pageVo = portalContentPicturesService.list(portalContentPicturesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 展示使用，分页查询
     * @param portalContentPicturesDto 图片轮播类内容发布
     * @return
     */
    @ApiOperation(value = "展示使用，分页查询", notes = "展示使用，分页查询")
    @PostMapping("/listForShow" )
    public Result<Page> listForShow(@RequestBody PortalContentPicturesDto portalContentPicturesDto) {
        Page pageVo = portalContentPicturesService.listForShow(portalContentPicturesDto);
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询图片轮播类内容发布
     * @param portalContentPicturesDto 图片轮播类内容发布
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentPicturesDto portalContentPicturesDto) {
        Page pageVo = portalContentPicturesService.lists(portalContentPicturesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询图片轮播类内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentPicturesVo> get(@PathVariable("id" ) Long id) {
        PortalContentPicturesVo portalContentPicturesVo = portalContentPicturesService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentPicturesVo);
    }

    /**
     * 新增图片轮播类内容发布
     * @param portalContentPicturesDto 图片轮播类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增图片轮播类内容发布", notes = "新增图片轮播类内容发布")
    @PostMapping
    public Result<Boolean> save(@RequestBody PortalContentPicturesDto portalContentPicturesDto) {
        Boolean result = portalContentPicturesService.save(portalContentPicturesDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改图片轮播类内容发布
     * @param portalContentPicturesDto 图片轮播类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改图片轮播类内容发布", notes = "修改图片轮播类内容发布")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentPicturesDto portalContentPicturesDto) {
        Boolean result = portalContentPicturesService.update(portalContentPicturesDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 删除图片轮播类内容发布
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "删除图片轮播类内容发布", notes = "删除图片轮播类内容发布")
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable("ids") String ids) {
        Boolean result = portalContentPicturesService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

//    /**
//     * 通过流程实例id 查询图片轮播类内容发布
//     * @param procViewDto
//     * @return Result
//     */
//    @ApiOperation(value = "通过流程实例id 查询图片轮播类内容发布", notes = "通过流程实例id 查询图片轮播类内容发布")
//    @PostMapping("/procView" )
//    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
//            PortalContentPicturesVo portalContentPicturesVo = portalContentPicturesService.procView(procViewDto);
//        log.debug("查询成功");
//        return Result.success(JacksonUtil.toJSONString(portalContentPicturesVo));
//    }
//
//    /**
//     * 新增图片轮播类内容发布
//     * @param portalContentPicturesDto 新增图片轮播类内容发布
//     * @return Result
//     */
//    @ApiOperation(value = "新增图片轮播类内容发布", notes = "新增图片轮播类内容发布")
//    @PostMapping("/procSave")
//    public Result<Boolean> procSave(@Validation @RequestBody PortalContentPicturesDto portalContentPicturesDto) {
//        log.debug("新增图片轮播类内容发布");
//        return Result.success(portalContentPicturesService.procSave(portalContentPicturesDto));
//    }
//
//    /**
//     * 图片轮播类内容发布提交
//     * @param portalContentPicturesDto 图片轮播类内容发布提交
//     * @return Result
//     */
//    @ApiOperation(value = "图片轮播类内容发布提交", notes = "图片轮播类内容发布提交")
//    @PostMapping("/procSubmit")
//    public Result<Boolean> procSubmit(@Validation(exclude = {
//            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentPicturesDto portalContentPicturesDto) {
//        log.debug("图片轮播类内容发布提交");
//        return Result.success(portalContentPicturesService.procSubmit(portalContentPicturesDto));
//    }
//
//    /**
//     * 图片轮播类内容发布-流程删除
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "图片轮播类内容发布-流程删除", notes = "图片轮播类内容发布-流程删除")
//    @GetMapping("/procDel")
//    public Result procDel(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("图片轮播类内容发布-流程删除");
//        return portalContentPicturesService.procDel(camundaProcinsId);
//    }
//
//    /**
//     * 图片轮播类内容发布-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "图片轮播类内容发布-流程中止", notes = "图片轮播类内容发布-流程中止")
//    @GetMapping("/procStop")
//    public Result procStop(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("图片轮播类内容发布-流程中止");
//        return portalContentPicturesService.procStop(camundaProcinsId);
//    }
//
//    /**
//     * 图片轮播类内容发布-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "图片轮播类内容发布-审批完成", notes = "图片轮播类内容发布-审批完成")
//    @GetMapping("/auditSucceed")
//    public Result auditSucceed(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("图片轮播类内容发布-审批完成");
//        return portalContentPicturesService.auditSucceed(camundaProcinsId);
//    }
}
