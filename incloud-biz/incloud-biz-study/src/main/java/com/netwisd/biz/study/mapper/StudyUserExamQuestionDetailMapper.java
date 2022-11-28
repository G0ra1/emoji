package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyUserExamQuestionDetail;
import com.netwisd.biz.study.dto.StudyUserExamQuestionDetailDto;
import com.netwisd.biz.study.vo.StudyUserExamQuestionDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 人员考试题目子表-详情表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Mapper
public interface StudyUserExamQuestionDetailMapper extends BaseMapper<StudyUserExamQuestionDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyUserExamQuestionDetailDto
     * @return
     */
    Page<StudyUserExamQuestionDetailVo> getPageList(Page page, @Param("studyUserExamQuestionDetailDto") StudyUserExamQuestionDetailDto studyUserExamQuestionDetailDto);
}
