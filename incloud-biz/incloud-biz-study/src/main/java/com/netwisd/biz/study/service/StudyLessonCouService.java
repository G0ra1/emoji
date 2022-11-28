package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyLessonCou;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonCouDto;
import com.netwisd.biz.study.vo.StudyLessonCouVo;
/**
 * @Description 课程课件表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:20:24
 */
public interface StudyLessonCouService extends IService<StudyLessonCou> {
    Page list(StudyLessonCouDto studyLessonCouDto);
    Page lists(StudyLessonCouDto studyLessonCouDto);
    StudyLessonCouVo get(Long id);
    Boolean save(StudyLessonCouDto studyLessonCouDto);
    Boolean update(StudyLessonCouDto studyLessonCouDto);
    Boolean delete(Long id);
}
