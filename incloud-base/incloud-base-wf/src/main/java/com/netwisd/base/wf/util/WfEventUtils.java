package com.netwisd.base.wf.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.wf.vo.WfEventParamRuntimeVo;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.SpringContextUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @description 事件相关的操作工具类
 * @date 2021/12/6 14:47
 */

@Slf4j
public class WfEventUtils {

    /**
     * 根据wfEventRuntimeVo获取对应的bean
     * @param wfEventRuntimeVo
     * @return
     */
    public static Object getTargetBean(WfEventRuntimeVo wfEventRuntimeVo){
        String bean = wfEventRuntimeVo.getListenerImpl();
        String beanId = wfEventRuntimeVo.getListenerId();
        log.info("bean : {},beanId:{}",bean,beanId);
        Object targetBean = null;
        try {
            targetBean = SpringContextUtils.getBean(beanId);
        }catch (Exception e){
            log.info("找不到相应有事件bean");
        }
        if(ObjectUtil.isEmpty(targetBean)){
            try {
                Class clazz = Class.forName(bean);
                targetBean = SpringContextUtils.getBean(clazz);
            }catch (Exception e){
                log.error("通过class也找不到相应有事件bean");
                throw new IncloudException("通过class找不到相应有事件bean");
            }
        }
        if(ObjectUtil.isEmpty(targetBean)){
            throw new IncloudException("找不到相应的事件bean，请检查是否配置！");
        }
        return targetBean;
    }

    @SneakyThrows
    public static void handleWfEventParamRuntimeVo(List<WfEventParamRuntimeVo> eventParamsByConditions, Object targetBean){
        Map<String,WfEventParamRuntimeVo> parametersNameValueMap = new HashMap<>();
        for (WfEventParamRuntimeVo wfEventParamRuntimeVo : eventParamsByConditions){
            String paramId = wfEventParamRuntimeVo.getParamId();
            parametersNameValueMap.put(paramId,wfEventParamRuntimeVo);
        }
        Field[] declaredFields = targetBean.getClass().getDeclaredFields();
        for(Field field : declaredFields){
            String name = field.getName();
            WfEventParamRuntimeVo wfEventParamRuntimeVo = parametersNameValueMap.get(name);
            if(ObjectUtil.isNotEmpty(wfEventParamRuntimeVo)){
                String paramType = wfEventParamRuntimeVo.getParamType();
                Method declaredMethod = null;
                if(paramType.equalsIgnoreCase("expression")){
                    log.error("自定义处理的事件，参数成员变量最好不要为org.camunda.bpm.engine.delegate.Expression类型！");
                    declaredMethod = targetBean.getClass().getDeclaredMethod("set" + StrUtil.upperFirst(name) + "Str", String.class);
                }if(paramType.equalsIgnoreCase("String")){
                    declaredMethod = targetBean.getClass().getDeclaredMethod("set" + StrUtil.upperFirst(name) + "Str", String.class);
                }
                declaredMethod.invoke(targetBean,wfEventParamRuntimeVo.getParamValue());
            }
        }
    }
}
