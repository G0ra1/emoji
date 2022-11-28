package com.netwisd.biz.study.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.netwisd.biz.study.constants.RangeTypeEnum;
import com.netwisd.biz.study.constants.SpecialTimeTypeEnum;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.mapper.StudySpecialHisMustExamUserMapper;
import com.netwisd.biz.study.vo.*;

import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.mapper.StudySpecialHisMapper;
import com.netwisd.biz.study.service.StudySpecialHisService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.netwisd.biz.study.service.StudySpecialHisLessonCouService;

import com.netwisd.biz.study.service.StudySpecialHisJieyeService;

import com.netwisd.biz.study.service.StudySpecialHisMaterialsService;

import com.netwisd.biz.study.service.StudySpecialHisRangeService;

import com.netwisd.biz.study.service.StudySpecialHisLessonService;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题历史表 功能描述...
 * @date 2022-05-13 14:23:33
 */
@Service
@Slf4j
public class StudySpecialHisServiceImpl extends BatchServiceImpl<StudySpecialHisMapper, StudySpecialHis> implements StudySpecialHisService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudySpecialHisMapper studySpecialHisMapper;

    @Autowired
    private StudySpecialHisLessonCouService studySpecialHisLessonCouService;
    @Autowired
    private StudySpecialHisMustExamUserMapper studySpecialHisMustExamUserMapper;

    @Autowired
    private StudySpecialHisJieyeService studySpecialHisJieyeService;

    @Autowired
    private StudySpecialHisMaterialsService studySpecialHisMaterialsService;

    @Autowired
    private StudySpecialHisRangeService studySpecialHisRangeService;

    @Autowired
    private StudySpecialHisLessonService studySpecialHisLessonService;

    /**
     * 获取专题的历史记录
     *
     * @param specialId
     * @return
     */
    @Override
    public List<StudySpecialHisVo> hisListForSpecial(Long specialId) {
        log.info("获取专题的历史记录：{}", specialId);
        LambdaQueryWrapper<StudySpecialHis> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(StudySpecialHis::getLinkId, specialId);
        queryWrapper.orderByDesc(StudySpecialHis::getCreateTime);
        List<StudySpecialHis> studySpecialAdjs = studySpecialHisMapper.selectList(queryWrapper);
        List<StudySpecialHisVo> studySpecialHisVos = DozerUtils.mapList(dozerMapper, studySpecialAdjs, StudySpecialHisVo.class);
        //组合专题时间
        if (CollectionUtils.isNotEmpty(studySpecialHisVos)) {
            for (StudySpecialHisVo studySpecialHisVo : studySpecialHisVos) {
                if (studySpecialHisVo.getSpecialTimeType().equals(SpecialTimeTypeEnum.ORDINARY.getCode())) {
                    LocalDateTime specialStartTime = studySpecialHisVo.getSpecialStartTime();
                    String startTime = specialStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDateTime specialEndTime = studySpecialHisVo.getSpecialEndTime();
                    String endTime = specialEndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    studySpecialHisVo.setSpecialTime(startTime.split("-")[0] + "年" + startTime.split("-")[1] + "月" + startTime.split("-")[2] + "日-"
                            + endTime.split("-")[0] + "年" + endTime.split("-")[1] + "月" + endTime.split("-")[2] + "日");
                } else {
                    LocalDateTime specialStartTime = studySpecialHisVo.getSpecialStartTime();
                    String startTime = specialStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    studySpecialHisVo.setSpecialTime(startTime.split("-")[0] + "年" + startTime.split("-")[1] + "月" + startTime.split("-")[2] + "日开始");
                }

            }
        }
        log.debug("查询条数:" + studySpecialHisVos.size());
        return studySpecialHisVos;
    }
    /**
     * 通过id查询专题历史详情
     *
     * @param id
     * @return
     */
    @Override
    public StudySpecialHisVo detail(Long id) {
        log.info("流程查看数据:{}", id);
        StudySpecialHisVo infoVo = dozerMapper.map(super.getById(id), StudySpecialHisVo.class);
        infoVo = buildHisChildren(infoVo);
        return infoVo;
    }

    /**
     * 构建子表数据
     *
     * @param studySpecialHisVo
     * @return
     */
    private StudySpecialHisVo buildHisChildren(StudySpecialHisVo studySpecialHisVo) {

        if (studySpecialHisVo != null && studySpecialHisVo.getId() != null) {
            //获取专题对象（机构、部门）范围
            List<String> rangeTypeList = new ArrayList<>();
            rangeTypeList.add(RangeTypeEnum.ORG.code);
            rangeTypeList.add(RangeTypeEnum.DEPT.code);
            List<StudySpecialHisRange> studySpecialHisRangeOrgDeptList = studySpecialHisRangeService.list(Wrappers.<StudySpecialHisRange>lambdaQuery()
                    .eq(StudySpecialHisRange::getSpecialId, studySpecialHisVo.getId())
                    .in(StudySpecialHisRange::getRangeType, rangeTypeList));
            if (CollectionUtils.isNotEmpty(studySpecialHisRangeOrgDeptList)) {
                studySpecialHisVo.setStudySpecialRangeOrgDeptList(DozerUtils.mapList(dozerMapper, studySpecialHisRangeOrgDeptList, StudySpecialHisRangeVo.class));
            }
            //获取专题对象（人员）范围
            List<StudySpecialHisRange> studySpecialHisRangeUserList = studySpecialHisRangeService.list(Wrappers.<StudySpecialHisRange>lambdaQuery()
                    .eq(StudySpecialHisRange::getSpecialId, studySpecialHisVo.getId())
                    .eq(StudySpecialHisRange::getRangeType, RangeTypeEnum.USER.code));
            if (CollectionUtils.isNotEmpty(studySpecialHisRangeUserList)) {
                studySpecialHisVo.setStudySpecialRangeUserList(DozerUtils.mapList(dozerMapper, studySpecialHisRangeUserList, StudySpecialHisRangeVo.class));
            }

            //获取专题必考人员信息
            List<StudySpecialHisMustExamUser> studySpecialHisMustExamUsers = studySpecialHisMustExamUserMapper.selectList(Wrappers.<StudySpecialHisMustExamUser>lambdaQuery().eq(StudySpecialHisMustExamUser::getSpecialId, studySpecialHisVo.getId()));
            if (CollectionUtils.isNotEmpty(studySpecialHisMustExamUsers)) {
                studySpecialHisVo.setStudySpecialMustExamUserList(DozerUtils.mapList(dozerMapper, studySpecialHisMustExamUsers, StudySpecialHisMustExamUserVo.class));
            }
            //获取课程信息
            List<StudySpecialHisLesson> studySpecialHisLessonList = studySpecialHisLessonService.list(Wrappers.<StudySpecialHisLesson>lambdaQuery().eq(StudySpecialHisLesson::getSpecialId, studySpecialHisVo.getId()));
            if (CollectionUtils.isNotEmpty(studySpecialHisLessonList)) {
                List<StudySpecialHisLessonVo> studySpecialHisLessonVoList = new ArrayList<>();
                //获取课程课件信息
                for (StudySpecialHisLesson studySpecialHisLesson : studySpecialHisLessonList) {
                    StudySpecialHisLessonVo studySpecialHisLessonVo = dozerMapper.map(studySpecialHisLesson, StudySpecialHisLessonVo.class);
                    if (null != studySpecialHisLesson && null != studySpecialHisLesson.getId()) {
                        List<StudySpecialHisLessonCou> studySpecialHisLessonCouList = studySpecialHisLessonCouService.list(Wrappers.<StudySpecialHisLessonCou>lambdaQuery().eq(StudySpecialHisLessonCou::getSpecialLessonId, studySpecialHisLesson.getId()));
                        if (CollectionUtils.isNotEmpty(studySpecialHisLessonCouList)) {
                            List<StudySpecialHisLessonCouVo> studySpecialHisLessonCouVoList = DozerUtils.mapList(dozerMapper, studySpecialHisLessonCouList, StudySpecialHisLessonCouVo.class);
                            studySpecialHisLessonVo.setStudySpecialLessonCouList(studySpecialHisLessonCouVoList);
                        }
                    }
                    studySpecialHisLessonVoList.add(studySpecialHisLessonVo);
                }
                studySpecialHisVo.setStudySpecialLessonList(studySpecialHisLessonVoList);
            }
            //获取学习资料
            List<StudySpecialHisMaterials> specialHisMaterialsList = studySpecialHisMaterialsService.list(Wrappers.<StudySpecialHisMaterials>lambdaQuery().eq(StudySpecialHisMaterials::getSpecialId, studySpecialHisVo.getId()));
            if (CollectionUtils.isNotEmpty(specialHisMaterialsList)) {
                studySpecialHisVo.setStudySpecialMaterialsList(DozerUtils.mapList(dozerMapper, specialHisMaterialsList, StudySpecialHisMaterialsVo.class));
            }

            //获取结业信息
            List<StudySpecialHisJieye> specialHisJieyeList = studySpecialHisJieyeService.list(Wrappers.<StudySpecialHisJieye>lambdaQuery().eq(StudySpecialHisJieye::getSpecialId, studySpecialHisVo.getId()));
            if (CollectionUtils.isNotEmpty(specialHisJieyeList)) {
                studySpecialHisVo.setStudySpecialJieyeList(DozerUtils.mapList(dozerMapper, specialHisJieyeList, StudySpecialHisJieyeVo.class));
            }
        }
        return studySpecialHisVo;
    }
}
