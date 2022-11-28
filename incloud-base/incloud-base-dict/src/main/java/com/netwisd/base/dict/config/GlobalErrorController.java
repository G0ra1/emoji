package com.netwisd.base.dict.config;

import com.netwisd.common.core.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description: 全局错误控制，不是异常处理，有些错误在框架在容器层面调用reponse响应的错误，异常是捕获不到的，所以写这个类处理http错误状态码的返回
 * @Author: zouliming
 * @Date: 2020/2/8 8:38 下午
 */
@RestController
public class GlobalErrorController implements ErrorController {
    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value=PATH)
    @ResponseBody
    public Result errorHander(HttpServletRequest request, HttpServletResponse response){
        ServletWebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> errorMap = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        Result result = Result.failed(response.getStatus(),errorMap.get("message").toString());
        return result;
    }
}
