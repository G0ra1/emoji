package com.netwisd.biz.study.service.impl;

import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.study.entity.StudyLessonAdjCou;
import com.netwisd.biz.study.mapper.StudyLessonAdjCouMapper;
import com.netwisd.biz.study.service.StudyLessonAdjCouService;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程调整课件 功能描述...
 * @date 2022-05-12 09:17:24
 */
@Service
@Slf4j
public class StudyLessonAdjCouServiceImpl extends BatchServiceImpl<StudyLessonAdjCouMapper, StudyLessonAdjCou> implements StudyLessonAdjCouService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonAdjCouMapper studyLessonAdjCouMapper;

}
