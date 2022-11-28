package com.netwisd.biz.study.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.study.entity.StudyUserExam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyUserExamDto;
import com.netwisd.biz.study.vo.StudyUserExamVo;
/**
 * @Description 人员考试 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
public interface StudyUserExamService extends BatchService<StudyUserExam> {
    /**
     * 学员答题保存
     * @param studyUserExamDto
     * @return
     */
    Boolean save(StudyUserExamDto studyUserExamDto);

    /**
     * 个人中心-我的考试
     * @param studyUserExamDto
     * @return
     */
    Page<StudyUserExamVo> pageList(StudyUserExamDto studyUserExamDto);

    /**
     * 个人中心- 我的考试-详情
     * @param id
     * @return
     */
    StudyUserExamVo getUserExamDetail(Long id);

}
