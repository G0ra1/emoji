package com.netwisd.common.core.util;

import com.netwisd.common.core.constants.ResultConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 统一返回结果集
 * @Author: zouliming
 * @Date: 2020/2/4 4:21 下午
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "统一返回结果集")
@Data
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "返回code")
	private int code;


	@ApiModelProperty(value = "返回msg")
	private String msg;


	@ApiModelProperty(value = "返回数据")
	private T data;

	public static <T> Result<T> success() {
			return returnResult(null, ResultConstants.SUCCESS.code, ResultConstants.SUCCESS.msg);
	}

	public static <T> Result<T> success(Result<T> result) {
		return result;
	}

	public static <T> Result<T> success(T data) {
		return returnResult(data, ResultConstants.SUCCESS.code, ResultConstants.SUCCESS.msg);
	}

	public static <T> Result<T> success(T data, String msg) {
		return returnResult(data, ResultConstants.SUCCESS.code, msg);
	}

	public static <T> Result<T> failed() {
		return returnResult(null, ResultConstants.FAIL.code, null);
	}

	public static <T> Result<T> failed(String msg) {
		return returnResult(null, ResultConstants.FAIL.code, msg);
	}

	public static <T> Result<T> failed(T data) {
		return returnResult(data, ResultConstants.FAIL.code, null);
	}

	public static <T> Result<T> failed(T data, String msg) {
		return returnResult(data, ResultConstants.FAIL.code, msg);
	}

	public static <T> Result<T> failed(T data, int code, String msg) {
		return returnResult(data, code, msg);
	}

	public static <T> Result<T> failed(int code, String msg) {
		return returnResult(null, code, msg);
	}

	private static <T> Result<T> returnResult(T data, int code, String msg) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setData(data);
		result.setMsg(msg);
		return result;
	}
}
