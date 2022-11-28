package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyExamQuestion;
import com.netwisd.biz.study.dto.StudyExamQuestionDto;
import com.netwisd.biz.study.vo.StudyExamQuestionVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 题目定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:55:15
 */
@Mapper
public interface StudyExamQuestionMapper extends BaseMapper<StudyExamQuestion> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyExamQuestionDto
     * @return
     */
    Page<StudyExamQuestionVo> getPageList(Page page, @Param("studyExamQuestionDto") StudyExamQuestionDto studyExamQuestionDto);
}
