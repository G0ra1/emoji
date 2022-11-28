package com.netwisd.common.core.exception;

import lombok.NoArgsConstructor;

/**
 * @Description: 检查时异常
 * @Author: zouliming
 * @Date: 2020/2/7 11:21 上午
 */
@NoArgsConstructor
public class CheckedException extends Exception {
	private static final long serialVersionUID = 1L;

	public CheckedException(String message) {
		super(message);
		//MailUtil.send("hutool@foxmail.com", "【系统异常】", message, false);
	}

	public CheckedException(Throwable cause) {
		super(cause);
	}

	public CheckedException(String message, Throwable cause) {
		super(message, cause);
		//MailUtil.send("hutool@foxmail.com", "【系统异常】", message, false);
	}

	public CheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		//MailUtil.send("hutool@foxmail.com", "【系统异常】", message, false);
	}

}
