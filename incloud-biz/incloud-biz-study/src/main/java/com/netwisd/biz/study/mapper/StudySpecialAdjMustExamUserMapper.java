package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialAdjMustExamUser;
import com.netwisd.biz.study.dto.StudySpecialAdjMustExamUserDto;
import com.netwisd.biz.study.vo.StudySpecialAdjMustExamUserVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题调整必考人员 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-06-21 14:48:33
 */
@Mapper
public interface StudySpecialAdjMustExamUserMapper extends BaseMapper<StudySpecialAdjMustExamUser> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialAdjMustExamUserDto
     * @return
     */
    Page<StudySpecialAdjMustExamUserVo> getPageList(Page page, @Param("studySpecialAdjMustExamUserDto") StudySpecialAdjMustExamUserDto studySpecialAdjMustExamUserDto);
}
