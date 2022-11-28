package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyExamQuestion;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyExamQuestionDto;
import com.netwisd.biz.study.vo.StudyExamQuestionVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description 题目定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:55:15
 */
public interface StudyExamQuestionService extends IService<StudyExamQuestion> {
    Page list(StudyExamQuestionDto studyExamQuestionDto);
    Page lists(StudyExamQuestionDto studyExamQuestionDto);
    StudyExamQuestionVo get(Long id);
    List<StudyExamQuestionVo> readExcel(MultipartFile file);
    void saveList(List<StudyExamQuestionDto> studyExamQuestionDefDtoList);
    Boolean save(StudyExamQuestionDto studyExamQuestionDto);
    Boolean update(StudyExamQuestionDto studyExamQuestionDto);
    Boolean delete(Long id);
}
