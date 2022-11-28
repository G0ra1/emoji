package com.netwisd.biz.study.service.impl;

import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudyExamPaperAdjDatabase;
import com.netwisd.biz.study.mapper.StudyExamPaperAdjDatabaseMapper;
import com.netwisd.biz.study.service.StudyExamPaperAdjDatabaseService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description 试卷调整题库 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@Service
@Slf4j
public class StudyExamPaperAdjDatabaseServiceImpl extends BatchServiceImpl<StudyExamPaperAdjDatabaseMapper, StudyExamPaperAdjDatabase> implements StudyExamPaperAdjDatabaseService {

}
