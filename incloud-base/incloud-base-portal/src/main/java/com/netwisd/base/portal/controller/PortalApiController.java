package com.netwisd.base.portal.controller;

import com.alibaba.fastjson.JSONObject;
import com.netwisd.base.portal.dto.*;
import com.netwisd.base.portal.entity.PortalYbzTodoTasks;
import com.netwisd.base.portal.service.*;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description $对外提供的restFull接口 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-18 10:45:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api" )
@Api(value = "api", tags = "对外提供的restFull接口")
@Slf4j
public class PortalApiController {

    private final PortalContentTodoTasksService portalContentTodoTasksService;

    private final PortalContentDoneTasksService portalContentDoneTasksService;

    private final PortalContentMydraftTasksService portalContentMydraftTasksService;

    private final PortalContentUnreadTasksService portalContentUnreadTasksService;

    private final PortalContentReadTasksService portalContentReadTasksService;

    private final PortalApiService portalApiService;

    private final PortalYbzTodoTasksService portalYbzTodoTasksService;

    private final PortalYbzDoneTasksService portalYbzDoneTasksService;

//    @Autowired
//    DefaultMQProducer defaultMQProducer;



    /**
     * 保存别的系统待办数据
     * @param portalContentTodoTasksDtos 待办数据
     * @return
     */
    @ApiOperation(value = "保存别的系统待办数据", notes = "保存别的系统待办数据")
    @PostMapping("/todo/saveList" )
    public Result todoSaveList(@RequestBody List<PortalContentTodoTasksDto> portalContentTodoTasksDtos) {
        return Result.success(portalContentTodoTasksService.saveList(portalContentTodoTasksDtos));
    }

    /**
     * 同步别的系统待办信息删除
     * @param apiTaskDto 所传数据的系统业务id 和系统业务Code
     * @return
     */
    @ApiOperation(value = "同步别的系统待办信息删除", notes = "同步别的系统待办信息删除")
    @PostMapping("/todo/delBySysBizIdAndCode" )
    public Result delBySysBizIdAndCode(@RequestBody ApiTaskDto apiTaskDto) {
        return Result.success(portalContentTodoTasksService.delBySysBizIdAndCode(apiTaskDto));
    }

    /**
     * 同步别的系统待办信息签收
     * @param apiTaskDto 所传数据的系统业务id 和 code
     * @return
     */
    @ApiOperation(value = "同步别的系统待办信息签收", notes = "同步别的系统待办信息签收")
    @PostMapping("/todo/cliamHandle" )
    public Result cliamHandle(@RequestBody ApiTaskDto apiTaskDto) {
        return Result.success(portalContentTodoTasksService.cliamHandle(apiTaskDto));
    }

    /**
     * 保存别的系统已办数据
     * @param portalContentTodoTasksDtos 已办数据
     * @return
     */
    @ApiOperation(value = "保存别的系统已办数据", notes = "保存别的系统已办数据")
    @PostMapping("/done/saveList" )
    public Result doneSaveList(@RequestBody List<PortalContentDoneTasksDto> portalContentTodoTasksDtos) {
        return Result.success(portalContentDoneTasksService.saveList(portalContentTodoTasksDtos));
    }

    /**
     * 同步别的系统已办信息删除
     * @param apiTaskDto 所传数据的系统业务id 和 code
     * @return
     */
    @ApiOperation(value = "同步别的系统已办信息删除", notes = "同步别的系统已办信息删除")
    @PostMapping("/done/delBySysBizIdAndCode" )
    public Result doneDelBySysBizIdAndCode(@RequestBody ApiTaskDto apiTaskDto) {
        return Result.success(portalContentDoneTasksService.delBySysBizIdAndCode(apiTaskDto));
    }

    /**
     * 保存别的系统 我的任务数据
     * @param portalContentMydraftTasksDtos 我的任务数据
     * @return
     */
    @ApiOperation(value = "保存别的系统 我的任务数据", notes = "保存别的系统 我的任务数据")
    @PostMapping("/mydraft/saveList" )
    public Result mydraftSaveList(@RequestBody List<PortalContentMydraftTasksDto> portalContentMydraftTasksDtos) {
        return Result.success(portalContentMydraftTasksService.saveList(portalContentMydraftTasksDtos));
    }

    /**
     * 同步别的系统 我的任务数据信息删除
     * @param apiTaskDto 所传数据的系统业务id 和 code
     * @return
     */
    @ApiOperation(value = "同步别的系统 我的任务数据 信息删除", notes = "同步别的系统 我的任务数据 信息删除")
    @PostMapping("/mydraft/delBySysBizIdAndCode" )
    public Result mydraftDelBySysBizIdAndCode(@RequestBody ApiTaskDto apiTaskDto) {
        return Result.success(portalContentMydraftTasksService.delBySysBizIdAndCode(apiTaskDto));
    }

    /**
     * 保存别的系统 待阅数据
     * @param portalContentUnreadTasksDtos 待阅数据
     * @return
     */
    @ApiOperation(value = "保存别的系统 待阅数据", notes = "保存别的系统 待阅数据")
    @PostMapping("/unread/saveList")
    public Result unreadSaveList(@RequestBody List<PortalContentUnreadTasksDto> portalContentUnreadTasksDtos) {
        return Result.success(portalContentUnreadTasksService.saveList(portalContentUnreadTasksDtos));
    }

