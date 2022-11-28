package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialAdjJieye;
import com.netwisd.biz.study.dto.StudySpecialAdjJieyeDto;
import com.netwisd.biz.study.vo.StudySpecialAdjJieyeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题调整与结业设置（子表） 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Mapper
public interface StudySpecialAdjJieyeMapper extends BaseMapper<StudySpecialAdjJieye> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialAdjJieyeDto
     * @return
     */
    Page<StudySpecialAdjJieyeVo> getPageList(Page page, @Param("studySpecialAdjJieyeDto") StudySpecialAdjJieyeDto studySpecialAdjJieyeDto);
}
