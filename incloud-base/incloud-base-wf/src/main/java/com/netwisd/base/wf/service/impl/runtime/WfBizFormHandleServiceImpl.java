package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.github.dozermapper.core.Mapper;
import com.netflix.ribbon.proxy.annotation.Http;
import com.netwisd.base.common.model.vo.ModelFormVo;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.vo.wf.WfMyInDuplicateTaskVo;
import com.netwisd.base.common.vo.wf.WfReceiveNotifyTaskVo;
import com.netwisd.base.wf.constants.BizMethodTypeEnum;
import com.netwisd.base.wf.constants.MyTaskTypeEnum;
import com.netwisd.base.wf.constants.YesNo;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.feign.ModelFeignClient;
import com.netwisd.base.wf.feign.WfHttpClient;
import com.netwisd.base.wf.service.WfProcdefService;
import com.netwisd.base.wf.service.WfSendNotifyTaskService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.vo.WfFormDefVo;
import com.netwisd.base.wf.vo.WfProcDefVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.exception.IncloudHttpException;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description 业务表单统一操作
 * @author 云数网讯 XHL
 * @date 2022-02-28 11:20:23
 */
@Service
@Slf4j
public class WfBizFormHandleServiceImpl implements WfBizFormHandleService {

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfProcessService wfProcessService;

    @Autowired
    private WfProcdefService wfProcdefService;

    @Autowired
    private ModelFeignClient modelFeignClient;

    @Autowired
    WfHttpClient wfHttpClient;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfReceiveNotifyTaskService wfReceiveNotifyTaskService;

    @Autowired
    WfSendNotifyTaskService wfSendNotifyTaskService;

    @Autowired
    WfMyInDuplicateTaskService wfMyInDuplicateTaskService;

    @Autowired
    WfMyOutDuplicateTaskService wfMyOutDuplicateTaskService;

    @Autowired
    WfEngineBizDataService wfEngineBizDataService;

    @Autowired
    WfForwardedTaskService wfForwardedTaskService;

    @Autowired
    WfDelegationTaskService wfDelegationTaskService;

