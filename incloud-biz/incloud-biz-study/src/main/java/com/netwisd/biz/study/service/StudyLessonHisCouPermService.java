package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyLessonHisCouPerm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonHisCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonHisCouPermVo;
/**
 * @Description 课程课件授权历史表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:53:32
 */
public interface StudyLessonHisCouPermService extends IService<StudyLessonHisCouPerm> {
    Page list(StudyLessonHisCouPermDto studyLessonHisCouPermDto);
    Page lists(StudyLessonHisCouPermDto studyLessonHisCouPermDto);
    StudyLessonHisCouPermVo get(Long id);
    Boolean save(StudyLessonHisCouPermDto studyLessonHisCouPermDto);
    Boolean update(StudyLessonHisCouPermDto studyLessonHisCouPermDto);
    Boolean delete(Long id);
}
