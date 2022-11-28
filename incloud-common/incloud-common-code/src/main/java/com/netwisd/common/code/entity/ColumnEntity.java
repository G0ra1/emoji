package com.netwisd.common.code.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: current project incloud
 * current package com.netwisd.common.codegen.entity
 * @Author: zouliming
 * @Date: 2020/2/4 2:41 下午
 */
@Data
public class ColumnEntity {
	/**
	 * 列表
	 */
	private String columnName;
	/**
	 * 数据类型
	 */
	private String dataType;

	/**
	 * 数据类型大写，与@cloumn中的DataType的属性名一致；
	 */
	private String dataTypeCase;
	/**
	 * 备注
	 */
	private String comments;

	/**
	 * 驼峰属性
	 */
	private String caseAttrName;
	/**
	 * 普通属性
	 */
	private String lowerAttrName;
	/**
	 * 属性类型
	 */
	private String attrType;
	/**
	 * 其他信息
	 */
	private String extra;
	/**
	 * 字段类型
	 */
	private String columnType;
	/**
	 * 是否可以为空
	 */
	private Boolean nullable;
	/**
	 * 是否隐藏
	 */
	private Boolean hidden;

	/**
	 * 字段长度
	 */
	private Integer length;

	/**
	 * 精度
	 */
	private Integer precision = 0;

	/**
	 * fk_table_name
	 * 关联表名称
	 */
	private String fkTableName;

	/**
	 * fk_field_name
	 * 关联表字段名称
	 */
	private String fkFieldName;
}
