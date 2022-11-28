package com.netwisd.common.core.exception;

import lombok.NoArgsConstructor;
import org.slf4j.helpers.MessageFormatter;

/**
 * @Description: 自定义异常
 * @Author: zouliming
 * @Date: 2020/2/7 11:21 上午
 */
@NoArgsConstructor
public class IncloudHttpException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncloudHttpException(String message) {
        super(message);
        //MailUtil.send("hutool@foxmail.com", "【系统异常】", message, false);
    }

    public IncloudHttpException(String message, Object... arguments) {
        super(MessageFormatter.arrayFormat(message, arguments).getMessage());
    }

    public IncloudHttpException(Throwable cause) {
        super(cause);
    }

    public IncloudHttpException(String message, Throwable cause) {
        super(message, cause);
        //MailUtil.send("hutool@foxmail.com", "【系统异常】", message, false);
    }

    public IncloudHttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        //MailUtil.send("hutool@foxmail.com", "【系统异常】", message, false);
    }

}
