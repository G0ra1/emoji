package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyExamDatabase;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyExamDatabaseDto;
import com.netwisd.biz.study.vo.StudyExamDatabaseVo;

import java.util.List;

/**
 * @Description 题库定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:53:35
 */
public interface StudyExamDatabaseService extends IService<StudyExamDatabase> {
    Page list(StudyExamDatabaseDto studyExamDatabaseDto);
    List<StudyExamDatabaseVo> lists(StudyExamDatabaseDto studyExamDatabaseDto);
    StudyExamDatabaseVo get(Long id);
    List<StudyExamDatabaseVo> listByIds(String id);
   Boolean save(StudyExamDatabaseDto studyExamDatabaseDto);
    Boolean update(StudyExamDatabaseDto studyExamDatabaseDto);
    Boolean delete(Long id);
}
