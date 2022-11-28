package com.netwisd.common.core.anntation;

import com.netwisd.common.core.constants.VarConstants;

import java.lang.annotation.*;

/**
 * @Description: 特殊处理不包含的逻辑
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/2 11:30 上午
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcludeAnntation {
    /**
     * 不包括哪个类的成员属性名
     * 默认空表示不排除任务一个变量
     * ALL 代表排除掉所有；
     * @return
     */
    String value() default VarConstants.DEFAULT;
    //不包括哪个类
    Class<?> clazz() default Object.class;

    String var() default "";

    //不包含哪些属性变量 -- 如果excludeClass属性同时设置，可以做到精确匹配，否则就是按excludeVar匹配所有类中符合条件的var
    String[] vars() default {};
}
