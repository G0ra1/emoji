package com.netwisd.biz.study.service.impl;

import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.study.entity.StudyLessonHisCou;
import com.netwisd.biz.study.mapper.StudyLessonHisCouMapper;
import com.netwisd.biz.study.service.StudyLessonHisCouService;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程历史课件 功能描述...
 * @date 2022-05-11 18:50:02
 */
@Service
@Slf4j
public class StudyLessonHisCouServiceImpl extends BatchServiceImpl<StudyLessonHisCouMapper, StudyLessonHisCou> implements StudyLessonHisCouService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonHisCouMapper studyLessonHisCouMapper;

}
