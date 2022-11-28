package com.netwisd.base.file.controller;

import com.alibaba.fastjson.JSONObject;
import com.netwisd.base.file.service.FileInfoServiceFactory;
import com.netwisd.base.file.util.ueditor.ActionEnter;
import com.netwisd.base.file.vo.FileInfoVo;
import com.netwisd.base.file.vo.FileResultVo;
import com.netwisd.common.core.exception.IncloudException;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@AllArgsConstructor
@RequestMapping("/ueditor" )
@Api(value = "ueditor", tags = "ueditorController")
@Slf4j
public class UeditorController {

    private final static String defaultStoreType = "default";

    @Autowired
    private FileInfoServiceFactory fileInfoServiceFactory;

    @PostMapping("exec")
    @ResponseBody
    public JSONObject exec(@RequestParam(value = "action") String action,@RequestParam(value = "upfile") MultipartFile upfile){
        if (ObjectUtils.isEmpty(upfile.getOriginalFilename())) {
            throw new IncloudException("请先上传附件！");
        }
        FileResultVo fileResultVo = fileInfoServiceFactory.getFileInfoService(defaultStoreType);
        FileInfoVo fileInfoVo = fileResultVo.getFileInfoService().upload(upfile,fileResultVo.getPoolName(),"portal");
        int i = fileInfoVo.getFileName().lastIndexOf(".");
        String substring = fileInfoVo.getFileName().substring(i);
        String imageStr = "{\"state\":\"SUCCESS\"," +
                "\"size\":"+fileInfoVo.getFileSize()+"," +
                "\"original\":\""+fileInfoVo.getFileName()+"\"," +
                "\"title\":\""+fileInfoVo.getFileMd5Code()+substring+"\"," +
                "\"type\":\""+substring+"\"," +
                "\"url\":\""+fileInfoVo.getFileUrl()+"\"" +
                "}";
        return JSONObject.parseObject(imageStr);

    }

    @GetMapping("exec")
    public void exec(@RequestParam(value = "action") String action, HttpServletResponse response, HttpServletRequest request){
        response.setContentType("application/javascript");
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Credentials","true");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
