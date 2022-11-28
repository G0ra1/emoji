package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.constants.BrowseTypeEnum;
import com.netwisd.biz.study.constants.SpecialTimeTypeEnum;
import com.netwisd.biz.study.dto.StudyBrowseDto;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.mapper.StudyBrowseMapper;
import com.netwisd.biz.study.mapper.StudySpecialMapper;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.util.StudyTimeUtil;
import com.netwisd.biz.study.vo.StudyLessonForShowVo;
import com.netwisd.biz.study.vo.StudySpecialVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 云数网讯 sun@netwisd.com
 * @Description 浏览记录表 功能描述...
 * @date 2022-05-24 11:10:47
 */
@Service
@Slf4j
public class StudyBrowseServiceImpl extends ServiceImpl<StudyBrowseMapper, StudyBrowse> implements StudyBrowseService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyBrowseMapper studyBrowseMapper;

    @Autowired
    StudySpecialLessonService specialLessonService;

    @Autowired
    StudySpecialMapper studySpecialMapper;

    @Autowired
    StudyLessonCouService studyLessonCouService;

    @Autowired
    StudyCouService studyCouService;

    @Autowired
    StudySpecialService studySpecialService;

    @Autowired
    StudyLessonService studyLessonService;
    /**
     * 保存或更新浏览记录
     *
     * @param studyBrowseDto
     * @return
     */
    @Transactional
    @Override
    public void save(StudyBrowseDto studyBrowseDto) {
        List<StudyBrowse> studyBrowse = super.list(Wrappers.<StudyBrowse>lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(studyBrowseDto.getFkId()), StudyBrowse::getFkId, studyBrowseDto.getFkId())
                .eq(ObjectUtils.isNotEmpty(studyBrowseDto.getBrowseType()), StudyBrowse::getBrowseType, studyBrowseDto.getBrowseType())
                .eq(ObjectUtils.isNotEmpty(studyBrowseDto.getUserId()),StudyBrowse::getUserId,studyBrowseDto.getUserId())
        );
        if (CollectionUtil.isNotEmpty(studyBrowse)){
            StudyBrowse browseOne = studyBrowse.get(0);
            browseOne.setCreateTime(LocalDateTime.now());
            super.updateById(browseOne);
        }else {
            StudyBrowse browse = dozerMapper.map(studyBrowseDto, StudyBrowse.class);
            boolean result = super.save(browse);
            if (result) {
                log.debug("保存成功");
            }
        }
    }

    /**
     * 分页获取浏览过的专题
     * @param studyBrowseDto
     * @return
     */
    @Override
    public Page <StudySpecialVo> getSpecials(StudyBrowseDto studyBrowseDto) {
        log.info("我的浏览-专题参数：{}", studyBrowseDto);
        //获取当前登陆人
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<StudyBrowse> studyBrowses = studyBrowseMapper.selectList(Wrappers.<StudyBrowse>lambdaQuery()
                .eq(StudyBrowse::getUserId, loginAppUser.getId())
                .eq(StudyBrowse::getBrowseType, BrowseTypeEnum.SPECIAL.code));
        //从浏览表里获取的专题id
        Page<StudySpecialVo> studySpecialVoPage  = new Page<>();
        if (CollectionUtil.isNotEmpty(studyBrowses)) {
            List<Long> specialIdList = studyBrowses.stream().map(StudyBrowse::getFkId).collect(Collectors.toList());
            Page<StudySpecial> special = studySpecialService.page(studyBrowseDto.getPage(), Wrappers.<StudySpecial>lambdaQuery()
                    .in(StudySpecial::getId, specialIdList)
                    .like(studyBrowseDto.getBrowseName() != null, StudySpecial::getSpecialName, studyBrowseDto.getBrowseName()));
            studySpecialVoPage = DozerUtils.mapPage(dozerMapper, special, StudySpecialVo.class);
            for (StudySpecialVo specialVo : studySpecialVoPage.getRecords()) {
                //设置专题剩余过期时间
                if (specialVo.getSpecialTimeType().equals(SpecialTimeTypeEnum.ORDINARY.code)) {//普通培训
                    if (specialVo.getSpecialEndTime() != null) {
                        specialVo.setRemaingTime(DateUtil.formatBetween(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), Date.from(specialVo.getSpecialEndTime().atZone(ZoneId.systemDefault()).toInstant()), BetweenFormatter.Level.MINUTE));
                    }
                } else {
                    specialVo.setRemaingTime("长期有效");
                }
                //设置课程数量
                specialVo.setLessCount(specialLessonService.count(Wrappers.<StudySpecialLesson>lambdaQuery()
                        .eq(StudySpecialLesson::getSpecialId, specialVo.getId())));
                //设置课程总学时
                specialVo.setStudyTime(studySpecialMapper.countSpecialStudyTime(specialVo.getId()));
                specialVo.setStudyTimeText(StudyTimeUtil.buildStudyTimeStr(specialVo.getStudyTime()));
            }
            log.info("我的浏览-专题获取的信息：{}", special.getTotal());
        }
            return studySpecialVoPage;
        }


    /**
     * 分页获取浏览过的课程
     * @param studyBrowseDto
     * @return
     */
    @Override
    public Page<StudyLessonForShowVo> getLessons(StudyBrowseDto studyBrowseDto) {
        log.info("我的浏览-课程参数：{}", studyBrowseDto);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<StudyBrowse> studyBrowses = studyBrowseMapper.selectList(Wrappers.<StudyBrowse>lambdaQuery()
                .eq(StudyBrowse::getUserId, loginAppUser.getId())
                .eq(StudyBrowse::getBrowseType, BrowseTypeEnum.LESSON.code));
        //从浏览表里获取的课程id
        Page<StudyLessonForShowVo> studyLessonVoPage = new Page<>();
        if (CollectionUtil.isNotEmpty(studyBrowses)) {
            List<Long> lessonIdList = studyBrowses.stream().map(StudyBrowse::getFkId).collect(Collectors.toList());
            Page<StudyLesson> lesson = studyLessonService.page(studyBrowseDto.getPage(), Wrappers.<StudyLesson>lambdaQuery()
                    .in(StudyLesson::getId, lessonIdList)
                    .like(studyBrowseDto.getBrowseName() != null, StudyLesson::getLessonName, studyBrowseDto.getBrowseName()));
           studyLessonVoPage = DozerUtils.mapPage(dozerMapper, lesson, StudyLessonForShowVo.class);
            for (StudyLessonForShowVo record : studyLessonVoPage.getRecords()) {
                //设置课件数量/总学时
                record.setCouSize(studyLessonCouService.count(Wrappers.<StudyLessonCou>lambdaQuery()
                        .eq(StudyLessonCou::getLessonId, record.getId())));
                List<StudyLessonCou> studyLessonCous = studyLessonCouService.list(Wrappers.<StudyLessonCou>lambdaQuery()
                        .eq(StudyLessonCou::getLessonId, record.getId()));
                List<StudyCou> studyCous = studyCouService.list(Wrappers.<StudyCou>lambdaQuery()
                        .in(StudyCou::getId, studyLessonCous.stream().map(StudyLessonCou::getCouId).collect(Collectors.toList())));
                record.setLessonStudyTime(StudyTimeUtil.buildStudyTimeStr(studyCous.stream().map(StudyCou::getStudyTime).reduce(Long::sum).orElse(null)));
            }
        }
        return studyLessonVoPage;
    }
}