    @Override
    @SneakyThrows
    public WfProcDefVo getFormInfo(String taskType,String id) {
        if(StringUtils.isBlank(taskType)) {
            throw new IncloudException("任务类型不能为空！");
        }
        if(StringUtils.isBlank(id)) {
            throw new IncloudException("任务ID不能为空！");
        }
        WfEngineDto.RespFormDto respFormDto = new WfEngineDto.RespFormDto();
        //根据任务ID查询出具体节点
        String camundaNodeKey = "";
        String camundaProcdefId = "";
        String procinsId = "";
        //草稿和待办
        if(MyTaskTypeEnum.DRAFT.code.equals(taskType) || MyTaskTypeEnum.TODO.code.equals(taskType)) {
            WfTodoTask wfTodoTask = wfTodoTaskService.getById(id);
            camundaNodeKey = wfTodoTask.getNodeKey();
            camundaProcdefId = wfTodoTask.getCamundaProcdefId();
            procinsId = wfTodoTask.getCamundaProcinsId();
            respFormDto = dozerMapper.map(wfTodoTask,WfEngineDto.RespFormDto.class);
            respFormDto.setBizPriority(wfTodoTask.getBizPriority());
        } else if(MyTaskTypeEnum.DONE.code.equals(taskType) || MyTaskTypeEnum.MY_DRAFT.code.equals(taskType)) { //已办
            WfDoneTask wfDoneTask = wfDoneTaskService.getById(id);
            camundaNodeKey = wfDoneTask.getNodeKey();
            camundaProcdefId = wfDoneTask.getCamundaProcdefId();
            procinsId = wfDoneTask.getCamundaProcinsId();
            respFormDto = dozerMapper.map(wfDoneTask,WfEngineDto.RespFormDto.class);
            respFormDto.setBizPriority(wfDoneTask.getBizPriority());
        }
        else if(MyTaskTypeEnum.RECEIVE_NOTIFY.code.equals(taskType)) { //收到知会
            WfReceiveNotifyTask wfReceiveNotifyTask = wfReceiveNotifyTaskService.getById(id);
            camundaNodeKey = wfReceiveNotifyTask.getCamundaNodeKey();
            camundaProcdefId = wfReceiveNotifyTask.getCamundaProcdefId();
            procinsId = wfReceiveNotifyTask.getCamundaProcinsId();
            respFormDto = dozerMapper.map(wfReceiveNotifyTask,WfEngineDto.RespFormDto.class);
            respFormDto.setBizPriority(wfReceiveNotifyTask.getBizPriority());
        }
        else if(MyTaskTypeEnum.SEND_NOTIFY.code.equals(taskType)) { //发出知会
            WfSendNotifyTask wfSendNotifyTask = wfSendNotifyTaskService.getById(id);
            camundaNodeKey = wfSendNotifyTask.getCamundaNodeKey();
            camundaProcdefId = wfSendNotifyTask.getCamundaProcdefId();
            procinsId = wfSendNotifyTask.getCamundaProcinsId();
            respFormDto = dozerMapper.map(wfSendNotifyTask,WfEngineDto.RespFormDto.class);
            respFormDto.setBizPriority(wfSendNotifyTask.getBizPriority());
        }
        else if(MyTaskTypeEnum.FORWARD.code.equals(taskType)) { //转办
            WfForwardedTask wfForwardedTask = wfForwardedTaskService.getById(id);
            camundaNodeKey = wfForwardedTask.getCamundaNodeKey();
            camundaProcdefId = wfForwardedTask.getCamundaProcdefId();
            procinsId = wfForwardedTask.getCamundaProcinsId();
            respFormDto = dozerMapper.map(wfForwardedTask,WfEngineDto.RespFormDto.class);
            respFormDto.setBizPriority(wfForwardedTask.getBizPriority());
        }
        else if(MyTaskTypeEnum.IN_DUPLICATE.code.equals(taskType)) { //收到的传阅
            WfMyInDuplicateTask wfMyInDuplicateTask = wfMyInDuplicateTaskService.getById(id);
            camundaNodeKey = wfMyInDuplicateTask.getCamundaNodeKey();
            camundaProcdefId = wfMyInDuplicateTask.getCamundaProcdefId();
            procinsId = wfMyInDuplicateTask.getCamundaProcinsId();
            respFormDto = dozerMapper.map(wfMyInDuplicateTask,WfEngineDto.RespFormDto.class);
            respFormDto.setBizPriority(wfMyInDuplicateTask.getBizPriority());
        }else if(MyTaskTypeEnum.OUT_DUPLICATE.code.equals(taskType)) { //发出的传阅
            WfMyOutDuplicateTask wfMyOutDuplicateTask = wfMyOutDuplicateTaskService.getById(id);
            camundaNodeKey = wfMyOutDuplicateTask.getCamundaNodeKey();
            camundaProcdefId = wfMyOutDuplicateTask.getCamundaProcdefId();
            procinsId = wfMyOutDuplicateTask.getCamundaProcinsId();
            respFormDto = dozerMapper.map(wfMyOutDuplicateTask,WfEngineDto.RespFormDto.class);
            respFormDto.setBizPriority(wfMyOutDuplicateTask.getBizPriority());
        }
        else if(MyTaskTypeEnum.DELEGATION.code.equals(taskType)) { //我委托的待办
            WfDelegationTask wfDelegationTask = wfDelegationTaskService.getById(id);
            camundaNodeKey = wfDelegationTask.getCamundaNodeKey();
            camundaProcdefId = wfDelegationTask.getCamundaProcdefId();
            procinsId = wfDelegationTask.getCamundaProcinsId();
            respFormDto = dozerMapper.map(wfDelegationTask,WfEngineDto.RespFormDto.class);
            respFormDto.setBizPriority(wfDelegationTask.getBizPriority());
        }
        //根据流程实例和节点信息查询出流程定义、表单节点信息、表单权限信息、流程按钮信息
        WfProcDefVo wfProcDefVo = wfProcdefService.getProcDefInfoByCamundaProcdefId(camundaProcdefId, camundaNodeKey);
        buildFormInfo(wfProcDefVo,procinsId,respFormDto);
        return wfProcDefVo;
    }

