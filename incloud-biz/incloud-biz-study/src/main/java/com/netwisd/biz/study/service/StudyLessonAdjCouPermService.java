package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyLessonAdjCouPerm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonAdjCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonAdjCouPermVo;
/**
 * @Description 课程课件授权调整表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:54:14
 */
public interface StudyLessonAdjCouPermService extends IService<StudyLessonAdjCouPerm> {
    Page list(StudyLessonAdjCouPermDto studyLessonAdjCouPermDto);
    Page lists(StudyLessonAdjCouPermDto studyLessonAdjCouPermDto);
    StudyLessonAdjCouPermVo get(Long id);
    Boolean save(StudyLessonAdjCouPermDto studyLessonAdjCouPermDto);
    Boolean update(StudyLessonAdjCouPermDto studyLessonAdjCouPermDto);
    Boolean delete(Long id);
}
