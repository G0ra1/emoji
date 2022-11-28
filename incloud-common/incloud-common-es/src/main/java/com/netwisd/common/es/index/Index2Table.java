package com.netwisd.common.es.index;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @description 当前当前索引与实体的对应关系，实现类需要覆盖此方法实现重写
 * @date 2022/1/5 16:17
 */
public interface Index2Table {
    Map<String,Class> map = new HashMap();
    String database_prefix = "incloud4_";

    String getModulePrefix();
    Class get(String tableName);
    Map<String,Class> getMap();
}
