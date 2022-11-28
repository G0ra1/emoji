package com.netwisd.common.code.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.common.code.entity.*;
import com.netwisd.common.core.constants.FieldConvert;
import com.netwisd.common.core.data.FieldVm;
import com.netwisd.common.core.exception.IncloudException;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description: current project incloud
 * 代码生成工具类
 * @Author: zouliming
 * @Date: 2020/2/3 6:11 下午
 */

@Slf4j
@UtilityClass
public class GenUtils {
    public final String CRUD_PREFIX = "export const tableOption =";
    private final String ENTITY_JAVA_VM = "Entity.java.vm";
    private final String DTO_JAVA_VM = "Dto.java.vm";
    private final String VO_JAVA_VM = "Vo.java.vm";
    private final String MAPPER_JAVA_VM = "Mapper.java.vm";
    private final String SERVICE_JAVA_VM = "Service.java.vm";
    private final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";
    private final String CONTROLLER_JAVA_VM = "Controller.java.vm";
    private final String MAPPER_XML_VM = "Mapper.xml.vm";

    private List<String> getTemplates(String templatePacket) {
        List<String> templates = new ArrayList<>();
        /**
         * 可以根据不同项目的业务再修改模板玩法
         */
        templates.add("template/" + templatePacket + "/Entity.java.vm");
        templates.add("template/" + templatePacket + "/Dto.java.vm");
        templates.add("template/" + templatePacket + "/Vo.java.vm");
        templates.add("template/" + templatePacket + "/Mapper.java.vm");
        templates.add("template/" + templatePacket + "/Mapper.xml.vm");
        templates.add("template/" + templatePacket + "/Service.java.vm");
        templates.add("template/" + templatePacket + "/ServiceImpl.java.vm");
        templates.add("template/" + templatePacket + "/Controller.java.vm");
        return templates;
    }

    @SneakyThrows
    public void generatorCode(ModelConfig modelConfig, ZipOutputStream zip, String templatePacket) {
        //配置信息
        Configuration config = getConfig();
        Configuration allConfig = getAllConfig();
        //获取EntityConfig
        EntityConfig entityConfig = modelConfig.getEntityConfig();
        generator(zip, templatePacket, config, allConfig, entityConfig);
    }

    @SneakyThrows
    void writeByTemplates(ZipOutputStream zip, String templatePacket, Map<String, Object> map, TableEntity tableEntity) {
        VelocityContext context = new VelocityContext(map);
        //获取模板列表
        List<String> templates = getTemplates(templatePacket);
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);

