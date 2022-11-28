package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyExamPaperQuestion;
import com.netwisd.biz.study.mapper.StudyExamPaperQuestionMapper;
import com.netwisd.biz.study.service.StudyExamPaperQuestionService;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyExamPaperQuestionDto;
import com.netwisd.biz.study.vo.StudyExamPaperQuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 试卷和题目结果 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 15:59:53
 */
@Service
@Slf4j
public class StudyExamPaperQuestionServiceImpl extends BatchServiceImpl<StudyExamPaperQuestionMapper, StudyExamPaperQuestion> implements StudyExamPaperQuestionService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyExamPaperQuestionMapper studyExamPaperQuestionMapper;

    /**
    * 单表简单查询操作
    * @param studyExamPaperQuestionDto
    * @return
    */
    @Override
    public Page list(StudyExamPaperQuestionDto studyExamPaperQuestionDto) {
        LambdaQueryWrapper<StudyExamPaperQuestion> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<StudyExamPaperQuestion> page = studyExamPaperQuestionMapper.selectPage(studyExamPaperQuestionDto.getPage(),queryWrapper);
        Page<StudyExamPaperQuestionVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyExamPaperQuestionVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyExamPaperQuestionDto
    * @return
    */
    @Override
    public Page lists(StudyExamPaperQuestionDto studyExamPaperQuestionDto) {
        Page<StudyExamPaperQuestionVo> pageVo = studyExamPaperQuestionMapper.getPageList(studyExamPaperQuestionDto.getPage(),studyExamPaperQuestionDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyExamPaperQuestionVo get(Long id) {
        StudyExamPaperQuestion studyExamPaperQuestion = super.getById(id);
        StudyExamPaperQuestionVo studyExamPaperQuestionVo = null;
        if(studyExamPaperQuestion !=null){
            studyExamPaperQuestionVo = dozerMapper.map(studyExamPaperQuestion,StudyExamPaperQuestionVo.class);
        }
        log.debug("查询成功");
        return studyExamPaperQuestionVo;
    }

    /**
    * 保存实体
    * @param studyExamPaperQuestionDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyExamPaperQuestionDto studyExamPaperQuestionDto) {
        StudyExamPaperQuestion studyExamPaperQuestion = dozerMapper.map(studyExamPaperQuestionDto,StudyExamPaperQuestion.class);
        boolean result = super.save(studyExamPaperQuestion);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyExamPaperQuestionDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyExamPaperQuestionDto studyExamPaperQuestionDto) {
        studyExamPaperQuestionDto.setUpdateTime(LocalDateTime.now());
        StudyExamPaperQuestion studyExamPaperQuestion = dozerMapper.map(studyExamPaperQuestionDto,StudyExamPaperQuestion.class);
        boolean result = super.updateById(studyExamPaperQuestion);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
}
