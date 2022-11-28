package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyUserExam;
import com.netwisd.biz.study.dto.StudyUserExamDto;
import com.netwisd.biz.study.vo.StudyUserExamVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 人员考试 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-26 17:08:29
 */
@Mapper
public interface StudyUserExamMapper extends BaseMapper<StudyUserExam> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyUserExamDto
     * @return
     */
    Page<StudyUserExamVo> getPageList(Page page, @Param("studyUserExamDto") StudyUserExamDto studyUserExamDto);
}
