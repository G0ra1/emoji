package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialLessonCou;
import com.netwisd.biz.study.dto.StudySpecialLessonCouDto;
import com.netwisd.biz.study.vo.StudySpecialLessonCouVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题课程与课件表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Mapper
public interface StudySpecialLessonCouMapper extends BaseMapper<StudySpecialLessonCou> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialLessonCouDto
     * @return
     */
    Page<StudySpecialLessonCouVo> getPageList(Page page, @Param("studySpecialLessonCouDto") StudySpecialLessonCouDto studySpecialLessonCouDto);
}
