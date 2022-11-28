package com.netwisd.common.es.util;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 读取索引
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/16 12:39 下午
 */
public class IndexUtil {
    /*private static Setting wfSetting = new Setting("indexs/indexs.setting");

    public static List<String> getWfIndexs(String indexs){
        wfSetting.autoLoad(true);
        if(StrUtil.isEmpty(indexs)){
            String result = wfSetting.get("wf");
            List<String> strings = Arrays.asList(StrUtil.splitToArray(result, ','));
            return strings;
        }
        List<String> results = Arrays.asList(StrUtil.splitToArray(indexs, ','));
        return results;
    }*/

    public static List<String> getIndex(String index,Map<String,Class> map){
        List<String> list = new ArrayList<>();
        if(StrUtil.isEmpty(index)){
            for (Map.Entry<String,Class> entry:map.entrySet()){
                String key = entry.getKey();
                list.add(key);
            }
        }else {
            list.add(map.keySet().stream().filter(key -> key.equals(index)).findFirst().get());
        }
        return list;
    }
}
