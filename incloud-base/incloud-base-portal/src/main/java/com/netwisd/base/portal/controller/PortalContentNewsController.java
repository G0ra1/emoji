package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentNewsDto;
import com.netwisd.base.portal.vo.PortalContentNewsVo;
import com.netwisd.base.portal.service.PortalContentNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-16 17:58:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentNews" )
@Api(value = "portalContentNews", tags = "新闻通告类内容发布Controller")
@Slf4j
public class PortalContentNewsController {

    private final  PortalContentNewsService portalContentNewsService;

    /**
     * 分页查询新闻通告类内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentNewsDto 新闻通告类内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentNewsDto portalContentNewsDto) {
        Page pageVo = portalContentNewsService.list(portalContentNewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页集合查询
     * @param portalContentNewsDto 不分页集合查询
     * @return
     */
    @ApiOperation(value = "不分页集合查询", notes = "不分页集合查询")
    @PostMapping("/lists" )
    public Result<List<PortalContentNewsVo>> lists(@RequestBody PortalContentNewsDto portalContentNewsDto) {
        List<PortalContentNewsVo> portalContentNewsVos = portalContentNewsService.lists(portalContentNewsDto);
        log.debug("查询条数:"+portalContentNewsVos.size());
        return Result.success(portalContentNewsVos);
    }

    /**
     * 移动端查全部数据
     * @param portalContentNewsDto 新闻通告类内容发布
     * @return
     */
    @ApiOperation(value = "移动端查全部数据", notes = "移动端查全部数据")
    @PostMapping("/findForMobile" )
    public Result<Page> findForMobile(@RequestBody PortalContentNewsDto portalContentNewsDto) {
        Page pageVo = portalContentNewsService.findForMobile(portalContentNewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询新闻通告类内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentNewsVo> get(@PathVariable("id" ) Long id) {
        PortalContentNewsVo portalContentNewsVo = portalContentNewsService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentNewsVo);
    }

    /**
     * 新增新闻通告类内容发布
     * @param portalContentNewsDto 新闻通告类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增新闻通告类内容发布", notes = "新增新闻通告类内容发布")
    @PostMapping
    public Result<Boolean> save(@RequestBody PortalContentNewsDto portalContentNewsDto) {
        Boolean result = portalContentNewsService.save(portalContentNewsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改新闻通告类内容发布
     * @param portalContentNewsDto 新闻通告类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改新闻通告类内容发布", notes = "修改新闻通告类内容发布")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentNewsDto portalContentNewsDto) {
        Boolean result = portalContentNewsService.update(portalContentNewsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 删除新闻通告类内容发布
     * @param ids Result
     */
    @ApiOperation(value = "删除新闻通告类内容发布", notes = "删除新闻通告类内容发布")
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable("ids") String ids) {
        Boolean result = portalContentNewsService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 增加点击量
     * @param id
     * @return
     */
    @ApiOperation(value = "增加点击量", notes = "增加点击量")
    @GetMapping("/addHits/{id}" )
    public Result<Boolean> addHits(@PathVariable("id" ) Long id) {
        Boolean result = portalContentNewsService.addHits(id);
        log.debug("点击量增加成功");
        return Result.success(result);
    }

//    /**
//     * 通过流程实例id 查询新闻通告类内容发布
//     * @param procViewDto
//     * @return Result
//     */
//    @ApiOperation(value = "通过流程实例id 查询新闻通告类内容发布", notes = "通过流程实例id 查询新闻通告类内容发布")
//    @PostMapping("/procView" )
//    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
//            PortalContentNewsVo portalContentNewsVo = portalContentNewsService.procView(procViewDto);
//        log.debug("查询成功");
//        return Result.success(JacksonUtil.toJSONString(portalContentNewsVo));
//    }
//
//    /**
//     * 新增新闻通告类内容发布
//     * @param portalContentNewsDto 新增新闻通告类内容发布
//     * @return Result
//     */
//    @ApiOperation(value = "新增新闻通告类内容发布", notes = "新增新闻通告类内容发布")
//    @PostMapping("/procSave")
//    public Result<Boolean> procSave(@RequestBody PortalContentNewsDto portalContentNewsDto) {
//        log.debug("新增新闻通告类内容发布");
//        return Result.success(portalContentNewsService.procSave(portalContentNewsDto));
//    }
//
//    /**
//     * 新闻通告类内容发布提交
//     * @param portalContentNewsDto 新闻通告类内容发布提交
//     * @return Result
//     */
//    @ApiOperation(value = "新闻通告类内容发布提交", notes = "新闻通告类内容发布提交")
//    @PostMapping("/procSubmit")
//    public Result<Boolean> procSubmit(@Validation(exclude = {
//            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentNewsDto portalContentNewsDto) {
//        log.debug("新闻通告类内容发布提交");
//        return Result.success(portalContentNewsService.procSubmit(portalContentNewsDto));
//    }
//
//    /**
//     * 新闻通告类内容-流程删除
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "新闻通告类内容-流程删除", notes = "新闻通告类内容-流程删除")
//    @GetMapping("/procDel")
//    public Result procDel(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("新闻通告类内容-流程删除");
//        return portalContentNewsService.procDel(camundaProcinsId);
//    }
//
//    /**
//     * 新闻通告类内容-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "新闻通告类内容-流程中止", notes = "新闻通告类内容-流程中止")
//    @GetMapping("/procStop")
//    public Result procStop(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("新闻通告类内容-流程中止");
//        return portalContentNewsService.procStop(camundaProcinsId);
//    }
//
//    /**
//     * 新闻通告类内容-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "新闻通告类内容-审批完成", notes = "新闻通告类内容-审批完成")
//    @GetMapping("/auditSucceed")
//    public Result auditSucceed(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("新闻通告类内容-审批完成");
//        return portalContentNewsService.auditSucceed(camundaProcinsId);
//    }
}
