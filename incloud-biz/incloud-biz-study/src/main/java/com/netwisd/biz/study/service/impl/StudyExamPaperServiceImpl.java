package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.user.eunm.EnableStateEnum;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.biz.study.constants.*;
import com.netwisd.biz.study.dto.*;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.fegin.WfClient;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.*;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 云数网讯 sundong@netwisd.com
 * @Description 试卷结果表 功能描述...
 * @date 2022-04-29 17:32:00
 */
@Service
@Slf4j
public class StudyExamPaperServiceImpl extends BatchServiceImpl<StudyExamPaperMapper, StudyExamPaper> implements StudyExamPaperService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyExamPaperMapper studyExamPaperMapper;

    @Autowired
    private StudyExamDatabaseService studyExamDatabaseService;

    @Autowired
    private StudyExamQuestionService studyExamQuestionService;//题目service

    @Autowired
    private StudyExamPaperQuestionMapper studyExamPaperQuestionMapper;

    @Autowired
    private StudyExamPaperDatabaseMapper studyExamPaperDatabaseMapper;

    @Autowired
    private StudyExamPaperDatabaseService studyExamPaperDatabaseService;//试卷题库service

    @Autowired
    private StudyExamQuestionMapper studyExamQuestionMapper;
    @Autowired
    private StudyExamPaperQuestionService studyExamPaperQuestionService;//试卷与题目service

    @Autowired
    private StudyExamQuestionAnswerMapper studyExamQuestionAnswerMapper;

    @Autowired
    private WfClient wfClient;

    @Autowired
    private StudyUserExamMapper studyUserExamMapper;

    @Autowired
    private StudyUserExamQuestionMapper studyUserExamQuestionMapper;

    @Autowired
    private StudyExamQuestionAnswerService studyExamQuestionAnswerService;

    @Autowired
    private StudyUserExamService studyUserExamService;

    @Autowired
    private StudyUserExamQuestionService studyUserExamQuestionService;

    @Autowired
    private StudyUserExamQuestionDetailService studyUserExamQuestionDetailService;

    @Autowired
    private StudyUserExamQuestionDetailMapper questionDetailMapper;

    @Autowired
    private MdmClient mdmClient;

    @Autowired
    private StudyUserExamQuestionDetailService detailService;

    /**
     * 单表简单查询操作
     *
     * @param studyExamPaperDto
     * @return
     */
    @Override
    public Page<StudyExamPaperVo> list(StudyExamPaperDto studyExamPaperDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper<StudyExamPaper> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //如果是讲师,只看到自己申请的
            queryWrapper.eq(StudyExamPaper::getCreateUserId, loginAppUser.getId());
        }
        queryWrapper.eq(studyExamPaperDto.getIsEnable()!=null,StudyExamPaper::getIsEnable,studyExamPaperDto.getIsEnable());
        queryWrapper.orderByDesc(StudyExamPaper::getCreateTime);
        Page<StudyExamPaper> page = studyExamPaperMapper.selectPage(studyExamPaperDto.getPage(), queryWrapper);
        Page<StudyExamPaperVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyExamPaperVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param studyExamPaperDto
     * @return
     */
    @Override
    public List<StudyExamPaperVo> lists(StudyExamPaperDto studyExamPaperDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper<StudyExamPaper> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //如果是讲师,只看到自己申请的
            queryWrapper.eq(StudyExamPaper::getCreateUserId, loginAppUser.getId());
        }
        queryWrapper.like(StringUtils.isNotBlank(studyExamPaperDto.getTypeName()), StudyExamPaper::getTypeName, studyExamPaperDto.getTypeName());
        queryWrapper.eq(StringUtils.isNotBlank(studyExamPaperDto.getTypeCode()), StudyExamPaper::getTypeCode, studyExamPaperDto.getTypeCode());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyExamPaperDto.getPaperCode()), StudyExamPaper::getPaperCode, studyExamPaperDto.getPaperCode());
        queryWrapper.like(ObjectUtils.isNotEmpty(studyExamPaperDto.getPaperName()), StudyExamPaper::getPaperName, studyExamPaperDto.getPaperName());
        queryWrapper.like(StringUtils.isNotBlank(studyExamPaperDto.getPaperType()), StudyExamPaper::getPaperType, studyExamPaperDto.getPaperType());
        queryWrapper.eq(StudyExamPaper::getIsEnable, EnableStateEnum.ENABLE.code);
        queryWrapper.eq(StudyExamPaper::getStatus, StudyStatus.TAKE_EFFECT.code);
        List<StudyExamPaper> studyExamPapers = studyExamPaperMapper.selectList(queryWrapper);
        List<StudyExamPaperVo> studyClassLessonDefVos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(studyExamPapers)) {
            studyClassLessonDefVos = DozerUtils.mapList(dozerMapper, studyExamPapers, StudyExamPaperVo.class);
        }
        log.debug("查询条数:" + studyClassLessonDefVos.size());
        return studyClassLessonDefVos;
    }

    /**
     * 查看试卷详情
     * @param id
     * @return
     */
    @Override
    public StudyExamPaperVo get(Long id) {
        StudyExamPaper studyExamPaper = super.getById(id);
        StudyExamPaperVo studyExamPaperVo = new StudyExamPaperVo();
        if (studyExamPaper != null) {
            studyExamPaperVo = dozerMapper.map(studyExamPaper, StudyExamPaperVo.class);
            //查看固定试卷
            if (studyExamPaper.getPaperCode().equals(PaperCodeEnum.GDSJ.getCode())) {
                LambdaQueryWrapper<StudyExamPaperQuestion> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(StudyExamPaperQuestion::getPaperId, id);
                queryWrapper.orderByAsc(StudyExamPaperQuestion::getSortNumber);
                List<StudyExamPaperQuestion> studyPaperQuestionList = studyExamPaperQuestionMapper.selectList(queryWrapper);
                List<StudyExamPaperQuestionVo> studyExamPaperQuestionVos = DozerUtils.mapList(dozerMapper, studyPaperQuestionList, StudyExamPaperQuestionVo.class);
                if (CollectionUtil.isNotEmpty(studyPaperQuestionList)) {
                    List<Long> idList = studyPaperQuestionList.stream().map(StudyExamPaperQuestion::getQuestionId).collect(Collectors.toList());
                    //所有问题
                    List<StudyExamQuestion> studyExamQuestions = studyExamQuestionService.listByIds(idList);
                    Map<Long, List<StudyExamQuestion>> questionMap = studyExamQuestions.stream().collect(Collectors.groupingBy(StudyExamQuestion::getId));

                    List<StudyExamQuestion> shortAnswer = studyExamQuestions.stream().filter(a -> a.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)).collect(Collectors.toList());
                    if (CollectionUtil.isNotEmpty(shortAnswer)) {
                        studyExamPaperVo.setIsHaveShortAnswer(YesNo.YES.code);
                    } else {
                        studyExamPaperVo.setIsHaveShortAnswer(YesNo.NO.code);
                    }
                    List<Long> questionIds = studyExamQuestions.stream().map(StudyExamQuestion::getId).collect(Collectors.toList());
                    LambdaQueryWrapper<StudyExamQuestionAnswer> queryWrapper2 = new LambdaQueryWrapper<>();
                    queryWrapper2.in(StudyExamQuestionAnswer::getQuestionId, questionIds);
                    //所有答案
                    List<StudyExamQuestionAnswer> studyExamQuestionAnswers1 = studyExamQuestionAnswerMapper.selectList(queryWrapper2);
                    List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos1 = DozerUtils.mapList(dozerMapper, studyExamQuestionAnswers1, StudyExamQuestionAnswerVo.class);
                    Map<Long, List<StudyExamQuestionAnswerVo>> maps = studyExamQuestionAnswerVos1.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswerVo::getQuestionId));
                    for (StudyExamPaperQuestionVo studyExamQuestionVo : studyExamPaperQuestionVos) {
                        List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos2 = maps.get(studyExamQuestionVo.getQuestionId());
                        studyExamQuestionVo.setParse(questionMap.get(studyExamQuestionVo.getQuestionId()).get(0).getParse());
                        studyExamQuestionVo.setAnswers(studyExamQuestionAnswerVos2);
                    }
                }
                studyExamPaperVo.setStudyExamPaperApplyQuestionList(studyExamPaperQuestionVos);

            } else {
                LambdaQueryWrapper<StudyExamPaperDatabase> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(StudyExamPaperDatabase::getPaperId, id);
                List<StudyExamPaperDatabase> studyExamPaperDatabases = studyExamPaperDatabaseMapper.selectList(queryWrapper);
                List<StudyExamPaperDatabaseVo> studyExamPaperDatabaseVos = DozerUtils.mapList(dozerMapper, studyExamPaperDatabases, StudyExamPaperDatabaseVo.class);
                studyExamPaperVo.setStudyExamPaperApplyDatabaseList(studyExamPaperDatabaseVos);
            }
        }
        return studyExamPaperVo;
    }

    /**
     * 分页获取待阅试卷列表
     *
     * @param studyUserExamDto
     * @return
     */
    @Override
    public Page<StudyUserExamVo> getPaperList(StudyUserExamDto studyUserExamDto) {
        LambdaQueryWrapper<StudyUserExam> queryWrapper = new LambdaQueryWrapper<>();
        //根据阅卷老师id和试卷状态(已交卷)筛选出所有待阅试卷
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        queryWrapper.eq(StudyUserExam::getMarkingId, loginAppUser.getId());
        if (studyUserExamDto.getPaperStatus()!=null){
            queryWrapper.eq(StudyUserExam::getPaperStatus,studyUserExamDto.getPaperStatus());
        }else {
            queryWrapper.eq(StudyUserExam::getPaperStatus,PaperStatusEnum.SUBMITTED_PAPERS.code).or()
                    .eq(StudyUserExam::getPaperStatus,PaperStatusEnum.MARKED_PAPERS.code);
        }
        queryWrapper.like(ObjectUtils.isNotEmpty(studyUserExamDto.getCreateUserName()), StudyUserExam::getCreateUserName, studyUserExamDto.getCreateUserName());
        queryWrapper.like(ObjectUtils.isNotEmpty(studyUserExamDto.getPaperName()), StudyUserExam::getPaperName, studyUserExamDto.getPaperName());
        queryWrapper.like(ObjectUtils.isNotEmpty(studyUserExamDto.getMarkingName()), StudyUserExam::getMarkingName, studyUserExamDto.getMarkingName());
            Page<StudyUserExam> studyUserExamPage = studyUserExamMapper.selectPage(studyUserExamDto.getPage(), queryWrapper);
        return DozerUtils.mapPage(dozerMapper, studyUserExamPage, StudyUserExamVo.class);

    }

    /**
     * 试卷无简答题自动阅卷给出分数
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean autoMarkPaper(Long id) {
        log.info("自动阅卷开始;参数:{}",id);
        //通过人员答题id查
        StudyUserExam studyUserExam = studyUserExamService.getById(id);
        //通过人员答题id查题目集合
        List<StudyUserExamQuestion> userExamQuestions = studyUserExamQuestionMapper.selectList(Wrappers.<StudyUserExamQuestion>lambdaQuery()
                .eq(StudyUserExamQuestion::getUserExamId, id));

        List<Long> idList = userExamQuestions.stream().map(StudyUserExamQuestion::getId).collect(Collectors.toList());

        questionDetailMapper.delete(Wrappers.<StudyUserExamQuestionDetail>lambdaQuery().in(StudyUserExamQuestionDetail::getUserExamQuestionId, idList));

        for (StudyUserExamQuestion studyUserExamQuestion : userExamQuestions) {
            //通过题目id获取正确答案
            List<StudyExamQuestionAnswer> StudyExamQuestionAnswers = studyExamQuestionAnswerService.list(Wrappers.<StudyExamQuestionAnswer>lambdaQuery()
                    .eq(StudyExamQuestionAnswer::getQuestionId, studyUserExamQuestion.getQuestionId())
            .orderByAsc(StudyExamQuestionAnswer::getSort));
            List<StudyUserExamQuestionDetail> studyUserExamQuestionDetails = DozerUtils.mapList(dozerMapper, StudyExamQuestionAnswers, StudyUserExamQuestionDetail.class);
            if (studyUserExamQuestion.getQuestionCode().equals(QuestionCode.JUDGMENT.code)){
                Integer isTrue = studyUserExamQuestionDetails.get(0).getIsTrue();
                if (isTrue.equals(YesNo.NO.code)){
                    studyUserExamQuestionDetails.get(0).setIsTrue(YesNo.YES.code);
                }
            }
            for (StudyUserExamQuestionDetail detail : studyUserExamQuestionDetails){
                detail.setUserExamQuestionId(studyUserExamQuestion.getId());
            }
            studyUserExamQuestionDetailService.saveBatch(studyUserExamQuestionDetails);
            //获取人员答案
            String userAnswer = studyUserExamQuestion.getUserAnswer();
            //获取正确答案--单选题---多选题---填空题
            List<StudyUserExamQuestionDetail> studyUserExamQuestionDetailList = studyUserExamQuestionDetailService.list(Wrappers.<StudyUserExamQuestionDetail>lambdaQuery().eq(StudyUserExamQuestionDetail::getUserExamQuestionId, studyUserExamQuestion.getId())
                    .eq(StudyUserExamQuestionDetail::getIsTrue, YesNo.YES.code)
            .orderByAsc(StudyUserExamQuestionDetail::getSort));
            List<String> rightList = studyUserExamQuestionDetailList.stream().map(StudyUserExamQuestionDetail::getAnswer).collect(Collectors.toList());
            //单选题
            if (studyUserExamQuestion.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code)) {
                if (StringUtils.isNotBlank(userAnswer)) {
                    if (rightList.get(0).equals(userAnswer)) {
                        studyUserExamQuestion.setIsCorrect(YesNo.YES.code);
                        studyUserExamQuestion.setScore(studyUserExamQuestion.getQuestionScore());
                    } else {
                        //答错不得分
                        studyUserExamQuestion.setIsCorrect(YesNo.NO.code);
                        studyUserExamQuestion.setScore(BigDecimal.ZERO);
                    }
                } else {
                    //未作答不得分
                    studyUserExamQuestion.setIsCorrect(YesNo.NO.code);
                    studyUserExamQuestion.setScore(BigDecimal.ZERO);
                }
            }
            //多选题
            if (studyUserExamQuestion.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code)) {
                if (StringUtils.isNotBlank(userAnswer)) {
                    List<String> userList = Stream.of(userAnswer.split(",")).sorted().collect(Collectors.toList());
                    Collections.sort(rightList);
                    if (ListUtils.isEqualList(userList,rightList)) {
                        studyUserExamQuestion.setIsCorrect(YesNo.YES.code);
                        studyUserExamQuestion.setScore(studyUserExamQuestion.getQuestionScore());
                    } else {
                        //答错不得分
                        studyUserExamQuestion.setIsCorrect(YesNo.NO.code);
                        studyUserExamQuestion.setScore(BigDecimal.ZERO);
                    }
                } else {
                    //未作答不得分
                    studyUserExamQuestion.setIsCorrect(YesNo.NO.code);
                    studyUserExamQuestion.setScore(BigDecimal.ZERO);
                }
            }
            //判断题
            if (studyUserExamQuestion.getQuestionCode().equals(QuestionCode.JUDGMENT.code)) {
                //获取判断题
                List<StudyUserExamQuestionDetail> studyUserExamQuestionDetailsJUDGMENT = studyUserExamQuestionDetailService.list(Wrappers.<StudyUserExamQuestionDetail>lambdaQuery().eq(StudyUserExamQuestionDetail::getUserExamQuestionId, studyUserExamQuestion.getId()));
                List<String> rightListJUDGMENT = studyUserExamQuestionDetailsJUDGMENT.stream().map(StudyUserExamQuestionDetail::getAnswer).collect(Collectors.toList());
                if (StringUtils.isNotBlank(userAnswer)) {
                    if (rightListJUDGMENT.get(0).equals(userAnswer)) {
                        studyUserExamQuestion.setIsCorrect(YesNo.YES.code);
                        studyUserExamQuestion.setScore(studyUserExamQuestion.getQuestionScore());
                    } else {
                        //答错不得分
                        studyUserExamQuestion.setIsCorrect(YesNo.NO.code);
                        studyUserExamQuestion.setScore(BigDecimal.ZERO);
                    }
                } else {
                    //未作答不得分
                    studyUserExamQuestion.setIsCorrect(YesNo.NO.code);
                    studyUserExamQuestion.setScore(BigDecimal.ZERO);
                }
            }
            //填空题
            if (QuestionCode.COMPLETION.code.equals(studyUserExamQuestion.getQuestionCode())) {
                if (StringUtils.isNotBlank(userAnswer)) {
                    List<String> userList = Stream.of(userAnswer.split(",")).collect(Collectors.toList());
                    //少作答不得分
                    if (ListUtils.isEqualList(userList,rightList)) {
                        studyUserExamQuestion.setIsCorrect(YesNo.YES.code);
                        studyUserExamQuestion.setScore(studyUserExamQuestion.getQuestionScore());
                    } else {
                        studyUserExamQuestion.setIsCorrect(YesNo.NO.code);
                        studyUserExamQuestion.setScore(BigDecimal.ZERO);
                    }
                } else {
                    //填空题未作答不得分
                    studyUserExamQuestion.setIsCorrect(YesNo.NO.code);
                    studyUserExamQuestion.setScore(BigDecimal.ZERO);
                }
            }
           //修改人员答题提目表
            studyUserExamQuestionService.updateById(studyUserExamQuestion);
        }
        //获取所有答对的题目分值
        List<StudyUserExamQuestion> userExamQuestionList = studyUserExamQuestionMapper.selectList(Wrappers.<StudyUserExamQuestion>lambdaQuery()
                .eq(StudyUserExamQuestion::getUserExamId, id)
                .eq(StudyUserExamQuestion::getIsCorrect,YesNo.YES.code));
        //定义初始分数
        BigDecimal totalScore = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(userExamQuestionList)) {
            totalScore = userExamQuestionList.stream().map(StudyUserExamQuestion::getScore).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        studyUserExam.setExamScore(totalScore);

        BigDecimal passScore = studyUserExam.getSpecialExamQualifiedScore();
        if (totalScore.compareTo(passScore) > -1) {
            studyUserExam.setExamLevel(YesNo.YES.code);
        } else {
            studyUserExam.setExamLevel(YesNo.NO.code);
        }
        studyUserExam.setRemark("继续努力");
        return studyUserExamService.updateById(studyUserExam);
    }

    /**
     * 获取待阅试卷详情
     * @param id
     * @return
     */
    @Override
    public StudyUserExamVo markPaperDetail(Long id) {
        LambdaQueryWrapper<StudyUserExam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyUserExam::getId, id);
        StudyUserExam studyUserExam = studyUserExamMapper.selectOne(queryWrapper);
        List<StudyUserExamQuestion> list = studyUserExamQuestionMapper.selectList(Wrappers.<StudyUserExamQuestion>lambdaQuery()
                .eq(StudyUserExamQuestion::getUserExamId, id));
        List<StudyUserExamQuestionVo> userExamQuestionVos = DozerUtils.mapList(dozerMapper, list, StudyUserExamQuestionVo.class);
        List<Long> questionIdList = userExamQuestionVos.stream().map(StudyUserExamQuestionVo::getQuestionId).collect(Collectors.toList());
        //取出所有题目的正确答案,并按题目id分组
        LambdaQueryWrapper<StudyExamQuestionAnswer> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(StudyExamQuestionAnswer::getQuestionId, questionIdList);
        wrapper.eq(StudyExamQuestionAnswer::getIsTrue, YesNo.YES.code);
        List<StudyExamQuestionAnswer> questionAnswers = studyExamQuestionAnswerService.list(wrapper);
        List<StudyExamQuestionAnswer> studyQuestionAnswer = studyExamQuestionAnswerMapper.selectList(Wrappers.<StudyExamQuestionAnswer>lambdaQuery()
                .in(StudyExamQuestionAnswer::getQuestionId, questionIdList)
                .orderByAsc(StudyExamQuestionAnswer::getSort));
        List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos = DozerUtils.mapList(dozerMapper, studyQuestionAnswer, StudyExamQuestionAnswerVo.class);
        //所有题目答案(选择题包含非正确选项)
        Map<Long, List<StudyExamQuestionAnswerVo>> map = studyExamQuestionAnswerVos.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswerVo::getQuestionId));
        //所有题目答案(选择题不包含非正确选项)
        Map<Long, List<StudyExamQuestionAnswer>> answerMap = questionAnswers.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswer::getQuestionId));
        for (StudyUserExamQuestionVo questionVo : userExamQuestionVos) {
            //给试卷题目设置正确答案,选择题除返回正确答案外,也返回非正确选项
            //单选题或多选题除返回所有选项外,还返回根据sort升序查询的列表索引设置的A B C D 等
            if (questionVo.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code)
                    || questionVo.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code)) {
                List<StudyExamQuestionAnswerVo> questionAnswer = map.get(questionVo.getQuestionId());
                char sortText = 'A';
                for (StudyExamQuestionAnswerVo answer : questionAnswer) {
                    answer.setSortText(sortText);
                    sortText = (char) (sortText + 1);
                }
            }
            List<StudyExamQuestionAnswerVo> questionAnswer = map.get(questionVo.getQuestionId());
            List<StudyUserExamQuestionDetailVo> questionDetailVos = DozerUtils.mapList(dozerMapper, questionAnswer, StudyUserExamQuestionDetailVo.class);
            for (StudyUserExamQuestionDetailVo detailVo : questionDetailVos) {
                if (questionVo.getQuestionCode().equals(QuestionCode.JUDGMENT.code)) {
                    if (detailVo.getIsTrue().equals(YesNo.NO.code)) {
                        detailVo.setIsTrue(YesNo.YES.code);
                    }
                }
                detailVo.setUserExamQuestionId(questionVo.getId());
            }
            questionVo.setAnswers(questionDetailVos);
            //从user-exam-question表里获取每道题目分数
            List<StudyUserExamQuestion> paperQuestionList = studyUserExamQuestionMapper.selectList(Wrappers.<StudyUserExamQuestion>lambdaQuery()
                    .eq(StudyUserExamQuestion::getUserExamId, id)
                    .in(StudyUserExamQuestion::getQuestionId, questionIdList));
            Map<Long, List<StudyUserExamQuestion>> questionMap = paperQuestionList.stream().collect(Collectors.groupingBy(StudyUserExamQuestion::getQuestionId));
            StudyUserExamQuestion studyUserExamQuestion = questionMap.get(questionVo.getQuestionId()).get(0);
            //取出正确答案内容
            List<StudyExamQuestionAnswer> studyExamQuestionAnswers = answerMap.get(questionVo.getQuestionId());
            if (CollectionUtil.isNotEmpty(studyExamQuestionAnswers)) {
                //单选和判断,取一个正确答案
                StudyExamQuestionAnswer studyExamQuestionAnswer = studyExamQuestionAnswers.get(0);
                String answers = studyExamQuestionAnswer.getAnswer();
                //题目类型
                Integer questionCode = studyUserExamQuestion.getQuestionCode();
                //题目分数
                BigDecimal questionScore = studyUserExamQuestion.getQuestionScore();
                //单选题
                if (QuestionCode.SINGLE_CHOICE.code.equals(questionCode)) {
                    //对比答案，设置分数
                    String userAnswer = questionVo.getUserAnswer();
                    if (StringUtils.isNotBlank(userAnswer)) {
                        if (answers.equals(userAnswer)) {
                            questionVo.setIsCorrect(YesNo.YES.code);
                            questionVo.setScore(questionScore);
                        } else {
                            //答错不得分
                            questionVo.setIsCorrect(YesNo.NO.code);
                            questionVo.setScore(BigDecimal.ZERO);
                        }
                    } else {
                        //未作答不得分
                        questionVo.setIsCorrect(YesNo.NO.code);
                        questionVo.setScore(BigDecimal.ZERO);
                    }
                }
                //判断题
                if (QuestionCode.JUDGMENT.code.equals(questionCode)) {
                    String userAnswer = questionVo.getUserAnswer();
                    if (StringUtils.isNotBlank(userAnswer)) {
                        if (answers.equals(userAnswer)) {
                            questionVo.setIsCorrect(YesNo.YES.code);
                            questionVo.setScore(questionScore);
                        } else {
                            //答错不得分
                            questionVo.setIsCorrect(YesNo.NO.code);
                            questionVo.setScore(BigDecimal.ZERO);
                        }
                    } else {
                        //未作答不得分
                        questionVo.setIsCorrect(YesNo.NO.code);
                        questionVo.setScore(BigDecimal.ZERO);
                    }
                }
                //多选题
                if (QuestionCode.MULTIPLE_CHOICE.code.equals(questionCode)) {
                    //多选题正确答案
                    List<StudyExamQuestionAnswer> answer = answerMap.get(questionVo.getQuestionId());
                    List<String> rightList = answer.stream().map(StudyExamQuestionAnswer::getAnswer).collect(Collectors.toList());
                    String userAnswers = questionVo.getUserAnswer();
                    if (StringUtils.isNotBlank(userAnswers)) {
                        List<String> userList = Stream.of(userAnswers.split(",")).sorted().collect(Collectors.toList());
                        Collections.sort(rightList);
                        if (ListUtils.isEqualList(userList,rightList)) {
                            questionVo.setIsCorrect(YesNo.YES.code);
                            questionVo.setScore(questionScore);
                        } else {
                            //答错不得分
                            questionVo.setIsCorrect(YesNo.NO.code);
                            questionVo.setScore(BigDecimal.ZERO);
                        }
                    } else {
                        //未作答不得分
                        questionVo.setIsCorrect(YesNo.NO.code);
                        questionVo.setScore(BigDecimal.ZERO);
                    }
                }
                //填空题
                if (QuestionCode.COMPLETION.code.equals(questionCode)) {
                    List<StudyExamQuestionAnswer> answer = answerMap.get(questionVo.getQuestionId());
                    List<String> rightList = answer.stream().map(StudyExamQuestionAnswer::getAnswer).collect(Collectors.toList());
                    String userAnswer = questionVo.getUserAnswer();
                    if (StringUtils.isNotBlank(userAnswer)) {
                        List<String> userList = Stream.of(userAnswer.split(",")).collect(Collectors.toList());
                        //少作答不得分
                        if (ListUtils.isEqualList(userList,rightList)) {
                            questionVo.setIsCorrect(YesNo.YES.code);
                            questionVo.setScore(questionScore);
                        } else {
                            questionVo.setIsCorrect(YesNo.NO.code);
                            questionVo.setScore(BigDecimal.ZERO);
                        }
                    } else {
                        //填空题未作答不得分
                        questionVo.setIsCorrect(YesNo.NO.code);
                        questionVo.setScore(BigDecimal.ZERO);
                    }
                }
                //简答题
                if (QuestionCode.SHORT_ANSWER.code.equals(questionCode)) {
                    String userAnswer = questionVo.getUserAnswer();
                    //简答题未作答不得分
                    if (StringUtils.isBlank(userAnswer)) {
                        questionVo.setIsCorrect(YesNo.NO.code);
                        questionVo.setScore(BigDecimal.ZERO);
                    }
                }
            } else {
                if (studyUserExamQuestion.getQuestionCode().equals(QuestionCode.JUDGMENT.code)) {
                    String answer = "错";
                    if (StringUtils.isNotBlank(questionVo.getUserAnswer())) {
                        if (answer.equals(questionVo.getUserAnswer())) {
                            questionVo.setIsCorrect(YesNo.YES.code);
                            questionVo.setScore(studyUserExamQuestion.getQuestionScore());
                        } else {
                            questionVo.setIsCorrect(YesNo.NO.code);
                            questionVo.setScore(BigDecimal.ZERO);
                        }
                    } else {
                        questionVo.setIsCorrect(YesNo.NO.code);
                        questionVo.setScore(BigDecimal.ZERO);
                    }
                }
            }
        }
        List<StudyUserExamQuestionVo> rights = userExamQuestionVos.stream().filter(right -> right.getIsCorrect() != null && right.getIsCorrect().equals(YesNo.YES.code)).collect(Collectors.toList());
        BigDecimal totalScore = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(rights)) {
            totalScore = rights.stream().map(StudyUserExamQuestionVo::getScore).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        BigDecimal passScore = studyUserExam.getSpecialExamQualifiedScore();
        if (totalScore.compareTo(passScore) > -1) {
            studyUserExam.setExamLevel(YesNo.YES.code);
        } else {
            studyUserExam.setExamLevel(YesNo.NO.code);
        }
        studyUserExam.setExamScore(totalScore);
        studyUserExamService.updateById(studyUserExam);
        StudyUserExamVo studyUserExamVo = dozerMapper.map(studyUserExam, StudyUserExamVo.class);
        studyUserExamVo.setQuestionList(userExamQuestionVos);

        return studyUserExamVo;
    }

    /**
     * 阅卷保存
     * @param studyUserExamDto
     * @return
     */
    @Override
    @Transactional
    public Boolean teacherMarking(StudyUserExamDto studyUserExamDto) {
        List<StudyUserExamQuestionDto> userExamQuestionList = studyUserExamDto.getQuestionList();
        List<StudyUserExamQuestion> questionLists = studyUserExamQuestionMapper.selectList(Wrappers.<StudyUserExamQuestion>lambdaQuery().eq(StudyUserExamQuestion::getUserExamId, studyUserExamDto.id));
        List<Long> questionIdList = questionLists.stream().map(StudyUserExamQuestion::getId).collect(Collectors.toList());
        //删除问题详情表里原有数据
        questionDetailMapper.delete(Wrappers.<StudyUserExamQuestionDetail>lambdaQuery().in(StudyUserExamQuestionDetail::getUserExamQuestionId, questionIdList));
        for (StudyUserExamQuestionDto studyUserExamQuestion : userExamQuestionList) {
            //填空或简答题有未批阅的题目,保存时提示有未批阅的题目
            if (studyUserExamQuestion.getQuestionCode().equals(QuestionCode.COMPLETION.code) ||
                    studyUserExamQuestion.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)) {
                if (studyUserExamQuestion.getIsCorrect() == null || studyUserExamQuestion.getScore() == null) {
                    throw new IncloudException("题目:" + studyUserExamQuestion.getQuestion() + "未批阅,请判断题目对错,并给出分数");
                }
            }
            List<StudyUserExamQuestionDetailDto> answers = studyUserExamQuestion.getAnswers();
            List<StudyUserExamQuestionDetail> details = DozerUtils.mapList(dozerMapper, answers, StudyUserExamQuestionDetail.class);
            for (StudyUserExamQuestionDetail detail : details) {
                detail.setId(IdGenerator.getIdGenerator());
                detail.setUserExamQuestionId(studyUserExamQuestion.getId());
            }
            detailService.saveBatch(details);
        }
        List<StudyUserExamQuestion> questionList = DozerUtils.mapList(dozerMapper, userExamQuestionList, StudyUserExamQuestion.class);
        //筛选出所有老师批阅正确的题目
        List<StudyUserExamQuestion> rights = questionList.stream().filter(right -> right.getIsCorrect() != null && right.getIsCorrect().equals(YesNo.YES.code)).collect(Collectors.toList());
        //更新人员答题题目信息,(题目的对错以及得分)
        studyUserExamQuestionService.updateBatchById(questionList);
        BigDecimal totalScore = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(rights)) {
            totalScore = rights.stream().map(StudyUserExamQuestion::getScore).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        //设置阅卷时间
        studyUserExamDto.setMarkingTime(LocalDateTime.now());
        //设置考试分数
        studyUserExamDto.setExamScore(totalScore);
        //设置试卷已阅卷
        studyUserExamDto.setPaperStatus(PaperStatusEnum.MARKED_PAPERS.code);
        StudyUserExam studyUserExam = dozerMapper.map(studyUserExamDto, StudyUserExam.class);
        return studyUserExamService.updateById(studyUserExam);
    }

    /**
     * 获取已阅试卷详情
     *
     * @param id
     * @return
     */
    @Override
    public StudyUserExamVo markedPaperDetail(Long id) {
        StudyUserExam studyUserExam = studyUserExamMapper.selectOne(Wrappers.<StudyUserExam>lambdaQuery().eq(StudyUserExam::getId, id));
        List<StudyUserExamQuestion> questionList = studyUserExamQuestionMapper.selectList(Wrappers.<StudyUserExamQuestion>lambdaQuery()
                .eq(StudyUserExamQuestion::getUserExamId, id));
        List<Long> idList = questionList.stream().map(StudyUserExamQuestion::getId).collect(Collectors.toList());
        List<StudyUserExamQuestionDetail> detail = questionDetailMapper.selectList(Wrappers.<StudyUserExamQuestionDetail>lambdaQuery().in(StudyUserExamQuestionDetail::getUserExamQuestionId, idList)
                .orderByAsc(StudyUserExamQuestionDetail::getSort));
        List<StudyUserExamQuestionDetailVo> questionDetailVos = DozerUtils.mapList(dozerMapper, detail, StudyUserExamQuestionDetailVo.class);
        Map<Long, List<StudyUserExamQuestionDetailVo>> map = questionDetailVos.stream().collect(Collectors.groupingBy(StudyUserExamQuestionDetailVo::getUserExamQuestionId));
        List<StudyUserExamQuestionVo> userExamQuestionVos = DozerUtils.mapList(dozerMapper, questionList, StudyUserExamQuestionVo.class);
        for (StudyUserExamQuestionVo questionVo : userExamQuestionVos) {
            if (questionVo.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code) ||
                    questionVo.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code)) {
                List<StudyUserExamQuestionDetailVo> details = map.get(questionVo.getId());
                char sortText = 'A';
                for (StudyUserExamQuestionDetailVo answer : details) {
                    answer.setSortText(sortText);
                    sortText = (char) (sortText + 1);
                }
            }
            List<StudyUserExamQuestionDetailVo> details = map.get(questionVo.getId());
            questionVo.setAnswers(details);
        }
        StudyUserExamVo studyUserExamVo = dozerMapper.map(studyUserExam, StudyUserExamVo.class);
        studyUserExamVo.setQuestionList(userExamQuestionVos);
        return studyUserExamVo;
    }

    /**
     * 修改试卷申请状态(启用或者停用)
     *
     * @Param id
     */
    @Override
    public Boolean isEnable(Long id) {
        StudyExamPaper studyExamPaper = studyExamPaperMapper.selectById(id);
        if (!studyExamPaper.getAuditStatus().equals(WfProcessEnum.DONE.getType())) {
            throw new IncloudException("请在流程审批完成后再进行修改！");
        }
        if (studyExamPaper.getIsEnable().equals(com.netwisd.base.common.constants.YesNo.YES.code)) {
            studyExamPaper.setIsEnable(com.netwisd.base.common.constants.YesNo.NO.code);
        } else {
            studyExamPaper.setIsEnable(com.netwisd.base.common.constants.YesNo.YES.code);
        }
        studyExamPaperMapper.updateById(studyExamPaper);
        return true;
    }

    /**
     * 通过procInstId获取试卷信息
     *
     * @param procInstId
     * @return
     */
    private StudyExamPaper getByProcInstId(String procInstId) {
        LambdaQueryWrapper<StudyExamPaper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamPaper::getCamundaProcinsId, procInstId);
        return studyExamPaperMapper.selectOne(queryWrapper);
    }

    /**
     * 新增试卷流程提交赋值提交时间和未审核状态
     *
     * @param procInstId
     * @return
     */
    @Override
    public Boolean procAfterSubmit(String procInstId) {
        //赋值提交时间
        StudyExamPaper studyExamPaper = this.getByProcInstId(procInstId);
        studyExamPaper.setAuditSubmitTime(LocalDateTime.now());
        //赋值未审核状态
        studyExamPaper.setStatus(StudyStatus.NO_TAKE_EFFECT.code);
        studyExamPaperMapper.updateById(studyExamPaper);
        return true;
    }

    /**
     * 检验新增修改数据
     *
     * @param studyExamPaperDto
     */
    private void checkData(StudyExamPaperDto studyExamPaperDto) {
        if (com.netwisd.base.common.util.StringUtils.isBlank(studyExamPaperDto.getTypeName())) {
            throw new IncloudException("请选择试卷标签");
        }
        if (com.netwisd.base.common.util.StringUtils.isBlank(studyExamPaperDto.getPaperName())) {
            throw new IncloudException("请填写试卷名称");
        }
        if (com.netwisd.base.common.util.StringUtils.isBlank(studyExamPaperDto.getPaperType())) {
            throw new IncloudException("请选择试卷类型");
        }
        if (ObjectUtils.isEmpty(studyExamPaperDto.getPaperCode())) {
            throw new IncloudException("请选择出题类型");
        }
        if (ObjectUtils.isEmpty(studyExamPaperDto.getSpecialExamTime())) {
            throw new IncloudException("请填写试卷时长");
        }
        //1是考试试卷,可能有0,1，表示既是考试也是练习,所以用contains
        if (studyExamPaperDto.getPaperType().contains("1")) {
            if (ObjectUtils.isEmpty(studyExamPaperDto.getSpecialExamQualifiedScore())) {
                throw new IncloudException("请填写合格分数");
            }
        }
        if (ObjectUtils.isEmpty(studyExamPaperDto.getStatus())) {
            studyExamPaperDto.setStatus(StudyStatus.NO_TAKE_EFFECT.code);
        }
    }

    /**
     * 新增试卷流程申请
     *
     * @param studyExamPaperDto
     * @return
     */
    @Transactional
    @Override
    public StudyExamPaperVo procSaveOrUpdate(StudyExamPaperDto studyExamPaperDto) {
        this.checkData(studyExamPaperDto);
        //试卷类型(固定或随机)
        Integer paperCode = studyExamPaperDto.getPaperCode();
        //固定试卷集合
        List<StudyExamPaperQuestionDto> studyExamPaperQuestionDtoList = studyExamPaperDto.getStudyExamPaperApplyQuestionList();
        //随机试卷集合
        List<StudyExamPaperDatabaseDto> databaseList = studyExamPaperDto.getStudyExamPaperApplyDatabaseList();
        //申请固定试卷
        if (paperCode.equals(PaperCodeEnum.GDSJ.code)) {
            if (CollectionUtil.isNotEmpty(studyExamPaperQuestionDtoList)) {
                //给每个题目设置排序号,回显时按照排序号排序
                for (int i = 0; i < studyExamPaperQuestionDtoList.size(); i++) {
                    studyExamPaperQuestionDtoList.get(i).setSortNumber(i);
                }
                //单选题分值
                BigDecimal singleScore = studyExamPaperDto.getSingleScore();
                //多选题分值
                BigDecimal multipleScore = studyExamPaperDto.getMultipleScore();
                //填空题分值
                BigDecimal completionScore = studyExamPaperDto.getCompletionScore();
                //判断题分值
                BigDecimal judgmentScore = studyExamPaperDto.getJudgmentScore();
                //简答题分值
                BigDecimal shortAnswerScore = studyExamPaperDto.getShortAnswerScore();
                List<StudyExamPaperQuestionDto> shortAnswerLists = studyExamPaperQuestionDtoList.stream().filter(shortAnswer -> shortAnswer.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)).collect(Collectors.toList());
                if (CollectionUtil.isEmpty(shortAnswerLists)) {
                    studyExamPaperDto.setIsHaveShortAnswer(YesNo.NO.code);
                } else {
                    studyExamPaperDto.setIsHaveShortAnswer(YesNo.YES.code);
                }
                //给没有具体分值的题目设置分数
                BigDecimal b = new BigDecimal(100);
                for (StudyExamPaperQuestionDto studyExamPaperQuestionDto : studyExamPaperQuestionDtoList) {
                    if (studyExamPaperQuestionDto.getQuestionScore() == null || studyExamPaperQuestionDto.getQuestionScore().compareTo(BigDecimal.ZERO) == 0) {
                        if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code)) {
                            //如果题目分值不为空并且大于0,根据题目类型设置题目分数
                            if (null != singleScore) {
                                if (singleScore.compareTo(BigDecimal.ZERO) > 0 && singleScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(singleScore);
                                } else {
                                    if (singleScore.compareTo(b) > -1) {
                                        throw new IncloudException("单选题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写单选题分值");
                            }
                        } else if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code)) {
                            if (null != multipleScore) {
                                if (multipleScore.compareTo(BigDecimal.ZERO) > 0 && multipleScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(multipleScore);
                                } else {
                                    if (multipleScore.compareTo(b) > -1) {
                                        throw new IncloudException("多选题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写多选题分值");
                            }
                        } else if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.COMPLETION.code)) {
                            if (null != completionScore) {
                                if (completionScore.compareTo(BigDecimal.ZERO) > 0 && completionScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(completionScore);
                                } else {
                                    if (completionScore.compareTo(b) > -1) {
                                        throw new IncloudException("填空题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写填空题分值");
                            }
                        } else if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.JUDGMENT.code)) {
                            if (null != judgmentScore) {
                                if (judgmentScore.compareTo(BigDecimal.ZERO) > 0 && judgmentScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(judgmentScore);
                                } else {
                                    if (judgmentScore.compareTo(b) > -1) {
                                        throw new IncloudException("判断题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写判断题分值");
                            }
                        } else if (studyExamPaperQuestionDto.getQuestionCode().equals(QuestionCode.SHORT_ANSWER.code)) {
                            if (null != shortAnswerScore) {
                                if (shortAnswerScore.compareTo(BigDecimal.ZERO) > 0 && shortAnswerScore.compareTo(b) < 0) {
                                    studyExamPaperQuestionDto.setQuestionScore(shortAnswerScore);
                                } else {
                                    if (shortAnswerScore.compareTo(b) > -1) {
                                        throw new IncloudException("简答题分值过大");
                                    }
                                }
                            } else {
                                throw new IncloudException("请填写简答题分值");
                            }
                        }
                    } else {
                        BigDecimal questionScore = studyExamPaperQuestionDto.getQuestionScore();
                        if (questionScore.compareTo(b) > -1) {
                            throw new IncloudException("单题分值过大");
                        }
                    }
                    studyExamPaperQuestionDto.setIdSign(true);
                    studyExamPaperQuestionDto.setPaperId(studyExamPaperDto.getId());
                }
                BigDecimal paperTotalScore = studyExamPaperQuestionDtoList.stream().map(StudyExamPaperQuestionDto::getQuestionScore).reduce(BigDecimal.ZERO, BigDecimal::add);
                studyExamPaperDto.setPaperTotalScore(paperTotalScore);
                studyExamPaperDto.setIsEnable(YesNo.NO.code);
                studyExamPaperDto.setStatus(StudyStatus.NO_TAKE_EFFECT.code);
                studyExamPaperDto.setAuditSubmitTime(LocalDateTime.now());
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
                studyExamPaperDto.setCreateUserName(loginAppUser.getUserNameCh());
                StudyExamPaper studyExamPaper = dozerMapper.map(studyExamPaperDto, StudyExamPaper.class);
                super.saveOrUpdate(studyExamPaper);
                List<StudyExamPaperQuestion> studyExamPaperQuestions = DozerUtils.mapList(dozerMapper, studyExamPaperQuestionDtoList, StudyExamPaperQuestion.class);
                List<Long> idList = studyExamPaperQuestions.stream().map(StudyExamPaperQuestion::getQuestionId).collect(Collectors.toList());
                List<StudyExamQuestion> studyExamQuestions = studyExamQuestionService.listByIds(idList);
                //设置题目为引用状态
                studyExamQuestions.forEach(question -> question.setIsQuote(YesNo.YES.code));
                studyExamQuestionService.updateBatchById(studyExamQuestions);
                studyExamPaperQuestionService.saveOrUpdateBatch(studyExamPaperQuestions);
            } else {
                throw new IncloudException("请选择固定试卷题目");
            }
        }
        //申请随机试卷
        if (paperCode.equals(PaperCodeEnum.SJSJ.code)) {
            if (CollectionUtil.isNotEmpty(databaseList)) {
                //选择了题库,未选择任何题型的数量和分值
                if (studyExamPaperDto.getSingleNumber() == null &&
                        studyExamPaperDto.getSingleScore() == null &&
                        studyExamPaperDto.getMultipleNumber() == null &&
                        studyExamPaperDto.getMultipleScore() == null &&
                        studyExamPaperDto.getCompletionNumber() == null &&
                        studyExamPaperDto.getCompletionScore() == null &&
                        studyExamPaperDto.getJudgmentNumber() == null &&
                        studyExamPaperDto.getJudgmentScore() == null &&
                        studyExamPaperDto.getShortAnswerNumber() == null &&
                        studyExamPaperDto.getShortAnswerScore() == null) {
                    throw new IncloudException("请至少选择一种题型的数量和分值");
                }
                List<Long> idList = databaseList.stream().map(StudyExamPaperDatabaseDto::getDatabaseId).collect(Collectors.toList());
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
                BigDecimal b = new BigDecimal(100);
                //单选题
                BigDecimal singleScore = studyExamPaperDto.getSingleScore();
                Integer singleNumber = studyExamPaperDto.getSingleNumber();
                if (singleNumber != null) {
                    if (singleNumber > singleChoiceList.size()) {
                        throw new IncloudException("所选题库中单选题实际数量不足,请修改单选题数量或添加单选题到所选题库");
                    }
                    if (singleScore == null) {
                        throw new IncloudException("请选择单选题分值");
                    } else {
                        if (singleScore.compareTo(b) > -1) {
                            throw new IncloudException("单选题分值过大");
                        }
                    }
                } else {
                    if (singleScore != null) {
                        throw new IncloudException("请选择单选题数量");
                    }
                }
                //多选题
                BigDecimal multipleScore = studyExamPaperDto.getMultipleScore();
                Integer multipleNumber = studyExamPaperDto.getMultipleNumber();
                if (multipleNumber != null) {
                    if (multipleNumber > multipleChoiceList.size()) {
                        throw new IncloudException("所选题库中多选题实际数量不足,请修改多选题数量或添加多选题到所选题库");
                    }
                    if (multipleScore == null) {
                        throw new IncloudException("请选择多选题分值");
                    } else {
                        if (multipleScore.compareTo(b) > -1) {
                            throw new IncloudException("多选题分值过大");
                        }
                    }
                } else {
                    if (multipleScore != null) {
                        throw new IncloudException("请选择多选题数量");
                    }
                }
                //填空题
                BigDecimal completionScore = studyExamPaperDto.getCompletionScore();
                Integer completionNumber = studyExamPaperDto.getCompletionNumber();
                if (completionNumber != null) {
                    if (completionNumber > completion.size()) {
                        throw new IncloudException("所选题库中填空题实际数量不足,请修改填空题数量或添加填空题到所选题库");
                    }
                    if (completionScore == null) {
                        throw new IncloudException("请选择填空题分值");
                    } else {
                        if (completionScore.compareTo(b) > -1) {
                            throw new IncloudException("填空题分值过大");
                        }
                    }
                } else {
                    if (completionScore != null) {
                        throw new IncloudException("请选择填空题数量");
                    }
                }
                //判断题
                BigDecimal judgmentScore = studyExamPaperDto.getJudgmentScore();
                Integer judgmentNumber = studyExamPaperDto.getJudgmentNumber();
                if (judgmentNumber != null) {
                    if (judgmentNumber > judgment.size()) {
                        throw new IncloudException("所选题库中判断题实际数量不足,请修改判断题数量或添加判断题到所选题库");
                    }
                    if (judgmentScore == null) {
                        throw new IncloudException("请选择判断题分值");
                    } else {
                        if (judgmentScore.compareTo(b) > -1) {
                            throw new IncloudException("判断题分值过大");
                        }
                    }
                } else {
                    if (judgmentScore != null) {
                        throw new IncloudException("请选择判断题数量");
                    }
                }
                //简答题
                BigDecimal shortAnswerScore = studyExamPaperDto.getShortAnswerScore();
                Integer shortAnswerNumber = studyExamPaperDto.getShortAnswerNumber();
                if (shortAnswerNumber==null && shortAnswerScore==null){
                    studyExamPaperDto.setIsHaveShortAnswer(YesNo.NO.code);
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
                        studyExamPaperDto.setIsHaveShortAnswer(YesNo.YES.code);
                    }else {
                        studyExamPaperDto.setIsHaveShortAnswer(YesNo.NO.code);
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
                BigDecimal judgmentTotalScore = BigDecimal.ZERO;
                if (judgmentNumber != null) {
                    judgmentTotalScore = judgmentScore.multiply(BigDecimal.valueOf(judgmentNumber));
                }
                BigDecimal shortAnswers = BigDecimal.ZERO;
                if (shortAnswerNumber!=null) {
                     shortAnswers = shortAnswerScore.multiply(BigDecimal.valueOf(shortAnswerNumber));
                }
                for (StudyExamPaperDatabaseDto studyExamPaperDatabaseDto : databaseList) {
                    studyExamPaperDatabaseDto.setPaperId(studyExamPaperDto.getId());
                    studyExamPaperDatabaseDto.setIdSign(true);
                }
                //随机试卷总分
                BigDecimal totalScore = single.add(mul).add(com).add(judgmentTotalScore).add(shortAnswers);
                studyExamPaperDto.setPaperTotalScore(totalScore);
                //设置
                studyExamPaperDto.setAuditSubmitTime(LocalDateTime.now());
                StudyExamPaper studyExamPaper = dozerMapper.map(studyExamPaperDto, StudyExamPaper.class);
                super.saveOrUpdate(studyExamPaper);
                List<StudyExamPaperDatabase> studyExamPaperDatabases = DozerUtils.mapList(dozerMapper, databaseList, StudyExamPaperDatabase.class);
                studyExamPaperDatabaseService.saveOrUpdateBatch(studyExamPaperDatabases);
            } else {
                throw new IncloudException("请添加题库");
            }
        }
        return procDetail(studyExamPaperDto.getCamundaProcinsId());
    }

    /**
     * 试卷新增流程审批通过
     *
     * @Param procInstId
     */
    @Override
    public Boolean procAuditSuccess(String procInstId) {
        LambdaQueryWrapper<StudyExamPaper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamPaper::getCamundaProcinsId, procInstId);
        StudyExamPaper studyExamPaper = studyExamPaperMapper.selectOne(queryWrapper);
        //设置试卷启用状态为启用
        studyExamPaper.setIsEnable(YesNo.YES.code);
        //设置审核通过时间为现在时间
        studyExamPaper.setAuditSuccessTime(LocalDateTime.now());
        //设置试卷状态为已生效
        studyExamPaper.setStatus(StudyStatus.TAKE_EFFECT.code);
        return super.updateById(studyExamPaper);
    }

    /**
     * 通过结果id查出结果转调整
     *
     * @Param id
     */
    @Override
    public StudyExamPaperAdjVo getPaper(Long id) {
        StudyExamPaper studyExamPaper = super.getById(id);
        if (StudyStatus.NO_TAKE_EFFECT.code.equals(studyExamPaper.getStatus())) {
            throw new IncloudException("该流程试卷状态为未生效，不能调整！");
        }
        if (StudyStatus.IN_UPDATE.code.equals(studyExamPaper.getStatus())) {
            throw new IncloudException("该流程试卷状态为调整中，不能再次调整！");
        }
        StudyExamPaperAdjVo studyExamPaperAdjVo = dozerMapper.map(studyExamPaper, StudyExamPaperAdjVo.class);
        Integer paperCode = studyExamPaper.getPaperCode();
        if (ObjectUtils.isNotEmpty(studyExamPaperAdjVo)) {
            //固定试卷查看题目
            if (paperCode.equals(PaperCodeEnum.GDSJ.code)) {
                LambdaQueryWrapper<StudyExamPaperQuestion> queryWrapperOne = new LambdaQueryWrapper<>();
                queryWrapperOne.eq(StudyExamPaperQuestion::getPaperId, id);
                List<StudyExamPaperQuestion> studyPaperQuestions = studyExamPaperQuestionMapper.selectList(queryWrapperOne);
                List<StudyExamPaperAdjQuestionVo> questionVos = DozerUtils.mapList(dozerMapper, studyPaperQuestions, StudyExamPaperAdjQuestionVo.class);
                List<Long> idList = questionVos.stream().map(StudyExamPaperAdjQuestionVo::getQuestionId).collect(Collectors.toList());
                List<StudyExamQuestion> studyExamQuestions = studyExamQuestionService.listByIds(idList);
                Map<Long, List<StudyExamQuestion>> questionMap = studyExamQuestions.stream().collect(Collectors.groupingBy(StudyExamQuestion::getId));
                List<StudyExamQuestionAnswer> answer = studyExamQuestionAnswerMapper.selectList(Wrappers.<StudyExamQuestionAnswer>lambdaQuery().in(StudyExamQuestionAnswer::getQuestionId, idList));
                Map<Long, List<StudyExamQuestionAnswer>> map = answer.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswer::getQuestionId));
                for (StudyExamPaperAdjQuestionVo questionVo : questionVos) {
                    List<StudyExamQuestionAnswer> answers = map.get(questionVo.getQuestionId());
                    questionVo.setParse(questionMap.get(questionVo.getQuestionId()).get(0).getParse());
                    questionVo.setAnswers(answers);
                }
                studyExamPaperAdjVo.setStudyExamPaperApplyQuestionList(questionVos);
                //随机试卷查看规则
            } else if (paperCode.equals(PaperCodeEnum.SJSJ.code)) {
                LambdaQueryWrapper<StudyExamPaperDatabase> queryWrapperTwo = new LambdaQueryWrapper<>();
                queryWrapperTwo.eq(StudyExamPaperDatabase::getPaperId, id);
                List<StudyExamPaperDatabase> studyExamPaperRules = studyExamPaperDatabaseMapper.selectList(queryWrapperTwo);
                List<StudyExamPaperAdjDatabaseVo> studyExamPaperApplyDatabaseVos = DozerUtils.mapList(dozerMapper, studyExamPaperRules, StudyExamPaperAdjDatabaseVo.class);
                studyExamPaperAdjVo.setStudyExamPaperApplyDatabaseList(studyExamPaperApplyDatabaseVos);
            }
        }
        dataEmpty(studyExamPaperAdjVo);
        return studyExamPaperAdjVo;
    }

    /**
     * 清空表中无用信息
     *
     * @param studyExamPaperAdjVo
     */
    private void dataEmpty(StudyExamPaperAdjVo studyExamPaperAdjVo) {
        studyExamPaperAdjVo.setLinkId(studyExamPaperAdjVo.getId());
        studyExamPaperAdjVo.setId(null);
        studyExamPaperAdjVo.setCreateTime(null);
        studyExamPaperAdjVo.setUpdateTime(null);
        studyExamPaperAdjVo.setCreateUserId(null);
        studyExamPaperAdjVo.setCreateUserName(null);
        studyExamPaperAdjVo.setCreateUserParentOrgId(null);
        studyExamPaperAdjVo.setCreateUserParentOrgName(null);
        studyExamPaperAdjVo.setCreateUserParentDeptId(null);
        studyExamPaperAdjVo.setCreateUserParentDeptName(null);
        studyExamPaperAdjVo.setCreateUserOrgFullId(null);
        if (CollectionUtils.isNotEmpty(studyExamPaperAdjVo.getStudyExamPaperApplyQuestionList())) {
            List<StudyExamPaperAdjQuestionVo> questionList = studyExamPaperAdjVo.getStudyExamPaperApplyQuestionList();
            questionList.forEach(question -> {
                question.setId(null);
                question.setCreateTime(null);
                question.setUpdateTime(null);
                question.setCreateUserId(null);
                question.setCreateUserName(null);
                question.setCreateUserParentOrgId(null);
                question.setCreateUserParentOrgName(null);
                question.setCreateUserParentDeptId(null);
                question.setCreateUserParentDeptName(null);
                question.setCreateUserOrgFullId(null);
                question.setPaperId(null);
            });
        }
        if (CollectionUtils.isNotEmpty(studyExamPaperAdjVo.getStudyExamPaperApplyDatabaseList())) {
            List<StudyExamPaperAdjDatabaseVo> studyExamPaperApplyDatabaseVos = studyExamPaperAdjVo.getStudyExamPaperApplyDatabaseList();
            studyExamPaperApplyDatabaseVos.forEach(database -> {
                database.setId(null);
                database.setCreateTime(null);
                database.setUpdateTime(null);
                database.setCreateUserId(null);
                database.setCreateUserName(null);
                database.setCreateUserParentOrgId(null);
                database.setCreateUserParentOrgName(null);
                database.setCreateUserParentDeptId(null);
                database.setCreateUserParentDeptName(null);
                database.setCreateUserOrgFullId(null);
                database.setPaperId(null);
            });
        }
    }

    /**
     * 流程查看详情
     *
     * @Param procInstId
     */
    @Override
    public StudyExamPaperVo procDetail(String procInstId) {
        LambdaQueryWrapper<StudyExamPaper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamPaper::getCamundaProcinsId, procInstId);
        StudyExamPaper studyExamPaper = studyExamPaperMapper.selectOne(queryWrapper);
        StudyExamPaperVo studyExamPaperVo = dozerMapper.map(studyExamPaper, StudyExamPaperVo.class);
        //固定试卷查看题目
        if (studyExamPaperVo.getPaperCode().equals(PaperCodeEnum.GDSJ.code)) {
            LambdaQueryWrapper<StudyExamPaperQuestion> queryWrapperOne = new LambdaQueryWrapper<>();
            queryWrapperOne.eq(StudyExamPaperQuestion::getPaperId, studyExamPaperVo.getId());
            List<StudyExamPaperQuestion> studyPaperQuestions = studyExamPaperQuestionMapper.selectList(queryWrapperOne);
            List<StudyExamPaperQuestionVo> studyExamPaperApplyQuestionVos = DozerUtils.mapList(dozerMapper, studyPaperQuestions, StudyExamPaperQuestionVo.class);
            studyExamPaperVo.setStudyExamPaperApplyQuestionList(studyExamPaperApplyQuestionVos);
            //随机试卷查看规则
        } else if (studyExamPaperVo.getPaperCode().equals(PaperCodeEnum.SJSJ.code)) {
            LambdaQueryWrapper<StudyExamPaperDatabase> queryWrapperTwo = new LambdaQueryWrapper<>();
            queryWrapperTwo.eq(StudyExamPaperDatabase::getPaperId, studyExamPaperVo.getId());
            List<StudyExamPaperDatabase> studyExamPaperRules = studyExamPaperDatabaseMapper.selectList(queryWrapperTwo);
            List<StudyExamPaperDatabaseVo> studyExamPaperApplyDatabaseVos = DozerUtils.mapList(dozerMapper, studyExamPaperRules, StudyExamPaperDatabaseVo.class);
            studyExamPaperVo.setStudyExamPaperApplyDatabaseList(studyExamPaperApplyDatabaseVos);
        }
        return studyExamPaperVo;
    }

    /**
     * 试卷删除
     *
     * @Param paperId
     */
    @Override
    @Transactional
    public Boolean delete(Long paperId) {
        log.info("删除试卷参数:{}", paperId);
        StudyExamPaper studyExamPaper = super.getById(paperId);
        if (ObjectUtil.isNotNull(studyExamPaper)) {
            if (!studyExamPaper.getAuditStatus().equals(WfProcessEnum.DRAFT.getType())) {
                throw new IncloudException("仅审核状态为草稿可删除");
            }
            log.info("删除:{" + studyExamPaper.getPaperName() + "}试卷");
            boolean paperDelete = super.removeById(paperId);
            if (paperDelete) {
                if (studyExamPaper.getPaperCode().equals(PaperCodeEnum.GDSJ.code)) {
                    studyExamPaperQuestionMapper.delete(Wrappers.<StudyExamPaperQuestion>lambdaQuery().eq(StudyExamPaperQuestion::getPaperId, paperId));
                } else {
                    studyExamPaperDatabaseMapper.delete(Wrappers.<StudyExamPaperDatabase>lambdaQuery().eq(StudyExamPaperDatabase::getPaperId, paperId));
                }
                WfEngineDto.StateDto stateDto = new WfEngineDto.StateDto();
                stateDto.setCamundaProcdefId(studyExamPaper.getCamundaProcdefId());
                stateDto.setCamundaProcinsId(studyExamPaper.getCamundaProcinsId());
                wfClient.deleteOnlyProcess(stateDto);
            }
        }
        return true;
    }

    /**
     * 流程删除
     *
     * @Param procInstId
     */
    @Override
    @Transactional
    public Boolean procDelete(String procInstId) {
        StudyExamPaper studyExamPaper = this.getByProcInstId(procInstId);
        studyExamPaperMapper.deleteById(studyExamPaper.getId());
        //删除固定试卷
        if (studyExamPaper.getPaperCode().equals(PaperCodeEnum.GDSJ.code)) {
            LambdaQueryWrapper<StudyExamPaperQuestion> questionWrapper = new LambdaQueryWrapper<>();
            questionWrapper.eq(StudyExamPaperQuestion::getPaperId, studyExamPaper.getId());
            studyExamPaperQuestionMapper.delete(questionWrapper);
        }
        //删除随机试卷
        if (studyExamPaper.getPaperCode().equals(PaperCodeEnum.SJSJ.code)) {
            LambdaQueryWrapper<StudyExamPaperDatabase> databaseWrapper = new LambdaQueryWrapper<>();
            databaseWrapper.eq(StudyExamPaperDatabase::getPaperId, studyExamPaper.getId());
            studyExamPaperDatabaseMapper.delete(databaseWrapper);
        }
        return true;
    }
}
