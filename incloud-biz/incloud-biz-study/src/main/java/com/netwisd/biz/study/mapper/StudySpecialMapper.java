package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudySpecial;
import com.netwisd.biz.study.dto.StudySpecialDto;
import com.netwisd.biz.study.vo.StudySpecialVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题定义表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Mapper
public interface StudySpecialMapper extends BaseMapper<StudySpecial> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studySpecialDto
     * @return
     */
    Page<StudySpecialVo> getPageList(Page page, @Param("studySpecialDto") StudySpecialDto studySpecialDto);
    /**
     * 学员端首页专题分页查询
     *
     * @param page
     * @param specialDto
     * @return
     */
    Page<StudySpecialVo> xueyuanPageList(Page page, @Param("specialDto") StudySpecialDto specialDto);

    /**
     * 统计专题学时
     * @param specialId
     * @return
     */
    Long countSpecialStudyTime(@Param("specialId")Long specialId);
}
