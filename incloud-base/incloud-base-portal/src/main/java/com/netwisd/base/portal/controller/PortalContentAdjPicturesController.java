package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentAdjPicturesDto;
import com.netwisd.base.portal.vo.PortalContentAdjPicturesVo;
import com.netwisd.base.portal.service.PortalContentAdjPicturesService;
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
 * @Description 调整 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:15:30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentAdjPictures" )
@Api(value = "portalContentAdjPictures", tags = "调整 图片轮播类内容发布Controller")
@Slf4j
public class PortalContentAdjPicturesController {

    private final  PortalContentAdjPicturesService portalContentAdjPicturesService;

    /**
     * 分页查询调整 图片轮播类内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentAdjPicturesDto 调整 图片轮播类内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        Page pageVo = portalContentAdjPicturesService.list(portalContentAdjPicturesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询调整 图片轮播类内容发布
     * @param portalContentAdjPicturesDto 调整 图片轮播类内容发布
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        Page pageVo = portalContentAdjPicturesService.lists(portalContentAdjPicturesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询调整 图片轮播类内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentAdjPicturesVo> get(@PathVariable("id" ) Long id) {
        PortalContentAdjPicturesVo portalContentAdjPicturesVo = portalContentAdjPicturesService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentAdjPicturesVo);
    }

    /**
     * 新增调整 图片轮播类内容发布
     * @param portalContentAdjPicturesDto 调整 图片轮播类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增调整 图片轮播类内容发布", notes = "新增调整 图片轮播类内容发布")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        Boolean result = portalContentAdjPicturesService.save(portalContentAdjPicturesDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改调整 图片轮播类内容发布
     * @param portalContentAdjPicturesDto 调整 图片轮播类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改调整 图片轮播类内容发布", notes = "修改调整 图片轮播类内容发布")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        Boolean result = portalContentAdjPicturesService.update(portalContentAdjPicturesDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过流程实例id 查询调整 图片轮播类内容发布
     * @param procViewDto
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id 查询调整 图片轮播类内容发布", notes = "通过流程实例id 查询调整 图片轮播类内容发布")
    @PostMapping("/procView" )
    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
            PortalContentAdjPicturesVo portalContentAdjPicturesVo = portalContentAdjPicturesService.procView(procViewDto);
        log.debug("查询成功");
        return Result.success(JacksonUtil.toJSONString(portalContentAdjPicturesVo));
    }

    /**
     * 新增调整 图片轮播类内容发布
     * @param portalContentAdjPicturesDto 新增调整 图片轮播类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增调整 图片轮播类内容发布", notes = "新增调整 图片轮播类内容发布")
    @PostMapping("/procSave")
    public Result<Boolean> procSave(@Validation @RequestBody PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        log.debug("新增调整 图片轮播类内容发布");
        return Result.success(portalContentAdjPicturesService.procSave(portalContentAdjPicturesDto));
    }

    /**
     * 调整 图片轮播类内容发布提交
     * @param portalContentAdjPicturesDto 调整 图片轮播类内容发布提交
     * @return Result
     */
    @ApiOperation(value = "调整 图片轮播类内容发布提交", notes = "调整 图片轮播类内容发布提交")
    @PostMapping("/procSubmit")
    public Result<Boolean> procSubmit(@Validation(exclude = {
            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        log.debug("调整 图片轮播类内容发布提交");
        return Result.success(portalContentAdjPicturesService.procSubmit(portalContentAdjPicturesDto));
    }
}
