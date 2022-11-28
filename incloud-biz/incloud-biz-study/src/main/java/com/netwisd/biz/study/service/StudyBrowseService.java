package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyBrowse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyBrowseDto;
import com.netwisd.biz.study.vo.StudyLessonForShowVo;
import com.netwisd.biz.study.vo.StudySpecialVo;

/**
 * @Description 浏览记录表 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-24 11:10:47
 */
public interface StudyBrowseService extends IService<StudyBrowse> {
    /**
     * 新增或更新浏览记录
     * @param studyBrowseDto
     * @return
     */
    void save(StudyBrowseDto studyBrowseDto);
    /**
     * 分页获取浏览过的课程
     * @param studyBrowseDto
     * @return
     */
    Page <StudyLessonForShowVo> getLessons(StudyBrowseDto studyBrowseDto);
    /**
     * 分页获取浏览过的专题
     * @param studyBrowseDto
     * @return
     */
    Page <StudySpecialVo> getSpecials(StudyBrowseDto studyBrowseDto);
}
