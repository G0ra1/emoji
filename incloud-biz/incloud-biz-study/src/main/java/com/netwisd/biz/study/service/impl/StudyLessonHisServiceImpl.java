package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.study.constants.RangeTypeEnum;
import com.netwisd.biz.study.constants.UseRangeEnum;
import com.netwisd.biz.study.entity.StudyLessonHis;
import com.netwisd.biz.study.entity.StudyLessonHisCou;
import com.netwisd.biz.study.entity.StudyLessonHisCouPerm;
import com.netwisd.biz.study.entity.StudyLessonHisMarterials;
import com.netwisd.biz.study.mapper.StudyLessonHisCouPermMapper;
import com.netwisd.biz.study.mapper.StudyLessonHisMapper;
import com.netwisd.biz.study.service.StudyLessonHisCouService;
import com.netwisd.biz.study.service.StudyLessonHisMarterialsService;
import com.netwisd.biz.study.service.StudyLessonHisService;
import com.netwisd.biz.study.vo.StudyLessonHisCouPermVo;
import com.netwisd.biz.study.vo.StudyLessonHisCouVo;
import com.netwisd.biz.study.vo.StudyLessonHisMarterialsVo;
import com.netwisd.biz.study.vo.StudyLessonHisVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程历史 功能描述...
 * @date 2022-05-11 18:50:02
 */
@Service
@Slf4j
public class StudyLessonHisServiceImpl extends BatchServiceImpl<StudyLessonHisMapper, StudyLessonHis> implements StudyLessonHisService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonHisMapper studyLessonHisMapper;

    @Autowired
    private StudyLessonHisMarterialsService studyLessonHisMarterialsService;

    @Autowired
    private StudyLessonHisCouService studyLessonHisCouService;

    @Autowired
    private StudyLessonHisCouPermMapper studyLessonHisCouPermMapper;

    @Override
    public List<StudyLessonHisVo> hisListForLesson(Long lessonId) {
        //根据创建时间倒叙查询所有历史
        LambdaQueryWrapper<StudyLessonHis> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonHis::getLinkId,lessonId);
        List<StudyLessonHis> studyLessonHis = studyLessonHisMapper.selectList(queryWrapper);
        return DozerUtils.mapList(dozerMapper,studyLessonHis,StudyLessonHisVo.class);
    }

    @Override
    public StudyLessonHisVo detail(Long id) {
        StudyLessonHis studyLessonHis = studyLessonHisMapper.selectById(id);
        StudyLessonHisVo lessonHisVo = dozerMapper.map(studyLessonHis, StudyLessonHisVo.class);
        //历史课程课件
        LambdaQueryWrapper<StudyLessonHisCou> lessonHisCouWrapper = new LambdaQueryWrapper<>();
        lessonHisCouWrapper.eq(StudyLessonHisCou::getLessonHisId,id);
        List<StudyLessonHisCou> lessonHisCouList = studyLessonHisCouService.list(lessonHisCouWrapper);
        List<StudyLessonHisCouVo> lessonHisCouVoList = DozerUtils.mapList(dozerMapper, lessonHisCouList, StudyLessonHisCouVo.class);
        //课程课件中放入课件权限信息
        if (CollectionUtils.isNotEmpty(lessonHisCouList)) {
            //查询课程课件授权信息
            LambdaQueryWrapper<StudyLessonHisCouPerm> lessonHisCouPermWrapper = new LambdaQueryWrapper<>();
            lessonHisCouPermWrapper.eq(StudyLessonHisCouPerm::getLessonHisId,id);
            List<StudyLessonHisCouPerm> lessonHisCouPermList = studyLessonHisCouPermMapper.selectList(lessonHisCouPermWrapper);
            if (CollectionUtils.isNotEmpty(lessonHisCouPermList)) {
                //如果课程课件授权信息不为空 将授权信息按照课件进行分组
                Map<Long, List<StudyLessonHisCouPerm>> couIdMap = lessonHisCouPermList.stream().collect(Collectors.groupingBy(StudyLessonHisCouPerm::getCouId));
                //循环课程课件表
                for (StudyLessonHisCouVo lessonHisCouVo : lessonHisCouVoList){
                    //获取课件授权集合
                    List<StudyLessonHisCouPerm> lessonHisCouPerms = couIdMap.get(lessonHisCouVo.getCouId());
                    List<StudyLessonHisCouPermVo> orgPermList = new ArrayList<>();
                    List<StudyLessonHisCouPermVo> userPermList = new ArrayList<>();
                    //如果课程课件是私有的 放入课件权限
                    if (lessonHisCouVo.getUseRange().equals(UseRangeEnum.SIYOU.code)) {
                        for (StudyLessonHisCouPerm lessonHisCouPerm : lessonHisCouPerms){
                            StudyLessonHisCouPermVo lessonHisCouPermVo = dozerMapper.map(lessonHisCouPerm, StudyLessonHisCouPermVo.class);
                            if (lessonHisCouPerm.getRangeType().equals(RangeTypeEnum.USER.code)) {
                                userPermList.add(lessonHisCouPermVo);
                            }else {
                                orgPermList.add(lessonHisCouPermVo);
                            }
                        }
                    }
                    lessonHisCouVo.setUserPermList(userPermList);
                    lessonHisCouVo.setOrgPermList(orgPermList);
                }
            }
        }
        lessonHisVo.setCouList(lessonHisCouVoList);
        //历史课程学习资料
        LambdaQueryWrapper<StudyLessonHisMarterials> lessonHisMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonHisMarterialsWrapper.eq(StudyLessonHisMarterials::getLessonHisId,id);
        List<StudyLessonHisMarterials> lessonHisMarterialsList = studyLessonHisMarterialsService.list(lessonHisMarterialsWrapper);
        List<StudyLessonHisMarterialsVo> lessonHisMarterialsVoList = DozerUtils.mapList(dozerMapper, lessonHisMarterialsList, StudyLessonHisMarterialsVo.class);
        lessonHisVo.setMarterialsList(lessonHisMarterialsVoList);
        return lessonHisVo;
    }
}
