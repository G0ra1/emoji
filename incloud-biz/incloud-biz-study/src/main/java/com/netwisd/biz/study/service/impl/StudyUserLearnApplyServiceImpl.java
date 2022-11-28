package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.constants.LearnApplyStatusEnum;
import com.netwisd.biz.study.constants.LearnApplyTargetTypeEnum;
import com.netwisd.biz.study.constants.RangeTypeEnum;
import com.netwisd.biz.study.dto.StudyUserLearnApplyDto;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.*;
import com.netwisd.biz.study.service.StudyUserLearnApplyService;
import com.netwisd.biz.study.vo.StudyUserLearnApplyVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 人员学习申请
 * @date 2022-04-25 09:39:13
 */
@Service
@Slf4j
public class StudyUserLearnApplyServiceImpl extends ServiceImpl<StudyUserLearnApplyMapper, StudyUserLearnApply> implements StudyUserLearnApplyService {

    private static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyUserLearnApplyMapper studyUserLearnApplyMapper;

    @Autowired
    private StudySpecialMapper studySpecialMapper;

    @Autowired
    private StudySpecialRangeMapper studySpecialRangeMapper;

    @Autowired
    private StudyLessonMapper studyLessonMapper;

    @Autowired
    private StudyCouMapper studyCouMapper;

    @Autowired
    private StudyCouPermMapper studyCouPermMapper;

    @Autowired
    private MdmClient mdmClient;//主数据client

