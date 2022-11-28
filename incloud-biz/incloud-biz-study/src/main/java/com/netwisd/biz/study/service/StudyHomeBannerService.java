package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyHomeBanner;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyHomeBannerDto;
import com.netwisd.biz.study.vo.StudyHomeBannerVo;

import java.util.List;

/**
 * @Description 在线学习轮播图表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-17 14:17:50
 */
public interface StudyHomeBannerService extends IService<StudyHomeBanner> {
    Page list(StudyHomeBannerDto studyHomeBannerDto);
    List<StudyHomeBannerVo> lists(StudyHomeBannerDto studyHomeBannerDto);
    StudyHomeBannerVo get(Long id);
    Boolean save(StudyHomeBannerDto studyHomeBannerDto);
    Boolean update(StudyHomeBannerDto studyHomeBannerDto);
    Boolean delete(String ids);
}
