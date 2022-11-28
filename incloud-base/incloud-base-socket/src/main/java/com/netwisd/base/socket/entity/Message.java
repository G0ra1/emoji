package com.netwisd.base.socket.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String id;

    private String msgTitle;

    private String msgContent;

    private Long sendUserId;

    private String sendUserName;

    private String receiverUserId;

    private String receiverUserName;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss SSS")
    public LocalDateTime sendTime;

    private String msgPcUrl;

    private String msgH5Url;

    private String msgSource;

    private int unReadNumber;

}
