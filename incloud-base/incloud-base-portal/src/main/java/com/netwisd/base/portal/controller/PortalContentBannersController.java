package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentBannersDto;
import com.netwisd.base.portal.vo.PortalContentBannersVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.service.PortalContentBannersService;
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
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @Description banner类内容发布 功能描述...
 * @date 2021-08-19 10:17:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentBanners")
@Api(value = "portalContentBanners", tags = "   banner类内容发布Controller")
@Slf4j
public class PortalContentBannersController {

    private final PortalContentBannersService portalContentBannersService;

    /**
     * 分页查询   banner类内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     *
     * @param portalContentBannersDto banner类内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list")
    public Result<Page> list(@RequestBody PortalContentBannersDto portalContentBannersDto) {
        Page pageVo = portalContentBannersService.list(portalContentBannersDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 自定义查询   banner类内容发布
     *
     * @param portalContentBannersDto banner类内容发布
     * @return
     */
    @ApiOperation(value = "自定义查询", notes = "自定义查询")
    @PostMapping("/lists")
    public Result<List> lists(@RequestBody PortalContentBannersDto portalContentBannersDto) {
        List<PortalContentBannersVo> portalContentBannersVos = portalContentBannersService.lists(portalContentBannersDto);
        log.debug("查询条数:" + portalContentBannersVos.size());
        return Result.success(portalContentBannersVos);
    }

    /**
     * 通过id查询   banner类内容发布
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    public Result<PortalContentBannersVo> get(@PathVariable("id") Long id) {
        PortalContentBannersVo portalContentBannersVo = portalContentBannersService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentBannersVo);
    }

    /**
     * 新增   banner类内容发布
     *
     * @param portalContentBannersDto banner类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增   banner类内容发布", notes = "新增   banner类内容发布")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentBannersDto portalContentBannersDto) {
        Boolean result = portalContentBannersService.save(portalContentBannersDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改   banner类内容发布
     *
     * @param portalContentBannersDto banner类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改   banner类内容发布", notes = "修改   banner类内容发布")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentBannersDto portalContentBannersDto) {
        Boolean result = portalContentBannersService.update(portalContentBannersDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除   banner类内容发布
     *
     * @param ids
     * @return Result
     */
    @ApiOperation(value = "通过id删除   banner类内容发布", notes = "通过id删除   banner类内容发布")
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = portalContentBannersService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }



    /**
     * 修改点击量
     *
     * @param id
     * @return Result
     */
    @ApiOperation(value = " 修改点击量", notes = " 修改点击量")
    @GetMapping("/upHits")
    public Result<Boolean> upHits(@RequestParam("id") Long id) {
        Boolean result = portalContentBannersService.upHits(id);
        log.debug("点击量+1");
        return Result.success(result);
    }
}
