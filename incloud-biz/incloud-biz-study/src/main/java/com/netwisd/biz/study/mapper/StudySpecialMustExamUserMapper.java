package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialMustExamUser;
import com.netwisd.biz.study.dto.StudySpecialMustExamUserDto;
import com.netwisd.biz.study.vo.StudySpecialMustExamUserVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题必考人员 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-06-21 14:29:47
 */
@Mapper
public interface StudySpecialMustExamUserMapper extends BaseMapper<StudySpecialMustExamUser> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialMustExamUserDto
     * @return
     */
    Page<StudySpecialMustExamUserVo> getPageList(Page page, @Param("studySpecialMustExamUserDto") StudySpecialMustExamUserDto studySpecialMustExamUserDto);
}
