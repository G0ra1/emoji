package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentHisNewsDto;
import com.netwisd.base.portal.vo.PortalContentHisNewsVo;
import com.netwisd.base.portal.service.PortalContentHisNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 历史 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-29 19:26:33
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentHisNews" )
@Api(value = "portalContentHisNews", tags = "历史 新闻通告类内容发布Controller")
@Slf4j
public class PortalContentHisNewsController {

    private final  PortalContentHisNewsService portalContentHisNewsService;

    /**
     * 分页查询历史 新闻通告类内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentHisNewsDto 历史 新闻通告类内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentHisNewsDto portalContentHisNewsDto) {
        Page pageVo = portalContentHisNewsService.list(portalContentHisNewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询历史 新闻通告类内容发布
     * @param portalContentHisNewsDto 历史 新闻通告类内容发布
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentHisNewsDto portalContentHisNewsDto) {
        Page pageVo = portalContentHisNewsService.lists(portalContentHisNewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询历史 新闻通告类内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentHisNewsVo> get(@PathVariable("id" ) Long id) {
        PortalContentHisNewsVo portalContentHisNewsVo = portalContentHisNewsService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentHisNewsVo);
    }

    /**
     * 新增历史 新闻通告类内容发布
     * @param portalContentHisNewsDto 历史 新闻通告类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增历史 新闻通告类内容发布", notes = "新增历史 新闻通告类内容发布")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentHisNewsDto portalContentHisNewsDto) {
        Boolean result = portalContentHisNewsService.save(portalContentHisNewsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改历史 新闻通告类内容发布
     * @param portalContentHisNewsDto 历史 新闻通告类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改历史 新闻通告类内容发布", notes = "修改历史 新闻通告类内容发布")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentHisNewsDto portalContentHisNewsDto) {
        Boolean result = portalContentHisNewsService.update(portalContentHisNewsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除历史 新闻通告类内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除历史 新闻通告类内容发布", notes = "通过id删除历史 新闻通告类内容发布")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentHisNewsService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
