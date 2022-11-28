package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyUserStudyRecords;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyUserStudyRecordsDto;
import com.netwisd.biz.study.vo.StudySpecialLessonDetailVo;
import com.netwisd.biz.study.vo.StudyUserRecordsVo;

import java.util.Map;

/**
 * @Description 用户学习记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-20 11:02:11
 */
public interface StudyUserStudyRecordsService extends IService<StudyUserStudyRecords> {
    Map<String,String> saveLessonRecord(StudyUserStudyRecordsDto userStudyRecordsDto);
    Map<String,String> saveSpecialRecord(StudyUserStudyRecordsDto userStudyRecordsDto);
    Page<StudyUserRecordsVo> getUserRecords(StudyUserStudyRecordsDto userStudyRecordsDto);
    Long getUserStudyMsg(Long userId,int type);
}
