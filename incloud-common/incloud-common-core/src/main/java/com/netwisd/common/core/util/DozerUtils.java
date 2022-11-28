package com.netwisd.common.core.util;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import java.util.*;

/**
 * @Description: 集合的对象转换
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/7 9:47 下午
 */
public class DozerUtils {
    /**
     * 转换成List，注意第一个map代表映射的意思，不是map结构
     * @param mapper
     * @param sourceList
     * @param targetObjectClass
     * @param <T>
     * @param <S>
     * @return
     */
    public static<T,S> List<T> mapList(final Mapper mapper, List<S> sourceList, Class<T> targetObjectClass){
        List<T> targetList = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(sourceList)){
            sourceList.forEach(s -> {
                targetList.add(mapper.map(s,targetObjectClass));
            });
        }
        return targetList;
    }

    /**
     * 转换成Map，注意第一个map代表映射的意思，不是map结构
     * @param mapper
     * @param sourceMap
     * @param targetObjectClass
     * @param <T>
     * @param <S>
     * @return
     */
    public static<T,S> Map<String,S> mapMap(final Mapper mapper, Map<String,S> sourceMap, Class<T> targetObjectClass){
        Map targetMap = new HashMap();
        if(ObjectUtil.isNotEmpty(sourceMap)){
            for (Map.Entry entry:sourceMap.entrySet()){
                Object key = entry.getKey();
                Object value = entry.getValue();
                targetMap.put(key,mapper.map(value,targetObjectClass));
            }
        }
        return targetMap;
    }

    /**
     * 转换成Set，注意第一个map代表映射的意思，不是map结构
     * @param mapper
     * @param sourceSet
     * @param targetObjectClass
     * @param <T>
     * @param <S>
     * @return
     */
    public static<T,S> Set<T> mapSet(final Mapper mapper, Set<S> sourceSet, Class<T> targetObjectClass){
        Set<T> targetSet = new HashSet<>();
        if(ObjectUtil.isNotEmpty(targetSet)){
            sourceSet.forEach(s -> {
                targetSet.add(mapper.map(s,targetObjectClass));
            });
        }
        return targetSet;
    }

    /**
     * 转换成Page，注意第一个map代表映射的意思，不是map结构
     * @param mapper
     * @param sourcePage
     * @param targetObjectClass
     * @param <T>
     * @param <S>
     * @return
     */
    public static<T,S> Page<T> mapPage(final Mapper mapper, Page<S> sourcePage, Class<T> targetObjectClass){
        List<S> records = sourcePage.getRecords();
        List<T> ts = mapList(mapper, records, targetObjectClass);
        /**
         * 处理dozermapper中(包含当前6.5.0，6.5.2中两个版本)的一个bug；
         * 处理字段映射时，使用Collections.emptyList();然后添加元素；
         * 相当于以下代码：
         * List<Object> objects = Collections.emptyList();
         * objects.add("abc");
         */
        sourcePage.setRecords(null);

        Page<T> map = mapper.map(sourcePage, Page.class);
        map.setRecords(ts);
        return map;
    }
}
