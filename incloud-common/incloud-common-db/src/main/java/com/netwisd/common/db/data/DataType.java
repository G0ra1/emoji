package com.netwisd.common.db.data;

import com.netwisd.common.db.anntation.Length;

/**
 * @Description: 定义数据库的数据类型
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:07 下午
 */
public class DataType {
    @Length
    public static final  String INT = "int";

    @Length
    public static final  String VARCHAR = "varchar";

    @Length(length=0)
    public static final  String TEXT = "text";

    @Length(length=0)
    public static final  String MEDIUMTEXT = "mediumtext";

    @Length(length=0)
    public static final  String LONGTEXT = "longtext";

    @Length(length=0)
    public static final  String DATETIME = "datetime";

    @Length(length=0)
    public static final  String DATE = "date";

    @Length(length=2)
    public static final  String DECIMAL = "decimal";

    @Length(length=2)
    public static final  String DOUBLE = "double";

    @Length
    public static final  String CHAR = "char";

    /**
     * 等于java中的long
     */
    @Length
    public static final  String BIGINT = "bigint";

}
