package com.netwisd.biz.study.service.impl;

import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudyUserExamQuestion;
import com.netwisd.biz.study.mapper.StudyUserExamQuestionMapper;
import com.netwisd.biz.study.service.StudyUserExamQuestionService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 人员考试题目表 功能描述...
 * @date 2022-05-26 17:08:29
 */
@Service
@Slf4j
public class StudyUserExamQuestionServiceImpl extends BatchServiceImpl<StudyUserExamQuestionMapper, StudyUserExamQuestion> implements StudyUserExamQuestionService {

}
