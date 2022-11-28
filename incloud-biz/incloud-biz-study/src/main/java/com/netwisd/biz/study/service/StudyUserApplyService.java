package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyUserApply;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyUserApplyDto;
import com.netwisd.biz.study.vo.StudyUserApplyVo;

import java.util.List;

/**
 * @Description 在线学习人员申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-03-23 16:06:26
 */
public interface StudyUserApplyService extends IService<StudyUserApply> {
    Page list(StudyUserApplyDto studyUserApplyDto);
    List<StudyUserApplyVo> lists(StudyUserApplyDto studyUserApplyDto);
    StudyUserApplyVo get(Long id);
    Boolean save(StudyUserApplyDto studyUserApplyDto);
    Boolean update(List<StudyUserApplyDto> studyUserApplyDtos);
    Boolean delete(String id);
}
