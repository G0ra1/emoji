package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyMarterials;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyMarterialsDto;
import com.netwisd.biz.study.vo.StudyMarterialsVo;

import java.util.List;

/**
 * @Description 学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 20:04:23
 */
public interface StudyMarterialsService extends IService<StudyMarterials> {
    Page list(StudyMarterialsDto studyMarterialsDto);
    List<StudyMarterialsVo> lists(StudyMarterialsDto studyMarterialsDto);
    StudyMarterialsVo get(Long id);
    Boolean save(StudyMarterialsDto studyMarterialsDto);
    Boolean update(StudyMarterialsDto studyMarterialsDto);
    Boolean delete(String ids);
}
