package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyLessonMarterials;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonMarterialsDto;
import com.netwisd.biz.study.vo.StudyLessonMarterialsVo;
/**
 * @Description 课程学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:21:47
 */
public interface StudyLessonMarterialsService extends IService<StudyLessonMarterials> {
    Page list(StudyLessonMarterialsDto studyLessonMarterialsDto);
    Page lists(StudyLessonMarterialsDto studyLessonMarterialsDto);
    StudyLessonMarterialsVo get(Long id);
    Boolean save(StudyLessonMarterialsDto studyLessonMarterialsDto);
    Boolean update(StudyLessonMarterialsDto studyLessonMarterialsDto);
    Boolean delete(Long id);
}
