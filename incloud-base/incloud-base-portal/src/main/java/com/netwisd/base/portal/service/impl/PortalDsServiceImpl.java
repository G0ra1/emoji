package com.netwisd.base.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalDs;
import com.netwisd.base.portal.mapper.PortalDsMapper;
import com.netwisd.base.portal.service.PortalDsService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalDsDto;
import com.netwisd.base.portal.vo.PortalDsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 数据源管理 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-10 19:25:49
 */
@Service
@Slf4j
public class PortalDsServiceImpl extends ServiceImpl<PortalDsMapper, PortalDs> implements PortalDsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalDsMapper portalDsMapper;

//    @Autowired
//    DefaultMQProducer defaultMQProducer;


    /**
    * 单表简单查询操作
    * @param portalDsDto
    * @return
    */
    @Override
    public Page list(PortalDsDto portalDsDto) {
        LambdaQueryWrapper<PortalDs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(portalDsDto.getDsCode()), PortalDs::getDsCode,portalDsDto.getDsCode());
        queryWrapper.like(StringUtils.isNotBlank(portalDsDto.getDsName()), PortalDs::getDsName,portalDsDto.getDsName());
        Page<PortalDs> page = portalDsMapper.selectPage(portalDsDto.getPage(),queryWrapper);
        Page<PortalDsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalDsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalDsDto
    * @return
    */
    @Override
    public List<PortalDsVo> lists(PortalDsDto portalDsDto) {
        LambdaQueryWrapper<PortalDs> queryWrapper = new LambdaQueryWrapper<>();
        List<PortalDs> list = portalDsMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)) {
            List<PortalDsVo> listVo = DozerUtils.mapList(dozerMapper, list, PortalDsVo.class);
            log.debug("查询条数:"+list.size());
            return listVo;
        } else {
            return null;
        }
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalDsVo get(Long id) {
        PortalDs portalDs = super.getById(id);
        PortalDsVo portalDsVo = null;
        if(portalDs !=null){
            portalDsVo = dozerMapper.map(portalDs,PortalDsVo.class);
        }
        log.debug("查询成功");
        //toHtmlMQSend();
        //toHtmlMQReceive();
        return portalDsVo;

    }

    /**
    * 保存实体
    * @param portalDsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalDsDto portalDsDto) {
        PortalDs portalDs = dozerMapper.map(portalDsDto,PortalDs.class);
        boolean result = super.save(portalDs);
        if(result){
            log.debug("数据源保存成功.");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalDsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalDsDto portalDsDto) {
        PortalDs portalDs = dozerMapper.map(portalDsDto,PortalDs.class);
        portalDs.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalDs);
        if(result){
            log.debug("数据源修改成功.");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(String id) {
        if(StringUtils.isBlank(id)) {
            throw new IncloudException("删除数据的ID不能为空！");
        }
        List<String> streamStr = Stream.of(id.split(",")).collect(Collectors.toList());
        boolean result = super.removeByIds(streamStr);
        if(result){
            log.debug("数据源删除成功.");
        }
        return result;
    }

//    @SneakyThrows
//    void toHtmlMQSend(){
//        // 构建消息
//        Message msg = new Message("xhl-test","test",
//                "true".getBytes(RemotingHelper.DEFAULT_CHARSET));
//        // 同步发送
//        SendResult sendResult = defaultMQProducer.send(msg);
//        // 打印发送结果
//        log.info("function3类型函数全量获取消息发送：{}", sendResult);
//    }

//    @SneakyThrows
//    void toHtmlMQReceive(){
//        log.info("func3GlobalReceive！");
//        // 实例化消费者
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer1");
//        // 设置NameServer的地址
//        consumer.setNamesrvAddr("192.168.1.195:9876");
//        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
//        consumer.subscribe("xhl-test", "test");
//        //设置一下只拉取最后一次消息更新
//        //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        // 注册回调实现类来处理从broker拉取回来的消息
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                try {
//                    for (MessageExt messageExt : msgs){
//                        byte[] body = messageExt.getBody();
//                        String json = new String(body);
//                    }
//                }catch (Exception e){
//                    log.error("func3GlobalReceive 出错 {}",e.getMessage());
//                }finally {
//                    // 标记该消息已经被成功消费
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//            }
//        });
//        // 启动消费者实例
//        consumer.start();
//        log.info("func3GlobalReceive Started.");
//    }


}
