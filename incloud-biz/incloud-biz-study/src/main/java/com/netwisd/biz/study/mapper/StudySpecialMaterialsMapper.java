package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialMaterials;
import com.netwisd.biz.study.dto.StudySpecialMaterialsDto;
import com.netwisd.biz.study.vo.StudySpecialMaterialsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 专题与学习资料表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Mapper
public interface StudySpecialMaterialsMapper extends BaseMapper<StudySpecialMaterials> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialMaterialsDto
     * @return
     */
    Page<StudySpecialMaterialsVo> getPageList(Page page, @Param("studySpecialMaterialsDto") StudySpecialMaterialsDto studySpecialMaterialsDto);
    List<StudySpecialMaterialsVo> getSpecialMaterialsListBySpecialId(@Param("specialId") Long specialId);
}
