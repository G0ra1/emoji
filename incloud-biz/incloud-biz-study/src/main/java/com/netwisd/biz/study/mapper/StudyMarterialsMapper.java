package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyMarterials;
import com.netwisd.biz.study.dto.StudyMarterialsDto;
import com.netwisd.biz.study.vo.StudyMarterialsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 20:04:23
 */
@Mapper
public interface StudyMarterialsMapper extends BaseMapper<StudyMarterials> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyMarterialsDto
     * @return
     */
    Page<StudyMarterialsVo> getPageList(Page page, @Param("studyMarterialsDto") StudyMarterialsDto studyMarterialsDto);
}
