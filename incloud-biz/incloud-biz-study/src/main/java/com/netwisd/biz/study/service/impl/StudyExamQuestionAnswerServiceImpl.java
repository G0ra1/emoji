package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyExamQuestionAnswer;
import com.netwisd.biz.study.mapper.StudyExamQuestionAnswerMapper;
import com.netwisd.biz.study.service.StudyExamQuestionAnswerService;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyExamQuestionAnswerDto;
import com.netwisd.biz.study.vo.StudyExamQuestionAnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 问题答案表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2021-12-02 17:11:12
 */
@Service
@Slf4j
public class StudyExamQuestionAnswerServiceImpl extends BatchServiceImpl<StudyExamQuestionAnswerMapper, StudyExamQuestionAnswer> implements StudyExamQuestionAnswerService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyExamQuestionAnswerMapper studyExamQuestionAnswerMapper;

    /**
    * 单表简单查询操作
    * @param studyExamQuestionAnswerDto
    * @return
    */
    @Override
    public Page list(StudyExamQuestionAnswerDto studyExamQuestionAnswerDto) {
        LambdaQueryWrapper<StudyExamQuestionAnswer> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<StudyExamQuestionAnswer> page = studyExamQuestionAnswerMapper.selectPage(studyExamQuestionAnswerDto.getPage(),queryWrapper);
        Page<StudyExamQuestionAnswerVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyExamQuestionAnswerVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyExamQuestionAnswerDto
    * @return
    */
    @Override
    public Page lists(StudyExamQuestionAnswerDto studyExamQuestionAnswerDto) {
        Page<StudyExamQuestionAnswerVo> pageVo = studyExamQuestionAnswerMapper.getPageList(studyExamQuestionAnswerDto.getPage(),studyExamQuestionAnswerDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyExamQuestionAnswerVo get(Long id) {
        StudyExamQuestionAnswer studyExamQuestionAnswer = super.getById(id);
        StudyExamQuestionAnswerVo studyExamQuestionAnswerVo = null;
        if(studyExamQuestionAnswer !=null){
            studyExamQuestionAnswerVo = dozerMapper.map(studyExamQuestionAnswer,StudyExamQuestionAnswerVo.class);
        }
        log.debug("查询成功");
        return studyExamQuestionAnswerVo;
    }

    /**
    * 保存实体
    * @param studyExamQuestionAnswerDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyExamQuestionAnswerDto studyExamQuestionAnswerDto) {
        StudyExamQuestionAnswer studyExamQuestionAnswer = dozerMapper.map(studyExamQuestionAnswerDto,StudyExamQuestionAnswer.class);
        boolean result = super.save(studyExamQuestionAnswer);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyExamQuestionAnswerDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyExamQuestionAnswerDto studyExamQuestionAnswerDto) {
        studyExamQuestionAnswerDto.setUpdateTime(LocalDateTime.now());
        StudyExamQuestionAnswer studyExamQuestionAnswer = dozerMapper.map(studyExamQuestionAnswerDto,StudyExamQuestionAnswer.class);
        boolean result = super.updateById(studyExamQuestionAnswer);
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
