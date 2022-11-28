package com.netwisd.biz.study.service;

import com.netwisd.biz.study.dto.StudyUserExamDto;
import com.netwisd.biz.study.entity.StudyExamPaper;
import com.netwisd.biz.study.vo.StudyExamPaperVo;
import com.netwisd.biz.study.vo.StudyExamQuestionVo;
import com.netwisd.biz.study.vo.StudyUserExamVo;

/**
 * @Description 在线学习人员表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 *
 */
public interface StudyXyExamService {
    /**
     * 通过试卷id生成试卷
     * @param paperId
     * @return
     */
    StudyExamPaperVo generatePaper(Long paperId);

    /**
     * 提交试卷
     * @param userExamId
     * @return
     */
    Boolean  submittedPapers(Long userExamId);


}
