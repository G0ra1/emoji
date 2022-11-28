package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyLesson;
import com.netwisd.biz.study.dto.StudyLessonDto;
import com.netwisd.biz.study.vo.StudyLessonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课程表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:15:31
 */
@Mapper
public interface StudyLessonMapper extends BaseMapper<StudyLesson> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyLessonDto
     * @return
     */
    Page<StudyLessonVo> pageForIndex(Page page, @Param("studyLessonDto") StudyLessonDto studyLessonDto);
}
