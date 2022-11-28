package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyUser;
import com.netwisd.biz.study.dto.StudyUserDto;
import com.netwisd.biz.study.vo.StudyUserVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 在线学习人员表 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-11-22 09:59:58
 */
@Mapper
public interface StudyUserMapper extends BaseMapper<StudyUser> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyUserDto
     * @return
     */
    Page<StudyUserVo> getPageList(Page page, @Param("studyUserDto") StudyUserDto studyUserDto);
}
