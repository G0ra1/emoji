package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.dto.StudyLessonDto;
import com.netwisd.biz.study.entity.StudyLesson;
import com.netwisd.biz.study.vo.*;

import java.util.List;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课程表 功能描述...
 * @date 2022-04-19 19:15:31
 */
public interface StudyLessonService extends IService<StudyLesson> {
    Page list(StudyLessonDto studyLessonDto);

    List<StudyLessonForSpecialVo> lists(StudyLessonDto studyLessonDto);

    StudyLessonVo get(Long id);

    Boolean updateIsIndex(Long id);

    Boolean updateIsEnable(Long id);

    /**
     * 流程新增或修改方法
     *
     * @param studyLessonDto
     * @return
     */
    StudyLessonVo procSaveOrUpdate(StudyLessonDto studyLessonDto);

    /**
     * 流程回显获取详情
     *
     * @param procInstId
     * @return
     */
    StudyLessonVo procDetail(String procInstId);

    /**
     * 通过id组装调整信息
     *
     * @param id
     * @return
     */
    StudyLessonAdjVo detailForAdj(Long id);

    /**
     * 流程提交触发操作
     *
     * @param procInstId
     * @return
     */
    Boolean procAfterSubmit(String procInstId);

    /**
     * 审批完成触发操作
     *
     * @param procInstId
     * @return
     */
    Boolean procAuditSuccess(String procInstId);

    /**
     * 流程删除触发操作
     *
     * @param procInstId
     * @return
     */
    Boolean procDelete(String procInstId);

    /**
     * 页面删除
     * @param lessonId
     * @return
     */
    Boolean delete(Long lessonId);

    /**
     * 通过课程id查询专题课程详情接口
     * @param specialId
     * @param lessonId
     */
    StudySpecialLessonDetailVo getLessonDetailForSpecial(Long specialId, Long lessonId);
}
