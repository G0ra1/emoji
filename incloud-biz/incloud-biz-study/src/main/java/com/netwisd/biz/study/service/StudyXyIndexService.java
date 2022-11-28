package com.netwisd.biz.study.service;

import com.netwisd.biz.study.vo.*;

import java.util.List;
import java.util.Map;

public interface StudyXyIndexService {
    List<StudyLessonVo> getLessons(Integer current,Integer size,String lessonName);

    List<StudySpecialVo> getSpecials(Integer current,Integer size,String specialName);

    Map<String,Object> getLessonOrSpecialRanking(Integer size, String type);

    Map<String,Object> getStudyTimeRanking(Integer size);
}
