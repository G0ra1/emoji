package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyLessonScore;
import com.netwisd.biz.study.dto.StudyLessonScoreDto;

import java.math.BigDecimal;

/**
 * @Description 课程评分表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 17:27:45
 */
public interface StudyLessonScoreService extends IService<StudyLessonScore> {
    BigDecimal save(StudyLessonScoreDto studyLessonScoreDto);
    BigDecimal getLessonScore(Long lessonId);
    Integer getIsScore(Long lessonId);
}
