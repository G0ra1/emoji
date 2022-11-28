package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialAdjRange;
import com.netwisd.biz.study.dto.StudySpecialAdjRangeDto;
import com.netwisd.biz.study.vo.StudySpecialAdjRangeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题调整与对象表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Mapper
public interface StudySpecialAdjRangeMapper extends BaseMapper<StudySpecialAdjRange> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialAdjRangeDto
     * @return
     */
    Page<StudySpecialAdjRangeVo> getPageList(Page page, @Param("studySpecialAdjRangeDto") StudySpecialAdjRangeDto studySpecialAdjRangeDto);
}
