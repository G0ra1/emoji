package com.netwisd.biz.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.mdm.constants.YesNo;
import com.netwisd.biz.mdm.dto.PushMsgDto;
import com.netwisd.biz.mdm.entity.PushMsg;
import com.netwisd.biz.mdm.mapper.PushMsgMapper;
import com.netwisd.biz.mdm.service.PushMsgService;
import com.netwisd.biz.mdm.vo.PushMsgVo;
import com.netwisd.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
//import com.netwisd.base.common.user.LoginAppUser;
//import com.netwisd.base.common.util.AppUserUtil;

/**
 * @Description 推送消息 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-10-16 11:54:52
 */
@Service
@Slf4j
public class PushMsgServiceImpl extends ServiceImpl<PushMsgMapper, PushMsg> implements PushMsgService {


    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PushMsgMapper pushMsgMapper;


    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsMessagingTemplate jmsTemplate;

    // 发送消息，destination是发送到的队列，message是待发送的消息
    @Override
    public void sendMessage(Destination destination, final String message){
        jmsTemplate.convertAndSend(destination, message);
    }

    //@JmsListener(destination="cnec22.zh.itemcode")
    public void receive_cnec22_zh_itemcode(String message) throws IOException {
        String msg= URLDecoder.decode(message, "utf-8");
        msg= msg.substring(12);   // outputValue={....} 去除字符串中的outputValue=
        log.debug("cnec22.zh.itemcode message --------------start");
        log.debug(msg);
        log.debug("cnec22.zh.itemcode message --------------end");
        System.out.println("cnec22.zh.itemcode message: "+msg);
    }
    //@JmsListener(destination="cneczyj.zh.itemtype")
    public void receive_cnec22_zh_itemtype(String message) throws IOException {
        String msg= URLDecoder.decode(message, "utf-8");
        msg= msg.substring(12);   // outputValue={....} 去除字符串中的outputValue=
        log.debug("cnec22.zh.itemtype message --------------start");
        log.debug(msg);
        log.debug("cnec22.zh.itemtype message --------------end");
        System.out.println("cnec22.zh.itemtype message: "+msg);
    }

    /**
    * 单表简单查询操作
    * @param pushMsgDto
    * @return
    */
    @Override
    public Page list(PushMsgDto pushMsgDto) {
        log.debug("list查询消息列表参数：" + pushMsgDto);
        LambdaQueryWrapper<PushMsg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(pushMsgDto.getAccessToken()),PushMsg::getAccessToken,pushMsgDto.getAccessToken());
        queryWrapper.eq(StringUtils.isNotBlank(pushMsgDto.getDeptId()),PushMsg::getDeptId,pushMsgDto.getDeptId());
        queryWrapper.eq(StringUtils.isNotBlank(pushMsgDto.getDeptName()),PushMsg::getDeptName,pushMsgDto.getDeptName());
        queryWrapper.eq(StringUtils.isNotBlank(pushMsgDto.getModuleCode()),PushMsg::getModuleCode,pushMsgDto.getModuleCode());
        queryWrapper.eq(StringUtils.isNotBlank(pushMsgDto.getOrgId()),PushMsg::getOrgId,pushMsgDto.getOrgId());
        queryWrapper.eq(StringUtils.isNotBlank(pushMsgDto.getOrgName()),PushMsg::getOrgName,pushMsgDto.getOrgName());
        queryWrapper.eq(ObjectUtil.isNotNull(pushMsgDto.getState()),PushMsg::getState,pushMsgDto.getState());
        queryWrapper.eq(ObjectUtil.isNotNull(pushMsgDto.getPriority()),PushMsg::getPriority,pushMsgDto.getPriority());
        Page<PushMsgVo> page = pushMsgMapper.selectPage(pushMsgDto.getPage(),queryWrapper);
        log.debug("list查询查询条数:"+page.getTotal());
        return page;
    }

    /**
    * 自定义查询操作
    * @param pushMsgDto
    * @return
    */
    @Override
    public Page lists(PushMsgDto pushMsgDto) {
        Page<PushMsgVo> pageVo = pushMsgMapper.getPageList(pushMsgDto.getPage(),pushMsgDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PushMsgVo get(Long id) {
        PushMsg pushMsg = super.getById(id);
        PushMsgVo pushMsgVo = null;
        if(pushMsg !=null){
            pushMsgVo = dozerMapper.map(pushMsg,PushMsgVo.class);
        }
        log.debug("查询成功");
        return pushMsgVo;
    }

   /**
    * 保存实体
    * @param pushMsgDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PushMsgDto pushMsgDto) {
        PushMsg pushMsg = dozerMapper.map(pushMsgDto,PushMsg.class);
        boolean result = super.save(pushMsg);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param pushMsgDto
    * @return
    */
    @Transactional
    @Override
    public Result update(PushMsgDto pushMsgDto) {
        if(ObjectUtil.isNull(pushMsgDto.getId())) {
           return Result.failed("消息id不能为空！");
        }
        PushMsg pushMsg = new PushMsg();
        pushMsg.setId(pushMsgDto.getId());
        pushMsg.setState(YesNo.YES.code);
        pushMsg.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(pushMsg);
        if(result){
            log.debug("修改成功");
        }
        return Result.success();
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public PushMsgDto initPushMsg(PushMsgDto pushMsgDto) {
//        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//        pushMsgDto.setUserId(loginAppUser.getUserId());
//        pushMsgDto.setUserName(loginAppUser.getUsername());
//        pushMsgDto.setDeptId(loginAppUser.getDeptId());
//        pushMsgDto.setDeptName(loginAppUser.getDeptName());
//        pushMsgDto.setOrgId(loginAppUser.getMechanismId());
//        pushMsgDto.setOrgName(loginAppUser.getMechanismName());
//        String bearar = AppUserUtil.getAccessToken();
//        String accessToken = StrUtil.subAfter(bearar,"Bearer ",true);
//        pushMsgDto.setAccessToken(accessToken);
        pushMsgDto.setState(0); //默认未读
        return pushMsgDto;
    }

    @Override
    public Result queryNoneNum(String accessToken) {
        log.debug("queryNoneNum查询角标未读数量accessToken" + accessToken);
        if(StringUtils.isNotBlank(accessToken)) {
            LambdaQueryWrapper<PushMsg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PushMsg::getAccessToken,accessToken);
            queryWrapper.eq(PushMsg::getState,YesNo.NO.code);//未读
            int num = pushMsgMapper.selectCount(queryWrapper);
            log.debug("queryNoneNum查询角标未读数量返回值：" + num);
            return  Result.success(num);
        } else {
            return  Result.failed("accessToken 值不能为空！");
        }
    }
}
