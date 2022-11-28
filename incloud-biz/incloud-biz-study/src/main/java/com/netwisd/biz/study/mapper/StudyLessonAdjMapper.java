package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyLessonAdj;
import com.netwisd.biz.study.dto.StudyLessonAdjDto;
import com.netwisd.biz.study.vo.StudyLessonAdjVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课程调整申请 功能描述...
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @date 2022-05-12 09:17:24
 */
@Mapper
public interface StudyLessonAdjMapper extends BaseMapper<StudyLessonAdj> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyLessonAdjDto
     * @return
     */
    Page<StudyLessonAdjVo> getPageList(Page page, @Param("studyLessonAdjDto") StudyLessonAdjDto studyLessonAdjDto);
}
