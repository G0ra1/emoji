package com.netwisd.common.code.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description: current project incloud
 * current package com.netwisd.common.codegen.mapper
 * @Author: zouliming
 * @Date: 2020/2/4 2:21 下午
 */
@Mapper
public interface GeneratorMapper {

	 /**
	 * 查询表信息
	 *
	 * @param tableName 表名称
	 * @return
	 */
	Map<String, String> queryTable(String tableName);

	/**
	 * 查询表列信息
	 *
	 * @param tableName 表名称
	 * @return
	 */
	List<Map<String, String>> queryColumns(String tableName);
}
