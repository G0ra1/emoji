package com.netwisd.common.code.service;

import com.netwisd.common.code.entity.GenConfig;
import com.netwisd.common.code.entity.ModelConfig;

/**
 * @Description: current project incloud
 * current package com.netwisd.common.codegen.service
 * @Author: zouliming
 * @Date: 2020/2/1 3:01 下午
 */
public interface GeneratorService {
	/**
	 * 生成代码
	 *
	 * @param tableNames 表名称
	 * @param templatePacket 默认生成代码时，使用的模板包
	 * @return
	 */
	byte[] generatorCode(GenConfig tableNames,String templatePacket);
	/**
	 * 生成代码
	 *
	 * @param modelConfig 表和表结构
	 * @return
	 */
	byte[] generatorCode(ModelConfig modelConfig);
}
