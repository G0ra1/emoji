package com.netwisd.biz.study.service;

import com.netwisd.biz.study.dto.StudyUserExamDto;
import com.netwisd.biz.study.entity.StudyExamPaper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyExamPaperDto;
import com.netwisd.biz.study.vo.StudyExamPaperAdjVo;
import com.netwisd.biz.study.vo.StudyExamPaperVo;
import com.netwisd.biz.study.vo.StudyExamQuestionVo;
import com.netwisd.biz.study.vo.StudyUserExamVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;

/**
 * @Description 试卷结果表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 17:32:00
 */
public interface StudyExamPaperService extends BatchService<StudyExamPaper> {
    Page list(StudyExamPaperDto studyExamPaperDto);
    List<StudyExamPaperVo> lists(StudyExamPaperDto studyExamPaperDto);

    /**
     * 查看试卷详情
     * @param id
     * @return
     */
    StudyExamPaperVo get(Long id);
    /**
     * 获取待阅试卷详情
     * @param id
     * @return
     */
    StudyUserExamVo markPaperDetail (Long id);

    /**
     * 阅卷保存
     * @param studyUserExamDto
     * @return
     */
    Boolean teacherMarking (StudyUserExamDto studyUserExamDto );
    /**
     * 分页获取待阅试卷列表
     * @param studyUserExamDto
     * @return
     */
    Page<StudyUserExamVo> getPaperList(StudyUserExamDto studyUserExamDto);

    /**
     * 查看已阅试卷详情
     * @param id
     * @return
     */
    StudyUserExamVo markedPaperDetail (Long id);

    Boolean isEnable(Long id);
    Boolean delete(Long paperId);
    StudyExamPaperVo procSaveOrUpdate(StudyExamPaperDto studyExamPaperDto);
    Boolean procAuditSuccess(String procInstId);
    Boolean procAfterSubmit(String procInstId);
    StudyExamPaperAdjVo getPaper(Long id);
    StudyExamPaperVo procDetail(String procInstId);
    Boolean procDelete(String procInstId);

    /**
     * 自动阅卷方法，答题提交使用
     * @param id
     * @return
     */
     Boolean autoMarkPaper(Long id);
}
