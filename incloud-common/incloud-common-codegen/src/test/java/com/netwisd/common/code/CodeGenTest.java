package com.netwisd.common.code;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.common.code.constants.ModelPropertyEnum;
import com.netwisd.common.code.entity.*;
import com.netwisd.common.code.service.GeneratorService;
import com.netwisd.common.core.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: current project incloud
 * current package com.netwisd.common.codegen
 * @Author: zouliming
 * @Date: 2020/2/4 2:30 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CodeGenTest {

	@Autowired
	private GeneratorService generatorService;

//	@Value("panfakun")
	private String incloud_code = "panfakun";

	//默认生成代码时，使用的模板包：common / wf
	private static String templatePacket = "common";

	private String packageName = "com.netwisd.base";//基本包路径
	private String tablePrefix = "incloud_base_";//前缀，默认为""是incloud_，写上前缀，生成实体时会去掉前缀
	private String moduleName = "file";//其实就是生成相关代码包时的最上一层包名，为了生成时好处理
	private String tableName = "incloud_base_file_ds";//表名，实际数据库的表名，包括前缀
	private String comments = "文件数据源";
	private GenConfig genConfig = new GenConfig(packageName, incloud_code, moduleName, tablePrefix, tableName, comments);

	@Test
	public void build4Mac() {
		writeBytes2File("/Users/zouliming/gen/"+tableName+".zip");
	}

	@Test
	public void build4Win() {
		writeBytes2File("D:/gen/"+tableName+".zip");
	}

	@Test
	public void build4Web() {
		writeBytes2FileByWeb("D:\\1\\AutoBuild.zip");
	}

	private void writeBytes2File(String path){
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		genConfig.setAuthor(incloud_code);
		byte[] data = generatorService.generatorCode(genConfig, templatePacket);
		FileUtil.writeBytes(data,file);
	}

	private void writeBytes2FileByWeb(String path){
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		ModelConfig modelConfig = mockModelConfig(ModelPropertyEnum.ASSOCIATION);
		templatePacket = "common";
		modelConfig.setTemplatePacket(templatePacket);

		log.info("====================>{}", JacksonUtil.toJSONString(modelConfig));

		byte[] data = generatorService.generatorCode(modelConfig);
		FileUtil.writeBytes(data,file);
	}

	final static String defaultColumnName = "amount";

	ModelConfig mockModelConfig(ModelPropertyEnum modelPropertyEnum){
		ModelConfig modelConfig = new ModelConfig();
		switch (modelPropertyEnum){
			case SINGLE:
				modelConfig.setModelPropertyEnum(ModelPropertyEnum.SINGLE);
				modelConfig.setEntityConfig(singleModel(packageName,moduleName,tablePrefix,tableName,comments,defaultColumnName,null,null));
				break;
			case ASSOCIATION:
				modelConfig.setModelPropertyEnum(ModelPropertyEnum.ASSOCIATION);
				modelConfig.setEntityConfig(associationModel());
				break;
			case MASTER_SLAVER:
				modelConfig.setModelPropertyEnum(ModelPropertyEnum.MASTER_SLAVER);
				modelConfig.setEntityConfig(associationModel());
				break;
		}
		return modelConfig;
	}

	EntityConfig associationModel(){
		String parentPackageName = "com.netwisd";
		String parentModuleName = "demo";
		String parentTablePrefix = "incloud_demo_";
		String parentTableName = "incloud_demo_master";
		String parentComments = "测试主表";

		String subPackageName = "com.netwisd";
		String subModuleName = "demo";
		String subTablePrefix = "incloud_demo_";
		String subTableName = "incloud_demo_slaver1";
		String subComments = "测试子表1";

		String sub2PackageName = "com.netwisd";
		String sub2ModuleName = "demo";
		String sub2TablePrefix = "incloud_demo_";
		String sub2TableName = "incloud_demo_slaver2";
		String sub2Comments = "测试子表2";

		String sub22PackageName = "com.netwisd";
		String sub22ModuleName = "demo";
		String sub22TablePrefix = "incloud_demo_";
		String sub22TableName = "incloud_demo_slaver22";
		String sub22Comments = "测试子表22";

		EntityConfig parent = singleModel(parentPackageName, parentModuleName, parentTablePrefix, parentTableName, parentComments,defaultColumnName,null,null);
		EntityConfig sub = singleModel(subPackageName, subModuleName, subTablePrefix, subTableName, subComments,"fk_id","incloud_demo_master","id");
		EntityConfig sub2 = singleModel(sub2PackageName, sub2ModuleName, sub2TablePrefix, sub2TableName, sub2Comments,"fkk_id","incloud_demo_master","id");
		EntityConfig sub22 = singleModel(sub22PackageName, sub22ModuleName, sub22TablePrefix, sub22TableName, sub22Comments,"fkkk_id","incloud_demo_slaver2","id");
		List<EntityConfig> subSubList = new ArrayList<>();
		subSubList.add(sub22);
		sub2.setEntityConfigList(subSubList);

		List<EntityConfig> subList = new ArrayList<>();
		subList.add(sub);
		subList.add(sub2);

		parent.setEntityConfigList(subList);
		return parent;
	}

	EntityConfig singleModel(String packageName, String moduleName, String tablePrefix, String tableName, String comments, String columnName,String fkTableName, String fkFieldName){
		EntityConfig entityConfig = new EntityConfig(packageName,incloud_code,moduleName,tablePrefix,tableName,comments,null,null);

		FieldConfig fieldConfig = new FieldConfig();
		fieldConfig.setName("id");
		fieldConfig.setNameCh("主键");
		fieldConfig.setDbType("bigint");
		fieldConfig.setLength(20);
		fieldConfig.setPrecision(0);
		fieldConfig.setIsNotNull(1);
		fieldConfig.setIsKey(1);
		fieldConfig.setIsUnique(1);

		FieldConfig fieldConfig1 = new FieldConfig();
		fieldConfig1.setName("test_name");
		fieldConfig1.setNameCh("测试名称");
		fieldConfig1.setDbType("varchar");
		fieldConfig1.setLength(255);
		fieldConfig1.setPrecision(0);
		fieldConfig1.setIsNotNull(1);
		fieldConfig1.setIsKey(0);
		fieldConfig1.setIsUnique(0);

		FieldConfig fieldConfig2 = new FieldConfig();
		fieldConfig2.setName(columnName);
		fieldConfig2.setNameCh(columnName);
		fieldConfig2.setDbType("bigint");
		fieldConfig2.setLength(20);
		fieldConfig2.setPrecision(0);
		fieldConfig2.setIsNotNull(1);
		fieldConfig2.setIsKey(0);
		fieldConfig2.setIsUnique(0);
		if(StrUtil.isNotEmpty(fkTableName)){
			fieldConfig2.setFkTableName(fkTableName);
		}
		if(StrUtil.isNotEmpty(fkFieldName)){
			fieldConfig2.setFkFieldName(fkFieldName);
		}

		List<FieldConfig> list = new ArrayList();
		list.add(fieldConfig);
		list.add(fieldConfig1);
		list.add(fieldConfig2);
		entityConfig.setFieldConfigList(list);
		return entityConfig;
	}
}
