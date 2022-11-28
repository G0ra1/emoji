package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyExamPaperAdjQuestion;
import com.netwisd.biz.study.dto.StudyExamPaperAdjQuestionDto;
import com.netwisd.biz.study.vo.StudyExamPaperAdjQuestionVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 试卷调整题目 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@Mapper
public interface StudyExamPaperAdjQuestionMapper extends BaseMapper<StudyExamPaperAdjQuestion> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyExamPaperAdjQuestionDto
     * @return
     */
    Page<StudyExamPaperAdjQuestionVo> getPageList(Page page, @Param("studyExamPaperAdjQuestionDto") StudyExamPaperAdjQuestionDto studyExamPaperAdjQuestionDto);
}
