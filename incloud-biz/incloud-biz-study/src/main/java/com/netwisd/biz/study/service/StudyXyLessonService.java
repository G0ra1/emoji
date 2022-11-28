package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonDto;
import com.netwisd.biz.study.vo.StudyLessonForShowVo;
import com.netwisd.biz.study.vo.StudyLessonVo;

public interface StudyXyLessonService {
    Page<StudyLessonVo> pageForIndex(StudyLessonDto lessonDto);
    StudyLessonForShowVo getLessonDetail(Long id);
}
