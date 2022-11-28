package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecialHisMaterials;
import com.netwisd.biz.study.dto.StudySpecialHisMaterialsDto;
import com.netwisd.biz.study.vo.StudySpecialHisMaterialsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题历史与学习资料表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Mapper
public interface StudySpecialHisMaterialsMapper extends BaseMapper<StudySpecialHisMaterials> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialHisMaterialsDto
     * @return
     */
    Page<StudySpecialHisMaterialsVo> getPageList(Page page, @Param("studySpecialHisMaterialsDto") StudySpecialHisMaterialsDto studySpecialHisMaterialsDto);
}
