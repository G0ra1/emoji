package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentPicnewsDto;
import com.netwisd.base.portal.vo.PortalContentPicnewsVo;
import com.netwisd.base.portal.service.PortalContentPicnewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.util.JacksonUtil;

/**
 * @Description 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-20 10:09:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentPicnews" )
@Api(value = "portalContentPicnews", tags = "图片新闻类内容发布Controller")
@Slf4j
public class PortalContentPicnewsController {

    private final  PortalContentPicnewsService portalContentPicnewsService;

    /**
     * 分页查询图片新闻类内容发布
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentPicnewsDto 图片新闻类内容发布
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentPicnewsDto portalContentPicnewsDto) {
        Page pageVo = portalContentPicnewsService.list(portalContentPicnewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询图片新闻类内容发布
     * @param portalContentPicnewsDto 图片新闻类内容发布
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentPicnewsDto portalContentPicnewsDto) {
        Page pageVo = portalContentPicnewsService.lists(portalContentPicnewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询图片新闻类内容发布
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentPicnewsVo> get(@PathVariable("id" ) Long id) {
        PortalContentPicnewsVo portalContentPicnewsVo = portalContentPicnewsService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentPicnewsVo);
    }

    /**
     * 新增图片新闻类内容发布
     * @param portalContentPicnewsDto 图片新闻类内容发布
     * @return Result
     */
    @ApiOperation(value = "新增图片新闻类内容发布", notes = "新增图片新闻类内容发布")
    @PostMapping
    public Result<Boolean> save(@RequestBody PortalContentPicnewsDto portalContentPicnewsDto) {
        Boolean result = portalContentPicnewsService.save(portalContentPicnewsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改图片新闻类内容发布
     * @param portalContentPicnewsDto 图片新闻类内容发布
     * @return Result
     */
    @ApiOperation(value = "修改图片新闻类内容发布", notes = "修改图片新闻类内容发布")
    @PutMapping
    public Result<Boolean> update(@RequestBody PortalContentPicnewsDto portalContentPicnewsDto) {
        Boolean result = portalContentPicnewsService.update(portalContentPicnewsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 删除图片新闻类内容发布
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "删除图片新闻类内容发布", notes = "删除图片新闻类内容发布")
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable("ids") String ids) {
        Boolean result = portalContentPicnewsService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 增加点击量
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "增加点击量", notes = "增加点击量")
    @GetMapping("/addHits")
    public Result<Boolean> addHits(@RequestParam("id") Long id) {
        Boolean result = portalContentPicnewsService.addHits(id);
        log.debug("增加成功");
        return Result.success(result);
    }

//    /**
//     * 通过流程实例id 查询图片新闻类内容发布
//     * @param procViewDto
//     * @return Result
//     */
//    @ApiOperation(value = "通过流程实例id 查询图片新闻类内容发布", notes = "通过流程实例id 查询图片新闻类内容发布")
//    @PostMapping("/procView" )
//    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
//            PortalContentPicnewsVo portalContentPicnewsVo = portalContentPicnewsService.procView(procViewDto);
//        log.debug("查询成功");
//        return Result.success(JacksonUtil.toJSONString(portalContentPicnewsVo));
//    }
//
//    /**
//     * 新增图片新闻类内容发布
//     * @param portalContentPicnewsDto 新增图片新闻类内容发布
//     * @return Result
//     */
//    @ApiOperation(value = "新增图片新闻类内容发布", notes = "新增图片新闻类内容发布")
//    @PostMapping("/procSave")
//    public Result<Boolean> procSave(@RequestBody PortalContentPicnewsDto portalContentPicnewsDto) {
//        log.debug("新增图片新闻类内容发布");
//        return Result.success(portalContentPicnewsService.procSave(portalContentPicnewsDto));
//    }
//
//    /**
//     * 图片新闻类内容发布提交
//     * @param portalContentPicnewsDto 图片新闻类内容发布提交
//     * @return Result
//     */
//    @ApiOperation(value = "图片新闻类内容发布提交", notes = "图片新闻类内容发布提交")
//    @PostMapping("/procSubmit")
//    public Result<Boolean> procSubmit(@Validation(exclude = {
//            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentPicnewsDto portalContentPicnewsDto) {
//        log.debug("图片新闻类内容发布提交");
//        return Result.success(portalContentPicnewsService.procSubmit(portalContentPicnewsDto));
//    }
//
//    /**
//     * 图片新闻类内容发布-流程删除
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "图片新闻类内容发布-流程删除", notes = "图片新闻类内容发布-流程删除")
//    @GetMapping("/procDel")
//    public Result procDel(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("图片新闻类内容发布-流程删除");
//        return portalContentPicnewsService.procDel(camundaProcinsId);
//    }
//
//    /**
//     * 图片新闻类内容发布-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "图片新闻类内容发布-流程中止", notes = "图片新闻类内容发布-流程中止")
//    @GetMapping("/procStop")
//    public Result procStop(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("图片新闻类内容发布-流程中止");
//        return portalContentPicnewsService.procStop(camundaProcinsId);
//    }
//
//    /**
//     * 图片新闻类内容发布-审批完成
//     * @param camundaProcinsId 流程实例id
//     * @return Result
//     */
//    @ApiOperation(value = "图片新闻类内容发布-审批完成", notes = "图片新闻类内容发布-审批完成")
//    @GetMapping("/auditSucceed")
//    public Result auditSucceed(@RequestParam("camundaProcinsId") String camundaProcinsId) {
//        log.debug("图片新闻类内容发布-审批完成");
//        return portalContentPicnewsService.auditSucceed(camundaProcinsId);
//    }
}
