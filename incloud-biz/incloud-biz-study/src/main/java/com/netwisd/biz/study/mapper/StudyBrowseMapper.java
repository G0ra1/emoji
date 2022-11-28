package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyBrowse;
import com.netwisd.biz.study.dto.StudyBrowseDto;
import com.netwisd.biz.study.vo.StudyBrowseVo;
import com.netwisd.biz.study.vo.StudyLessonVo;
import com.netwisd.biz.study.vo.StudySpecialVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 浏览记录表 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-24 11:10:47
 */
@Mapper
public interface StudyBrowseMapper extends BaseMapper<StudyBrowse> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyBrowseDto
     * @return
     */
    Page<StudyBrowseVo> getPageList(Page page, @Param("studyBrowseDto") StudyBrowseDto studyBrowseDto);

    /**
     * 我的浏览-专题
     *  @param page
     *  @param studyBrowseDto
     *
     * @return
     */
    Page<StudySpecialVo> getSpecials(Page page, @Param("studyBrowseDto") StudyBrowseDto studyBrowseDto);

    /**
     * 我的浏览-课程
     *
     * @return
     */
    Page<StudyLessonVo> getLessons(Page page, @Param("studyBrowseDto") StudyBrowseDto studyBrowseDto);

}