    /**
     * 构建返回业务表单信息
     * @param wfProcDefVo
     * @param procinsId
     * @param respFormDto
     * @throws IOException
     */
    public void buildFormInfo(WfProcDefVo wfProcDefVo, String procinsId,WfEngineDto.RespFormDto respFormDto) throws IOException {
        //设置草稿和待办时需要task信息
        //根据表单信息 查询表单拼接path
        List<WfFormDefVo> wfFormDefVos = wfProcDefVo.getWfFormDefs();
        List<WfEngineDto.BizData> bizDatas = new ArrayList<>();
        for (WfFormDefVo wfFormDefVo : wfFormDefVos) {
            ModelFormVo modelFormVo = null;
            try {
                modelFormVo = modelFeignClient.formDetailByFormName(wfFormDefVo.getFormName());
            }catch (Exception e){
                log.error("根据表单名称：{}获取表单失败！",modelFormVo.getFormName());
                throw new IncloudException("根据表单名称：{}获取表单失败！",modelFormVo.getFormName());
            }
            //实时取建模
            wfFormDefVo.setPageUrl(modelFormVo.getPageUrl());
            WfEngineDto.BizData bizData = new WfEngineDto.BizData();
            bizData.setUrl(modelFormVo.getFormGetUrl());
            bizData.setMethodType(modelFormVo.getFormGetMethodType());
            bizData.setFormName(wfFormDefVo.getFormName());
            bizData.setFormNameCh(wfFormDefVo.getFormNameCh());
            //httpClient 根据流程实例id 查询具体表单的业务数据
            WfDto wfDto = new WfDto();
            wfDto.setCamundaProcinsId(procinsId);
            String params = this.invoke(bizData,procinsId);
            bizData.setParams(params);
            bizDatas.add(bizData);
        }
        respFormDto.setBizDataList(bizDatas);
        wfProcDefVo.setRespFormDto(respFormDto);
    }

    /**
     * httpClient 调用业务系统表单数据
     * @param bizData
     * @param procinsId
     * @return
     * @throws IOException
     */
    public String invoke(WfEngineDto.BizData bizData, String procinsId) throws IOException {
        HttpResponse response = null;
        JSONObject params = new JSONObject();
        //params.set("camundaProcinsId",procinsId);
        if(StringUtils.isNotBlank(bizData.getFormName()) && StrUtil.isNotEmpty(bizData.getUrl()) && StrUtil.isNotEmpty(bizData.getMethodType())){
            bizData.setUrl(bizData.getUrl());
            bizData.setMethodType(bizData.getMethodType());
        }else {
            log.info("业务数据未发生任何改变，不进行http调用！");
            return "";
        }
        try {
            //调业务view方法
            if(bizData.getMethodType().equals(HttpPost.METHOD_NAME)){
                response = wfHttpClient.executePost(bizData.getUrl(), params, HttpResponse.class);
            }else if(bizData.getMethodType().equals(HttpPut.METHOD_NAME)) {
                response = wfHttpClient.executePut(bizData.getUrl(), params, HttpResponse.class);
            }else if(bizData.getMethodType().equals(HttpGet.METHOD_NAME)){
                //调业务update方法
                response = wfHttpClient.executeGet(bizData.getUrl()+"/"+procinsId, params, HttpResponse.class);
            }else {
                log.error("不支持的请求类型！");
            }
        }catch (Exception e){
            throw new IncloudException("调用接口:{}失败，请查找日志！",bizData.getUrl());
        }
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            String result = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            log.info("执行成功！");
            Result resultData = JacksonUtil.parseObject(result, Result.class);
            Object data = resultData.getData();
            return ObjectUtil.isEmpty(data) ? null : JacksonUtil.toJSONString(data);
        }else {
            log.error("自定义任务执行失败，错误代码：{}",response.getStatusLine().getStatusCode());
            throw new IncloudHttpException("自定义任务执行失败!");
        }
    }
}
