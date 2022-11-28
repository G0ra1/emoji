package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentPicturesDto;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentVideosDto;
import com.netwisd.base.portal.vo.PortalContentVideosVo;
import com.netwisd.base.portal.service.PortalContentVideosService;
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
 * @Description  视频类内容发布 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-23 14:08:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentVideos" )
@Api(value = "portalContentVideos", tags = " 视频类内容发布Controller")
@Slf4j
public class PortalContentVideosController {

    private final  PortalContentVideosService portalContentVideosService;

    /**
     * 分页查询 视频类内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentVideosDto  视频类内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentVideosDto portalContentVideosDto) {
        Page pageVo = portalContentVideosService.list(portalContentVideosDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 自定义查询 视频类内容发布
     * @param portalContentVideosDto  视频类内容发布
     * @return
     */
    @ApiOperation(value = "自定义查询", notes = "自定义查询")
    @PostMapping("/lists" )
    public Result<List> lists(@RequestBody PortalContentVideosDto portalContentVideosDto) {
        List<PortalContentVideosVo> portalContentVideosVos = portalContentVideosService.lists(portalContentVideosDto);
        log.debug("查询条数:"+portalContentVideosVos.size());
        return Result.success(portalContentVideosVos);
    }

    /**
     * 通过id查询 视频类内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentVideosVo> get(@PathVariable("id" ) Long id) {
        PortalContentVideosVo portalContentVideosVo = portalContentVideosService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentVideosVo);
    }

    /**
     * 新增 视频类内容发布
     * @param portalContentVideosDto  视频类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增 视频类内容发布", notes = "新增 视频类内容发布")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentVideosDto portalContentVideosDto) {
        Boolean result = portalContentVideosService.save(portalContentVideosDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改 视频类内容发布
     * @param portalContentVideosDto  视频类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改 视频类内容发布", notes = "修改 视频类内容发布")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentVideosDto portalContentVideosDto) {
        Boolean result = portalContentVideosService.update(portalContentVideosDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除 视频类内容发布
     * @param ids id
     * @return Result
     */
    @ApiOperation(value = "通过id删除 视频类内容发布", notes = "通过id删除 视频类内容发布")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = portalContentVideosService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 展示使用，分页查询
     * @param portalContentVideosDto 视频类内容发布
     * @return
     */
    @ApiOperation(value = "展示使用，分页查询", notes = "展示使用，分页查询")
    @PostMapping("/listForShow" )
    public Result<Page> listForShow(@RequestBody PortalContentVideosDto portalContentVideosDto) {
        Page pageVo = portalContentVideosService.listForShow(portalContentVideosDto);
        return Result.success(pageVo);
    }


}
