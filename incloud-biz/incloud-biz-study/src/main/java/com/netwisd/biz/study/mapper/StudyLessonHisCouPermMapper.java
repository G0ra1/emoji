package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyLessonHisCouPerm;
import com.netwisd.biz.study.dto.StudyLessonHisCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonHisCouPermVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课程课件授权历史表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:53:32
 */
@Mapper
public interface StudyLessonHisCouPermMapper extends BaseMapper<StudyLessonHisCouPerm> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyLessonHisCouPermDto
     * @return
     */
    Page<StudyLessonHisCouPermVo> getPageList(Page page, @Param("studyLessonHisCouPermDto") StudyLessonHisCouPermDto studyLessonHisCouPermDto);
}
