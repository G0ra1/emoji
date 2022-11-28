package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyLessonAdjCouPerm;
import com.netwisd.biz.study.dto.StudyLessonAdjCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonAdjCouPermVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课程课件授权调整表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:54:14
 */
@Mapper
public interface StudyLessonAdjCouPermMapper extends BaseMapper<StudyLessonAdjCouPerm> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyLessonAdjCouPermDto
     * @return
     */
    Page<StudyLessonAdjCouPermVo> getPageList(Page page, @Param("studyLessonAdjCouPermDto") StudyLessonAdjCouPermDto studyLessonAdjCouPermDto);
}
