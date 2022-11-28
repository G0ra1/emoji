package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyUserExamQuestion;
import com.netwisd.biz.study.dto.StudyUserExamQuestionDto;
import com.netwisd.biz.study.vo.StudyUserExamQuestionVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 人员考试题目表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Mapper
public interface StudyUserExamQuestionMapper extends BaseMapper<StudyUserExamQuestion> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyUserExamQuestionDto
     * @return
     */
    Page<StudyUserExamQuestionVo> getPageList(Page page, @Param("studyUserExamQuestionDto") StudyUserExamQuestionDto studyUserExamQuestionDto);
}
