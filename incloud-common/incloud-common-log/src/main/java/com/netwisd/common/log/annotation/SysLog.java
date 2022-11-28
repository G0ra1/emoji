package com.netwisd.common.log.annotation;

import com.netwisd.common.log.constant.CommonConstant;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 日志内容
     *
     * @return
     */
    String value() default "";

    /**
     * 日志类型 (登录、退出、操作)
     *
     * @return
     */
    String logType() default CommonConstant.LOG_TYPE_OPERATE;

    /**
     * 操作日志类型
     *
     * @return （1查询，2添加，3修改，4删除,5导入，6导出）
     */
    String operateType() default CommonConstant.OPERATE_TYPE_QUERY;

}
