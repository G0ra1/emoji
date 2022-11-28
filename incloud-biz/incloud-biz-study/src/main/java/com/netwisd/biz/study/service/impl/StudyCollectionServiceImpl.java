package com.netwisd.biz.study.service.impl;

import cn.hutool.core.util.ObjectUtil;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.constants.CollectionTypeEnum;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.mapper.StudyCollectionMapper;
import com.netwisd.biz.study.mapper.StudySpecialMapper;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.util.StudyTimeUtil;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.exception.IncloudException;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyCollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 收藏表 功能描述...
 * @date 2022-05-06 14:55:57
 */
@Service
@Slf4j
public class StudyCollectionServiceImpl extends ServiceImpl<StudyCollectionMapper, StudyCollection> implements StudyCollectionService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudySpecialMapper studySpecialMapper;//专题mapper

    @Autowired
    private StudySpecialService studySpecialService;//专题service

    @Autowired
    private StudyCollectionMapper studyCollectionMapper;//收藏mapper

    @Autowired
    private StudyLessonService studyLessonService;//课程service
    @Autowired
    private StudyLessonCouService studyLessonCouService;//课程课件service

    @Autowired
    private StudySpecialLessonService studySpecialLessonService;//专题课程service

    @Autowired
    private StudyCouService studyCouService;//课件service

    /**
     * 收藏/取消收藏
     *
     * @param studyCollectionDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(StudyCollectionDto studyCollectionDto) {
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        StudyCollection studyCollection = dozerMapper.map(studyCollectionDto, StudyCollection.class);
        Boolean aBoolean = checkExistence(studyCollection.getCollectionId(), studyCollection.getCollectionType());
        if (aBoolean) {
            super.remove(Wrappers.<StudyCollection>lambdaQuery()
                    .eq(ObjectUtil.isNotNull(studyCollection.getCollectionId()), StudyCollection::getCollectionId, studyCollection.getCollectionId())
                    .eq(ObjectUtil.isNotNull(studyCollection.getCollectionType()), StudyCollection::getCollectionType, studyCollection.getCollectionType()));
            log.debug("取消收藏成功");
            return true;
        }
        studyCollection.setUserId(appUser.getId());
        studyCollection.setCreateTime(LocalDateTime.now());
        boolean result = super.save(studyCollection);
        if (result) {
            log.debug("收藏成功");
        }
        return result;
    }

    /**
     * 取消收藏(单独的点击取消收藏)
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean remove(Long id) {
        log.info("取消收藏：" + id);
        boolean removeById = super.removeById(id);
        return removeById;
    }

    /**
     * 我的收藏-专题
     *
     * @return
     */
    @Override
    public Page<StudySpecialVo> getSpecialPage(StudyCollectionDto studyCollectionDto) {
        log.info("我的收藏-专题参数：{}", studyCollectionDto);
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
        //查询收藏表获取专题id集合
        List<StudyCollection> studyCollections = studyCollectionMapper.selectList(Wrappers.<StudyCollection>lambdaQuery()
                .eq(StudyCollection::getUserId, appUser.getId())// todo 测试使用 appUser.getId()/0
                .eq(StudyCollection::getCollectionType, CollectionTypeEnum.SPECIAL.code));
        Page<StudySpecial> specials = new Page<>();
        if (CollectionUtils.isNotEmpty(studyCollections)) {
            List<Long> specialIds = studyCollections.stream().map(StudyCollection::getCollectionId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(specialIds)) {
                //通过专题id集合获取专题信息
                specials = studySpecialService.page(studyCollectionDto.getPage(), Wrappers.<StudySpecial>lambdaQuery()
                        .in(StudySpecial::getId, specialIds)
                        .like(null != studyCollectionDto.getCollectionName(), StudySpecial::getSpecialName, studyCollectionDto.getCollectionName()));
            }
        }
        Page<StudySpecialVo> studySpecialVoPage = DozerUtils.mapPage(dozerMapper, specials, StudySpecialVo.class);
        if (null != studySpecialVoPage) {
            for (StudySpecialVo record : studySpecialVoPage.getRecords()) {
                //设置课程数量/总学时
                record.setLessCount(studySpecialLessonService.count(Wrappers.<StudySpecialLesson>lambdaQuery()
                        .eq(StudySpecialLesson::getSpecialId, record.getId())));
                record.setStudyTime(studySpecialMapper.countSpecialStudyTime(record.getId()));
                record.setStudyTimeText(StudyTimeUtil.buildStudyTimeStr(record.getStudyTime()));
            }
            log.info("我的收藏-专题获取的信息：{}", specials.getTotal());
        }
        return studySpecialVoPage;
    }

    /**
     * 我的收藏-课程
     *
     * @param studyCollectionDto
     * @return
     */
    @Override
    public Page getLessonPage(StudyCollectionDto studyCollectionDto) {
        log.info("我的收藏-课程参数：{}", studyCollectionDto);
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
        //查询收藏表获取课程id集合
        List<StudyCollection> studyCollections = studyCollectionMapper.selectList(Wrappers.<StudyCollection>lambdaQuery()
                .eq(StudyCollection::getUserId, appUser.getId())//todo 测试使用 appUser.getId()/0
                .eq(StudyCollection::getCollectionType, CollectionTypeEnum.LESSON.code));
        Page lessons = new Page();
        if (CollectionUtils.isNotEmpty(studyCollections)) {
            List<Long> lessonIds = studyCollections.stream().map(StudyCollection::getCollectionId).collect(Collectors.toList());
            //通过课程id集合获取课程信息
            lessons = studyLessonService.page(studyCollectionDto.getPage(), Wrappers.<StudyLesson>lambdaQuery()
                    .in(StudyLesson::getId, lessonIds)
                    .like(null != studyCollectionDto.getCollectionName(), StudyLesson::getLessonName, studyCollectionDto.getCollectionName()));
        }
        Page<StudyLessonForShowVo> studyLessonForShowVoPage = DozerUtils.mapPage(dozerMapper, lessons, StudyLessonForShowVo.class);
        if (null != studyLessonForShowVoPage) {
            for (StudyLessonForShowVo record : studyLessonForShowVoPage.getRecords()) {
                //设置课件数量/总学时
                record.setCouSize(studyLessonCouService.count(Wrappers.<StudyLessonCou>lambdaQuery()
                        .eq(StudyLessonCou::getLessonId, record.getId())));
                List<StudyLessonCou> studyLessonCous = studyLessonCouService.list(Wrappers.<StudyLessonCou>lambdaQuery()
                        .eq(StudyLessonCou::getLessonId, record.getId()));
                List<StudyCou> studyCous = studyCouService.list(Wrappers.<StudyCou>lambdaQuery()
                        .in(StudyCou::getId, studyLessonCous.stream().map(StudyLessonCou::getCouId).collect(Collectors.toList())));
                record.setLessonStudyTime(StudyTimeUtil.buildStudyTimeStr(studyCous.stream().map(map -> map.getStudyTime()).reduce(Long::sum).get()));
            }
        }
        return studyLessonForShowVoPage;
    }

    /**
     * 校验是否收藏（存在就取消收藏-删除）
     *
     * @return
     */
    private Boolean checkExistence(Long collectionId, int collectionType) {
        List<StudyCollection> list = super.list(Wrappers.<StudyCollection>lambdaQuery()
                .eq(ObjectUtil.isNotNull(collectionId), StudyCollection::getCollectionId, collectionId)
                .eq(ObjectUtil.isNotNull(collectionType), StudyCollection::getCollectionType, collectionType));
        if (CollectionUtils.isNotEmpty(list)) {
            return true;
        }
        return false;
    }

}