            //添加到zip
            zip.putNextEntry(new ZipEntry(Objects
                    .requireNonNull(getFileName(template, tableEntity.getCaseClassName()
                            , map.get("package").toString(), map.get("moduleName").toString()))));
            IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
            IoUtil.close(sw);
            zip.closeEntry();
        }
    }

    @SneakyThrows
    void generator(ZipOutputStream zip, String templatePacket, Configuration config, Configuration allConfig, EntityConfig entityConfig) {
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(entityConfig.getTableName());
        tableEntity.setComments(entityConfig.getTableNameCh());
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), entityConfig.getTablePrefix());
        tableEntity.setCaseClassName(className);
        tableEntity.setLowerClassName(StringUtils.uncapitalize(className));

        //获取需要在swagger文档中隐藏的属性字段
        List<Object> hiddenColumns = config.getList("hiddenColumn");
        //列信息
        List<ColumnEntity> columnList = new ArrayList<>();
        for (FieldConfig column : entityConfig.getFieldConfigList()) {
            //跳过父类中已经定义的这个两个内置字段
			if(Arrays.stream(FieldConvert.values()).map(FieldConvert::getName).collect(Collectors.toList()).contains(column.getName()) && !column.getName().equals(FieldConvert.ID.getName())){
				continue;
			}
            /*if (column.getName().equals(FieldConvert.CREATETIME.getName()) || column.getName().equals(FieldConvert.UPDATETIME.getName())) {
                continue;
            }*/
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.getName());
            columnEntity.setDataType(column.getDbType());
            columnEntity.setDataTypeCase(StrUtil.swapCase(columnEntity.getDataType()));
            columnEntity.setComments(column.getNameCh());
            columnEntity.setExtra(null);
            columnEntity.setNullable(!(column.getIsNotNull() == 1));
            columnEntity.setColumnType(column.getDbType());
            columnEntity.setLength(column.getLength());
            columnEntity.setPrecision(ObjectUtil.isNull(column.getPrecision()) ? 0 : column.getPrecision());

            columnEntity.setFkTableName(column.getFkTableName());
            columnEntity.setFkFieldName(column.getFkFieldName());
            //隐藏不需要的在接口文档中展示的字段
            if (hiddenColumns.contains(column.getName())) {
                columnEntity.setHidden(Boolean.TRUE);
            } else {
                columnEntity.setHidden(Boolean.FALSE);
            }
            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setCaseAttrName(attrName);
            columnEntity.setLowerAttrName(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if (column.getIsKey() == 1) {
                tableEntity.setId(columnEntity);
            }

            columnList.add(columnEntity);
        }
        tableEntity.setColumns(columnList);

        //没主键，则第一个字段为主键
        if (tableEntity.getId() == null) {
            tableEntity.setId(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableEntity.getTableName());
        map.put("id", tableEntity.getId());
        map.put("className", tableEntity.getCaseClassName());
        map.put("classname", tableEntity.getLowerClassName());
        map.put("pathName", tableEntity.getLowerClassName());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("datetime", DateUtil.now());
        map.put("comments", tableEntity.getComments());
        map.put("author", entityConfig.getAuthor());
        map.put("moduleName", entityConfig.getModuleName());
        map.put("package", entityConfig.getPackageName());
        map.put("mainPath", entityConfig.getPackageName());

        Map<String, String> subClassNameMap = new HashMap<>();
        Map<String, String> packageNameMap = new HashMap<>();
        //代表这一个实体，关联了几个字段对应的外键
        Map<String, List<FieldVm>> subFieldVmMap = new HashMap<>();
        List<FieldVm> subFieldVms = new ArrayList<>();

        Map<String, List<FieldVm>> currentFieldVmMap = new HashMap<>();
        List<FieldVm> currentFieldVms = new ArrayList<>();

        List<EntityConfig> entityConfigList = entityConfig.getEntityConfigList();
        //为主表构建调用子表信息用的
        if (entityConfigList != null && !entityConfigList.isEmpty()) {
            for (EntityConfig subEntityConfig : entityConfigList) {
                String subClassName = tableToJava(subEntityConfig.getTableName(), subEntityConfig.getTablePrefix());
                initFieldVm(subEntityConfig, tableEntity, allConfig, subFieldVms, true, subClassName);
                subClassNameMap.put(subClassName, StringUtils.uncapitalize(subClassName));
                packageNameMap.put(subClassName, subEntityConfig.getPackageName() + "." + subEntityConfig.getModuleName());
                subFieldVmMap.put(subClassName, subFieldVms);
                //如果有子表，做一次递归调用
                generator(zip, templatePacket, config, allConfig, subEntityConfig);
            }
        }
        //为当前表构建用的
        initFieldVm(entityConfig, tableEntity, allConfig, currentFieldVms, false, className);
        currentFieldVmMap.put(className, currentFieldVms);

        map.put("subClassNameMap", subClassNameMap);
        map.put("packageNameMap", packageNameMap);
        map.put("subFieldVmMap", subFieldVmMap);
        map.put("currentFieldVmMap", currentFieldVmMap);

        //把数据根据模板写入zip包
        writeByTemplates(zip, templatePacket, map, tableEntity);
    }

    void initFieldVm(EntityConfig subEntityConfig, TableEntity tableEntity, Configuration allConfig, List<FieldVm> fieldVms, Boolean isMaster, String className) {
        List<FieldConfig> fieldConfigList = subEntityConfig.getFieldConfigList();
        for (FieldConfig fieldConfig : fieldConfigList) {
            String fkTableName = fieldConfig.getFkTableName();
            if (StrUtil.isNotEmpty(fkTableName)) {
                if (isMaster) {
                    if (fkTableName.equals(tableEntity.getTableName())) {
                        initFieldVm(fieldConfig, allConfig, fieldVms, className);
                    }
                } else {
                    initFieldVm(fieldConfig, allConfig, fieldVms, className);
                }
            }
        }
    }

    void initFieldVm(FieldConfig fieldConfig, Configuration allConfig, List<FieldVm> fieldVms, String className) {
        FieldVm fieldVm = new FieldVm();
        fieldVm.setName(fieldConfig.getName());
        fieldVm.setCaseName(columnToJava(fieldConfig.getName()));
        fieldVm.setFieldType(allConfig.getString(fieldConfig.getDbType(), "unknowType"));
        fieldVm.setFkFieldName(fieldConfig.getFkFieldName());
        fieldVm.setClassName(className);
        fieldVms.add(fieldVm);
    }

    /**
     * 生成代码
     */
    @SneakyThrows
    public void generatorCode(GenConfig genConfig, Map<String, String> table,
                              List<Map<String, String>> columns, ZipOutputStream zip, String templatePacket) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));

        if (StrUtil.isNotBlank(genConfig.getComments())) {
            tableEntity.setComments(genConfig.getComments());
        } else {
            tableEntity.setComments(table.get("tableComment"));
        }

        String tablePrefix;
        if (StrUtil.isNotBlank(genConfig.getTablePrefix())) {
            tablePrefix = genConfig.getTablePrefix();
        } else {
            tablePrefix = config.getString("tablePrefix");
        }

        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setCaseClassName(className);
        tableEntity.setLowerClassName(StringUtils.uncapitalize(className));
        //获取需要在swagger文档中隐藏的属性字段
        List<Object> hiddenColumns = config.getList("hiddenColumn");
        //列信息
        List<ColumnEntity> columnList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            //跳过父类中已经定义的这个两个内置字段
            if(Arrays.stream(FieldConvert.values()).map(FieldConvert::getName).collect(Collectors.toList()).contains(column.get("columnName")) && !column.get("columnName").equals(FieldConvert.ID.getName())){
                continue;
            }
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setDataTypeCase(StrUtil.swapCase(columnEntity.getDataType()));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));
            columnEntity.setNullable("YES".equals(column.get("isNullable")));
            columnEntity.setColumnType(column.get("columnType"));
            columnEntity.setLength(0);
            String length = StrUtil.subBetween(column.get("columnType"), "(", ")");
            if (StrUtil.isNotEmpty(length)) {
                if (StrUtil.contains(length, ",")) {
                    String[] split = length.split(",");
                    length = split[0];
                    columnEntity.setPrecision(Integer.valueOf(split[1]));
                }
                columnEntity.setLength(Integer.valueOf(length));
            }
            //隐藏不需要的在接口文档中展示的字段
            if (hiddenColumns.contains(column.get("columnName"))) {
                columnEntity.setHidden(Boolean.TRUE);
            } else {
                columnEntity.setHidden(Boolean.FALSE);
            }
            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setCaseAttrName(attrName);
            columnEntity.setLowerAttrName(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getId() == null) {
                tableEntity.setId(columnEntity);
            }

            columnList.add(columnEntity);
        }
        tableEntity.setColumns(columnList);

        //没主键，则第一个字段为主键
        if (tableEntity.getId() == null) {
            tableEntity.setId(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableEntity.getTableName());
        map.put("id", tableEntity.getId());
        map.put("className", tableEntity.getCaseClassName());
        map.put("classname", tableEntity.getLowerClassName());
        map.put("pathName", tableEntity.getLowerClassName());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("datetime", DateUtil.now());

        if (StrUtil.isNotBlank(genConfig.getComments())) {
            map.put("comments", genConfig.getComments());
        } else {
            map.put("comments", tableEntity.getComments());
        }

        if (StrUtil.isNotBlank(genConfig.getAuthor())) {
            map.put("author", genConfig.getAuthor());
        } else {
            map.put("author", config.getString("author"));
        }

        if (StrUtil.isNotBlank(genConfig.getModuleName())) {
            map.put("moduleName", genConfig.getModuleName());
        } else {
            map.put("moduleName", config.getString("moduleName"));
        }

        if (StrUtil.isNotBlank(genConfig.getPackageName())) {
            map.put("package", genConfig.getPackageName());
            map.put("mainPath", genConfig.getPackageName());
        } else {
            map.put("package", config.getString("package"));
            map.put("mainPath", config.getString("mainPath"));
        }

        writeByTemplates(zip, templatePacket, map, tableEntity);
    }


    /**
     * 列名转换成Java属性名
     */
    public String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    private String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    private Configuration getConfig() {
        try {
            return new PropertiesConfiguration("incloud.properties");
        } catch (ConfigurationException e) {
            throw new IncloudException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取配置信息
     */
    private Configuration getAllConfig() {
        try {
            return new PropertiesConfiguration("incloud.all.properties");
        } catch (ConfigurationException e) {
            throw new IncloudException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    private String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = className + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains(ENTITY_JAVA_VM)) {
            return packagePath + "entity" + File.separator + className + ".java";
        }

        if (template.contains(DTO_JAVA_VM)) {
            return packagePath + "dto" + File.separator + className + "Dto.java";
        }

        if (template.contains(VO_JAVA_VM)) {
            return packagePath + "vo" + File.separator + className + "Vo.java";
        }

        if (template.contains(MAPPER_JAVA_VM)) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains(SERVICE_JAVA_VM)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains(SERVICE_IMPL_JAVA_VM)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(CONTROLLER_JAVA_VM)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains(MAPPER_XML_VM)) {
            return className + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }

        return null;
    }
}
