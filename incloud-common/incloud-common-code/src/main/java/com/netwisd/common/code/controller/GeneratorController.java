package com.netwisd.common.code.controller;

/*import cn.hutool.core.io.IoUtil;
import com.netwisd.common.code.entity.GenConfig;
import com.netwisd.common.code.service.GeneratorService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;*/

/**
 * @Description: 只是一个测试controller，使用junit测试类生成
 * current package com.netwisd.common.codegen.controller
 * @Author: zouliming
 * @Date: 2020/2/4 2:21 下午
 */
/*@RestController
@AllArgsConstructor
@RequestMapping("/gen")*/
public class GeneratorController {
	//private final GeneratorService generatorService;

	/**
	 * 只是一个测试controller，使用junit测试类生成
	 * 只是一个测试controller，使用junit测试类生成
	 * 只是一个测试controller，使用junit测试类生成
	 */
/*	@SneakyThrows
	@GetMapping("/code")
	public void generatorCode(HttpServletResponse response) {
		GenConfig genConfig = new GenConfig(
				"com.netwisd.common",
				"zouliming",
				"codegen",
				"",
				"incloud_test",
				"自动创建测试表");
		byte[] data = generatorService.generatorCode(genConfig);
		response.reset();
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", genConfig.getTableName()));
		response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
		response.setContentType("application/octet-stream; charset=UTF-8");

		IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
	}*/
}