    /**
     * 分页查询人员学习申请
     *
     * @param infoDto
     * @return
     */
    @Override
    public Page pageList(StudyUserLearnApplyDto infoDto) {
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
        Integer studyUserRole = mdmClient.getStudyUserRole(appUser.getId());//获取当前登陆人的角色
        LambdaQueryWrapper<StudyUserLearnApply> queryWrapper = new LambdaQueryWrapper<>();
        //如果当前登陆人是老师，尽可查看自己上传。
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {//判断是否是老师
            queryWrapper.eq(StudyUserLearnApply::getCreateUserId, appUser.getId());
        }
        queryWrapper.eq(infoDto.getTargetType() != null, StudyUserLearnApply::getTargetType, infoDto.getTargetType());
        queryWrapper.like(infoDto.getTargetType()!=null && infoDto.getTargetType().equals(LearnApplyTargetTypeEnum.SPECIAL.code) && StringUtils.isNotBlank(infoDto.getTargetName()),StudyUserLearnApply::getTargetName,infoDto.getTargetName());
        queryWrapper.like(infoDto.getTargetType()!=null && infoDto.getTargetType().equals(LearnApplyTargetTypeEnum.COU.code) && StringUtils.isNotBlank(infoDto.getTargetName()),StudyUserLearnApply::getLessonName,infoDto.getTargetName());
        queryWrapper.eq(infoDto.getUserId() != null, StudyUserLearnApply::getUserId, infoDto.getUserId());
        queryWrapper.eq(infoDto.getTargetId() != null, StudyUserLearnApply::getTargetId, infoDto.getTargetId());
        queryWrapper.eq(infoDto.getTargetManagerId() != null, StudyUserLearnApply::getTargetManagerId, infoDto.getTargetManagerId());
//        queryWrapper.like(StringUtils.isNotBlank(infoDto.getTargetName()), StudyUserLearnApply::getTargetName, infoDto.getTargetName());
        queryWrapper.eq(infoDto.getApplyStatus() != null, StudyUserLearnApply::getApplyStatus, infoDto.getApplyStatus());
        queryWrapper.orderByDesc(StudyUserLearnApply::getApplyTime);
        Page<StudyUserLearnApply> page = studyUserLearnApplyMapper.selectPage(infoDto.getPage(), queryWrapper);
        Page<StudyUserLearnApplyVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyUserLearnApplyVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public StudyUserLearnApplyVo detail(Long id) {
        StudyUserLearnApply info = super.getById(id);
        log.debug("查询成功");
        return Optional.ofNullable(info).map(x -> dozerMapper.map(x, StudyUserLearnApplyVo.class)).orElseGet(() -> new StudyUserLearnApplyVo());
    }

    @Override
    @Transactional
    public void dealApply(StudyUserLearnApplyDto infoDto) {
        Optional.ofNullable(infoDto.getId()).orElseThrow(() -> new IncloudException("请传递记录ID"));
        Optional.ofNullable(infoDto.getApplyStatus()).orElseThrow(() -> new IncloudException("请选择处理结果"));
        if (StringUtils.isBlank(infoDto.getDealMessage())) {
            infoDto.setDealMessage(infoDto.getApplyStatus() == LearnApplyStatusEnum.APPLY_PASS.code ? "同意" : "不同意");
        }
        StudyUserLearnApply info = this.getById(infoDto.getId());
        if (info.getApplyStatus() != LearnApplyStatusEnum.NO_AUDIT.code) {
            throw new IncloudException("该记录已处理");
        }
        info.setApplyStatus(infoDto.getApplyStatus());
        info.setDealTime(LocalDateTime.now());
        info.setDealMessage(infoDto.getDealMessage());
        info.setUpdateTime(LocalDateTime.now());
        this.updateById(info);
        //处理成果逻辑
        if (infoDto.getApplyStatus() == LearnApplyStatusEnum.APPLY_PASS.code) {
            LoginAppUser appUser = AppUserUtil.getLoginAppUser();
            if (LearnApplyTargetTypeEnum.SPECIAL.code == info.getTargetType()) {//专题
                StudySpecialRange specialRange = new StudySpecialRange();
                specialRange.setId(IDENTIFIER_GENERATOR.nextId(this).longValue());
                specialRange.setSpecialId(info.getTargetId());
                specialRange.setRangeType(RangeTypeEnum.USER.code);
                specialRange.setRangeId(info.getUserId());
                specialRange.setRangeName(info.getUserName());
                specialRange.setCreateTime(LocalDateTime.now());
                specialRange.setCreateUserId(appUser.getId());
                specialRange.setCreateUserName(appUser.getUsername());
                //保存
                studySpecialRangeMapper.insert(specialRange);
            } else if (LearnApplyTargetTypeEnum.COU.code == info.getTargetType()) {//课件
                StudyCouPerm couPerm = new StudyCouPerm();
                couPerm.setId(IDENTIFIER_GENERATOR.nextId(this).longValue());
                couPerm.setCouId(info.getTargetId());
                couPerm.setRangeType(RangeTypeEnum.USER.code);
                couPerm.setRangeId(info.getUserId());
                couPerm.setRangeName(info.getUserName());
                couPerm.setCreateTime(LocalDateTime.now());
                couPerm.setCreateUserId(appUser.getId());
                couPerm.setCreateUserName(appUser.getUsername());
                //保存
                studyCouPermMapper.insert(couPerm);
            }
        }
    }

    @Override
    @Transactional
    public Boolean specialLearnApply(StudyUserLearnApplyDto infoDto) {
        log.info("专题学习申请");
        Optional.ofNullable(infoDto.getTargetId()).orElseThrow(() -> new IncloudException("请传递专题ID"));
        Optional.ofNullable(infoDto.getApplyMessage()).orElseThrow(() -> new IncloudException("请输入申请原因"));

        LoginAppUser appUser = AppUserUtil.getLoginAppUser();

        //查询是否有已经申请的记录
        if (studyUserLearnApplyMapper.selectCount(Wrappers.<StudyUserLearnApply>lambdaQuery().eq(StudyUserLearnApply::getUserId, appUser.getId()).eq(StudyUserLearnApply::getTargetType, LearnApplyTargetTypeEnum.SPECIAL.code).eq(StudyUserLearnApply::getTargetId, infoDto.getTargetId()).in(StudyUserLearnApply::getApplyStatus, Arrays.asList(LearnApplyStatusEnum.NO_AUDIT.code, LearnApplyStatusEnum.APPLY_PASS.code))) > 0) {
            throw new IncloudException("您已申请过该专题,请勿重复操作");
        }

        StudyUserLearnApply info = new StudyUserLearnApply();
        info.setId(IDENTIFIER_GENERATOR.nextId(this).longValue());
        info.setCreateTime(LocalDateTime.now());
        info.setCreateUserId(appUser.getId());
        info.setCreateUserName(appUser.getUserNameCh());

        info.setUserId(appUser.getId());
        info.setUserName(appUser.getUsername());
        info.setTargetType(LearnApplyTargetTypeEnum.SPECIAL.code);
        info.setTargetId(infoDto.getTargetId());
        StudySpecial special = studySpecialMapper.selectById(infoDto.getTargetId());
        info.setTargetName(special.getSpecialName());
        info.setTargetManagerId(special.getCreateUserId());
        info.setTargetManagerName(special.getCreateUserName());
        info.setApplyMessage(infoDto.getApplyMessage());
        info.setApplyTime(LocalDateTime.now());
        info.setApplyStatus(LearnApplyStatusEnum.NO_AUDIT.code);
        return studyUserLearnApplyMapper.insert(info) > 0;
    }

    @Override
    @Transactional
    public Boolean couLearnApply(StudyUserLearnApplyDto infoDto) {
        log.info("课件学习申请");
        Optional.ofNullable(infoDto.getLessonId()).orElseThrow(() -> new IncloudException("请传递课程ID"));
        Optional.ofNullable(infoDto.getTargetId()).orElseThrow(() -> new IncloudException("请传递课件ID"));
        Optional.ofNullable(infoDto.getApplyMessage()).orElseThrow(() -> new IncloudException("请输入申请原因"));

        LoginAppUser appUser = AppUserUtil.getLoginAppUser();

        //查询是否有已经申请的记录
        if (studyUserLearnApplyMapper.selectCount(Wrappers.<StudyUserLearnApply>lambdaQuery().eq(StudyUserLearnApply::getUserId, appUser.getId()).eq(StudyUserLearnApply::getTargetType, LearnApplyTargetTypeEnum.COU.code).eq(StudyUserLearnApply::getTargetId, infoDto.getTargetId()).eq(StudyUserLearnApply::getLessonId, infoDto.getLessonId()).in(StudyUserLearnApply::getApplyStatus, Arrays.asList(LearnApplyStatusEnum.NO_AUDIT.code, LearnApplyStatusEnum.APPLY_PASS.code))) > 0) {
            throw new IncloudException("您已申请过该课件,请勿重复操作");
        }

        StudyUserLearnApply info = new StudyUserLearnApply();
        info.setId(IDENTIFIER_GENERATOR.nextId(this).longValue());
        info.setCreateTime(LocalDateTime.now());
        info.setCreateUserId(appUser.getId());
        info.setCreateUserName(appUser.getUserName());

        info.setUserId(appUser.getId());
        info.setUserName(appUser.getUsername());
        info.setTargetType(LearnApplyTargetTypeEnum.COU.code);
        info.setTargetId(infoDto.getTargetId());
        StudyLesson lesson = Optional.ofNullable(studyLessonMapper.selectById(infoDto.getLessonId())).orElseGet(() -> new StudyLesson());
        StudyCou cou = Optional.ofNullable(studyCouMapper.selectById(infoDto.getTargetId())).orElseGet(() -> new StudyCou());
        info.setLessonId(lesson.getId());
        info.setLessonName(lesson.getLessonName());
        info.setTargetName(cou.getCouName());
        info.setTargetManagerId(lesson.getCreateUserId());
        info.setTargetManagerName(lesson.getCreateUserName());
        info.setApplyMessage(infoDto.getApplyMessage());
        info.setApplyTime(LocalDateTime.now());
        info.setApplyStatus(LearnApplyStatusEnum.NO_AUDIT.code);
        return studyUserLearnApplyMapper.insert(info) > 0;
    }
}
