package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyLessonCouPerm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonCouPermVo;
/**
 * @Description 课程课件授权表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 11:13:20
 */
public interface StudyLessonCouPermService extends IService<StudyLessonCouPerm> {
    Page list(StudyLessonCouPermDto studyLessonCouPermDto);
    Page lists(StudyLessonCouPermDto studyLessonCouPermDto);
    StudyLessonCouPermVo get(Long id);
    Boolean save(StudyLessonCouPermDto studyLessonCouPermDto);
    Boolean update(StudyLessonCouPermDto studyLessonCouPermDto);
    Boolean delete(Long id);
}
