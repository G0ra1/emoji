package com.netwisd.common.code.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description: current project incloud
 * current package com.netwisd.common.codegen.entity
 * @Author: zouliming
 * @Date: 2020/2/4 2:31 下午
 */
@Data
@AllArgsConstructor
public class GenConfig {
	/**
	 * 包名
	 */
	private String packageName;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 表前缀
	 */
	private String tablePrefix;

	/**
	 * 表名称
	 */
	private String tableName;

	/**
	 * 表备注
	 */
	private String comments;
}
