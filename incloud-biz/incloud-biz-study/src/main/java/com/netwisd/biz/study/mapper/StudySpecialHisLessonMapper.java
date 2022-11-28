package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialHisLesson;
import com.netwisd.biz.study.dto.StudySpecialHisLessonDto;
import com.netwisd.biz.study.vo.StudySpecialHisLessonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题历史与课程表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Mapper
public interface StudySpecialHisLessonMapper extends BaseMapper<StudySpecialHisLesson> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialHisLessonDto
     * @return
     */
    Page<StudySpecialHisLessonVo> getPageList(Page page, @Param("studySpecialHisLessonDto") StudySpecialHisLessonDto studySpecialHisLessonDto);
}
