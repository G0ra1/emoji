package com.netwisd.biz.study.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyCertificateTemplateDto;
import com.netwisd.biz.study.service.StudyCertificateTemplateService;
import com.netwisd.biz.study.vo.StudyCertificateTemplateVo;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 证书模板管理
 * @date 2022-04-19 19:31:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyCertificateTemplate")
@Api(value = "studyCertificateTemplate", tags = "证书模板管理Controller")
@Slf4j
public class StudyCertificateTemplateController {

    private final StudyCertificateTemplateService studyCertificateTemplateService;

    /**
     * 分页查询证书模板
     *
     * @param infoDto
     * @return
     */
    @ApiOperation(value = "分页查询证书模板", notes = "分页查询证书模板")
    @PostMapping("/pageList")
    public Result<Page> list(@RequestBody StudyCertificateTemplateDto infoDto) {
        Page pageVo = studyCertificateTemplateService.pageList(infoDto);
        return Result.success(pageVo);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    public Result<StudyCertificateTemplateVo> detail(@PathVariable("id") Long id) {
        log.debug("证书模板通过id获取详情:{}", id);
        return Result.success(studyCertificateTemplateService.get(id));
    }

    /**
     * 新增证书模板
     *
     * @param infoDto
     * @return
     */
    @ApiOperation(value = "新增证书模板", notes = "新增证书模板")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyCertificateTemplateDto infoDto) {
        Boolean result = studyCertificateTemplateService.save(infoDto);
        return Result.success(result);
    }

    /**
     * 修改证书模板
     *
     * @param infoDto
     * @return
     */
    @ApiOperation(value = "修改证书模板", notes = "修改证书模板")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyCertificateTemplateDto infoDto) {
        Boolean result = studyCertificateTemplateService.update(infoDto);
        return Result.success(result);
    }

    /**
     * 通过id批量删除证书模板
     *
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "通过id批量删除证书模板", notes = "通过id批量删除证书模板")
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable String ids) {
        log.debug("通过，ids:{}删除证书模板", ids);
        Boolean result = studyCertificateTemplateService.delete(ids);
        return Result.success(result);
    }


    /**
     * 获取证书模板预览流
     *
     * @param id
     * @param response
     */
    @ApiOperation(value = "获取证书模板预览流", notes = "获取证书模板预览流")
    @GetMapping("/preview/{id}")
    public void previewTemplate(@PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) {
        studyCertificateTemplateService.previewTemplate(id, response, request);
    }

    /**
     * 下载证书模板
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/downloadTemplate")
    public void downFrozenTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String templateName = "证书模板.docx";
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            templateName = java.net.URLEncoder.encode(templateName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            templateName = new String((templateName).getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", templateName));
        response.setContentType("application/force-download");
        //response.setContentType("content-type:octet-stream");
        response.setCharacterEncoding("UTF-8");

        //文件在项目中的存放路径
        String filePath = getClass().getResource("/templates/certificate_template.docx").getPath();

        filePath = URLDecoder.decode(filePath, "UTF-8");
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/certificate_template.docx");
        /*IoUtil.copy(inputStream, response.getOutputStream());*/
        ServletOutputStream out = response.getOutputStream();
        int b = 0;
        byte[] buffer = new byte[1024];
        while ((b = inputStream.read(buffer)) != -1) {
            //写到输出流(out)中
            out.write(buffer, 0, b);
        }
        if (inputStream != null) {
            inputStream.close();
        }

        if (out != null) {
            out.flush();
            out.close();
        }
    }


    /**
     * 下载证书模板
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/downloadTest")
    public void downloadTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String templateName = "证书模板.docx";
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            templateName = java.net.URLEncoder.encode(templateName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            templateName = new String((templateName).getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", templateName));
        response.setContentType("application/force-download");
        //response.setContentType("content-type:octet-stream");
        response.setCharacterEncoding("UTF-8");

        //文件在项目中的存放路径
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/certificate_template.docx");
        ServletOutputStream out = response.getOutputStream();
        long size = IoUtil.copy(inputStream, out);
        log.info("文件流copy了：{}", size);
        if (out != null) {
            out.flush();
            out.close();
        }

        if (inputStream != null) {
            inputStream.close();
        }

    }
}
