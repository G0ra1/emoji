package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyHomeBanner;
import com.netwisd.biz.study.dto.StudyHomeBannerDto;
import com.netwisd.biz.study.vo.StudyHomeBannerVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 在线学习轮播图表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-17 14:17:50
 */
@Mapper
public interface StudyHomeBannerMapper extends BaseMapper<StudyHomeBanner> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyHomeBannerDto
     * @return
     */
    Page<StudyHomeBannerVo> getPageList(Page page, @Param("studyHomeBannerDto") StudyHomeBannerDto studyHomeBannerDto);
}
