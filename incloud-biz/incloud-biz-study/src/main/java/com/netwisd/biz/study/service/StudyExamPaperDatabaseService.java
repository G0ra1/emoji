package com.netwisd.biz.study.service;

import com.netwisd.biz.study.entity.StudyExamPaperDatabase;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyExamPaperDatabaseDto;
import com.netwisd.biz.study.vo.StudyExamPaperDatabaseVo;
import com.netwisd.common.db.data.BatchService;

/**
 * @Description 试卷申请题库结果表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 17:36:55
 */
public interface StudyExamPaperDatabaseService extends BatchService<StudyExamPaperDatabase> {
    Page list(StudyExamPaperDatabaseDto studyExamPaperDatabaseDto);
    Page lists(StudyExamPaperDatabaseDto studyExamPaperDatabaseDto);
    StudyExamPaperDatabaseVo get(Long id);
    Boolean save(StudyExamPaperDatabaseDto studyExamPaperDatabaseDto);
    Boolean update(StudyExamPaperDatabaseDto studyExamPaperDatabaseDto);
    Boolean delete(Long id);
}
