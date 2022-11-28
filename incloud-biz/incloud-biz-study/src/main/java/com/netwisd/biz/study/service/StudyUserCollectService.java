package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyUserCollect;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyUserCollectDto;
import com.netwisd.biz.study.vo.StudyUserCollectVo;
/**
 * @Description 人员收藏表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-27 11:16:09
 */
public interface StudyUserCollectService extends IService<StudyUserCollect> {
    Page list(StudyUserCollectDto studyUserCollectDto);
    Page lists(StudyUserCollectDto studyUserCollectDto);
    StudyUserCollectVo get(Long id);
    Boolean save(StudyUserCollectDto studyUserCollectDto);
    Boolean update(StudyUserCollectDto studyUserCollectDto);
    Boolean delete(Long id);
}
