package com.netwisd.common.db.anntation;

import com.netwisd.common.db.data.DataType;

import java.lang.annotation.*;

/**
 * @Description: 列注解
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:10 下午
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Column {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";

    /**
     * 字段类型
     *
     * @return 字段类型
     */
    String type() default DataType.VARCHAR;

    /**
     * 字段长度，默认是255
     *
     * @return 字段长度，默认是255
     */
    long length() default 255;

    /**
     * 小数点长度，默认是0
     *
     * @return 小数点长度，默认是0
     */
    int precision() default 0;

    /**
     * 是否为可以为null，true是可以，false是不可以，默认为true
     *
     * @return 是否为可以为null，true是可以，false是不可以，默认为true
     */
    boolean isNull() default true;

    /**
     * 是否是主键，默认false
     *
     * @return 是否是主键，默认false
     */
    boolean isKey() default false;

    /**
     * 是否自动递增，默认false 只有主键才能使用
     *
     * @return 是否自动递增，默认false 只有主键才能使用
     */
    boolean isAutoIncrement() default false;

    /**
     * 默认值，默认为null
     *
     * @return 默认值，默认为null
     */
    String defaultValue() default "NULL";

    /**
     * 是否是唯一，默认false
     *
     * @return 是否是唯一，默认false
     */
    boolean isUnique() default false;

    /**
     * 字段注释
     * @return 字段注释
     */
    String comment() default "";

    /**
     * 关联表名称
     * @return 关联表名称
     */
    String fkTableName() default "";

    /**
     * 关联表字段
     * @return 关联表字段
     */
    String fkFieldName() default "";
}
