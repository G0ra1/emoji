package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialRange;
import com.netwisd.biz.study.dto.StudySpecialRangeDto;
import com.netwisd.biz.study.vo.StudySpecialRangeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题与对象表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Mapper
public interface StudySpecialRangeMapper extends BaseMapper<StudySpecialRange> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialRangeDto
     * @return
     */
    Page<StudySpecialRangeVo> getPageList(Page page, @Param("studySpecialRangeDto") StudySpecialRangeDto studySpecialRangeDto);
}
