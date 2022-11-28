package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.github.dozermapper.core.Mapper;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.netwisd.base.common.mdm.dto.MdmDutyDto;
import com.netwisd.base.common.mdm.dto.MdmOrgDto;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.*;
import com.netwisd.base.mdm.constants.MqTagEnum;
import com.netwisd.base.mdm.service.*;
import com.netwisd.common.core.util.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class MdmMqServiceImpl implements MdmMqService {

    @Value("${spring.rocketmq.disable}")
    private boolean disable;

    @Value("${spring.rocketmq.orgTopics}")
    private String orgTopics;

    @Value("${spring.rocketmq.postTopics}")
    private String postTopics;

    @Value("${spring.rocketmq.dutyTopics}")
    private String dutyTopics;

    @Value("${spring.rocketmq.userTopics}")
    private String userTopics;

    @Value("${spring.rocketmq.mdmUserCache}")
    private String mdmUserCache;

    @Autowired
    @Lazy
    private MdmOrgService mdmOrgService;

    @Autowired
    @Lazy
    private MdmUserService mdmUserService;

    @Autowired
    @Lazy
    private MdmPostService mdmPostService;

    @Autowired
    @Lazy
    private MdmDutyService mdmDutyService;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private Mapper dozerMapper;

    @Override
    @SneakyThrows
    public Result sendRocketMq(String topic, String tag, String msg){
        if(disable) {//判断如果禁用则不发送消息
            log.error("mq消息已经禁用！");
            return  Result.success();
        }
        if (StringUtils.isEmpty(msg)) {
            log.error("发送rocketMq msg为空。");
            return  Result.success();
        }
        if(StringUtils.isEmpty(topic)) {
            log.error("发送rocketMq topic为空。");
            return  Result.success();
        }
        if(StringUtils.isEmpty(tag)) {
            log.error("发送rocketMq tag。");
            return  Result.success();
        }
        log.info("发送rocketMq消息内容：" + msg);
        try {
            List<String> streamStr = Stream.of(topic.split(",")).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(streamStr)) {
                for (String tp : streamStr) {
                    Message sendMsg = new Message(tp, tag, msg.getBytes());
                    // 默认3秒超时
                    SendResult sendResult = defaultMQProducer.send(sendMsg,new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                            return mqs.get(0); //数据量不大 直接指定一个队列
                        }
                    }, msg);
                    log.info("消息发送响应：" + sendResult.toString());
                }
            } else {
                log.error("发送rocketMq topic为空。");
                return  Result.success();
            }
        } catch (Exception e) {
            log.error("消息发送失败！msg:" + msg);
        }
        return  Result.success();
    }

    @Override
    @SneakyThrows
    public Result syncMqForOrgs(MdmOrgDto mdmOrgDto){
        List<MdmOrgAllVo> orgs = mdmOrgService.list(mdmOrgDto);
        for (MdmOrgAllVo org : orgs) {
            if(null != org.getLvType() && 0 != org.getLvType()) { //判断必须设置了 geps 级别类型才能推送
                MdmOrgAllVo _org = dozerMapper.map(org, MdmOrgAllVo.class);
                _org.setKids(null);
                log.info("Rocket 同步全量机构--发送MQ消息内容：" + org.toString());
                this.sendRocketMq(orgTopics, MqTagEnum.ADD.code, JSON.toJSONString(_org, SerializerFeature.WriteMapNullValue));
            }
            List<MdmOrgAllVo> kids = org.getKids();
            handleForOrg(kids);
        }
        return Result.success();
    }

    public void handleForOrg(List<MdmOrgAllVo> kids) {
        if(CollectionUtil.isNotEmpty(kids)) {
            for (MdmOrgAllVo kid : kids) {
                if(null != kid.getLvType() && 0 != kid.getLvType()) { //判断必须设置了 geps 级别类型才能推送
                    MdmOrgAllVo _org = dozerMapper.map(kid, MdmOrgAllVo.class);
                    _org.setKids(null);
                    log.info("Rocket 同步全量机构--发送MQ消息内容：" + _org.toString());
                    this.sendRocketMq(orgTopics, MqTagEnum.ADD.code, JSON.toJSONString(_org, SerializerFeature.WriteMapNullValue));
                }
                handleForOrg(kid.getKids());
            }
        }
    }

    @Override
    public Result syncMqForUsers(MdmUserDto mdmUserDto) {
        //查询出所有的机构信息 发送岗位时判断机构是否设置 lvType
        //MdmOrgDto mdmOrgDto = new MdmOrgDto();
        //List<MdmOrgAllVo> orgs = mdmOrgService.lists(mdmOrgDto);
        //Map<Long,List<MdmOrgAllVo>> mapOrg = orgs.stream().filter(d->null != d.getLvType() && 0!=d.getLvType()).collect(Collectors.groupingBy(MdmOrgAllVo::getId));
        //查出所有用户
        List<MdmUserVo> users = mdmUserService.lists(mdmUserDto);
        String tops[] = userTopics.split(",");
        for (MdmUserVo user : users) {
            this.sendRocketMq(tops[0], MqTagEnum.EDIT.code, JSON.toJSONString(user, SerializerFeature.WriteMapNullValue));
            //List<MdmOrgAllVo> orgList = mapOrg.get(user.getParentDeptId());
            //if(CollectionUtil.isNotEmpty(orgList)) {
               // MdmOrgAllVo mdmOrgAllVo = orgList.get(0);
//                if(null != mdmOrgAllVo.getLvType() && 0 != mdmOrgAllVo.getLvType()) {
//                    log.info("Rocket 同步全量用户--发送MQ消息内容：" + user.toString());
//                    this.sendRocketMq(tops[0], MqTagEnum.EDIT.code, JSON.toJSONString(user, SerializerFeature.WriteMapNullValue));
//                }
//                if(null != mdmOrgAllVo.getOaLvType() && 0 != mdmOrgAllVo.getOaLvType()) {
//                    log.info("Rocket 同步全量用户--发送MQ消息内容：" + user.toString());
//                    this.sendRocketMq(tops[1], MqTagEnum.EDIT.code, JSON.toJSONString(user, SerializerFeature.WriteMapNullValue));
//                }
            //}
        }
        return Result.success();
    }

    @Override
    public Result syncMqForPosts(MdmPostDto mdmPostDto) {
        //查询出所有的机构信息 发送岗位时判断机构是否设置 lvType
        MdmOrgDto mdmOrgDto = new MdmOrgDto();
        List<MdmOrgAllVo> orgs = mdmOrgService.lists(mdmOrgDto);
        Map<Long,List<MdmOrgAllVo>> mapOrg = orgs.stream().filter(d->null != d.getLvType() && 0!=d.getLvType()).collect(Collectors.groupingBy(MdmOrgAllVo::getId));
        List<MdmPostVo> posts = mdmPostService.lists(mdmPostDto);
        for (MdmPostVo post : posts) {
            //获取 岗位的直接上级 判断上级机构是否设置 lvType  如果设置了才发mq
            List<MdmOrgAllVo> orgList = mapOrg.get(post.getParentDeptId());
            if(CollectionUtil.isNotEmpty(orgList)) {
                MdmOrgAllVo mdmOrgAllVo = orgList.get(0);
                if(null != mdmOrgAllVo.getLvType() && 0 != mdmOrgAllVo.getLvType()) {
                    log.info("Rocket 同步全量岗位--发送MQ消息内容：" + post.toString());
                    this.sendRocketMq(postTopics, MqTagEnum.ADD.code, JSON.toJSONString(post, SerializerFeature.WriteMapNullValue));
                }
            }
        }
        return Result.success();
    }

    @Override
    public Result syncMqForDutys(MdmDutyDto mdmDutyDto) {
        MdmOrgDto mdmOrgDto = new MdmOrgDto();
        List<MdmOrgAllVo> orgs = mdmOrgService.lists(mdmOrgDto);
        Map<Long,List<MdmOrgAllVo>> mapOrg = orgs.stream().filter(d->null != d.getLvType() && 0!=d.getLvType()).collect(Collectors.groupingBy(MdmOrgAllVo::getId));
        //查询出所有职务
        List<MdmDutyVo> dutys = mdmDutyService.lists(mdmDutyDto);
        for (MdmDutyVo duty : dutys) {
            List<MdmOrgAllVo> orgList = mapOrg.get(duty.getParentDeptId());
            if(CollectionUtil.isNotEmpty(orgList)) {
                MdmOrgAllVo mdmOrgAllVo = orgList.get(0);
                if (null != mdmOrgAllVo.getLvType() && 0 != mdmOrgAllVo.getLvType()) {
                    log.info("Rocket 同步全量职务--发送MQ消息内容：" + duty.toString());
                    this.sendRocketMq(dutyTopics, MqTagEnum.ADD.code, JSON.toJSONString(duty, SerializerFeature.WriteMapNullValue));
                }
            }
        }
        return Result.success();
    }

//    @Override
//    @SneakyThrows
//    public Result sendAddRocketMqForCache(String tag, String msg) {
//        if (StringUtils.isEmpty(msg)) {
//            log.error("缓存主数据人员数据.发送rocketMq msg为空。");
//            return  Result.success();
//        }
//
//        if(StringUtils.isEmpty(tag)) {
//            log.error("缓存主数据人员数据.发送rocketMq tag。");
//            return  Result.success();
//        }
//        log.info("缓存主数据人员数据.发送rocketMq消息内容：" + msg);
//        if(StringUtils.isNotEmpty(mdmUserCache)) {
//            Message sendMsg = new Message(mdmUserCache, tag, msg.getBytes());
//            // 默认3秒超时
//            SendResult sendResult = defaultMQProducer.send(sendMsg,new MessageQueueSelector() {
//                @Override
//                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                    return mqs.get(0); //数据量不大 直接指定一个队列
//                }
//            }, msg);
//            log.info("缓存主数据人员数据.消息发送响应：" + sendResult.toString());
//        } else {
//            log.error("缓存主数据人员数据.发送rocketMq topic为空。");
//            return  Result.success();
//        }
//        return  Result.success();
//    }
}
