package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.constants.PaperCodeEnum;
import com.netwisd.biz.study.constants.QuestionCode;
import com.netwisd.biz.study.constants.YesNo;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.mapper.StudyExamPaperHisDatabaseMapper;
import com.netwisd.biz.study.mapper.StudyExamPaperHisQuestionMapper;
import com.netwisd.biz.study.mapper.StudyExamQuestionAnswerMapper;
import com.netwisd.biz.study.service.StudyExamQuestionService;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.mapper.StudyExamPaperHisMapper;
import com.netwisd.biz.study.service.StudyExamPaperHisService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyExamPaperHisDto;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Description 试卷历史 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 15:43:35
 */
@Service
@Slf4j
public class StudyExamPaperHisServiceImpl extends BatchServiceImpl<StudyExamPaperHisMapper, StudyExamPaperHis> implements StudyExamPaperHisService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyExamPaperHisMapper studyExamPaperHisMapper;

    @Autowired
    private StudyExamPaperHisQuestionMapper studyExamPaperHisQuestionMapper;

    @Autowired
    private StudyExamQuestionService studyExamQuestionService;

    @Autowired
    private StudyExamQuestionAnswerMapper studyExamQuestionAnswerMapper;

    @Autowired
    private StudyExamPaperHisDatabaseMapper studyExamPaperHisDatabaseMapper;

    /**
    * 单表简单查询操作
    * @param studyExamPaperHisDto
    * @return
    */
    @Override
    public Page list(StudyExamPaperHisDto studyExamPaperHisDto) {
        LambdaQueryWrapper<StudyExamPaperHis> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<StudyExamPaperHis> page = studyExamPaperHisMapper.selectPage(studyExamPaperHisDto.getPage(),queryWrapper);
        Page<StudyExamPaperHisVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyExamPaperHisVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyExamPaperHisDto
    * @return
    */
    @Override
    public Page lists(StudyExamPaperHisDto studyExamPaperHisDto) {
        Page<StudyExamPaperHisVo> pageVo = studyExamPaperHisMapper.getPageList(studyExamPaperHisDto.getPage(),studyExamPaperHisDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyExamPaperHisVo get(Long id) {
        StudyExamPaperHis studyExamPaperHis = super.getById(id);
        StudyExamPaperHisVo studyExamPaperHisVo = new StudyExamPaperHisVo();
        if (studyExamPaperHis != null) {
            studyExamPaperHisVo = dozerMapper.map(studyExamPaperHis, StudyExamPaperHisVo.class);
            //查看固定试卷
            if (studyExamPaperHis.getPaperCode().equals(PaperCodeEnum.GDSJ.getCode())) {
                LambdaQueryWrapper<StudyExamPaperHisQuestion> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(StudyExamPaperHisQuestion::getPaperId, id);
                queryWrapper.orderByAsc(StudyExamPaperHisQuestion::getSortNumber);
                List<StudyExamPaperHisQuestion> studyPaperHisQuestionList = studyExamPaperHisQuestionMapper.selectList(queryWrapper);
                List<StudyExamPaperHisQuestionVo> studyExamPaperQuestionVos = DozerUtils.mapList(dozerMapper, studyPaperHisQuestionList, StudyExamPaperHisQuestionVo.class);
                if (CollectionUtil.isNotEmpty(studyPaperHisQuestionList)) {
                    List<Long> idList = studyPaperHisQuestionList.stream().map(StudyExamPaperHisQuestion::getQuestionId).collect(Collectors.toList());
                    //所有问题
                    List<StudyExamQuestion> studyExamQuestions = studyExamQuestionService.listByIds(idList);
                    Map<Long, List<StudyExamQuestion>> questionMap = studyExamQuestions.stream().collect(Collectors.groupingBy(StudyExamQuestion::getId));

                    List<StudyExamQuestion> shortAnswer = studyExamQuestions.stream().filter(a -> a.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)).collect(Collectors.toList());
                    if (CollectionUtil.isNotEmpty(shortAnswer)){
                        studyExamPaperHisVo.setIsHaveShortAnswer(YesNo.YES.code);
                    }else {
                        studyExamPaperHisVo.setIsHaveShortAnswer(YesNo.NO.code);
                    }
                    List<Long> questionIds = studyExamQuestions.stream().map(StudyExamQuestion::getId).collect(Collectors.toList());
                    LambdaQueryWrapper<StudyExamQuestionAnswer> queryWrapper2 = new LambdaQueryWrapper<>();
                    queryWrapper2.in(StudyExamQuestionAnswer::getQuestionId, questionIds);
                    //所有答案
                    List<StudyExamQuestionAnswer> studyExamQuestionAnswers1 = studyExamQuestionAnswerMapper.selectList(queryWrapper2);
                    List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos1 = DozerUtils.mapList(dozerMapper, studyExamQuestionAnswers1, StudyExamQuestionAnswerVo.class);
                    Map<Long, List<StudyExamQuestionAnswerVo>> maps = studyExamQuestionAnswerVos1.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswerVo::getQuestionId));
                    for (StudyExamPaperHisQuestionVo studyExamQuestionVo : studyExamPaperQuestionVos) {
                        List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos2 = maps.get(studyExamQuestionVo.getId());
                        studyExamQuestionVo.setParse(questionMap.get(studyExamQuestionVo.getQuestionId()).get(0).getParse());
                        studyExamQuestionVo.setAnswers(studyExamQuestionAnswerVos2);
                    }
                }
                studyExamPaperHisVo.setStudyExamPaperApplyQuestionList(studyExamPaperQuestionVos);

            } else {
                LambdaQueryWrapper<StudyExamPaperHisDatabase> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(StudyExamPaperHisDatabase::getPaperId,id);
                List<StudyExamPaperHisDatabase> studyExamPaperDatabases = studyExamPaperHisDatabaseMapper.selectList(queryWrapper);
                List<StudyExamPaperHisDatabaseVo> studyExamPaperDatabaseVos = DozerUtils.mapList(dozerMapper, studyExamPaperDatabases, StudyExamPaperHisDatabaseVo.class);
                studyExamPaperHisVo.setStudyExamPaperApplyDatabaseList(studyExamPaperDatabaseVos);
            }
        }
        return studyExamPaperHisVo;
    }

    @Override
    public List<StudyExamPaperHisVo> hisListForPaper(Long paperId) {
        //根据创建时间倒叙查询所有历史
        LambdaQueryWrapper<StudyExamPaperHis> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamPaperHis::getLinkId,paperId);
        queryWrapper.orderByDesc(StudyExamPaperHis::getCreateTime);
        List<StudyExamPaperHis> studyExamPaperHis = studyExamPaperHisMapper.selectList(queryWrapper);
        return DozerUtils.mapList(dozerMapper, studyExamPaperHis, StudyExamPaperHisVo.class);
    }

}
