package com.netwisd.common.code.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: current project incloud
 * current package com.netwisd.common.codegen.entity
 * @Author: zouliming
 * @Date: 2020/2/4 2:31 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityConfig {
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
    private String tableNameCh;

    /**
     * 子表集合
     */
    private List<EntityConfig> entityConfigList = new ArrayList<>();

    /**
     * 字段集合
     */
    private List<FieldConfig> fieldConfigList;
}
