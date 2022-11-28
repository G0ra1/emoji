package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.biz.study.constants.PaperCodeEnum;
import com.netwisd.biz.study.constants.QuestionCode;
import com.netwisd.biz.study.constants.StudyStatus;
import com.netwisd.biz.study.constants.YesNo;
import com.netwisd.biz.study.dto.*;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.fegin.WfClient;
import com.netwisd.biz.study.mapper.*;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @Description 试卷调整申请 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@Service
@Slf4j
public class StudyExamPaperAdjServiceImpl extends BatchServiceImpl<StudyExamPaperAdjMapper, StudyExamPaperAdj> implements StudyExamPaperAdjService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyExamPaperAdjMapper studyExamPaperAdjMapper;

    @Autowired
    private StudyExamPaperAdjQuestionService studyExamPaperAdjQuestionService;

    @Autowired
    private StudyExamPaperAdjDatabaseService studyExamPaperAdjDatabaseService;

    @Autowired
    private StudyExamPaperService studyExamPaperService;

    @Autowired
    private StudyExamDatabaseService studyExamDatabaseService;

    @Autowired
    private StudyExamQuestionMapper studyExamQuestionMapper;

    @Autowired
    private StudyExamPaperAdjQuestionMapper studyExamPaperAdjQuestionMapper;

    @Autowired
    private StudyExamPaperAdjDatabaseMapper studyExamPaperAdjDatabaseMapper;

    @Autowired
    private StudyExamPaperQuestionService studyExamPaperQuestionService;

    @Autowired
    private StudyExamPaperHisService studyExamPaperHisService;

    @Autowired
    private StudyExamPaperHisQuestionService studyExamPaperHisQuestionService;

    @Autowired
    private StudyExamPaperHisDatabaseService studyExamPaperHisDatabaseService;

    @Autowired
    private StudyExamPaperQuestionMapper studyExamPaperQuestionMapper;

    @Autowired
    private StudyExamPaperDatabaseService studyExamPaperDatabaseService;

    @Autowired
    private StudyExamPaperDatabaseMapper studyExamPaperDatabaseMapper;

    @Autowired
    private StudyExamQuestionService studyExamQuestionService;

    @Autowired
    private StudyExamQuestionAnswerMapper studyExamQuestionAnswerMapper;

    @Autowired
    private WfClient wfClient;
    /**
    * 单表简单查询操作
    * @param studyExamPaperAdjDto
    * @return
    */
    @Override
    public Page list(StudyExamPaperAdjDto studyExamPaperAdjDto) {
        LambdaQueryWrapper<StudyExamPaperAdj> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<StudyExamPaperAdj> page = studyExamPaperAdjMapper.selectPage(studyExamPaperAdjDto.getPage(),queryWrapper);
        Page<StudyExamPaperAdjVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyExamPaperAdjVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 查看调整试卷详情
    * @param id
    * @return
    */
    @Override
    public StudyExamPaperAdjVo get(Long id) {
        StudyExamPaperAdj studyExamPaperAdj = super.getById(id);
        StudyExamPaperAdjVo studyExamPaperAdjVo = new StudyExamPaperAdjVo();
        if (studyExamPaperAdj != null) {
            studyExamPaperAdjVo = dozerMapper.map(studyExamPaperAdj, StudyExamPaperAdjVo.class);
            //查看固定试卷
            if (studyExamPaperAdj.getPaperCode().equals(PaperCodeEnum.GDSJ.getCode())) {
                LambdaQueryWrapper<StudyExamPaperAdjQuestion> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(StudyExamPaperAdjQuestion::getPaperId, id);
                queryWrapper.orderByAsc(StudyExamPaperAdjQuestion::getSortNumber);
                List<StudyExamPaperAdjQuestion> studyPaperAdjQuestionList = studyExamPaperAdjQuestionMapper.selectList(queryWrapper);
                List<StudyExamPaperAdjQuestionVo> studyExamPaperQuestionVos = DozerUtils.mapList(dozerMapper, studyPaperAdjQuestionList, StudyExamPaperAdjQuestionVo.class);
                if (CollectionUtil.isNotEmpty(studyPaperAdjQuestionList)) {
                    List<Long> idList = studyPaperAdjQuestionList.stream().map(StudyExamPaperAdjQuestion::getQuestionId).collect(Collectors.toList());
                    //所有问题
                    List<StudyExamQuestion> studyExamQuestions = studyExamQuestionService.listByIds(idList);
                    Map<Long, List<StudyExamQuestion>> questionMap = studyExamQuestions.stream().collect(Collectors.groupingBy(StudyExamQuestion::getId));
                    List<StudyExamQuestion> shortAnswer = studyExamQuestions.stream().filter(a -> a.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)).collect(Collectors.toList());
                    if (CollectionUtil.isNotEmpty(shortAnswer)){
                        studyExamPaperAdjVo.setIsHaveShortAnswer(YesNo.YES.code);
                    }else {
                        studyExamPaperAdjVo.setIsHaveShortAnswer(YesNo.NO.code);
                    }
                    List<Long> questionIds = studyExamQuestions.stream().map(StudyExamQuestion::getId).collect(Collectors.toList());
                    LambdaQueryWrapper<StudyExamQuestionAnswer> queryWrapper2 = new LambdaQueryWrapper<>();
                    queryWrapper2.in(StudyExamQuestionAnswer::getQuestionId, questionIds);
                    //所有答案
                    List<StudyExamQuestionAnswer> studyExamQuestionAnswers1 = studyExamQuestionAnswerMapper.selectList(queryWrapper2);
                    List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos1 = DozerUtils.mapList(dozerMapper, studyExamQuestionAnswers1, StudyExamQuestionAnswerVo.class);
                    Map<Long, List<StudyExamQuestionAnswerVo>> maps = studyExamQuestionAnswerVos1.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswerVo::getQuestionId));
                    for (StudyExamPaperAdjQuestionVo studyExamQuestionVo : studyExamPaperQuestionVos) {
                        List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos2 = maps.get(studyExamQuestionVo.getId());
                        //设置题目解析
                        studyExamQuestionVo.setParse(questionMap.get(studyExamQuestionVo.getQuestionId()).get(0).getParse());
                        //设置题目答案
                        studyExamQuestionVo.setAnswers(studyExamQuestionAnswerVos2);
                    }
                }
                studyExamPaperAdjVo.setStudyExamPaperApplyQuestionList(studyExamPaperQuestionVos);

            } else {
                LambdaQueryWrapper<StudyExamPaperAdjDatabase> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(StudyExamPaperAdjDatabase::getPaperId,id);
                List<StudyExamPaperAdjDatabase> studyExamPaperDatabases = studyExamPaperAdjDatabaseMapper.selectList(queryWrapper);
                List<StudyExamPaperAdjDatabaseVo> studyExamPaperDatabaseVos = DozerUtils.mapList(dozerMapper, studyExamPaperDatabases, StudyExamPaperAdjDatabaseVo.class);
                studyExamPaperAdjVo.setStudyExamPaperApplyDatabaseList(studyExamPaperDatabaseVos);
            }
        }
        return studyExamPaperAdjVo;
    }

    /**
     * 查看一张试卷的多次调整记录
     * @param paperId
     * @return
     */
    @Override
    public List<StudyExamPaperAdjVo> adjListForPaper(Long paperId) {
        //按调整发起时间倒叙查询所有
        LambdaQueryWrapper<StudyExamPaperAdj> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamPaperAdj::getLinkId,paperId);
        queryWrapper.orderByDesc(StudyExamPaperAdj::getCreateTime);
        List<StudyExamPaperAdj> studyExamPaperAdjList = studyExamPaperAdjMapper.selectList(queryWrapper);
        return DozerUtils.mapList(dozerMapper, studyExamPaperAdjList, StudyExamPaperAdjVo.class);
    }

    /**
     * 检验新增修改数据
     * @param studyExamPaperAdjDto
     */
    private void checkData(StudyExamPaperAdjDto studyExamPaperAdjDto){
        if (com.netwisd.base.common.util.StringUtils.isBlank(studyExamPaperAdjDto.getTypeName())) {
            throw new IncloudException("请选择试卷标签");
        }
        if (com.netwisd.base.common.util.StringUtils.isBlank(studyExamPaperAdjDto.getPaperName())) {
            throw new IncloudException("请填写试卷名称");
        }
        if (com.netwisd.base.common.util.StringUtils.isBlank(studyExamPaperAdjDto.getPaperType())) {
            throw new IncloudException("请选择试卷类型");
        }
        if (ObjectUtils.isEmpty(studyExamPaperAdjDto.getPaperCode())) {
            throw new IncloudException("请选择出题类型");
        }
        if (ObjectUtils.isEmpty(studyExamPaperAdjDto.getSpecialExamTime())) {
            throw new IncloudException("请填写试卷时长");
        }
        //1是考试试卷,可能有0,1，表示既是考试也是练习,所以用contains
        if (studyExamPaperAdjDto.getPaperType().contains("1")) {
            if (ObjectUtils.isEmpty(studyExamPaperAdjDto.getSpecialExamQualifiedScore())) {
                throw new IncloudException("请填写合格分数");
            }
        }
    }
    /**
     * 流程调整
     * @param studyExamPaperAdjDto
     * @return
     */
    @Override
    @Transactional
    public StudyExamPaperAdjVo procSaveOrUpdate (StudyExamPaperAdjDto studyExamPaperAdjDto){
        this.checkData(studyExamPaperAdjDto);
        StudyExamPaperAdj studyExamPaperAdj = dozerMapper.map(studyExamPaperAdjDto, StudyExamPaperAdj.class);

        //试卷类型(固定或随机)
        Integer paperCode = studyExamPaperAdjDto.getPaperCode();
        //固定试卷集合
        List<StudyExamPaperAdjQuestionDto> studyExamPaperQuestionDtoList = studyExamPaperAdjDto.getStudyExamPaperApplyQuestionList();
        //随机试卷集合
        List<StudyExamPaperAdjDatabaseDto> studyExamPaperDatabaseDtoList = studyExamPaperAdjDto.getStudyExamPaperApplyDatabaseList();
        //调整固定试卷
        if (paperCode.equals(PaperCodeEnum.GDSJ.code)) {
            if (CollectionUtil.isNotEmpty(studyExamPaperQuestionDtoList)) {
                //单选题分值
                BigDecimal singleScore = studyExamPaperAdjDto.getSingleScore();
                //多选题分值
                BigDecimal multipleScore = studyExamPaperAdjDto.getMultipleScore();
                //填空题分值
                BigDecimal completionScore = studyExamPaperAdjDto.getCompletionScore();
                //判断题分值
                BigDecimal judgmentScore = studyExamPaperAdjDto.getJudgmentScore();
                //简答题分值
                BigDecimal shortAnswerScore = studyExamPaperAdjDto.getShortAnswerScore();
                List<StudyExamPaperAdjQuestionDto> shortAnswerLists = studyExamPaperQuestionDtoList.stream().filter(shortAnswer -> shortAnswer.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)).collect(Collectors.toList());
                if (CollectionUtil.isEmpty(shortAnswerLists)) {
                    studyExamPaperAdjDto.setIsHaveShortAnswer(YesNo.NO.code);
                } else {
                    studyExamPaperAdjDto.setIsHaveShortAnswer(YesNo.YES.code);
                }
                //给题目没有具体分值的题目设置分数
                BigDecimal b  = new BigDecimal(100);
                for (StudyExamPaperAdjQuestionDto studyExamPaperQuestionDto : studyExamPaperQuestionDtoList) {
                    if (studyExamPaperQuestionDto.getQuestionScore() == null || studyExamPaperQuestionDto.getQuestionScore().compareTo(BigDecimal.ZERO) == 0) {
                        if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code)) {
                            //如果题目分值不为空并且大于0小于100,根据题目类型设置题目分数
                            if (null != singleScore) {
                                if (singleScore.compareTo(BigDecimal.ZERO) > 0 && singleScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(singleScore);
                                }else {
                                    if (singleScore.compareTo(b)>-1) {
                                        throw new IncloudException("单选题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写单选题分值");
                            }
                        }
                        if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code)) {
                            if (null != multipleScore) {
                                if (multipleScore.compareTo(BigDecimal.ZERO) > 0 && multipleScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(multipleScore);
                                }else {
                                    if (multipleScore.compareTo(b)>-1) {
                                        throw new IncloudException("多选题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写多选题分值");
                            }
                        }
                        if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.COMPLETION.code)) {
                            if (null != completionScore) {
                                if (completionScore.compareTo(BigDecimal.ZERO) > 0 && completionScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(completionScore);
                                }else {
                                    if (completionScore.compareTo(b)>-1) {
                                        throw new IncloudException("填空题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写填空题分值");
                            }
                        }
                        if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.JUDGMENT.code)) {
                            if (null != judgmentScore) {
                                if (judgmentScore.compareTo(BigDecimal.ZERO) > 0 && judgmentScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(judgmentScore);
                                }else {
                                    if (judgmentScore.compareTo(b)>-1) {
                                        throw new IncloudException("判断题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写判断题分值");
                            }
                        }
                        if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)) {
                            if (null != shortAnswerScore) {
                                if (shortAnswerScore.compareTo(BigDecimal.ZERO) > 0 && shortAnswerScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(shortAnswerScore);
                                }else {
                                    if (shortAnswerScore.compareTo(b)>-1) {
                                        throw new IncloudException("简答题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写简答题分值");
                            }
                        }
                    }else {
                        BigDecimal questionScore = studyExamPaperQuestionDto.getQuestionScore();
                        BigDecimal score = new BigDecimal(100);
                        if (questionScore.compareTo(score)>-1){
                            throw new IncloudException("单题分值过大");
                        }
                    }
                    studyExamPaperQuestionDto.setIdSign(true);
                    studyExamPaperQuestionDto.setPaperId(studyExamPaperAdjDto.getId());
                }
                BigDecimal paperTotalScore = studyExamPaperQuestionDtoList.stream().map(StudyExamPaperAdjQuestionDto::getQuestionScore).reduce(BigDecimal.ZERO, BigDecimal::add);
                studyExamPaperAdj.setPaperTotalScore(paperTotalScore);
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
                studyExamPaperAdj.setCreateUserName(loginAppUser.getUserNameCh());
                super.saveOrUpdate(studyExamPaperAdj);
                List<StudyExamPaperAdjQuestion> studyExamPaperQuestions = DozerUtils.mapList(dozerMapper, studyExamPaperQuestionDtoList, StudyExamPaperAdjQuestion.class);
                List<Long> idList = studyExamPaperQuestions.stream().map(StudyExamPaperAdjQuestion::getQuestionId).collect(Collectors.toList());
                List<StudyExamQuestion> studyExamQuestions = studyExamQuestionService.listByIds(idList);
                //设置题目为引用状态
                studyExamQuestions.forEach(question -> question.setIsQuote(YesNo.YES.code));
                studyExamQuestionService.updateBatchById(studyExamQuestions);
                studyExamPaperAdjQuestionService.saveOrUpdateBatch(studyExamPaperQuestions);
            }
        }
        //调整随机试卷
        if (paperCode.equals(PaperCodeEnum.SJSJ.code)) {
            if (CollectionUtil.isNotEmpty(studyExamPaperDatabaseDtoList)) {
                List<Long> idList = studyExamPaperDatabaseDtoList.stream().map(StudyExamPaperAdjDatabaseDto::getDatabaseId).collect(Collectors.toList());
                String idLists = StringUtils.join(",", idList);
                List<StudyExamDatabaseVo> studyExamDatabases = studyExamDatabaseService.listByIds(idLists);
                for (StudyExamDatabaseVo studyExamDatabase : studyExamDatabases) {
                    if (CollectionUtil.isEmpty(studyExamDatabase.getStudyExamQuestionDefList())) {
                        throw new IncloudException("选择的" + studyExamDatabase.getDatabaseName() + "题库中没有题目,请重新选择");
                    }
                }
                LambdaQueryWrapper<StudyExamQuestion> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(StudyExamQuestion::getDatabaseId, idList);
                List<StudyExamQuestion> questions = studyExamQuestionMapper.selectList(queryWrapper);
                //随机试卷所选题库里的所有题目中所有单选题
                List<StudyExamQuestion> singleChoiceList = questions.stream().filter(a -> a.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code)).collect(Collectors.toList());
                //随机试卷所选题库里的所有题目中所有多选题
                List<StudyExamQuestion> multipleChoiceList = questions.stream().filter(a -> a.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code)).collect(Collectors.toList());
                //随机试卷所选题库里的所有题目中所有填空题
                List<StudyExamQuestion> completion = questions.stream().filter(a -> a.getQuestionCode().equals(QuestionCode.COMPLETION.code)).collect(Collectors.toList());
                //随机试卷所选题库里的所有题目中所有判断题
                List<StudyExamQuestion> judgment = questions.stream().filter(a -> a.getQuestionCode().equals(QuestionCode.JUDGMENT.code)).collect(Collectors.toList());
                //随机试卷所选题库里的所有题目中所有简答题
                List<StudyExamQuestion> shortAnswer = questions.stream().filter(a -> a.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)).collect(Collectors.toList());
                //单选题
                BigDecimal b = new BigDecimal(100);
                BigDecimal singleScore = studyExamPaperAdjDto.getSingleScore();
                Integer singleNumber = studyExamPaperAdjDto.getSingleNumber();

                if (singleNumber != null) {
                    if (singleNumber > singleChoiceList.size()) {
                        throw new IncloudException("所选题库中单选题实际数量不足,请修改单选题数量或添加单选题到所选题库");
                    }
                    if (singleScore==null){
                        throw new IncloudException("请选择单选题分值");
                    }else {
                        if (singleScore.compareTo(b)>-1){
                            throw new IncloudException("单选题分值过大");
                        }
                    }
                }else {
                    if (singleScore != null){
                        throw new IncloudException("请选择单选题数量");
                    }
                }

                //多选题
                BigDecimal multipleScore = studyExamPaperAdjDto.getMultipleScore();
                Integer multipleNumber = studyExamPaperAdjDto.getMultipleNumber();
                if (multipleNumber != null) {
                    if (multipleNumber > multipleChoiceList.size()) {
                        throw new IncloudException("所选题库中多选题实际数量不足,请修改多选题数量或添加多选题到所选题库");
                    }
                    if (multipleScore==null){
                        throw new IncloudException("请选择多选题分值");
                    }else {
                        if (multipleScore.compareTo(b)>-1){
                            throw new IncloudException("多选题分值过大");
                        }
                    }
                }else {
                    if (multipleScore != null){
                        throw new IncloudException("请选择多选题数量");
                    }
                }
                BigDecimal completionScore = studyExamPaperAdjDto.getCompletionScore();
                Integer completionNumber = studyExamPaperAdjDto.getCompletionNumber();
                if (completionNumber != null) {
                    if (completionNumber > completion.size()) {
                        throw new IncloudException("所选题库中填空题实际数量不足,请修改填空题数量或添加填空题到所选题库");
                    }
                    if (completionScore==null){
                        throw new IncloudException("请选择填空题分值");
                    }else {
                        if (completionScore.compareTo(b)>-1){
                            throw new IncloudException("填空题分值过大");
                        }
                    }
                }else {
                    if (completionScore != null){
                        throw new IncloudException("请选择填空题数量");
                    }
                }
                BigDecimal judgmentScore = studyExamPaperAdjDto.getJudgmentScore();
                Integer judgmentNumber = studyExamPaperAdjDto.getJudgmentNumber();
                if (judgmentNumber != null) {
                    if (judgmentNumber > judgment.size()) {
                        throw new IncloudException("所选题库中判断题实际数量不足,请修改判断题数量或添加判断题到所选题库");
                    }
                    if (judgmentScore==null){
                        throw new IncloudException("请选择判断题分值");
                    }else {
                        if (judgmentScore.compareTo(b)>-1){
                            throw new IncloudException("判断题分值过大");
                        }
                    }
                }else {
                    if (judgmentScore != null){
                        throw new IncloudException("请选择判断题数量");
                    }
                }
                BigDecimal shortAnswerScore = studyExamPaperAdjDto.getShortAnswerScore();
                Integer shortAnswerNumber = studyExamPaperAdjDto.getShortAnswerNumber();
                if (shortAnswerNumber==null && shortAnswerScore==null){
                    studyExamPaperAdj.setIsHaveShortAnswer(YesNo.NO.code);
                }
                if (shortAnswerNumber != null) {
                    if (shortAnswerNumber > shortAnswer.size()) {
                        throw new IncloudException("所选题库中简答题实际数量不足,请修改简答题数量或添加简答题到所选题库");
                    }
                    if (shortAnswerScore == null) {
                        throw new IncloudException("请选择简答题分值");
                    } else {
                        if (shortAnswerScore.compareTo(b) > -1) {
                            throw new IncloudException("简答题分值过大");
                        }
                    }
                    BigDecimal score = new BigDecimal(1);
                    if (shortAnswerNumber>=1 && shortAnswerScore.compareTo(score) > -1){
                        studyExamPaperAdj.setIsHaveShortAnswer(YesNo.YES.code);
                    }else {
                        studyExamPaperAdj.setIsHaveShortAnswer(YesNo.NO.code);
                    }
                } else {
                    if (shortAnswerScore != null) {
                        throw new IncloudException("请选择简答题数量");
                    }
                }
                BigDecimal single = BigDecimal.ZERO;
                if (singleNumber != null) {
                    single = singleScore.multiply(BigDecimal.valueOf(singleNumber));
                }
                BigDecimal mul = BigDecimal.ZERO;
                if (multipleNumber != null) {
                    mul = multipleScore.multiply(BigDecimal.valueOf(multipleNumber));
                }
                BigDecimal com = BigDecimal.ZERO;
                if (completionNumber != null) {
                    com = completionScore.multiply(BigDecimal.valueOf(completionNumber));
                }
                BigDecimal judg = BigDecimal.ZERO;
                if (judgmentNumber != null) {
                    judg = judgmentScore.multiply(BigDecimal.valueOf(judgmentNumber));
                }
                BigDecimal shortAnswers = BigDecimal.ZERO;
                if (shortAnswerNumber != null) {
                    shortAnswers = shortAnswerScore.multiply(BigDecimal.valueOf(shortAnswerNumber));
                }
                for (StudyExamPaperAdjDatabaseDto studyExamPaperDatabaseDto : studyExamPaperDatabaseDtoList) {
                    studyExamPaperDatabaseDto.setPaperId(studyExamPaperAdjDto.getId());
                    studyExamPaperDatabaseDto.setIdSign(true);
                }
                //随机试卷总分
                BigDecimal totalScore = single.add(mul).add(com).add(judg).add(shortAnswers);
                studyExamPaperAdj.setPaperTotalScore(totalScore);
                super.saveOrUpdate(studyExamPaperAdj);
                List<StudyExamPaperAdjDatabase> studyExamPaperDatabases = DozerUtils.mapList(dozerMapper, studyExamPaperDatabaseDtoList, StudyExamPaperAdjDatabase.class);
                studyExamPaperAdjDatabaseService.saveOrUpdateBatch(studyExamPaperDatabases);
            }
        }
        return get(studyExamPaperAdj.getId());
    }
    /**
     * 流程调整申请提交,赋值提交时间,更改结果表数据为调整中
     * @Param procInstId
     */
    @Override
    @Transactional
    public Boolean procAfterSubmit(String processInstanceId) {
        LambdaUpdateWrapper<StudyExamPaperAdj> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(StudyExamPaperAdj::getAuditSubmitTime,LocalDateTime.now());
        wrapper.eq(StudyExamPaperAdj::getCamundaProcinsId,processInstanceId);
        super.update(wrapper);
        LambdaQueryWrapper<StudyExamPaperAdj> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamPaperAdj::getCamundaProcinsId,processInstanceId);
        StudyExamPaperAdj studyExamPaperAdj = studyExamPaperAdjMapper.selectOne(queryWrapper);
        LambdaUpdateWrapper<StudyExamPaper> wrapper1 = new LambdaUpdateWrapper<>();
        wrapper1.set(StudyExamPaper::getStatus,StudyStatus.IN_UPDATE.code);
        wrapper1.eq(StudyExamPaper::getId,studyExamPaperAdj.getLinkId());
        studyExamPaperService.update(wrapper1);
        return true;
    }
    /**
     * 流程调整申请通过
     * @Param procInstId
     */
    @Override
    @Transactional
    public Boolean procAuditSuccess(String procInstId) {
        LambdaQueryWrapper<StudyExamPaperAdj> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamPaperAdj::getCamundaProcinsId, procInstId);
        StudyExamPaperAdj studyExamPaperAdj = studyExamPaperAdjMapper.selectOne(queryWrapper);
        studyExamPaperAdj.setAuditSuccessTime(LocalDateTime.now());
        super.updateById(studyExamPaperAdj);
        Long resultId = studyExamPaperAdj.getLinkId();
        StudyExamPaper studyExamPaper = studyExamPaperService.getById(resultId);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        StudyExamPaperHis studyExamPaperHis = dozerMapper.map(studyExamPaper, StudyExamPaperHis.class);
        studyExamPaperHis.setId(IdGenerator.getIdGenerator());
        studyExamPaperHis.setLinkId(resultId);
        studyExamPaperHis.setCreateTime(LocalDateTime.now());
        studyExamPaperHis.setUpdateTime(LocalDateTime.now());
        studyExamPaperHis.setCreateUserId(loginAppUser.getId());
        studyExamPaperHis.setCreateUserName(loginAppUser.getUserNameCh());
        studyExamPaperHis.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
        studyExamPaperHis.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
        studyExamPaperHis.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
        studyExamPaperHis.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
        studyExamPaperHis.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
        //结果数据插入历史表
        studyExamPaperHisService.save(studyExamPaperHis);
        //更新调整表数据到结果表
        StudyExamPaper studyPaper = dozerMapper.map(studyExamPaperAdj, StudyExamPaper.class);
        studyPaper.setId(resultId);
        //流程通过,试卷状态从调整中转为已生效
        studyPaper.setStatus(StudyStatus.TAKE_EFFECT.code);
        studyPaper.setIsEnable(YesNo.YES.code);
        studyPaper.setUpdateTime(LocalDateTime.now());
        if (studyExamPaperAdj.getSingleNumber() == null && studyExamPaperAdj.getSingleScore() == null) {
            studyPaper.setSingleNumber(0);
            studyPaper.setSingleScore(BigDecimal.ZERO);
        }
        if (studyExamPaperAdj.getMultipleNumber() == null && studyExamPaperAdj.getMultipleScore() == null) {
            studyPaper.setMultipleNumber(0);
            studyPaper.setMultipleScore(BigDecimal.ZERO);
        }
        if (studyExamPaperAdj.getCompletionNumber() == null && studyExamPaperAdj.getCompletionScore() == null) {
            studyPaper.setCompletionNumber(0);
            studyPaper.setCompletionScore(BigDecimal.ZERO);
        }
        if (studyExamPaperAdj.getJudgmentNumber() == null && studyExamPaperAdj.getJudgmentScore() == null) {
            studyPaper.setJudgmentNumber(0);
            studyPaper.setJudgmentScore(BigDecimal.ZERO);
        }
        if (studyExamPaperAdj.getShortAnswerNumber() == null && studyExamPaperAdj.getShortAnswerScore() == null) {
            studyPaper.setShortAnswerNumber(0);
            studyPaper.setShortAnswerScore(BigDecimal.ZERO);
        }
        studyExamPaperService.updateById(studyPaper);
        if (studyExamPaperAdj.getPaperCode().equals(PaperCodeEnum.GDSJ.code)) {
            List<StudyExamPaperAdjQuestion> questionAdj = studyExamPaperAdjQuestionMapper.selectList(Wrappers.<StudyExamPaperAdjQuestion>lambdaQuery().eq(StudyExamPaperAdjQuestion::getPaperId, studyExamPaperAdj.getId()));
            List<StudyExamPaperQuestion> questionResult = studyExamPaperQuestionMapper.selectList(Wrappers.<StudyExamPaperQuestion>lambdaQuery().eq(StudyExamPaperQuestion::getPaperId, studyExamPaper.getId()));
            LambdaUpdateWrapper<StudyExamQuestion> updateWrapper = new LambdaUpdateWrapper<>();
            if (questionAdj.size() < questionResult.size()) {
                Map<Long, List<StudyExamPaperAdjQuestion>> map = questionAdj.stream().collect(Collectors.groupingBy(StudyExamPaperAdjQuestion::getQuestionId));
                for (StudyExamPaperQuestion question : questionResult) {
                    List<StudyExamPaperAdjQuestion> questions = map.get(question.getQuestionId());
                    if (CollectionUtil.isEmpty(questions)) {
                        List<StudyExamPaperQuestion> questionList = studyExamPaperQuestionMapper.selectList(Wrappers.<StudyExamPaperQuestion>lambdaQuery().eq(StudyExamPaperQuestion::getQuestionId, question.getQuestionId()));
                        if (questionList.size() == 1) {
                            updateWrapper.set(StudyExamQuestion::getIsQuote, YesNo.NO.code).eq(StudyExamQuestion::getId, question.getQuestionId());
                            studyExamQuestionService.update(updateWrapper);
                        }
                    }
                }
            }
            List<StudyExamPaperQuestion> studyExamPaperQuestions = studyExamPaperQuestionService.list(Wrappers.<StudyExamPaperQuestion>lambdaQuery()
                    .eq(ObjectUtils.allNotNull(resultId), StudyExamPaperQuestion::getPaperId, resultId));
            List<StudyExamPaperHisQuestion> studyExamPaperHisQuestions = DozerUtils.mapList(dozerMapper, studyExamPaperQuestions, StudyExamPaperHisQuestion.class);
            studyExamPaperHisQuestions.forEach(studyExamPaperHisQuestion -> {
                studyExamPaperHisQuestion.setId(IdGenerator.getIdGenerator());
                studyExamPaperHisQuestion.setPaperId(studyExamPaperHis.id);
                studyExamPaperHisQuestion.setCreateTime(LocalDateTime.now());
                studyExamPaperHisQuestion.setUpdateTime(LocalDateTime.now());
                studyExamPaperHisQuestion.setCreateUserId(loginAppUser.getId());
                studyExamPaperHisQuestion.setCreateUserName(loginAppUser.getUserName());
                studyExamPaperHisQuestion.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
                studyExamPaperHisQuestion.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
                studyExamPaperHisQuestion.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
                studyExamPaperHisQuestion.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
                studyExamPaperHisQuestion.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
            });
            studyExamPaperHisQuestionService.saveBatch(studyExamPaperHisQuestions);
            //删除结果表数据
            int studyPaperQuestionDelete = studyExamPaperQuestionMapper.delete(Wrappers.<StudyExamPaperQuestion>lambdaQuery().eq(StudyExamPaperQuestion::getPaperId, resultId));
            if (studyPaperQuestionDelete > 0) {
                log.info("删除结果子表数据成功");
            }
            LambdaQueryWrapper<StudyExamPaperAdjQuestion> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(StudyExamPaperAdjQuestion::getPaperId, studyExamPaperAdj.getId());
            List<StudyExamPaperAdjQuestion> studyExamPaperAdjQuestions = studyExamPaperAdjQuestionMapper.selectList(wrapper);
            List<StudyExamPaperQuestion> studyExamPaperQuestionList = DozerUtils.mapList(dozerMapper, studyExamPaperAdjQuestions, StudyExamPaperQuestion.class);
            studyExamPaperQuestionList.forEach(studyExamPaperQuestion -> {
                studyExamPaperQuestion.setId(IdGenerator.getIdGenerator());
                studyExamPaperQuestion.setPaperId(studyPaper.id);
                studyExamPaperQuestion.setCreateTime(LocalDateTime.now());
                studyExamPaperQuestion.setUpdateTime(LocalDateTime.now());
                studyExamPaperQuestion.setCreateUserId(loginAppUser.getId());
                studyExamPaperQuestion.setCreateUserName(loginAppUser.getUserName());
                studyExamPaperQuestion.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
                studyExamPaperQuestion.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
                studyExamPaperQuestion.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
                studyExamPaperQuestion.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
                studyExamPaperQuestion.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
            });
            studyExamPaperQuestionService.saveBatch(studyExamPaperQuestionList);
        } else if (studyExamPaperAdj.getPaperCode().equals(PaperCodeEnum.SJSJ.code)) {
            List<StudyExamPaperDatabase> studyExamPaperDatabase = studyExamPaperDatabaseService.list(Wrappers.<StudyExamPaperDatabase>lambdaQuery()
                    .eq(ObjectUtils.allNotNull(resultId), StudyExamPaperDatabase::getPaperId, resultId));
            List<StudyExamPaperHisDatabase> studyExamPaperHisDatabase = DozerUtils.mapList(dozerMapper, studyExamPaperDatabase, StudyExamPaperHisDatabase.class);
            studyExamPaperHisDatabase.forEach(studyPaperHisDatabase -> {
                studyPaperHisDatabase.setId(IdGenerator.getIdGenerator());
                studyPaperHisDatabase.setPaperId(studyExamPaperHis.id);
                studyPaperHisDatabase.setCreateTime(LocalDateTime.now());
                studyPaperHisDatabase.setUpdateTime(LocalDateTime.now());
                studyPaperHisDatabase.setCreateUserId(loginAppUser.getId());
                studyPaperHisDatabase.setCreateUserName(loginAppUser.getUserName());
                studyPaperHisDatabase.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
                studyPaperHisDatabase.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
                studyPaperHisDatabase.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
                studyPaperHisDatabase.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
                studyPaperHisDatabase.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
            });
            //结果数据插入历史表
            studyExamPaperHisDatabaseService.saveBatch(studyExamPaperHisDatabase);
            //删除结果表数据
            int studyPaperDatabaseDelete = studyExamPaperDatabaseMapper.delete(Wrappers.<StudyExamPaperDatabase>lambdaQuery().eq(StudyExamPaperDatabase::getPaperId, resultId));
            if (studyPaperDatabaseDelete > 0) {
                log.info("删除结果子表数据成功");
            }
            LambdaQueryWrapper<StudyExamPaperAdjDatabase> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(StudyExamPaperAdjDatabase::getPaperId, studyExamPaperAdj.getId());
            List<StudyExamPaperAdjDatabase> studyExamPaperAdjDatabase = studyExamPaperAdjDatabaseMapper.selectList(wrapper);
            List<StudyExamPaperDatabase> studyExamPaperDatabaseList = DozerUtils.mapList(dozerMapper, studyExamPaperAdjDatabase, StudyExamPaperDatabase.class);
            studyExamPaperDatabaseList.forEach(studyPaperDatabase -> {
                studyPaperDatabase.setId(IdGenerator.getIdGenerator());
                studyPaperDatabase.setPaperId(studyPaper.id);
                studyPaperDatabase.setCreateTime(LocalDateTime.now());
                studyPaperDatabase.setUpdateTime(LocalDateTime.now());
                studyPaperDatabase.setCreateUserId(loginAppUser.getId());
                studyPaperDatabase.setCreateUserName(loginAppUser.getUserName());
                studyPaperDatabase.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
                studyPaperDatabase.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
                studyPaperDatabase.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
                studyPaperDatabase.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
                studyPaperDatabase.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
            });
            studyExamPaperDatabaseService.saveBatch(studyExamPaperDatabaseList);
        }
        return true;
    }

    /**
     * 调整流程查看详情
     * @Param procInstId
     */
    @Override
    public StudyExamPaperAdjVo procDetail(String procInstId){
        LambdaQueryWrapper<StudyExamPaperAdj> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamPaperAdj::getCamundaProcinsId,procInstId);
        StudyExamPaperAdj studyExamPaperAdj = studyExamPaperAdjMapper.selectOne(queryWrapper);
        StudyExamPaperAdjVo studyExamPaperAdjVo = dozerMapper.map(studyExamPaperAdj, StudyExamPaperAdjVo.class);
        //固定试卷查看题目
        if (studyExamPaperAdjVo.getPaperCode().equals(PaperCodeEnum.GDSJ.code)) {
            LambdaQueryWrapper<StudyExamPaperAdjQuestion> queryWrapperOne = new LambdaQueryWrapper<>();
            queryWrapperOne.eq(StudyExamPaperAdjQuestion::getPaperId, studyExamPaperAdjVo.getId());
            List<StudyExamPaperAdjQuestion> studyPaperQuestions = studyExamPaperAdjQuestionMapper.selectList(queryWrapperOne);
            List<StudyExamPaperAdjQuestionVo> studyExamPaperApplyQuestionVos = DozerUtils.mapList(dozerMapper, studyPaperQuestions, StudyExamPaperAdjQuestionVo.class);
            studyExamPaperAdjVo.setStudyExamPaperApplyQuestionList(studyExamPaperApplyQuestionVos);
            //随机试卷查看规则
        } else if (studyExamPaperAdjVo.getPaperCode().equals(PaperCodeEnum.SJSJ.code)) {
            LambdaQueryWrapper<StudyExamPaperAdjDatabase> queryWrapperTwo = new LambdaQueryWrapper<>();
            queryWrapperTwo.eq(StudyExamPaperAdjDatabase::getPaperId, studyExamPaperAdjVo.getId());
            List<StudyExamPaperAdjDatabase> studyExamPaperRules = studyExamPaperAdjDatabaseMapper.selectList(queryWrapperTwo);
            List<StudyExamPaperAdjDatabaseVo> studyExamPaperDatabaseVos = DozerUtils.mapList(dozerMapper, studyExamPaperRules, StudyExamPaperAdjDatabaseVo.class);
            studyExamPaperAdjVo.setStudyExamPaperApplyDatabaseList(studyExamPaperDatabaseVos);
        }
        return studyExamPaperAdjVo;
    }

    /**
     * 通过procInstId获取详情
     * @param procInstId
     * @return
     */
    private StudyExamPaperAdj getByProcInstId(String procInstId){
        LambdaQueryWrapper<StudyExamPaperAdj> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamPaperAdj::getCamundaProcinsId,procInstId);
        return studyExamPaperAdjMapper.selectOne(queryWrapper);
    }

    /**
     * 调整试卷删除
     * @Param paperId
     */
    @Override
    @Transactional
    public Boolean delete(Long paperAdjId){
        log.info("删除调整试卷参数：{}", paperAdjId);
        StudyExamPaperAdj studyExamPaperAdj = super.getById(paperAdjId);
        if (ObjectUtil.isNotNull(studyExamPaperAdj)) {
            if (!studyExamPaperAdj.getAuditStatus().equals(WfProcessEnum.DRAFT.getType())) {
                throw new IncloudException("仅审核状态为草稿可删除！");
            }
            log.info("删除:{" + studyExamPaperAdj.getPaperName() + "}试卷");
            boolean paperDelete = super.removeById(paperAdjId);
            if (paperDelete) {
                if (studyExamPaperAdj.getPaperCode().equals(PaperCodeEnum.GDSJ.code)){
                    studyExamPaperAdjQuestionMapper.delete(Wrappers.<StudyExamPaperAdjQuestion>lambdaQuery().eq(StudyExamPaperAdjQuestion::getPaperId, paperAdjId));
                }else {
                    studyExamPaperAdjDatabaseMapper.delete(Wrappers.<StudyExamPaperAdjDatabase>lambdaQuery().eq(StudyExamPaperAdjDatabase::getPaperId, paperAdjId));
                }
                wfClient.deleteOnlyProcess(new WfEngineDto.StateDto(studyExamPaperAdj.getCamundaProcinsId(),studyExamPaperAdj.getCamundaProcdefId(),null));
            }
        }
        return true;
    }

    /**
    * 流程删除
    * @param procInstId
    * @return
    */
    @Transactional
    @Override
    public Boolean procDelete(String procInstId) {
        StudyExamPaperAdj studyExamPaperAdj = this.getByProcInstId(procInstId);
        studyExamPaperAdjMapper.deleteById(studyExamPaperAdj.getId());
        //删除固定试卷
        if (studyExamPaperAdj.getPaperCode().equals(PaperCodeEnum.GDSJ.code)) {
            LambdaQueryWrapper<StudyExamPaperAdjQuestion> questionWrapper = new LambdaQueryWrapper<>();
            questionWrapper.eq(StudyExamPaperAdjQuestion::getPaperId, studyExamPaperAdj.getId());
            studyExamPaperAdjQuestionService.remove(questionWrapper);
        }
        //删除随机试卷
        if (studyExamPaperAdj.getPaperCode().equals(PaperCodeEnum.SJSJ.code)) {
            LambdaQueryWrapper<StudyExamPaperAdjDatabase> DatabaseWrapper = new LambdaQueryWrapper<>();
            DatabaseWrapper.eq(StudyExamPaperAdjDatabase::getPaperId, studyExamPaperAdj.getId());
            studyExamPaperAdjDatabaseService.remove(DatabaseWrapper);
        }
        return true;
    }


}
