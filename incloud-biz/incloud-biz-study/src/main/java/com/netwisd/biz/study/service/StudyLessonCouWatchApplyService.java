package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyLessonCouWatchApply;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonCouWatchApplyDto;
import com.netwisd.biz.study.vo.StudyLessonCouWatchApplyVo;

import java.util.List;

/**
 * @Description 课程课件观看申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-29 14:22:04
 */
public interface StudyLessonCouWatchApplyService extends IService<StudyLessonCouWatchApply> {
    Page list(StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto);
    StudyLessonCouWatchApplyVo get(Long id);
    Boolean save(StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto);
    Boolean update(StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto);
    Boolean updateStatusBatch(List<StudyLessonCouWatchApplyDto> studyLessonCouWatchApplyDtos);
}
