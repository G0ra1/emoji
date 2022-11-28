package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyLessonScore;
import com.netwisd.biz.study.dto.StudyLessonScoreDto;
import com.netwisd.biz.study.vo.StudyLessonScoreVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课程评分表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 17:27:45
 */
@Mapper
public interface StudyLessonScoreMapper extends BaseMapper<StudyLessonScore> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyLessonScoreDto
     * @return
     */
    Page<StudyLessonScoreVo> getPageList(Page page, @Param("studyLessonScoreDto") StudyLessonScoreDto studyLessonScoreDto);
}
