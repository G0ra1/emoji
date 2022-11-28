package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyCollection;
import com.netwisd.biz.study.dto.StudyCollectionDto;
import com.netwisd.biz.study.vo.StudyLessonForShowVo;
import com.netwisd.biz.study.vo.StudySpecialVo;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 收藏表 功能描述...
 * @date 2022-05-06 14:55:57
 */
public interface StudyCollectionService extends IService<StudyCollection> {
    /**
     * 收藏/取消收藏
     *
     * @param studyCollectionDto
     * @return
     */
    Boolean save(StudyCollectionDto studyCollectionDto);

    /**
     * 取消收藏（单独取消收藏）
     *
     * @param id
     * @return
     */
    Boolean remove(Long id);

    /**
     * 我的收藏-专题
     *
     * @return
     */
    Page<StudySpecialVo> getSpecialPage(StudyCollectionDto studyCollectionDto);

    /**
     * 我的收藏-课程
     *
     * @return
     */
    Page<StudyLessonForShowVo> getLessonPage(StudyCollectionDto studyCollectionDto);


}
