package com.netwisd.common.db.cache;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import org.apache.commons.lang.WordUtils;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/12/15 6:11 下午
 */
public interface IncloudCache<T> {

    void put(T t, IncloudFunction<T,?>... functions);

    void puts(List<T> ts, IncloudFunction<T,?>... functions);

    T get(Object param);

    List<T> gets(List<Object> params);

    void del(Object... params);

    void del(List<Object> params);

    void clear();

    default String columnToString(IncloudFunction<T, ?> column) {
        return columnToString(column, true);
    }

    default String columnToString(IncloudFunction<T, ?> column, boolean onlyColumn) {
        return columnToJava(getColumn(IncloudFunction.resolve(column), onlyColumn));
    }

    /**
     * 列名转换成Java属性名
     */
    static String columnToJava(String columnName) {
        return StrUtil.lowerFirst(WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", ""));
    }

    /**
     * 获取 SerializedLambda 对应的列信息，从 lambda 表达式中推测实体类
     * <p>
     * 如果获取不到列信息，那么本次条件组装将会失败
     *
     * @param lambda     lambda 表达式
     * @param onlyColumn 如果是，结果: "name", 如果否： "name" as "name"
     * @return 列
     * @throws MybatisPlusException 获取不到列信息时抛出异常
     * @see SerializedLambda#getImplClass()
     * @see SerializedLambda#getImplMethodName()
     */
    default String getColumn(SerializedLambda lambda, boolean onlyColumn) throws MybatisPlusException {
        String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
        Class<?> aClass = lambda.getInstantiatedType();
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(aClass);
        Assert.notNull(columnMap, "找不到相应的类： [%s]", aClass.getName());
        ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(fieldName));
        Assert.notNull(columnCache, "当前实体： [%s] 找不到相应的属性名： [%s]",
                fieldName, aClass.getName());
        return onlyColumn ? columnCache.getColumn() : columnCache.getColumnSelect();
    }
}
