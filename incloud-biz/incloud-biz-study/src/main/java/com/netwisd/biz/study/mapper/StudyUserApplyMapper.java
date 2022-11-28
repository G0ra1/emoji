package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyUserApply;
import com.netwisd.biz.study.dto.StudyUserApplyDto;
import com.netwisd.biz.study.vo.StudyUserApplyVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 在线学习人员申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-03-23 16:06:26
 */
@Mapper
public interface StudyUserApplyMapper extends BaseMapper<StudyUserApply> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyUserApplyDto
     * @return
     */
    Page<StudyUserApplyVo> getPageList(Page page, @Param("studyUserApplyDto") StudyUserApplyDto studyUserApplyDto);
}
