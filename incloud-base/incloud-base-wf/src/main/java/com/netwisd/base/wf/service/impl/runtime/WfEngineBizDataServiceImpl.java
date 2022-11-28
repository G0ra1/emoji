package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.netwisd.base.common.model.vo.ModelFormVo;
import com.netwisd.base.wf.constants.BizMethodTypeEnum;
import com.netwisd.base.wf.feign.ModelFeignClient;
import com.netwisd.base.wf.feign.WfHttpClient;
import com.netwisd.base.wf.service.runtime.WfEngineBizDataService;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.exception.IncloudHttpException;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.JacksonUtil;
import com.netwisd.common.core.util.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2022/2/8 16:15
 */
@Service
@Slf4j
public class WfEngineBizDataServiceImpl implements WfEngineBizDataService {
    @Autowired
    WfHttpClient wfHttpClient;

    @Autowired
    ModelFeignClient modelFeignClient;

    @Override
    @SneakyThrows
    public String invoke(WfEngineDto.BizData bizData, WfDto wfDto, BizMethodTypeEnum bizMethodTypeEnum) {
        ModelFormVo modelFormVo = null;
        try {
            modelFormVo = modelFeignClient.formDetailByFormName(bizData.getFormName());
        }catch (Exception e){
            log.error("根据表单名称：{}获取表单失败！",bizData.getFormName());
            throw new IncloudException("根据表单名称：{}获取表单失败！",bizData.getFormName());
        }
        if(ObjectUtil.isNotEmpty(modelFormVo) && StrUtil.isNotEmpty(modelFormVo.getFormSaveUrl()) && StrUtil.isNotEmpty(modelFormVo.getFormSaveMethodType())){
            switch (bizMethodTypeEnum){
                case SAVE:
                    bizData.setUrl(modelFormVo.getFormSaveUrl());
                    bizData.setMethodType(modelFormVo.getFormSaveMethodType());
                    break;
                case UPDATE:
                    bizData.setUrl(modelFormVo.getFormUpdateUrl());
                    bizData.setMethodType(modelFormVo.getFormUpdateMethodType());
                    break;
                case VIEW:
                    bizData.setUrl(modelFormVo.getFormGetUrl());
                    bizData.setMethodType(modelFormVo.getFormGetMethodType());
                    break;
                default:
                    //nothing
            }
        }else {
            log.info("业务数据未发生任何改变，不进行http调用！");
            return null;
        }
        HttpResponse response = null;
        JSONObject params = null;
        if(!bizData.getIsChange()){
            log.info("业务数据未发生任何改变，不进行http调用！");
            return null;
        }
        String param = bizData.getParams();
        try {
            JSONConfig jc = new JSONConfig();
            jc.setDateFormat("yyyy-MM-dd HH:mm:ss");
            params = JSONUtil.parseObj(param, jc);
            params.set("camundaProcdefKey",wfDto.getCamundaProcdefKey());
            params.set("camundaProcdefId",wfDto.getCamundaProcdefId());
            params.set("camundaProcinsId",wfDto.getCamundaProcinsId());
            params.set("procdefTypeId",wfDto.getProcdefTypeId());
            params.set("procdefTypeName",wfDto.getProcdefName());
            params.set("procdefVersion",wfDto.getProcdefVersion());
            params.set("procdefName",wfDto.getProcdefName());
            params.set("processName",wfDto.getProcessName());
            params.set("reason",wfDto.getReason());
            params.set("bizKey",wfDto.getBizKey());
            params.set("camundaTaskId",wfDto.getCamundaTaskId());
            params.set("auditStatus",wfDto.getAuditStatus());
        }catch (Exception e){
            log.error("业务参数转换失败，请检查参数");
            e.printStackTrace();
            throw new IncloudException("业务参数转换失败，请检查参数！");
        }
        try {
            //业务主键
            Object id = params.get("id");
            //调业务save方法
            if(bizData.getMethodType().equals(HttpPost.METHOD_NAME)){
                response = wfHttpClient.executePost(bizData.getUrl(), params, HttpResponse.class);
            }else if(bizData.getMethodType().equals(HttpPut.METHOD_NAME)) {
                //如果业务ID为空——这是前端传过来的，证明这是新增，就按新增的方式去调
                if(ObjectUtil.isEmpty(id)){
                    response = wfHttpClient.executePost(modelFormVo.getFormSaveUrl(), params, HttpResponse.class);
                }else {
                    response = wfHttpClient.executePut(bizData.getUrl(), params, HttpResponse.class);
                }
            }else if(bizData.getMethodType().equals(HttpGet.METHOD_NAME)){
                //调业务update方法
                response = wfHttpClient.executeGet(bizData.getUrl(), params, HttpResponse.class);
            }else {
                log.error("不支持的请求类型！");
            }
        }catch (Exception e){
            throw new IncloudException("调用接口:{}失败，请查找日志！",bizData.getUrl());
        }
        String result = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            log.info("执行成功！");
            try {
                Result resultData = JacksonUtil.parseObject(result, Result.class);
                Object data = resultData.getData();
                return ObjectUtil.isEmpty(data) ? null : JacksonUtil.toJSONString(data);
            }catch (IncloudException e){
                log.error("转换result失败,result为：{}",result);
                throw new IncloudHttpException(result);
            }
        }else {
            log.error("自定义任务执行失败，错误代码：{}",response.getStatusLine().getStatusCode());
            throw new IncloudHttpException(result);
        }
    }
}