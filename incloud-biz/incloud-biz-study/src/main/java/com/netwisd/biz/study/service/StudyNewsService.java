package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyNews;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyNewsDto;
import com.netwisd.biz.study.vo.StudyNewsVo;

import java.util.List;

/**
 * @Description 在线学习通知公告表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-15 16:37:49
 */
public interface StudyNewsService extends IService<StudyNews> {
    Page list(StudyNewsDto studyNewsDto);
    List<StudyNewsVo> lists(StudyNewsDto studyNewsDto);
    StudyNewsVo get(Long id);
    Boolean save(StudyNewsDto studyNewsDto);
    Boolean update(StudyNewsDto studyNewsDto);
    Boolean delete(Long id);
    Boolean upHits(Long id);
}
