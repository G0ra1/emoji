package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyExamPaperQuestion;
import com.netwisd.biz.study.dto.StudyExamPaperQuestionDto;
import com.netwisd.biz.study.vo.StudyExamPaperQuestionVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 试卷和题目结果 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 15:59:53
 */
@Mapper
public interface StudyExamPaperQuestionMapper extends BaseMapper<StudyExamPaperQuestion> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyExamPaperQuestionDto
     * @return
     */
    Page<StudyExamPaperQuestionVo> getPageList(Page page, @Param("studyExamPaperQuestionDto") StudyExamPaperQuestionDto studyExamPaperQuestionDto);
}
