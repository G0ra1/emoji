package com.netwisd.base.socket.util;

import cn.hutool.core.map.MapUtil;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.netwisd.base.socket.config.JiguangConfig;
import com.netwisd.base.socket.dto.PushMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Slf4j
@Component
public class JiguangPushUtil {

    @Autowired
    private JiguangConfig jiguangConfig;

    //推送文本通知
    public static PushPayload buildPushMsgAllAlias(PushMsgDto pushMsg) {
        //IOS通知
        JsonObject sound = new JsonObject();
        sound.add("critical", new JsonPrimitive(1));
        sound.add("name", new JsonPrimitive("default"));
        sound.add("volume", new JsonPrimitive(0.2));
        //业务使用，扩展字段
        Map<String, String> extras = MapUtil.newHashMap();
        try {
            extras = BeanUtils.describe(pushMsg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())//推送所有平台
                .setAudience(Audience.alias(String.valueOf(pushMsg.getLoginId())))//设备别名
                .setNotification(
                        Notification.newBuilder()
                                .setAlert(pushMsg.getMsgContent())//这里指定了，则会覆盖上级统一指定的 alert 信息；内容可以为空字符串，则表示不展示到通知栏。
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder()
                                                .addExtras(extras)
                                                .setTitle(pushMsg.getMsgContent())//如果指定了，则通知里原来展示 App 名称的地方，将展示成这个字段。
                                                .build()
                                ).addPlatformNotification(
                                        IosNotification.newBuilder()
                                                .setAlert(pushMsg.getMsgContent())
                                                .setSound(sound)
                                                .incrBadge(1)
                                                .setMutableContent(false)
                                                .addExtras(extras)
                                                .build()
                                ).build()
                ).setOptions(
                        Options.newBuilder()
                                .setSendno(ServiceHelper.generateSendno())
                                .setApnsProduction(false)
                                .build()
                ).build();
    }

    public void sendPush(PushPayload payload) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(jiguangConfig.getMasterSecret(), jiguangConfig.getAppKey(), null, clientConfig);
        try {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            log.info(">>>>>>" + payload + ">>>>>>");
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            PushResult result = jpushClient.sendPush(payload);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            log.info(">>>>>>" + result + ">>>>>>");
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(result);
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            log.error("Sendno: " + payload.getSendno());
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            log.error("Sendno: " + payload.getSendno());
        } finally {
            jpushClient.close();
        }
    }

}
