package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyLessonCouWatchApply;
import com.netwisd.biz.study.dto.StudyLessonCouWatchApplyDto;
import com.netwisd.biz.study.vo.StudyLessonCouWatchApplyVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课程课件观看申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-29 14:22:04
 */
@Mapper
public interface StudyLessonCouWatchApplyMapper extends BaseMapper<StudyLessonCouWatchApply> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyLessonCouWatchApplyDto
     * @return
     */
    Page<StudyLessonCouWatchApplyVo> getPageList(Page page, @Param("studyLessonCouWatchApplyDto") StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto);
}
