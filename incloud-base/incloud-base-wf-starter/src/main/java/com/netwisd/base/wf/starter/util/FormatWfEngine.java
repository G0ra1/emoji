package com.netwisd.base.wf.starter.util;

import cn.hutool.core.util.ObjectUtil;
import com.netwisd.common.core.util.JacksonUtil;

import java.lang.reflect.Field;

/**
 * 提交保存工作流时 过滤掉 WfEngine信息
 */
public class FormatWfEngine {
    public static <T> String formatWfEngine(T t) {
        if(ObjectUtil.isNotEmpty(t)) {
            try {
                Class c = t.getClass().getSuperclass();
                Field f = c.getDeclaredField("wfEngine");
                if(null != f) {
                    f.setAccessible(true);
                    //给对象属性赋值
                    f.set(t,null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JacksonUtil.toJSONString(t);
    }

}
