package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyExamQuestionAnswer;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyExamQuestionAnswerDto;
import com.netwisd.biz.study.vo.StudyExamQuestionAnswerVo;
import com.netwisd.common.db.data.BatchService;

/**
 * @Description 问题答案表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2021-12-02 17:11:12
 */
public interface StudyExamQuestionAnswerService extends BatchService<StudyExamQuestionAnswer> {
    Page list(StudyExamQuestionAnswerDto studyExamQuestionAnswerDto);
    Page lists(StudyExamQuestionAnswerDto studyExamQuestionAnswerDto);
    StudyExamQuestionAnswerVo get(Long id);
    Boolean save(StudyExamQuestionAnswerDto studyExamQuestionAnswerDto);
    Boolean update(StudyExamQuestionAnswerDto studyExamQuestionAnswerDto);
    Boolean delete(Long id);
}
