package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.constants.NewsTypeEnum;
import com.netwisd.biz.study.entity.StudyNews;
import com.netwisd.biz.study.dto.StudyNewsDto;
import com.netwisd.biz.study.vo.StudyNewsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 在线学习通知公告表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-15 16:37:49
 */
@Mapper
public interface StudyNewsMapper extends BaseMapper<StudyNews> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyNewsDto
     * @return
     */
    Page<StudyNewsVo> getPageList(Page page, @Param("studyNewsDto") StudyNewsDto studyNewsDto);
}
