package com.netwisd.biz.study.service.impl;

import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudyExamPaperHisDatabase;
import com.netwisd.biz.study.mapper.StudyExamPaperHisDatabaseMapper;
import com.netwisd.biz.study.service.StudyExamPaperHisDatabaseService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description 试卷题库历史 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 15:43:35
 */
@Service
@Slf4j
public class StudyExamPaperHisDatabaseServiceImpl extends BatchServiceImpl<StudyExamPaperHisDatabaseMapper, StudyExamPaperHisDatabase> implements StudyExamPaperHisDatabaseService {

}
