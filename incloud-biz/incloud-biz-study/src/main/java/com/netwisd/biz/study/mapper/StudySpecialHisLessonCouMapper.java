package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialHisLessonCou;
import com.netwisd.biz.study.dto.StudySpecialHisLessonCouDto;
import com.netwisd.biz.study.vo.StudySpecialHisLessonCouVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题历史课程与课件表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Mapper
public interface StudySpecialHisLessonCouMapper extends BaseMapper<StudySpecialHisLessonCou> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialHisLessonCouDto
     * @return
     */
    Page<StudySpecialHisLessonCouVo> getPageList(Page page, @Param("studySpecialHisLessonCouDto") StudySpecialHisLessonCouDto studySpecialHisLessonCouDto);
}
