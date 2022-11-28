package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyExamPaperAdjDatabase;
import com.netwisd.biz.study.dto.StudyExamPaperAdjDatabaseDto;
import com.netwisd.biz.study.vo.StudyExamPaperAdjDatabaseVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 试卷调整题库 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@Mapper
public interface StudyExamPaperAdjDatabaseMapper extends BaseMapper<StudyExamPaperAdjDatabase> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyExamPaperAdjDatabaseDto
     * @return
     */
    Page<StudyExamPaperAdjDatabaseVo> getPageList(Page page, @Param("studyExamPaperAdjDatabaseDto") StudyExamPaperAdjDatabaseDto studyExamPaperAdjDatabaseDto);
}
