package com.netwisd.biz.study.service.impl;


import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudySpecialHisLesson;
import com.netwisd.biz.study.mapper.StudySpecialHisLessonMapper;
import com.netwisd.biz.study.service.StudySpecialHisLessonService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题历史与课程表 功能描述...
 * @date 2022-05-13 14:23:33
 */
@Service
@Slf4j
public class StudySpecialHisLessonServiceImpl extends BatchServiceImpl<StudySpecialHisLessonMapper, StudySpecialHisLesson> implements StudySpecialHisLessonService {

}
