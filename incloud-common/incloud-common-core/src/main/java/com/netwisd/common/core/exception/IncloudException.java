package com.netwisd.common.core.exception;

import cn.hutool.extra.mail.MailUtil;
import lombok.NoArgsConstructor;
import org.slf4j.helpers.MessageFormatter;

/**
 * @Description: 自定义异常
 * @Author: zouliming
 * @Date: 2020/2/7 11:21 上午
 */
@NoArgsConstructor
public class IncloudException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncloudException(String message) {
        super(message);
        //MailUtil.send("hutool@foxmail.com", "【系统异常】", message, false);
    }

    public IncloudException(String message, Object... arguments) {
        super(MessageFormatter.arrayFormat(message, arguments).getMessage());
    }

    public IncloudException(Throwable cause) {
        super(cause);
    }

    public IncloudException(String message, Throwable cause) {
        super(message, cause);
        //MailUtil.send("hutool@foxmail.com", "【系统异常】", message, false);
    }

    public IncloudException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        //MailUtil.send("hutool@foxmail.com", "【系统异常】", message, false);
    }

}
