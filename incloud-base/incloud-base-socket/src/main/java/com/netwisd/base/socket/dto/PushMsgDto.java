package com.netwisd.base.socket.dto;

import com.netwisd.base.socket.constant.PushConstants;
import lombok.Data;

@Data
public class PushMsgDto {

    //推送的消息类型
    private String pushType = PushConstants.PUSH_TYPE_MSG;

    //消息类型
    private String msgType;

    //消息内容
    private String msgContent;

    //消息图片URL
    private String msgPicUrl;

    //消息跳转URL
    private String msgLeadUrl;

    //应用Id
    private String appId;

    //应用名称
    private String appName;

    //用户登录Id
    private Long loginId;

    //未读个数
    private Integer unreadCount;

    //推送welink用户Id
    private String userIds;

    //消息标题
    private String msgTitle;

    //消息来源
    private String msgSource;


}
