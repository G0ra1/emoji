package com.netwisd.biz.study.service;

import com.netwisd.biz.study.entity.StudyLessonHis;
import com.netwisd.biz.study.vo.StudyLessonHisVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程历史 功能描述...
 * @date 2022-05-11 18:50:02
 */
public interface StudyLessonHisService extends BatchService<StudyLessonHis> {
    /**
     * 获取课程的历史记录
     * @param lessonId
     * @return
     */
    List<StudyLessonHisVo> hisListForLesson(Long lessonId);

    /**
     * 通过id查询课程历史详情
     * @param id
     * @return
     */
    StudyLessonHisVo detail(Long id);
}
