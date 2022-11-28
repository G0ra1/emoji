package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyLessonCou;
import com.netwisd.biz.study.dto.StudyLessonCouDto;
import com.netwisd.biz.study.vo.StudyLessonCouVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课程课件表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:20:24
 */
@Mapper
public interface StudyLessonCouMapper extends BaseMapper<StudyLessonCou> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyLessonCouDto
     * @return
     */
    Page<StudyLessonCouVo> getPageList(Page page, @Param("studyLessonCouDto") StudyLessonCouDto studyLessonCouDto);
}
