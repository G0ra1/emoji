package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyCollection;
import com.netwisd.biz.study.dto.StudyCollectionDto;
import com.netwisd.biz.study.entity.StudyLesson;
import com.netwisd.biz.study.entity.StudySpecial;
import com.netwisd.biz.study.vo.StudyCollectionVo;
import com.netwisd.biz.study.vo.StudyLessonVo;
import com.netwisd.biz.study.vo.StudySpecialVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 收藏表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-06 14:55:57
 */
@Mapper
public interface StudyCollectionMapper extends BaseMapper<StudyCollection> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyCollectionDto
     * @return
     */
    Page<StudyCollectionVo> getPageList(Page page, @Param("studyCollectionDto") StudyCollectionDto studyCollectionDto);
    /**
     * 我的收藏-专题
     *
     * @return
     */
    Page<StudySpecialVo> getSpecials(Page page, @Param("studyCollectionDto") StudyCollectionDto studyCollectionDto);

    /**
     * 我的收藏-课程
     *
     * @return
     */
    Page<StudyLessonVo> getLessons(Page page, @Param("studyCollectionDto") StudyCollectionDto studyCollectionDto);
}
