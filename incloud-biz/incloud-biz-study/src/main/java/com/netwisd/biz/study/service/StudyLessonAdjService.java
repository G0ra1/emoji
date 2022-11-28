package com.netwisd.biz.study.service;

import com.netwisd.biz.study.dto.StudyLessonAdjDto;
import com.netwisd.biz.study.entity.StudyLessonAdj;
import com.netwisd.biz.study.vo.StudyLessonAdjVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程调整申请 功能描述...
 * @date 2022-05-12 09:17:24
 */
public interface StudyLessonAdjService extends BatchService<StudyLessonAdj> {

    /**
     * 获取课程的调整记录
     *
     * @param lessonId
     * @return
     */
    List<StudyLessonAdjVo> adjListForLesson(Long lessonId);

    /**
     * 通过id查询详情
     *
     * @param id
     * @return
     */
    StudyLessonAdjVo detail(Long id);

    /**
     * 流程新增或修改
     *
     * @param studyLessonAdjDto
     * @return
     */
    StudyLessonAdjVo procSaveOrUpdate(StudyLessonAdjDto studyLessonAdjDto);

    /**
     * 流程回显获取详情
     *
     * @param procInstId
     * @return
     */
    StudyLessonAdjVo procDetail(String procInstId);

    /**
     * 流程提交触发操作
     *
     * @param processInstanceId
     * @return
     */
    Boolean procAfterSubmit(String processInstanceId);

    /**
     * 审批完成触发操作
     *
     * @param processInstanceId
     * @return
     */
    Boolean procAuditSuccess(String processInstanceId);

    /**
     * 流程删除触发操作
     *
     * @param processInstanceId
     * @return
     */
    Boolean procDelete(String processInstanceId);

    /**
     * 页面删除
     * @param lessonAdjId
     * @return
     */
    Boolean delete(Long lessonAdjId);
}
