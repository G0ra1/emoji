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
     * 课程模块-列表展示
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
                    //学时（秒）转 学时（时分秒）
                    lessonVo.setStudyTimeSize(StudyTimeUtil.buildStudyTimeStr(Long.valueOf(lessonVo.getStudyTime())));
                }
                //是否公开
                this.judgeLessonIsOpen(lessonVo);
            });
            lessonVoPage.setRecords(lessonVoList);
        }
        return lessonVoPage;
    }

    /**
     * 获取课程详情
     * @param id
     * @return
     */
    @Override
    @Transactional
    public StudyLessonForShowVo getLessonDetail(Long id) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        StudyLesson lesson = lessonMapper.selectById(id);
        //增加课程点击量
        lesson.setHits(ObjectUtils.isNotEmpty(lesson.getHits()) ? lesson.getHits()+1 : 1);
        lessonMapper.updateById(lesson);
        //增加浏览记录
        StudyBrowseDto studyBrowseDto = new StudyBrowseDto();
        studyBrowseDto.setUserId(loginAppUser.getId());
        studyBrowseDto.setBrowseType(BrowseTypeEnum.LESSON.code);
        studyBrowseDto.setFkId(id);
        studyBrowseService.save(studyBrowseDto);
        //返回结果
        StudyLessonForShowVo lessonForShowVo = dozerMapper.map(lesson, StudyLessonForShowVo.class);
        //是否收藏
        LambdaQueryWrapper<StudyCollection> collectionWrapper = new LambdaQueryWrapper<>();
        collectionWrapper.eq(StudyCollection::getCollectionId,id);
        collectionWrapper.eq(StudyCollection::getUserId,loginAppUser.getId());
        Integer userCollectNum = collectionMapper.selectCount(collectionWrapper);
        lessonForShowVo.setIsCollect(YesNo.NO.code);
        if (userCollectNum > 0) {
            lessonForShowVo.setIsCollect(YesNo.YES.code);
        }
        //课程评分
        lessonForShowVo.setScore(lessonScoreService.getLessonScore(id));
        //课程是否评过分
        lessonForShowVo.setIsScore(lessonScoreService.getIsScore(id));
        //学习记录
        LambdaQueryWrapper<StudyUserStudyRecords> userStudyRecordsWrapper = new LambdaQueryWrapper<>();
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getLessonId,id);
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getUserId,loginAppUser.getId());
        List<StudyUserStudyRecords> studyRecords = studyUserStudyRecordsMapper.selectList(userStudyRecordsWrapper);
        Map<Long, List<StudyUserStudyRecords>> couIdMap = null;
        if (CollectionUtils.isNotEmpty(studyRecords)) {
            couIdMap = studyRecords.stream().collect(Collectors.groupingBy(StudyUserStudyRecords::getCouId));
        }
        //课件信息
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
                //音频 和 视频 学时转换
                if (!CouTypeEnum.DOCUMNET.code.equals(couVo.getCouType())) {
                    //学时转时分秒
                    couForDetailVo.setVideoTime(StudyTimeUtil.buildStudyTimeStr(couVo.getStudyTime()));
                    studyTime = studyTime + couVo.getStudyTime();
                }
                //课件是否公开
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
                //累计学习时长、最后播放音视频时间节点
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
        //学习资料信息
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
     * 根据课程id查询课件id集合
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
     * 根据课件id集合 查询课件信息
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
     * 根据课件id集合 查询课件权限信息
     * @param couIdList
     * @return
     */
    private List<StudyCouPerm> getCouPermsByCouIds(List<Long> couIdList){
        LambdaQueryWrapper<StudyCouPerm> couPermWrapper = new LambdaQueryWrapper<>();
        couPermWrapper.in(StudyCouPerm::getCouId,couIdList);
        return couPermMapper.selectList(couPermWrapper);
    }

    /**
     * 判断该课件对当前登陆人 是否是公开的
     * @param couPermList
     * @param couVo
     * @return
     */
    private Integer judgeCouIsOpen(List<StudyCouPerm> couPermList,StudyCouVo couVo){
//        //是否全部公开 1 全部公开 0 部分公开
        int isOpen = 1;
        if (UseRangeEnum.SIYOU.code.equals(couVo.getUseRange())) {
            isOpen = 0;
            //机构
            List<Long> orgIdList = couPermList.stream().filter(x -> x.getRangeType().equals(RangeTypeEnum.ORG.code) && couVo.getId().equals(x.getCouId())).map(StudyCouPerm::getRangeId).collect(Collectors.toList());
            //部门
            List<Long> deptIdList = couPermList.stream().filter(x -> x.getRangeType().equals(RangeTypeEnum.DEPT.code) && couVo.getId().equals(x.getCouId())).map(StudyCouPerm::getRangeId).collect(Collectors.toList());
            //人员
            List<Long> userIdList = couPermList.stream().filter(x -> x.getRangeType().equals(RangeTypeEnum.USER.code) && couVo.getId().equals(x.getCouId())).map(StudyCouPerm::getRangeId).collect(Collectors.toList());
            //登陆人
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
     * 判断该课程对当前登陆人 是否是公开的
     * @param lessonVo
     * @return
     */
    private void judgeLessonIsOpen(StudyLessonVo lessonVo){
        //是否全部公开 1 全部公开 0 部分公开
        int isOpen = 1;
        //1.根据课程id获取课件id集合
        List<Long> couIds = this.getCouIdsByLessonId(lessonVo.getId());
        if (CollectionUtils.isNotEmpty(couIds)) {
            //2.根据课件id集合，查出所有课件权限
            List<StudyCouPerm> couPermList = this.getCouPermsByCouIds(couIds);
            //3.根据课件id集合，查出所有课件
            List<StudyCouVo> couVoList = this.getCousByCouIds(couIds);
            //4.根据课件权限 与 课件 判断该课程是否全部公开
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
