package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialJieye;
import com.netwisd.biz.study.dto.StudySpecialJieyeDto;
import com.netwisd.biz.study.vo.StudySpecialJieyeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题结业设置（子表）;证书 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Mapper
public interface StudySpecialJieyeMapper extends BaseMapper<StudySpecialJieye> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialJieyeDto
     * @return
     */
    Page<StudySpecialJieyeVo> getPageList(Page page, @Param("studySpecialJieyeDto") StudySpecialJieyeDto studySpecialJieyeDto);
}
