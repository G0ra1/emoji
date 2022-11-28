package com.netwisd.biz.mdm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: 全局异常处理
 * @Author: zouliming
 * @Date: 2020/2/7 1:00 下午
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler({FeignException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result feignException(FeignException exception){
        log.error("feignException:{}", exception);
        Result result = null;
        try {
            String content = new String(exception.content());
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(content, Map.class);
            String msg = "";
            if(map.containsKey("msg")){
                msg = map.get("msg")+"";
            }else {
                //兼容老平台
                msg = map.get("message")+"";
            }
            result = Result.failed(msg);
        }catch (Exception e){
            result = Result.failed("远程调用失败！");
        }
        return result;
    }

    @ExceptionHandler({IncloudException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result inCloudException(IncloudException exception) {
        log.error("IncloudException:{}", exception);
        Result result = Result.failed(exception.getMessage());
        return result;
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result runtimeException(RuntimeException exception) {
        log.error("RuntimeException:{}", exception);
        Result result = Result.failed(exception.getMessage());
        return result;
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result noHandlerFoundException(NoHandlerFoundException exception) {
        log.error("NoHandlerFoundException:{}", exception);
        Result result = Result.failed(HttpStatus.NOT_FOUND.value(),"请求路径错误或找不到资源！");
        return result;
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exception(HttpServletResponse response, Exception exception) {
        log.error("Exception:{}", exception);
        Result result = Result.failed("未知错误，请联系管理员！");
        if(response.getStatus() == HttpStatus.UNAUTHORIZED.value()){
            result = Result.failed(HttpStatus.UNAUTHORIZED.value(),"无权限请求，请登录！");
        }else if(exception instanceof IOException){
            result = Result.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(),"IO异常！");
        }else{
            //...其他各种异常可以在这处理...
        }
        return result;
    }
}
