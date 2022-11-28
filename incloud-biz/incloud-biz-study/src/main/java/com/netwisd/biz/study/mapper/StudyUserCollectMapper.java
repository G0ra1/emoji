package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyUserCollect;
import com.netwisd.biz.study.dto.StudyUserCollectDto;
import com.netwisd.biz.study.vo.StudyUserCollectVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 人员收藏表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-27 11:16:09
 */
@Mapper
public interface StudyUserCollectMapper extends BaseMapper<StudyUserCollect> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyUserCollectDto
     * @return
     */
    Page<StudyUserCollectVo> getPageList(Page page, @Param("studyUserCollectDto") StudyUserCollectDto studyUserCollectDto);
}
