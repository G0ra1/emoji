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
public @interface Fk {
    /**
     * 关联表名称
     * @return 关联表名称
     */
    String table() default "";

    /**
     * 关联表字段
     * @return 关联表字段
     */
    String field() default "";
}
