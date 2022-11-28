package com.netwisd.common.db.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.anntation.Fk;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @Description: 实体集合类的属性值赋值
 * @date 2021/11/10 16:49
 */
@Slf4j
public class EntityListConvert<T> {
    /**
     * 根据当前实体字段，映射目标List中的值，利用了反射机构
     * @param v
     * @param list
     * @param <T>
     * @param <V>
     */
    @SneakyThrows
    public static<T,V> void convert(V v, List<T> list){
        String table = null;
        if(v != null){
            Class<?> aClass = v.getClass();
            com.netwisd.common.db.anntation.Map annotation = aClass.getAnnotation(com.netwisd.common.db.anntation.Map.class);
            if(annotation != null){
                table = annotation.value();
            }
        }else {
            throw new IncloudException("传入的实体为空！");
        }
        if(StrUtil.isEmpty(table)){
            throw new IncloudException("父类表的表名为空，不合法！");
        }
        if(list != null && !list.isEmpty()){
            invoke(v,list,table);
        }
    }

    /**
     * 获取字段进行invoke
     * @param v
     * @param list
     * @param table
     * @param <T>
     * @param <V>
     */
    @SneakyThrows
    static<T,V> void invoke(V v, List<T> list, String table ){
        Map map = getField(v);
        for (T t : list) {
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            if(ObjectUtil.isNotEmpty(fields)){
                invoke(t,table,map,fields);
            }
        }
    }

    /**
     * 进行invoke反射执行
     * @param t
     * @param table
     * @param map
     * @param fields
     * @param <T>
     */
    @SneakyThrows
    static<T> void invoke(T t,String table,Map map,Field[] fields){
        for (Field field : fields){
            Fk annotation = field.getAnnotation(Fk.class);
            if(annotation != null){
                String fkTableName = annotation.table();
                if(StrUtil.isNotEmpty(fkTableName) && fkTableName.equals(table)){
                    String fkFieldName = annotation.field();
                    if(StrUtil.isEmpty(fkFieldName)){
                        continue;
                    }
                    if(map.containsKey(fkFieldName)){
                        field.setAccessible(true);
                        field.set(t,map.get(fkFieldName));
                    }
                }else {
                    continue;
                }
            }
        }
    }

    /**
     * 根据集合外建获取到所有字段名和对应的值
     * @param m
     * @param <M>
     * @return
     */
    @SneakyThrows
    static<M> Map getField(M m){
        Class<?> clazz = m.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field[] superFields = clazz.getSuperclass().getDeclaredFields();
        Map map = new HashMap();
        if(ObjectUtil.isNotEmpty(fields)){
            for (Field field : fields){
                field.setAccessible(true);
                map.put(field.getName(),field.get(m));
            }
        }
        if(ObjectUtil.isNotEmpty(superFields)){
            for (Field field : superFields){
                field.setAccessible(true);
                map.put(field.getName(),field.get(m));
            }
        }
        return map;
    }
}
