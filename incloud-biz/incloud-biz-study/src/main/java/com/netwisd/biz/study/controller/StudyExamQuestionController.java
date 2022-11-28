package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyExamQuestionDto;
import com.netwisd.biz.study.vo.StudyExamQuestionVo;
import com.netwisd.biz.study.service.StudyExamQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * @Description 题目定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:55:15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyExamQuestionDef" )
@Api(value = "studyExamQuestion", tags = "题目定义Controller")
@Slf4j
public class StudyExamQuestionController {

    private final  StudyExamQuestionService studyExamQuestionService;

    /**
     * 分页查询题目定义
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyExamQuestionDto 题目定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyExamQuestionDto studyExamQuestionDto) {
        Page pageVo = studyExamQuestionService.list(studyExamQuestionDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 题目查询分页
     * @param studyExamQuestionDto
     * @return
     */
    @ApiOperation(value = "题目查询分页", notes = "题目查询分页")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyExamQuestionDto studyExamQuestionDto) {
        Page pageVo = studyExamQuestionService.lists(studyExamQuestionDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询题目定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyExamQuestionVo> get(@PathVariable("id" ) Long id) {
        StudyExamQuestionVo studyExamQuestionVo = studyExamQuestionService.get(id);
        log.debug("查询成功");
        return Result.success(studyExamQuestionVo);
    }

    /**
     * 新增题目定义
     * @param studyExamQuestionDto 题目定义
     * @return Result
     */
    @ApiOperation(value = "新增题目定义", notes = "新增题目定义")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyExamQuestionDto studyExamQuestionDto) {
        Boolean result = studyExamQuestionService.save(studyExamQuestionDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 批量导入题目
     * @param studyExamDatabase 批量导题
     * @return Result
     */
    @ApiOperation(value = "批量导题", notes = "批量导题")
    @PostMapping("/batchAddQuestion")
    public Result<Boolean> saveList(@Validation @RequestBody List<StudyExamQuestionDto> studyExamDatabase) {
        studyExamQuestionService.saveList(studyExamDatabase);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 读取excel内容
     * @param file
     * @return
     */
    @ApiOperation(value = "读取excel内容", notes = "读取excel内容")
    @PostMapping("/readExcel" )
    public Result<List<StudyExamQuestionVo>> readExcel(MultipartFile file) {
        long startTime = System.currentTimeMillis();
        log.debug("开始读取excel内容，开始时间：{}",new Date());
        List<StudyExamQuestionVo> studyExamQuestionVos = studyExamQuestionService.readExcel(file);
        long endTime = System.currentTimeMillis();
        log.debug("excel文件内容读取成功，结束时间：{}",new Date());
        log.debug("用时：{}s",(endTime-startTime)/1000);
        return Result.success(studyExamQuestionVos);
    }
    /**
     * 修改题目定义
     * @param studyExamQuestionDto 题目定义
     * @return Result
     */
    @ApiOperation(value = "修改题目定义", notes = "修改题目定义")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyExamQuestionDto studyExamQuestionDto) {
        Boolean result = studyExamQuestionService.update(studyExamQuestionDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除题目定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除题目定义", notes = "通过id删除题目定义")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyExamQuestionService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 下载题目导入模板
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/downloadTemplate")
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String templateName = "题目导入模板.xlsx";
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

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/question_template.xlsx");
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

}
