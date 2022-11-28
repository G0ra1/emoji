package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyExamPaperDatabase;
import com.netwisd.biz.study.dto.StudyExamPaperDatabaseDto;
import com.netwisd.biz.study.vo.StudyExamPaperDatabaseVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 试卷申请题库结果表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 17:36:55
 */
@Mapper
public interface StudyExamPaperDatabaseMapper extends BaseMapper<StudyExamPaperDatabase> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyExamPaperDatabaseDto
     * @return
     */
    Page<StudyExamPaperDatabaseVo> getPageList(Page page, @Param("studyExamPaperDatabaseDto") StudyExamPaperDatabaseDto studyExamPaperDatabaseDto);
}
