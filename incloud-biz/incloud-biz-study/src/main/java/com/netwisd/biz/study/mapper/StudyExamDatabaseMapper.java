package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyExamDatabase;
import com.netwisd.biz.study.dto.StudyExamDatabaseDto;
import com.netwisd.biz.study.vo.StudyExamDatabaseVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 题库定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:53:35
 */
@Mapper
public interface StudyExamDatabaseMapper extends BaseMapper<StudyExamDatabase> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyExamDatabaseDto
     * @return
     */
    Page<StudyExamDatabaseVo> getPageList(Page page, @Param("studyExamDatabaseDto") StudyExamDatabaseDto studyExamDatabaseDto);
}
