package com.netwisd.biz.study.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.study.entity.StudyExamPaperAdj;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyExamPaperAdjDto;
import com.netwisd.biz.study.vo.StudyExamPaperAdjVo;
/**
 * @Description 试卷调整申请 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
public interface StudyExamPaperAdjService extends BatchService<StudyExamPaperAdj> {
    Page list(StudyExamPaperAdjDto studyExamPaperAdjDto);
    StudyExamPaperAdjVo get(Long id);
    StudyExamPaperAdjVo procSaveOrUpdate (StudyExamPaperAdjDto studyExamPaperAdjDto);
    StudyExamPaperAdjVo procDetail(String procInstId);
    Boolean procAfterSubmit(String processInstanceId);
    Boolean procAuditSuccess(String procInstId);
    List<StudyExamPaperAdjVo> adjListForPaper(Long paperId);
    Boolean delete(Long paperId);
    Boolean procDelete(String procInstId);
}
