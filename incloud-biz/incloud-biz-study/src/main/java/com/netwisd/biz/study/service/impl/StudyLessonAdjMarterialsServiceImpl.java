package com.netwisd.biz.study.service.impl;

import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.study.entity.StudyLessonAdjMarterials;
import com.netwisd.biz.study.mapper.StudyLessonAdjMarterialsMapper;
import com.netwisd.biz.study.service.StudyLessonAdjMarterialsService;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程调整学习资料 功能描述...
 * @date 2022-05-12 09:17:24
 */
@Service
@Slf4j
public class StudyLessonAdjMarterialsServiceImpl extends BatchServiceImpl<StudyLessonAdjMarterialsMapper, StudyLessonAdjMarterials> implements StudyLessonAdjMarterialsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonAdjMarterialsMapper studyLessonAdjMarterialsMapper;

}
