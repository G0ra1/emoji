package com.netwisd.biz.study.service.impl;


import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudyUserExamQuestionDetail;
import com.netwisd.biz.study.mapper.StudyUserExamQuestionDetailMapper;
import com.netwisd.biz.study.service.StudyUserExamQuestionDetailService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 人员考试题目子表-详情表 功能描述...
 * @date 2022-05-26 17:08:29
 */
@Service
@Slf4j
public class StudyUserExamQuestionDetailServiceImpl extends BatchServiceImpl<StudyUserExamQuestionDetailMapper, StudyUserExamQuestionDetail> implements StudyUserExamQuestionDetailService {

}
