package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialHis;
import com.netwisd.biz.study.dto.StudySpecialHisDto;
import com.netwisd.biz.study.vo.StudySpecialHisVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题历史表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Mapper
public interface StudySpecialHisMapper extends BaseMapper<StudySpecialHis> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialHisDto
     * @return
     */
    Page<StudySpecialHisVo> getPageList(Page page, @Param("studySpecialHisDto") StudySpecialHisDto studySpecialHisDto);
}
