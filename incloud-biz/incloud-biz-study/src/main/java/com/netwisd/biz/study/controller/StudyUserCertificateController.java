package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyUserCertificateDto;
import com.netwisd.biz.study.service.StudyUserCertificateService;
import com.netwisd.biz.study.vo.StudyUserCertificateVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 人员证书
 * @date 2022-04-25 09:39:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyUserCertificate")
@Api(value = "studyUserCertificate", tags = "人员证书Controller")
@Slf4j
public class StudyUserCertificateController {

    private final StudyUserCertificateService studyUserCertificateService;

    /**
     * 分页查询人员证书
     *
     * @param infoDto
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/pageList")
    public Result<Page> pageList(@RequestBody StudyUserCertificateDto infoDto) {
        Page pageVo = studyUserCertificateService.pageList(infoDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 获取证书详情
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "获取证书详情", notes = "获取证书详情")
    @GetMapping("/{id}")
    public Result<StudyUserCertificateVo> detail(@PathVariable("id") Long id) {
        StudyUserCertificateVo studyUserCertificateVo = studyUserCertificateService.detail(id);
        log.debug("查询成功");
        return Result.success(studyUserCertificateVo);
    }

    /**
     * 获取证书流
     *
     * @param id
     * @param response
     */
    @ApiOperation(value = "获取证书流", notes = "获取证书流")
    @GetMapping("/stream/{id}")
    public void stream(@PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) {
        studyUserCertificateService.stream(id, response, request);
    }

}
