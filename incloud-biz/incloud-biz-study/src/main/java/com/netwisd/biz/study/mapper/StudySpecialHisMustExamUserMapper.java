package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialHisMustExamUser;
import com.netwisd.biz.study.dto.StudySpecialHisMustExamUserDto;
import com.netwisd.biz.study.vo.StudySpecialHisMustExamUserVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题历史必考人员 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-06-21 14:50:51
 */
@Mapper
public interface StudySpecialHisMustExamUserMapper extends BaseMapper<StudySpecialHisMustExamUser> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialHisMustExamUserDto
     * @return
     */
    Page<StudySpecialHisMustExamUserVo> getPageList(Page page, @Param("studySpecialHisMustExamUserDto") StudySpecialHisMustExamUserDto studySpecialHisMustExamUserDto);
}
