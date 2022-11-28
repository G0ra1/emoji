package com.netwisd.biz.study.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.study.entity.StudyExamPaperHis;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyExamPaperHisDto;
import com.netwisd.biz.study.vo.StudyExamPaperHisVo;
/**
 * @Description 试卷历史 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 15:43:35
 */
public interface StudyExamPaperHisService extends BatchService<StudyExamPaperHis> {
    Page list(StudyExamPaperHisDto studyExamPaperHisDto);
    Page lists(StudyExamPaperHisDto studyExamPaperHisDto);
    StudyExamPaperHisVo get(Long id);
    List<StudyExamPaperHisVo> hisListForPaper(Long paperId);

}