    /**
     * 同步别的系统 待阅数据信息删除
     * @param apiTaskDto 所传数据的系统业务id 和code
     * @return
     */
    @ApiOperation(value = "同步别的系统 待阅 信息删除", notes = "同步别的系统 待阅 信息删除")
    @PostMapping("/unread/delBySysBizIdAndCode")
    public Result unreadDelBySysBizIdAndCode(@RequestBody ApiTaskDto apiTaskDto) {
        return Result.success(portalContentUnreadTasksService.delBySysBizIdAndCode(apiTaskDto));
    }


    /**
     * 保存别的系统 已阅数据
     * @param portalContentReadTasksDtos 已阅数据
     * @return
     */
    @ApiOperation(value = "保存别的系统 已阅数据", notes = "保存别的系统 已阅数据")
    @PostMapping("/read/saveList")
    public Result readSaveList(@RequestBody List<PortalContentReadTasksDto> portalContentReadTasksDtos) {
        return Result.success(portalContentReadTasksService.saveList(portalContentReadTasksDtos));
    }

    /**
     * 同步别的系统 已阅数据信息删除
     * @param apiTaskDto 所传数据的系统业务id 和code
     * @return
     */
    @ApiOperation(value = "同步别的系统 已阅数据 信息删除", notes = "同步别的系统 已阅数据 信息删除")
    @PostMapping("/read/delBySysBizIdAndCode")
    public Result readDelBySysBizIdAndCode(@RequestBody ApiTaskDto apiTaskDto) {
        return Result.success(portalContentReadTasksService.delBySysBizIdAndCode(apiTaskDto));
    }

    /**
     * 根据业务id和code 删除所有集中待办
     * @param apiTaskDto 所传数据的系统业务id 和code
     * @return
     */
    @ApiOperation(value = "根据业务id和code 删除所有集中待办", notes = "根据业务id和code 删除所有集中待办")
    @PostMapping("/allTask/delBySysBizIdAndCode")
    public Result allTaskDelBySysBizIdAndCode(@RequestBody ApiTaskDto apiTaskDto) {
        return Result.success(portalContentTodoTasksService.delAllBySysBizIdAndCode(apiTaskDto));
    }

    /**
     * 保存友报账系统待办数据
     * @param portalContentTodoTasksDtos 待办数据
     * @return
     */
    @ApiOperation(value = "保存友报账系统待办数据", notes = "保存友报账系统待办数据")
    @PostMapping("/ybz/todo/saveList" )
    public Result ybzTodoSaveList(@RequestBody List<PortalYbzTodoTasksDto> portalContentTodoTasksDtos) {
        return Result.success(portalYbzTodoTasksService.saveList(portalContentTodoTasksDtos));
    }

    /**
     * 保存友报账系统已办数据
     * @param portalYbzDoneTasksDtos 已办数据
     * @return
     */
    @ApiOperation(value = "保存友报账系统已办数据", notes = "保存友报账系统已办数据")
    @PostMapping("/ybz/done/saveList" )
    public Result ybzDoneSaveList(@RequestBody List<PortalYbzDoneTasksDto> portalYbzDoneTasksDtos) {
        return Result.success(portalYbzDoneTasksService.saveList(portalYbzDoneTasksDtos));
    }

//    /**
//     * 发送简单的MQ消息
//     * @return
//     */
//    @GetMapping("/send")
//    public Result send() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
//        PortalTaskNumDto portalTaskNumDto = new PortalTaskNumDto();
//        portalTaskNumDto.setIdCard("37xxxxxx");
//        portalTaskNumDto.setUserId(31232132L);
//        portalTaskNumDto.setUserNameCh("张三");
//        portalTaskNumDto.setSysCode("oa");
//        portalTaskNumDto.setNum(1000);
//        String jsonStr = JSONObject.toJSONString(portalTaskNumDto);
//       log.info("发送MQ消息内容：" + jsonStr);
//        Message sendMsg = new Message("oa_portal_tasks_topic", "todo", jsonStr.getBytes());
//        // 默认3秒超时
//        SendResult sendResult = defaultMQProducer.send(sendMsg,new MessageQueueSelector() {
//            @Override
//            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                //Long id = Long.valueOf((String)arg);
//                //long index = id % mqs.size();
//                return mqs.get(0);
//            }
//        }, jsonStr);
//        log.info("消息发送响应：" + sendResult.toString());
//        return new Result().success(sendResult);
//    }

    /**
     * 获取geps任务数量
     * @return
     */
    @GetMapping("/getGepsTaskCount")
    public Result getGepsTaskCount(){
        Map<String, Integer> gepsCount = portalApiService.getGepsCount();
        return Result.success(gepsCount);
    }

    /**
     * 获取oa任务数量
     * @return
     */
    @GetMapping("/getOaTaskCount")
    public Result getOaTaskCount(){
        Map<String, Integer> oaCount = portalApiService.getOaCount();
        return Result.success(oaCount);
    }

    /**
     * 调用webservice接口
     */
    public String sendWsdl(Object obj) {
        log.info("--------调用webservice接口begin-------");
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

        //对方的wsdl地址
        Client client = dcf.createClient("http://127.0.0.1:8888/WebService/GetUserTaskCount.asmx?wsdl");
        String json = null;
        try {
            //QName qName = new QName("", "GetUserTaskCount");                                                //*原文章链接：https://blog.csdn.net/qq_27471405/article/details/105275657     * 其他均为盗版，公众号：灵儿的笔记(zygxsq)
            //Object[] objects1= client.invoke(qName, "513822199109070199");
            Object[] objects1= client.invoke("GetUserTaskCount", "513822199109070199");

            json = JSONObject.toJSONString(objects1[0]);
            System.out.println("返回数据:" + json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            log.info("服务器断开连接，请稍后再试");
        }
        log.info("--------调用webservice接口end-------");
        return json;
    }


}
