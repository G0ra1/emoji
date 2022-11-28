package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyExamPaper;
import com.netwisd.biz.study.dto.StudyExamPaperDto;
import com.netwisd.biz.study.vo.StudyExamPaperVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 试卷结果表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 17:32:00
 */
@Mapper
public interface StudyExamPaperMapper extends BaseMapper<StudyExamPaper> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyExamPaperDto
     * @return
     */
    Page<StudyExamPaperVo> getPageList(Page page, @Param("studyExamPaperDto") StudyExamPaperDto studyExamPaperDto);
}
