package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyLessonComments;
import com.netwisd.biz.study.dto.StudyLessonCommentsDto;
import com.netwisd.biz.study.vo.StudyLessonCommentsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课程评论表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:18:19
 */
@Mapper
public interface StudyLessonCommentsMapper extends BaseMapper<StudyLessonComments> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyLessonCommentsDto
     * @return
     */
    Page<StudyLessonCommentsVo> getPageList(Page page, @Param("studyLessonCommentsDto") StudyLessonCommentsDto studyLessonCommentsDto);
}
