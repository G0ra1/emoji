package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyExamQuestionAnswer;
import com.netwisd.biz.study.dto.StudyExamQuestionAnswerDto;
import com.netwisd.biz.study.vo.StudyExamQuestionAnswerVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 问题答案表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2021-12-02 17:11:12
 */
@Mapper
public interface StudyExamQuestionAnswerMapper extends BaseMapper<StudyExamQuestionAnswer> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyExamQuestionAnswerDto
     * @return
     */
    Page<StudyExamQuestionAnswerVo> getPageList(Page page, @Param("studyExamQuestionAnswerDto") StudyExamQuestionAnswerDto studyExamQuestionAnswerDto);
}
