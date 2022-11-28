package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyLessonComments;
import com.netwisd.biz.study.dto.StudyLessonCommentsDto;

import java.util.Map;

/**
 * @Description 课程评论表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:18:19
 */
public interface StudyLessonCommentsService extends IService<StudyLessonComments> {
    Map<String,Object> getByLessonId(Long lessonId);
    Boolean save(StudyLessonCommentsDto studyLessonCommentsDto);
    Boolean update(StudyLessonCommentsDto studyLessonCommentsDto);
    Boolean delete(Long id);
}
