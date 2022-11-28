package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyLessonMarterials;
import com.netwisd.biz.study.dto.StudyLessonMarterialsDto;
import com.netwisd.biz.study.vo.StudyLessonMarterialsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课程学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:21:47
 */
@Mapper
public interface StudyLessonMarterialsMapper extends BaseMapper<StudyLessonMarterials> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyLessonMarterialsDto
     * @return
     */
    Page<StudyLessonMarterialsVo> getPageList(Page page, @Param("studyLessonMarterialsDto") StudyLessonMarterialsDto studyLessonMarterialsDto);
}
