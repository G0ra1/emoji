package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudySpecialDto;
import com.netwisd.biz.study.service.StudySpecialService;
import com.netwisd.biz.study.vo.StudySpecialAdjVo;
import com.netwisd.biz.study.vo.StudySpecialVo;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题定义表 功能描述...
 * @date 2022-05-13 10:59:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studySpecial")
@Api(value = "studySpecial", tags = "专题定义表Controller")
@Slf4j
public class StudySpecialController {

    private final StudySpecialService studySpecialService;

    /**
     * 分页查询专题定义申请表
     * 没有使用参数注解，就是默认从form请求的方式
     *
     * @param studySpecialApplyDto 专题定义申请表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/pageList")
    public Result<Page<StudySpecialVo>> pageList(@RequestBody StudySpecialDto studySpecialApplyDto) {
        Page<StudySpecialVo> pageVo = studySpecialService.list(studySpecialApplyDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询专题详情
     *
     * @param id
     * @return Result
     */
    @ApiOperation(value = "通过id查询详情", notes = "通过id查询详情")
    @GetMapping("/{id}")
    public Result<StudySpecialVo> detail(@PathVariable("id") Long id) {
        return Result.success(studySpecialService.detail(id));
    }

    /**
     * 设置专题是否首页展示/启用
     *
     * @param studySpecialDtos 专题定义表
     * @return Result
     */
    @ApiOperation(value = "设置专题是否首页展示/启用", notes = "设置专题是否首页展示/启用")
    @PostMapping("/updateIsIndex")
    public Result<Boolean> updateIsIndexOrEnable(@Validation @RequestBody List<StudySpecialDto> studySpecialDtos) {
        Boolean result = studySpecialService.updateIsIndexOrEnable(studySpecialDtos);
        log.debug("设置成功");
        return Result.success(result);
    }

    @ApiOperation(value = "启用停用", notes = "启用停用")
    @PutMapping("/isEnable/{id}")
    public Result<Boolean> isEnable(@PathVariable("id") Long id) {
        return Result.success(studySpecialService.isEnable(id));
    }

    @ApiOperation(value = "是否首页展示", notes = "是否首页展示")
    @PutMapping("/showIndex/{id}")
    public Result<Boolean> showIndex(@PathVariable("id") Long id) {
        return Result.success(studySpecialService.showIndex(id));
    }

    /**
     * 流程修改
     *
     * @param studySpecialDto
     * @return
     */
    @ApiOperation(value = "流程修改", notes = "流程修改")
    @PutMapping("/procUpdate")
    public Result<StudySpecialVo> procUpdate(@RequestBody @Validation StudySpecialDto studySpecialDto) {
        return Result.success(studySpecialService.procUpdate(studySpecialDto));
    }

    /**
     * 流程新增
     *
     * @param studySpecialDto
     * @return
     */
    @ApiOperation(value = "流程新增", notes = "流程新增")
    @PostMapping("/procSave")
    public Result<StudySpecialVo> procSave(@RequestBody @Validation StudySpecialDto studySpecialDto) {
        return Result.success(studySpecialService.procSave(studySpecialDto));
    }

    /**
     * 流程查看
     *
     * @param procInstId procInstId
     * @return
     */
    @ApiOperation(value = "流程查看", notes = "流程查看")
    @GetMapping("/procDetail/{procInstId}")
    public Result<StudySpecialVo> procDetail(@PathVariable String procInstId) {
        return Result.success(studySpecialService.procDetail(procInstId));
    }

    /**
     * 通过id组装调整信息
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id组装调整信息", notes = "通过id组装调整信息")
    @GetMapping("/detailForAdj/{id}")
    public Result<StudySpecialAdjVo> detailForAdj(@PathVariable("id") Long id) {
        return Result.success(studySpecialService.detailForAdj(id));
    }
    /**
     * 专题删除
     *
     * @param  specialId
     * @return
     */
    @ApiOperation(value = "专题删除", notes = "专题删除")
    @DeleteMapping("/delete/{specialId}")
    public Result<Boolean> procDelete(@PathVariable  Long specialId) {
        return Result.success(studySpecialService.delete(specialId));
    }

}
