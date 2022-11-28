package com.netwisd.common.core.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zouliming@netwisd.com
 * @Description:
 * @date 2021/11/915:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldVm {
    /**
     * name
     * 字段名称
     */
    private String name;

    /**
     * case_name
     * 驼峰属性
     */
    private String caseName;

    /**
     * fieldType
     * 字段类型
     */
    private String fieldType;

    /**
     * fk_field_name
     * 关联表字段名称
     */
    private String fkFieldName;

    /**
     * class_name
     * 关联表字段名称
     */
    private String className;
}
