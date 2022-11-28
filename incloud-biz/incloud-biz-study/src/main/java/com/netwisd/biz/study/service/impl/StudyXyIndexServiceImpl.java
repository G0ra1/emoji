package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.biz.study.constants.YesNo;
import com.netwisd.biz.study.entity.StudyLesson;
import com.netwisd.biz.study.entity.StudySpecial;
import com.netwisd.biz.study.entity.StudyUserStudyRecords;
import com.netwisd.biz.study.mapper.StudyLessonMapper;
import com.netwisd.biz.study.mapper.StudySpecialMapper;
import com.netwisd.biz.study.mapper.StudyUserStudyRecordsMapper;
import com.netwisd.biz.study.service.StudyXyIndexService;
import com.netwisd.biz.study.util.StudyTimeUtil;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;



@Service
public class StudyXyIndexServiceImpl implements StudyXyIndexService {

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonMapper studyLessonMapper;

    @Autowired
    private StudySpecialMapper studySpecialMapper;

    @Autowired
    private StudyUserStudyRecordsMapper studyUserStudyRecordsMapper;

    @Override
    public List<StudyLessonVo> getLessons(Integer current,Integer size,String lessonName) {
        LambdaQueryWrapper<StudyLesson> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLesson::getIsIndex, YesNo.YES.code);
        queryWrapper.eq(StudyLesson::getIsEnable,YesNo.YES.code);
        queryWrapper.eq(StudyLesson::getAuditStatus, WfProcessEnum.DONE.getType());
        if (StringUtils.isNotEmpty(lessonName)) {
            queryWrapper.like(StudyLesson::getLessonName,lessonName);
        }
        queryWrapper.orderByDesc(StudyLesson::getHits);
        if (ObjectUtils.isNotEmpty(current) || ObjectUtils.isNotEmpty(size)) {
            Page page = new Page();
            if (ObjectUtils.isNotEmpty(current))
                page.setCurrent(current);
            if (ObjectUtils.isNotEmpty(size))
                page.setSize(size);
            Page lessonPages = studyLessonMapper.selectPage(page, queryWrapper);
            if (CollectionUtils.isNotEmpty(lessonPages.getRecords())) {
                return DozerUtils.mapList(dozerMapper,lessonPages.getRecords(),StudyLessonVo.class);
            }
        }
        List<StudyLesson> lessonList = studyLessonMapper.selectList(queryWrapper);
        return DozerUtils.mapList(dozerMapper,lessonList,StudyLessonVo.class);
    }

    @Override
    public List<StudySpecialVo> getSpecials(Integer current,Integer size,String specialName) {
        LambdaQueryWrapper<StudySpecial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudySpecial::getIsIndex, YesNo.YES.code);
        queryWrapper.eq(StudySpecial::getIsEnable,YesNo.YES.code);
        queryWrapper.eq(StudySpecial::getAuditStatus, WfProcessEnum.DONE.getType());
        if (StringUtils.isNotEmpty(specialName)) {
            queryWrapper.like(StudySpecial::getSpecialName,specialName);
        }
        queryWrapper.orderByDesc(StudySpecial::getHits);
        if (ObjectUtils.isNotEmpty(current) || ObjectUtils.isNotEmpty(size)) {
            Page page = new Page();
            if (ObjectUtils.isNotEmpty(current))
                page.setCurrent(current);
            if (ObjectUtils.isNotEmpty(size))
                page.setSize(size);
            Page specialPages = studySpecialMapper.selectPage(page, queryWrapper);
            if (CollectionUtils.isNotEmpty(specialPages.getRecords())) {
                return DozerUtils.mapList(dozerMapper,specialPages.getRecords(),StudySpecialVo.class);
            }
        }
        List<StudySpecial> specialList = studySpecialMapper.selectList(queryWrapper);
        return DozerUtils.mapList(dozerMapper,specialList,StudySpecialVo.class);
    }

    @Override
    public Map<String,Object> getLessonOrSpecialRanking(Integer size,String type) {
        Map<String,Object> returnMap = new HashMap<>();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        StudyLessonOrSpecialRankingVo ranking = new StudyLessonOrSpecialRankingVo();
        //分组查询 人员学习记录表
        List<StudyLessonOrSpecialRankingVo> returnList = new ArrayList<>();
        List<StudyUserStudyRecordsVo> studyRecordsList = new ArrayList<>();
        if ("lesson".equals(type)) {
            studyRecordsList = studyUserStudyRecordsMapper.findLessonRanking();
        }
        if ("special".equals(type)) {
            studyRecordsList = studyUserStudyRecordsMapper.findSpecialRanking();
        }
        if (CollectionUtils.isNotEmpty(studyRecordsList)) {
            //对人进行分组
            Map<Long, List<StudyUserStudyRecordsVo>> userRecords = studyRecordsList.stream().filter(d -> null != d.getUserId()).collect(Collectors.groupingBy(StudyUserStudyRecordsVo::getUserId));
            Set<Long> userIds = userRecords.keySet();
            for (Long id : userIds){
                List<StudyUserStudyRecordsVo> studyRecords = userRecords.get(id);
                StudyLessonOrSpecialRankingVo rankingVo = new StudyLessonOrSpecialRankingVo();
                //对当前的人员的课程进行分组
                if ("lesson".equals(type)) {
                    Map<Long, List<StudyUserStudyRecordsVo>> lessonMap = studyRecords.stream().filter(d -> null != d.getLessonId()).collect(Collectors.groupingBy(StudyUserStudyRecordsVo::getLessonId));
                    rankingVo.setSize(lessonMap.size());
                }
                if ("special".equals(type)) {
                    Map<Long, List<StudyUserStudyRecordsVo>> specialMap = studyRecords.stream().filter(d -> null != d.getSpecialId()).collect(Collectors.groupingBy(StudyUserStudyRecordsVo::getSpecialId));
                    rankingVo.setSize(specialMap.size());
                }
                rankingVo.setUserNameCh(studyRecords.get(0).getUserNameCh());
                rankingVo.setUserOrgFullName(studyRecords.get(0).getCreateUserParentOrgName());
                 returnList.add(rankingVo);
                if (loginAppUser.getId().equals(id)) {
                    ranking = dozerMapper.map(rankingVo, StudyLessonOrSpecialRankingVo.class);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(returnList)) {
            //数量倒叙排序
            returnList.sort(Comparator.comparing(StudyLessonOrSpecialRankingVo::getSize).reversed().thenComparing(StudyLessonOrSpecialRankingVo::getSize));
            int i = returnList.lastIndexOf(ranking);
            returnMap.put("selfRanking",i);
        }
        if (returnList.size() < size) {
            returnMap.put("rankings",returnList);
            return returnMap;
        }
        returnList = returnList.subList(0, size);
        returnMap.put("rankings",returnList);
        return returnMap;
    }

    @Override
    public Map<String,Object> getStudyTimeRanking(Integer size) {
        Map<String,Object> returnMap = new HashMap<>();
        StudyTimeRankingVo ranking = new StudyTimeRankingVo();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<StudyTimeRankingVo> returnList = new ArrayList<>();
        List<StudyUserStudyRecords> userStudyRecords = studyUserStudyRecordsMapper.selectList(new LambdaQueryWrapper<>());
        if (CollectionUtils.isNotEmpty(userStudyRecords)) {
            Map<Long, List<StudyUserStudyRecords>> userIdGroup = userStudyRecords.stream().filter(d -> null != d.getUserId()).collect(Collectors.groupingBy(StudyUserStudyRecords::getUserId));
            Set<Long> userIds = userIdGroup.keySet();
            for (Long id : userIds){
                List<StudyUserStudyRecords> studyRecords = userIdGroup.get(id);
                long sumStudyTime = studyRecords.stream().mapToLong(StudyUserStudyRecords::getCumulativeStudyTime).sum();
                StudyTimeRankingVo rankingVo = new StudyTimeRankingVo();
                rankingVo.setUserNameCh(studyRecords.get(0).getUserNameCh());
                rankingVo.setUserOrgFullName(studyRecords.get(0).getCreateUserParentOrgName());
                rankingVo.setStudyTimes(sumStudyTime);
                rankingVo.setStudyTime(StudyTimeUtil.timeToStr(sumStudyTime));
                returnList.add(rankingVo);
                if (id.equals(loginAppUser.getId())) {
                    ranking = dozerMapper.map(rankingVo,StudyTimeRankingVo.class);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(returnList)) {
            //时长倒叙排序
            returnList.sort(Comparator.comparing(StudyTimeRankingVo::getStudyTimes).reversed().thenComparing(StudyTimeRankingVo::getStudyTimes));
            int i = returnList.lastIndexOf(ranking);
            returnMap.put("selfRanking",i);
        }
        if (returnList.size() < size) {
            returnMap.put("rankings",returnList);
            return returnMap;
        }
        List<StudyTimeRankingVo> studyTimeRankingVos = returnList.subList(0, size);
        returnMap.put("rankings",studyTimeRankingVos);
        return returnMap;
    }
}
