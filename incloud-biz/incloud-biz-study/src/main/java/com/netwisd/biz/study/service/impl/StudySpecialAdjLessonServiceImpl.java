package com.netwisd.biz.study.service.impl;


import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudySpecialAdjLesson;
import com.netwisd.biz.study.mapper.StudySpecialAdjLessonMapper;
import com.netwisd.biz.study.service.StudySpecialAdjLessonService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description 专题调整与课程表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Service
@Slf4j
public class StudySpecialAdjLessonServiceImpl extends BatchServiceImpl<StudySpecialAdjLessonMapper, StudySpecialAdjLesson> implements StudySpecialAdjLessonService {

}
