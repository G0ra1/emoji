package com.netwisd.base.wf.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.wf.constants.BooleanEnum;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.vo.WfVarDefVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ：zouliming@netwisd.com
 * @date ：Created in 2022/2/15 10:19 AM
 * @description：根据给定的key和表单json串获取相应的值
 */
public class VariableUtils {
    /**
     * 获取到来自于表单的变量以及值
     * @param wfVarDefVos 所有可能的变量
     * @param bizDataList 表单json串
     * @param varMappings 由变量映射出来的变量集合，需要在后续runtimeService的variable中再取值
     * @return
     */
    public static Map<String,Object> getVariableByKeyAndEntityJson(Set<WfVarDefVo> wfVarDefVos, List<WfEngineDto.BizData> bizDataList){
        Map variable = new HashMap();
        for (WfVarDefVo wfVarDefVo : wfVarDefVos){
            //获取变量的表单code
            String formName = wfVarDefVo.getFormName();
            //表单分组
            Map<String,List<WfEngineDto.BizData>> formNameMap = bizDataList.stream().collect(Collectors.groupingBy(WfEngineDto.BizData::getFormName));
            List<WfEngineDto.BizData> wfEngineDtos = formNameMap.get(formName);
            if(CollectionUtil.isNotEmpty(wfEngineDtos)) {
                //获取表单数据信息
                WfEngineDto.BizData bizData = wfEngineDtos.get(0);
                String params = bizData.getParams();
                String varJavaName = wfVarDefVo.getJavaName();
                JSONObject jsonObject = JSON.parseObject(params);
                //判断变量中的信息是否包含.  如果不包含说明是主表信息
                if(!varJavaName.contains(".")) {
                    variable.put(formName + "." + varJavaName,jsonObject.get(varJavaName));
                }
                //如果包含则表明是子表或者子子表信息
                if(varJavaName.contains(".")) {
                    String [] cVarJavaName = varJavaName.split("\\.");// A B
                    int len = cVarJavaName.length;
                    int wbc = 0;
                    String firstIndex = cVarJavaName[wbc];
                    Object object = jsonObject.get(firstIndex);
                    ++wbc;
                    recurve(variable,cVarJavaName,len, wbc, object,formName,varJavaName);
                }
            }
        }
        return variable;
    }

    /**
     * 递归处理
     * @param variable  变量
     * @param cVarJavaName 子变量名
     * @param len 变量.分隔的长度
     * @param wbc 计数器
     * @param ccObject 子对象
     */
    public static void recurve(Map variable,String [] cVarJavaName, int len, int wbc, Object ccObject,String formName,String varJavaName ) {
        //判断是对象
        if (ccObject instanceof JSONObject) {
            JSONObject cjSONObject = (JSONObject)ccObject;
            if(len-1 != wbc) {
                Object ccCObject =  cVarJavaName[wbc];
                wbc++;
                Object object = cjSONObject.get(ccCObject);
                recurve(variable,cVarJavaName,len,wbc,object,formName, varJavaName);
            } else {
                String lastIndex = cVarJavaName[wbc];
                variable.put(formName + "." + varJavaName,cjSONObject.get(lastIndex));
            }
        }
        //判断是数组
        if (ccObject instanceof JSONArray) {
            JSONArray cJSONArray = (JSONArray)ccObject;
            if(len-1 != wbc) {
                String ccCObject =  cVarJavaName[wbc];
                wbc++;
                for (int i = 0; i < cJSONArray.size(); i++) {
                    JSONObject cjSONObject = (JSONObject) cJSONArray.get(i);
                    Object obj = cjSONObject.get(ccCObject);
                    recurve(variable,cVarJavaName,len,wbc,obj,formName, varJavaName);
                }

            } else {
                StringBuilder sb = new StringBuilder();
                for (Object o : cJSONArray) {
                    JSONObject cJSONObject = (JSONObject)o;
                    sb.append(cJSONObject.get(cVarJavaName[wbc]));
                    sb.append(",");
                }
                //String lastIndex = cVarJavaName[wbc];
                String v = formName + "." + varJavaName;
                String val = (String)variable.get(v);
                //判断map中是否有值 如果有值继续拼接
                if(StringUtils.isBlank(val)) {
                    variable.put(v, StrUtil.removeSuffix(sb.toString(), ","));
                } else {
                    val = val+",";
                    variable.put(v, val + StrUtil.removeSuffix(sb.toString(), ","));
                }

            }
        }
    }
}
