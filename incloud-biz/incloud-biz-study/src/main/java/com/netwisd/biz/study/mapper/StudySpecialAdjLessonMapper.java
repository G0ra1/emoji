package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.dto.StudySpecialAdjLessonDto;
import com.netwisd.biz.study.entity.StudySpecialAdjLesson;
import com.netwisd.biz.study.vo.StudySpecialAdjLessonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题调整与课程表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Mapper
public interface StudySpecialAdjLessonMapper extends BaseMapper<StudySpecialAdjLesson> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialAdjLessonDto
     * @return
     */
    Page<StudySpecialAdjLessonVo> getPageList(Page page, @Param("studySpecialAdjLessonDto") StudySpecialAdjLessonDto studySpecialAdjLessonDto);
}
