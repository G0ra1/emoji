package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyExamPaperHis;
import com.netwisd.biz.study.dto.StudyExamPaperHisDto;
import com.netwisd.biz.study.vo.StudyExamPaperHisVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 试卷历史 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 15:43:35
 */
@Mapper
public interface StudyExamPaperHisMapper extends BaseMapper<StudyExamPaperHis> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyExamPaperHisDto
     * @return
     */
    Page<StudyExamPaperHisVo> getPageList(Page page, @Param("studyExamPaperHisDto") StudyExamPaperHisDto studyExamPaperHisDto);
}
