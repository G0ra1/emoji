package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialLesson;
import com.netwisd.biz.study.dto.StudySpecialLessonDto;
import com.netwisd.biz.study.vo.StudySpecialLessonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题与课程中间表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Mapper
public interface StudySpecialLessonMapper extends BaseMapper<StudySpecialLesson> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialLessonDto
     * @return
     */
    Page<StudySpecialLessonVo> getPageList(Page page, @Param("studySpecialLessonDto") StudySpecialLessonDto studySpecialLessonDto);
}
