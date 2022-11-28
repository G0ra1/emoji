package com.netwisd.common.core.constants;

/**
 * @Description: 定义想处理的特殊字段，比如在IModel中定义的ID想要创建表时排列到首位
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/20 5:44 下午
 */

public enum FieldConvert {
    ID("id"),CREATETIME("create_time"),UPDATETIME("update_time");

    //字段名称
    private String name;

    private FieldConvert(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
