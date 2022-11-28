/*
package com.netwisd.biz.study.job;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.dto.MessageReceiverUserDto;
import com.netwisd.biz.study.constants.PaperUserStatus;
import com.netwisd.biz.study.constants.StudyStatus;
import com.netwisd.biz.study.constants.YesNo;
import com.netwisd.biz.study.dto.StudyPlanDefDto;
import com.netwisd.biz.study.dto.StudyUserDto;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.feign.MsgClient;
import com.netwisd.biz.study.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

*/
/**
 * @Description: 任务调度——修改培训计划状态及相关信息。注：（studyPlan类名和方法名不可修改-planDefService里固定的名称）
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/17 11:24
 *//*

@Slf4j
@Component("studyPlan")
public class PlanTask {

    @Value("${incloud.study.msg.planCode}")
    private String planMsgCode;

    //消息的系统来源
    private String msgSource = "STUDY";

    @Autowired
    private StudyPlanDefService studyPlanDefService;

    @Autowired
    private StudyPlanUserService studyPlanUserService;

    @Autowired
    private StudyPlanLessonService studyPlanLessonService;

    @Autowired
    private StudyPlanCertificateService studyPlanCertificateService;

    @Autowired
    private StudyPlanLessonPaperService studyPlanLessonPaperService;

    @Autowired
    private StudyPlanPaperUserService studyPlanPaperUserService;

    @Autowired
    private MsgClient msgClient;

    */
/**
     * 任务调度-修改培训计划状态
     *
     * @param
     * @throws Exception
     *//*

    @Transactional
    public void updatePlan(Long planId) throws Exception {
        StudyPlanDef studyPlanDef = studyPlanDefService.getById(planId);
        if (null != studyPlanDef) {
            log.info("执行有参方法：" + studyPlanDef);
            //修改培训计划状态
            if (LocalDateTime.now().isBefore(studyPlanDef.getStartTime())) {
                studyPlanDef.setStatus(StudyStatus.NO_START.code.intValue());
            } else if (LocalDateTime.now().isAfter(studyPlanDef.getStartTime()) && LocalDateTime.now().isBefore(studyPlanDef.getEndTime())) {
                studyPlanDef.setStatus(StudyStatus.START.code.intValue());
                //消息通知。当计划开始时，通知相关人员
                //查出来该计划下的人员
                LambdaQueryWrapper<StudyPlanUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(StudyPlanUser::getPlanId, planId);
                List<StudyPlanUser> list = studyPlanUserService.list(queryWrapper);
                sendPlanMsg(list);
            } else if (LocalDateTime.now().isAfter(studyPlanDef.getEndTime())) {
                studyPlanDef.setStatus(StudyStatus.END.code.intValue());
            }
            //plan_def
            LambdaUpdateWrapper<StudyPlanDef> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(StudyPlanDef::getId, studyPlanDef.getId());
            updateWrapper.set(StudyPlanDef::getStatus, studyPlanDef.getStatus());
            studyPlanDefService.update(updateWrapper);
            //plan_certificate
            LambdaUpdateWrapper<StudyPlanCertificate> cUpdateWrapper = new LambdaUpdateWrapper<>();
            cUpdateWrapper.in(StudyPlanCertificate::getPlanId, studyPlanDef.getId());
            cUpdateWrapper.set(StudyPlanCertificate::getPlanStatus, studyPlanDef.getStatus());
            studyPlanCertificateService.update(cUpdateWrapper);
            //plan_lesson
            LambdaUpdateWrapper<StudyPlanLesson> lPpdateWrapper = new LambdaUpdateWrapper<>();
            lPpdateWrapper.in(StudyPlanLesson::getPlanId, studyPlanDef.getId());
            lPpdateWrapper.set(StudyPlanLesson::getPlanStatus, studyPlanDef.getStatus());
            studyPlanLessonService.update(lPpdateWrapper);
            //plan_user
            LambdaUpdateWrapper<StudyPlanUser> uUpdateWrapper = new LambdaUpdateWrapper<>();
            uUpdateWrapper.in(StudyPlanUser::getPlanId, studyPlanDef.getId());
            uUpdateWrapper.set(StudyPlanUser::getPlanStatus, studyPlanDef.getStatus());
            studyPlanUserService.update(uUpdateWrapper);
            //当计划结束时，需要修改在该计划下人员和试卷的状态为未判分，不管人员做没做这个试卷
            if (studyPlanDef.getStatus().intValue() == StudyStatus.END.code.intValue()) {
                LambdaQueryWrapper<StudyPlanLessonPaper> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(StudyPlanLessonPaper::getPlanId, planId);
                List<StudyPlanLessonPaper> lessonPapers = studyPlanLessonPaperService.list(queryWrapper);
                if (CollectionUtil.isNotEmpty(lessonPapers)) {
                    Set<Long> paperIds = lessonPapers.stream().map(StudyPlanLessonPaper::getPaperId).collect(Collectors.toSet());
                    //paper_user表修改状态
                    LambdaUpdateWrapper<StudyPlanPaperUser> wrapper = new LambdaUpdateWrapper<>();
                    wrapper.in(StudyPlanPaperUser::getPaperId, paperIds);
                    wrapper.set(StudyPlanPaperUser::getStatus, PaperUserStatus.NO_MARK.code);
                    studyPlanPaperUserService.update(wrapper);
                }
            }
        }
    }

    */
/**
     * 消息通知
     *
     * @param studyPlanUsers
     *//*

    @Transactional
    public void sendPlanMsg(List<StudyPlanUser> studyPlanUsers) {
        //当人员不为空时才去发消息
        if (CollectionUtil.isNotEmpty(studyPlanUsers)) {
            List<MessageReceiverUserDto> receiverUserList = new ArrayList<>();
            MessageDto messageDto = new MessageDto();
            for (StudyPlanUser studyPlanUser : studyPlanUsers) {
                MessageReceiverUserDto messageReceiverUserDto = new MessageReceiverUserDto();
                messageReceiverUserDto.setReceiverUserId(studyPlanUser.getUserId());
                messageReceiverUserDto.setReceiverUserName(studyPlanUser.getUserName());
                receiverUserList.add(messageReceiverUserDto);
            }
            //人员信息
            messageDto.setReceiverUserList(receiverUserList);
            //系统发送
            messageDto.setIsSystemSend(YesNo.YES.code);
            //消息来源——固定的"STUDY"
            messageDto.setMsgSource(msgSource);
            //消息模板code，nacos上配置，消息模板配置，code保持一致
            messageDto.setTmpCode(planMsgCode);
            //feignClient调incloud-base-msg发送消息
            msgClient.sendTmpMsg(messageDto);
        }
    }
}
*/
