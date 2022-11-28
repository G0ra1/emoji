package com.netwisd.biz.study.service.impl;

import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudySpecialLesson;
import com.netwisd.biz.study.mapper.StudySpecialLessonMapper;
import com.netwisd.biz.study.service.StudySpecialLessonService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题与课程中间表 功能描述...
 * @date 2022-05-13 10:59:05
 */
@Service
@Slf4j
public class StudySpecialLessonServiceImpl extends BatchServiceImpl<StudySpecialLessonMapper, StudySpecialLesson> implements StudySpecialLessonService {

}
