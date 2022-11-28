package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyExamPaperQuestion;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyExamPaperQuestionDto;
import com.netwisd.biz.study.vo.StudyExamPaperQuestionVo;
import com.netwisd.common.db.data.BatchService;

/**
 * @Description 试卷和题目结果 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 15:59:53
 */
public interface StudyExamPaperQuestionService extends BatchService<StudyExamPaperQuestion> {
    Page list(StudyExamPaperQuestionDto studyExamPaperQuestionDto);
    Page lists(StudyExamPaperQuestionDto studyExamPaperQuestionDto);
    StudyExamPaperQuestionVo get(Long id);
    Boolean save(StudyExamPaperQuestionDto studyExamPaperQuestionDto);
    Boolean update(StudyExamPaperQuestionDto studyExamPaperQuestionDto);
    Boolean delete(Long id);
}
