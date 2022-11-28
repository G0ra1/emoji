package com.netwisd.base.model.controller;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.netwisd.common.code.entity.ModelConfig;
import com.netwisd.common.code.service.GeneratorService;
import com.netwisd.common.core.exception.IncloudException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author zouliming@netwisd.com
 * @Description:
 * @date 2021/11/12 15:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/gen")
@Slf4j
public class GeneratorController {
    private final GeneratorService generatorService;

    /**
     * 下载代码附件
     */
    @PostMapping
    public void generatorCode(HttpServletResponse response, @RequestBody ModelConfig modelConfig) {
        try {
            byte[] data = generatorService.generatorCode(modelConfig);
            response.reset();
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", "generator"));
            //response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
            response.setContentType("application/octet-stream; charset=UTF-8");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
        } catch (Exception e) {
            log.error("文件生成异常", e);
            throw new IncloudException("文件生成异常");
        }
    }

    /**
     * 下载代码附件
     */
    @GetMapping
    public void test(HttpServletResponse response) {
        String json = ResourceUtil.readUtf8Str("gen/gen.json");
        ModelConfig modelConfig = JSONUtil.toBean(json, ModelConfig.class);
        byte[] data = generatorService.generatorCode(modelConfig);
        try {
            response.reset();
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", "generator"));
            //response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
            response.setContentType("application/octet-stream; charset=UTF-8");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
        } catch (Exception e) {
            log.error("文件生成异常", e);
            throw new IncloudException("文件生成异常");
        }
    }
}