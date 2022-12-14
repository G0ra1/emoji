package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.study.constants.*;
import com.netwisd.biz.study.dto.StudyBrowseDto;
import com.netwisd.biz.study.dto.StudyLessonDto;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.mapper.*;
import com.netwisd.biz.study.service.StudyBrowseService;
import com.netwisd.biz.study.service.StudyLessonScoreService;
import com.netwisd.biz.study.service.StudyXyLessonService;
import com.netwisd.biz.study.util.StudyTimeUtil;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class StudyXyLessonServiceImpl implements StudyXyLessonService {

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonMapper lessonMapper;

    @Autowired
    private StudyCollectionMapper collectionMapper;

    @Autowired
    private StudyLessonCouMapper lessonCouMapper;

    @Autowired
    private StudyCouMapper couMapper;

    @Autowired
    private StudyCouPermMapper couPermMapper;

    @Autowired
    private StudyLessonMarterialsMapper lessonMarterialsMapper;

    @Autowired
    private StudyMarterialsMapper marterialsMapper;

    @Autowired
    private StudyUserStudyRecordsMapper studyUserStudyRecordsMapper;

    @Autowired
    private StudyBrowseService studyBrowseService;

    @Autowired
    private StudyUserLearnApplyMapper studyUserLearnApplyMapper;

    @Autowired
    private StudyLessonScoreService lessonScoreService;
    /**
     * ????????????-????????????
     * @param lessonDto
     * @return
     */
    @Override
    public Page<StudyLessonVo> pageForIndex(StudyLessonDto lessonDto) {
        if (StringUtils.isNotBlank(lessonDto.getLabelCode())) {
            lessonDto.setLabelCodes(Arrays.asList(lessonDto.getLabelCode().split(",")));
        }
        Page<StudyLessonVo> lessonVoPage = lessonMapper.pageForIndex(lessonDto.getPage(), lessonDto);
        if (CollectionUtil.isNotEmpty(lessonVoPage.getRecords())) {
            List<StudyLessonVo> lessonVoList = lessonVoPage.getRecords();
            lessonVoList.forEach(lessonVo->{
                if (ObjectUtils.isEmpty(lessonVo.getStudyTime())) {
                    lessonVo.setStudyTimeSize("-");
                }else {
                    //?????????????????? ?????????????????????
                    lessonVo.setStudyTimeSize(StudyTimeUtil.buildStudyTimeStr(Long.valueOf(lessonVo.getStudyTime())));
                }
                //????????????
                this.judgeLessonIsOpen(lessonVo);
            });
            lessonVoPage.setRecords(lessonVoList);
        }
        return lessonVoPage;
    }

    /**
     * ??????????????????
     * @param id
     * @return
     */
    @Override
    @Transactional
    public StudyLessonForShowVo getLessonDetail(Long id) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        StudyLesson lesson = lessonMapper.selectById(id);
        //?????????????????????
        lesson.setHits(ObjectUtils.isNotEmpty(lesson.getHits()) ? lesson.getHits()+1 : 1);
        lessonMapper.updateById(lesson);
        //??????????????????
        StudyBrowseDto studyBrowseDto = new StudyBrowseDto();
        studyBrowseDto.setUserId(loginAppUser.getId());
        studyBrowseDto.setBrowseType(BrowseTypeEnum.LESSON.code);
        studyBrowseDto.setFkId(id);
        studyBrowseService.save(studyBrowseDto);
        //????????????
        StudyLessonForShowVo lessonForShowVo = dozerMapper.map(lesson, StudyLessonForShowVo.class);
        //????????????
        LambdaQueryWrapper<StudyCollection> collectionWrapper = new LambdaQueryWrapper<>();
        collectionWrapper.eq(StudyCollection::getCollectionId,id);
        collectionWrapper.eq(StudyCollection::getUserId,loginAppUser.getId());
        Integer userCollectNum = collectionMapper.selectCount(collectionWrapper);
        lessonForShowVo.setIsCollect(YesNo.NO.code);
        if (userCollectNum > 0) {
            lessonForShowVo.setIsCollect(YesNo.YES.code);
        }
        //????????????
        lessonForShowVo.setScore(lessonScoreService.getLessonScore(id));
        //?????????????????????
        lessonForShowVo.setIsScore(lessonScoreService.getIsScore(id));
        //????????????
        LambdaQueryWrapper<StudyUserStudyRecords> userStudyRecordsWrapper = new LambdaQueryWrapper<>();
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getLessonId,id);
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getUserId,loginAppUser.getId());
        List<StudyUserStudyRecords> studyRecords = studyUserStudyRecordsMapper.selectList(userStudyRecordsWrapper);
        Map<Long, List<StudyUserStudyRecords>> couIdMap = null;
        if (CollectionUtils.isNotEmpty(studyRecords)) {
            couIdMap = studyRecords.stream().collect(Collectors.groupingBy(StudyUserStudyRecords::getCouId));
        }
        //????????????
        LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
        lessonCouWrapper.eq(StudyLessonCou::getLessonId,id);
        List<StudyLessonCou> lessonCouList = lessonCouMapper.selectList(lessonCouWrapper);
        if (CollectionUtil.isNotEmpty(lessonCouList)) {
            List<Long> couIdList = lessonCouList.stream().map(StudyLessonCou::getCouId).collect(Collectors.toList());
            List<StudyCouPerm> couPermList = this.getCouPermsByCouIds(couIdList);
            List<StudyCouVo> couVoList = this.getCousByCouIds(couIdList);
            long studyTime = 0;
            List<StudyCouForDetailVo> couForDetailVoList = new ArrayList<>();
            for(StudyCouVo couVo : couVoList){
                StudyCouForDetailVo couForDetailVo = new StudyCouForDetailVo();
                BeanUtils.copyProperties(couVo,couForDetailVo);
                //?????? ??? ?????? ????????????
                if (!CouTypeEnum.DOCUMNET.code.equals(couVo.getCouType())) {
                    //??????????????????
                    couForDetailVo.setVideoTime(StudyTimeUtil.buildStudyTimeStr(couVo.getStudyTime()));
                    studyTime = studyTime + couVo.getStudyTime();
                }
                //??????????????????
                Integer isOpen = this.judgeCouIsOpen(couPermList, couVo);
                if (isOpen.equals(IsOpenEnum.NO_OPEN.code)) {
                    LambdaQueryWrapper<StudyUserLearnApply> userLearnApplyWrapper = new LambdaQueryWrapper<>();
                    userLearnApplyWrapper.eq(StudyUserLearnApply::getUserId,loginAppUser.getId());
                    userLearnApplyWrapper.eq(StudyUserLearnApply::getLessonId,lesson.getId());
                    userLearnApplyWrapper.eq(StudyUserLearnApply::getTargetId,couVo.getId());
                    userLearnApplyWrapper.eq(StudyUserLearnApply::getApplyStatus,ApplyStatus.NO_APPLY.code);
                    Integer applyNum = studyUserLearnApplyMapper.selectCount(userLearnApplyWrapper);
                    if (applyNum > 0) {
                        isOpen = IsOpenEnum.IN_APPLY.code;
                    }
                }
                couForDetailVo.setIsOpen(isOpen);
                //??????????????????????????????????????????????????????
                if (null != couIdMap && null != couIdMap.get(couVo.getId())) {
                    StudyUserStudyRecords userStudyRecord = couIdMap.get(couVo.getId()).get(0);
                    String cumulativeStudyTime = StudyTimeUtil.timeToStr(userStudyRecord.getCumulativeStudyTime());
                    couForDetailVo.setStudyTime(cumulativeStudyTime);
                    couForDetailVo.setLastVideoTime(userStudyRecord.getLastVideoTime());
                }else {
                    couForDetailVo.setLastVideoTime(0l);
                    couForDetailVo.setStudyTime("0");
                }
                couForDetailVoList.add(couForDetailVo);
            }
            lessonForShowVo.setCouList(couForDetailVoList);
            lessonForShowVo.setCouSize(couVoList.size());
            lessonForShowVo.setLessonStudyTime(StudyTimeUtil.buildStudyTimeStr(studyTime));
        }
        //??????????????????
        LambdaQueryWrapper<StudyLessonMarterials> lessonMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonMarterialsWrapper.eq(StudyLessonMarterials::getLessonId,id);
        List<StudyLessonMarterials> lessonMarterialsList = lessonMarterialsMapper.selectList(lessonMarterialsWrapper);
        if (CollectionUtil.isNotEmpty(lessonMarterialsList)) {
            List<Long> marterialsIdList = lessonMarterialsList.stream().map(StudyLessonMarterials::getMarterialsId).collect(Collectors.toList());
            LambdaQueryWrapper<StudyMarterials> marterialsWrapper = new LambdaQueryWrapper<>();
            marterialsWrapper.in(StudyMarterials::getId,marterialsIdList);
            List<StudyMarterials> marterialsList = marterialsMapper.selectList(marterialsWrapper);
            List<StudyMarterialsVo> marterialsVoList = DozerUtils.mapList(dozerMapper, marterialsList, StudyMarterialsVo.class);
            lessonForShowVo.setMarterialsList(marterialsVoList);
        }
        return lessonForShowVo;
    }

    /**
     * ????????????id????????????id??????
     * @param lessonId
     * @return
     */
    private List<Long> getCouIdsByLessonId(Long lessonId){
        LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
        lessonCouWrapper.eq(StudyLessonCou::getLessonId,lessonId);
        List<StudyLessonCou> lessonCouList = lessonCouMapper.selectList(lessonCouWrapper);
        if (CollectionUtil.isNotEmpty(lessonCouList)) {
            return lessonCouList.stream().map(StudyLessonCou::getCouId).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * ????????????id?????? ??????????????????
     * @param couIdList
     * @return
     */
    private List<StudyCouVo> getCousByCouIds(List<Long> couIdList){
        LambdaQueryWrapper<StudyCou> couWrapper = new LambdaQueryWrapper<>();
        couWrapper.in(StudyCou::getId,couIdList);
        List<StudyCou> couList = couMapper.selectList(couWrapper);
        return DozerUtils.mapList(dozerMapper, couList, StudyCouVo.class);
    }

    /**
     * ????????????id?????? ????????????????????????
     * @param couIdList
     * @return
     */
    private List<StudyCouPerm> getCouPermsByCouIds(List<Long> couIdList){
        LambdaQueryWrapper<StudyCouPerm> couPermWrapper = new LambdaQueryWrapper<>();
        couPermWrapper.in(StudyCouPerm::getCouId,couIdList);
        return couPermMapper.selectList(couPermWrapper);
    }

    /**
     * ????????????????????????????????? ??????????????????
     * @param couPermList
     * @param couVo
     * @return
     */
    private Integer judgeCouIsOpen(List<StudyCouPerm> couPermList,StudyCouVo couVo){
//        //?????????????????? 1 ???????????? 0 ????????????
        int isOpen = 1;
        if (UseRangeEnum.SIYOU.code.equals(couVo.getUseRange())) {
            isOpen = 0;
            //??????
            List<Long> orgIdList = couPermList.stream().filter(x -> x.getRangeType().equals(RangeTypeEnum.ORG.code) && couVo.getId().equals(x.getCouId())).map(StudyCouPerm::getRangeId).collect(Collectors.toList());
            //??????
            List<Long> deptIdList = couPermList.stream().filter(x -> x.getRangeType().equals(RangeTypeEnum.DEPT.code) && couVo.getId().equals(x.getCouId())).map(StudyCouPerm::getRangeId).collect(Collectors.toList());
            //??????
            List<Long> userIdList = couPermList.stream().filter(x -> x.getRangeType().equals(RangeTypeEnum.USER.code) && couVo.getId().equals(x.getCouId())).map(StudyCouPerm::getRangeId).collect(Collectors.toList());
            //?????????
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            List<Long> orgFullIdList = ObjectUtil.isNull(loginAppUser) ? Lists.newArrayList() : Stream.of(loginAppUser.getOrgFullId().split(",")).map(Long::valueOf).collect(Collectors.toList());
            Long userId = ObjectUtil.isNull(loginAppUser) ? 0L : loginAppUser.getId();

            for (Long userOgId : orgFullIdList) {
                if (userIdList.contains(userId) || orgIdList.contains(userOgId) || deptIdList.contains(userOgId)) {
                    isOpen = 1;
                    break;
                }
            }
        }
        return isOpen;
    }

    /**
     * ????????????????????????????????? ??????????????????
     * @param lessonVo
     * @return
     */
    private void judgeLessonIsOpen(StudyLessonVo lessonVo){
        //?????????????????? 1 ???????????? 0 ????????????
        int isOpen = 1;
        //1.????????????id????????????id??????
        List<Long> couIds = this.getCouIdsByLessonId(lessonVo.getId());
        if (CollectionUtils.isNotEmpty(couIds)) {
            //2.????????????id?????????????????????????????????
            List<StudyCouPerm> couPermList = this.getCouPermsByCouIds(couIds);
            //3.????????????id???????????????????????????
            List<StudyCouVo> couVoList = this.getCousByCouIds(couIds);
            //4.?????????????????? ??? ?????? ?????????????????????????????????
            for (StudyCouVo couVo : couVoList){
                isOpen = this.judgeCouIsOpen(couPermList,couVo);
                if (isOpen == 0) {
                    break;
                }
            }
        }
        lessonVo.setIsOpen(isOpen);
    }

}
