package com.netwisd.biz.study.service.impl;

import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.study.entity.StudyLessonHisMarterials;
import com.netwisd.biz.study.mapper.StudyLessonHisMarterialsMapper;
import com.netwisd.biz.study.service.StudyLessonHisMarterialsService;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程历史学习资料 功能描述...
 * @date 2022-05-11 18:50:02
 */
@Service
@Slf4j
public class StudyLessonHisMarterialsServiceImpl extends BatchServiceImpl<StudyLessonHisMarterialsMapper, StudyLessonHisMarterials> implements StudyLessonHisMarterialsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonHisMarterialsMapper studyLessonHisMarterialsMapper;

}
