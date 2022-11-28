package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyUserStudyRecords;
import com.netwisd.biz.study.dto.StudyUserStudyRecordsDto;
import com.netwisd.biz.study.vo.StudyUserRecordsVo;
import com.netwisd.biz.study.vo.StudyUserStudyRecordsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 用户学习记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-20 11:02:11
 */
@Mapper
public interface StudyUserStudyRecordsMapper extends BaseMapper<StudyUserStudyRecords> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyUserStudyRecordsDto
     * @return
     */
    Page<StudyUserStudyRecordsVo> getPageList(Page page, @Param("studyUserStudyRecordsDto") StudyUserStudyRecordsDto studyUserStudyRecordsDto);

    List<StudyUserStudyRecordsVo> findLessonRanking();

    List<StudyUserStudyRecordsVo> findSpecialRanking();

    Page<StudyUserRecordsVo> getUserRecords(Page page, @Param("studyUserStudyRecordsDto") StudyUserStudyRecordsDto studyUserStudyRecordsDto);
}
