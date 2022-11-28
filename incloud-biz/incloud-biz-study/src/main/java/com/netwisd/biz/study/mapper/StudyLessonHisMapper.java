package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonHisDto;
import com.netwisd.biz.study.entity.StudyLessonHis;
import com.netwisd.biz.study.vo.StudyLessonHisVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程历史 功能描述...
 * @date 2022-05-11 18:50:02
 */
@Mapper
public interface StudyLessonHisMapper extends BaseMapper<StudyLessonHis> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     *
     * @param page
     * @param studyLessonHisDto
     * @return
     */
    Page<StudyLessonHisVo> getPageList(Page page, @Param("studyLessonHisDto") StudyLessonHisDto studyLessonHisDto);
}
