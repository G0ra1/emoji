package com.netwisd.common.db.cache;

import cn.hutool.core.util.ObjectUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: cache的抽象实现类
 * @Author: zouliming@netwisd.com
 * @Date: 2021/12/15 5:22 下午
 */
@Slf4j
public abstract class IncloudAbstractCache<T> implements IncloudCache {

    private Map<Object,T> map = new HashMap();

    protected abstract Map<Object,T> getSingleMap();

    public IncloudAbstractCache(){
        Map singleMap = getSingleMap();
        if(singleMap != null){
            map = singleMap;
        }
    }

    @Override
    public void put(Object o,IncloudFunction... functions) {
        for (IncloudFunction function : functions){
            //String param = columnToString(function);
            Object apply = function.apply(o);
            if(ObjectUtil.isNotEmpty(apply)){
                map.put(apply,(T)o);
                continue;
            }
            log.error("根据相应字段，在object中找不到相应对应的value值！");
        }
    }

    @Override
    public void puts(List ts, IncloudFunction... functions) {
        ts.forEach(t -> {
            put(t,functions);
        });
    }

    @SneakyThrows
    @Override
    public T get(Object o) {
        return map.get(o);
    }

    @Override
    public List gets(List params) {
        List<T> list = new ArrayList<>();
        params.forEach(o -> {
            T t = get(o);
            if(ObjectUtil.isNotEmpty(t)){
                list.add(t);
            }
        });
        return list;
    }

    @Override
    public void del(Object... params) {
        for (Object param : params){
            map.remove(param);
        }
    }

    @Override
    public void del(List params) {
        params.forEach(param -> map.remove(param));
    }

    @Override
    public void clear() {
        map.clear();
    }
}
