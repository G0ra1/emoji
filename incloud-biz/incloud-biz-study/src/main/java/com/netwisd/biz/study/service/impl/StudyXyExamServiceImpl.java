package com.netwisd.biz.study.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.constants.*;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudyXyExamServiceImpl implements StudyXyExamService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyExamPaperService studyExamPaperService;//试卷service

    @Autowired
    private StudyExamPaperQuestionService studyExamPaperQuestionService;//试卷题目service

    @Autowired
    private StudyExamQuestionService studyExamQuestionService;//题目service

    @Autowired
    private StudyExamPaperDatabaseService studyExamPaperDatabaseService;//试卷题库service

    @Autowired
    private StudyExamQuestionAnswerService studyExamQuestionAnswerService;//试卷题目详情表service

    @Autowired
    private StudyUserExamService studyUserExamService;

    /**
     * 生成试卷
     *
     * @param paperId 试卷id
     * @return 考试试卷vo
     */
    @Override
    public StudyExamPaperVo generatePaper(Long paperId) {
        log.info("生成试卷-参数：{}", paperId);
        //通过试卷id获取 题目id集合/题库id集合
        StudyExamPaper studyExamPaper = studyExamPaperService.getById(paperId);
        if (ObjectUtil.isNull(studyExamPaper)) {
            throw new IncloudException(paperId + "：试卷不存在");
        }
        StudyExamPaperVo studyExamPaperVo = dozerMapper.map(studyExamPaper, StudyExamPaperVo.class);
        if (ObjectUtil.isNull(studyExamPaperVo.getPaperCode())) {
            throw new IncloudException("出题类型字段不可为空！");
        }
        if (PaperCodeEnum.GDSJ.getCode().equals(studyExamPaperVo.getPaperCode())) {
            log.info("---------------生成试卷-固定试卷开始---------");
            //固定试卷（试卷题目表）
            //通过试卷id查试卷题目表(试卷题目集合)(并通过序号排序)
            List<StudyExamPaperQuestion> studyExamPaperQuestions = studyExamPaperQuestionService.list(Wrappers.<StudyExamPaperQuestion>lambdaQuery()
                    .eq(StudyExamPaperQuestion::getPaperId, paperId)
                    .orderByAsc(StudyExamPaperQuestion::getSortNumber)
            );
            if (CollectionUtils.isEmpty(studyExamPaperQuestions)) {
                throw new IncloudException(paperId + "：固定试卷-题目信息不存在");
            }
            //定义题目集合
            List<StudyExamQuestionVo> studyExamQuestionVos = new ArrayList<>();
            for (StudyExamPaperQuestion studyExamPaperQuestion : studyExamPaperQuestions) {
                //通过题目id获取题目信息
                StudyExamQuestionVo studyExamQuestionVo = studyExamQuestionService.get(studyExamPaperQuestion.getQuestionId());
                List<StudyExamQuestionAnswer> studyExamQuestionAnswers = studyExamQuestionAnswerService.list(Wrappers.<StudyExamQuestionAnswer>lambdaQuery()
                        .eq(StudyExamQuestionAnswer::getQuestionId, studyExamQuestionVo.getId())
                        .orderByAsc(StudyExamQuestionAnswer::getSort));
                //转换类型（返回带答案的vo）
                List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVosList = DozerUtils.mapList(dozerMapper, studyExamQuestionAnswers, StudyExamQuestionAnswerVo.class);
                //给单选,多选题 设置选项前的A,B,C,D 等
                if (studyExamPaperQuestion.getQuestionCode().equals(QuestionCode.MULTIPLE_CHOICE.code) || studyExamPaperQuestion.getQuestionCode().equals(QuestionCode.SINGLE_CHOICE.code)) {
                 //定义字母开始值
                    char sortText = 'A';
                    for (StudyExamQuestionAnswerVo studyExamQuestionAnswerVos : studyExamQuestionAnswerVosList) {
                        studyExamQuestionAnswerVos.setSortText(sortText);
                        sortText = (char) (sortText + 1);
                    }
                }
                if (studyExamPaperQuestion.getQuestionCode().equals(QuestionCode.JUDGMENT.code)){
                    if (studyExamQuestionAnswerVosList.get(0).getIsTrue().equals(YesNo.NO.code))
                    studyExamQuestionAnswerVosList.get(0).setIsTrue(YesNo.YES.code);
                }
                studyExamQuestionVo.setAnswers(studyExamQuestionAnswerVosList);
                //将分值传入
                studyExamQuestionVo.setQuestionScore(studyExamPaperQuestion.getQuestionScore());
                //将序号传入
                studyExamQuestionVo.setSortNumber(studyExamPaperQuestion.getSortNumber());
                studyExamQuestionVos.add(studyExamQuestionVo);
            }
            //设置答题开始时间
            studyExamPaperVo.setAnswerStartTime(LocalDateTime.now());
            studyExamPaperVo.setQuestionList(studyExamQuestionVos);
            log.info("---------------生成试卷-固定试卷结束---------");

        } else {
            log.info("---------------生成试卷-随机试卷开始---------");
            //获取试卷规则信息--三元运算法 （当试卷规则为空时：赋值试卷规则为0）
            Integer singleNumber = null == studyExamPaperVo.getSingleNumber() ? 0 : studyExamPaperVo.getSingleNumber();//单选
            Integer multipleNumber = null == studyExamPaperVo.getMultipleNumber() ? 0 : studyExamPaperVo.getMultipleNumber();//多选
            Integer judgmentNumber = null == studyExamPaperVo.getJudgmentNumber() ? 0 : studyExamPaperVo.getJudgmentNumber();//
            Integer completionNumber = null == studyExamPaperVo.getCompletionNumber() ? 0 : studyExamPaperVo.getCompletionNumber();
            Integer shortAnswerNumber = null == studyExamPaperVo.getShortAnswerNumber() ? 0 : studyExamPaperVo.getShortAnswerNumber();
            //通过试卷id查试卷题库表
            List<StudyExamPaperDatabase> studyExamPaperDatabases = studyExamPaperDatabaseService.list(Wrappers.<StudyExamPaperDatabase>lambdaQuery().eq(StudyExamPaperDatabase::getPaperId, paperId));
            if (CollectionUtils.isEmpty(studyExamPaperDatabases)) {
                throw new IncloudException(paperId + "：随机试卷-题库不存在");
            }
            //存放各种类型题的集合
            List<StudyExamQuestionVo> examQuestionVos = new ArrayList<>();
            //获取题库id
            List<Long> databaseIds = studyExamPaperDatabases.stream().map(StudyExamPaperDatabase::getDatabaseId).collect(Collectors.toList());
            //通过题库id获取单选题目信息
            List<StudyExamQuestion> singleList = studyExamQuestionService.list(Wrappers.<StudyExamQuestion>lambdaQuery()
                    .eq(StudyExamQuestion::getQuestionCode, QuestionCode.SINGLE_CHOICE.code)
                    .in(StudyExamQuestion::getDatabaseId, databaseIds)
            );
            if (CollectionUtils.isNotEmpty(singleList)) {
                //打乱顺序
                Collections.shuffle(singleList);
                //截取
                List<StudyExamQuestion> singles = singleList.subList(0, singleNumber);
                for (StudyExamQuestion studyExamQuestion : singles) {
                    //转换类型
                    StudyExamQuestionVo studyExamQuestionVo = dozerMapper.map(studyExamQuestion, StudyExamQuestionVo.class);
                    //获取题目的选项
                    List<StudyExamQuestionAnswer> studyExamQuestionAnswers = studyExamQuestionAnswerService.list(Wrappers.<StudyExamQuestionAnswer>lambdaQuery()
                            .eq(StudyExamQuestionAnswer::getQuestionId, studyExamQuestionVo.getId())
                            .orderByAsc(StudyExamQuestionAnswer::getSort));
                    //定义返回选项信息集合
                    ArrayList<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVoList = new ArrayList<>();
                    //定义字母开始值
                    char sortText = 'A';
                    for (StudyExamQuestionAnswer studyExamQuestionAnswer : studyExamQuestionAnswers) {
                        StudyExamQuestionAnswerVo examQuestionAnswerVos = dozerMapper.map(studyExamQuestionAnswer, StudyExamQuestionAnswerVo.class);
                        examQuestionAnswerVos.setSortText(sortText);
                        studyExamQuestionAnswerVoList.add(examQuestionAnswerVos);
                        sortText = (char) (sortText + 1);
                    }
                    //将选项放进当前题目中
                    studyExamQuestionVo.setAnswers(studyExamQuestionAnswerVoList);
                    //设置分值
                    studyExamQuestionVo.setQuestionScore(studyExamPaperVo.getSingleScore());
                    examQuestionVos.add(studyExamQuestionVo);
                }
            }
            //通过题库id获取多选题目信息
            List<StudyExamQuestion> multipleChoiceList = studyExamQuestionService.list(Wrappers.<StudyExamQuestion>lambdaQuery()
                    .eq(StudyExamQuestion::getQuestionCode, QuestionCode.MULTIPLE_CHOICE.code)
                    .in(StudyExamQuestion::getDatabaseId, databaseIds)
            );
            if (CollectionUtils.isNotEmpty(multipleChoiceList)) {
                //打乱顺序
                Collections.shuffle(multipleChoiceList);
                //截取
                List<StudyExamQuestion> multipleChoices = multipleChoiceList.subList(0, multipleNumber);
                for (StudyExamQuestion multipleChoice : multipleChoices) {
                    //转换类型
                    StudyExamQuestionVo studyExamQuestionVo = dozerMapper.map(multipleChoice, StudyExamQuestionVo.class);
                    //获取题目的选项
                    List<StudyExamQuestionAnswer> studyExamQuestionAnswers = studyExamQuestionAnswerService.list(Wrappers.<StudyExamQuestionAnswer>lambdaQuery()
                            .eq(StudyExamQuestionAnswer::getQuestionId, studyExamQuestionVo.getId())
                            .orderByAsc(StudyExamQuestionAnswer::getSort));
                    //定义返回选项信息集合
                    ArrayList<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVoList = new ArrayList<>();
                    //定义字母开始值
                    char sortText = 'A';
                    for (StudyExamQuestionAnswer studyExamQuestionAnswer : studyExamQuestionAnswers) {
                        StudyExamQuestionAnswerVo examQuestionAnswerVos = dozerMapper.map(studyExamQuestionAnswer, StudyExamQuestionAnswerVo.class);
                        examQuestionAnswerVos.setSortText(sortText);
                        studyExamQuestionAnswerVoList.add(examQuestionAnswerVos);
                        sortText = (char) (sortText + 1);
                    }
                    //将选项放进当前题目中
                    studyExamQuestionVo.setAnswers(studyExamQuestionAnswerVoList);
                    //设置分值
                    studyExamQuestionVo.setQuestionScore(studyExamPaperVo.getMultipleScore());
                    examQuestionVos.add(studyExamQuestionVo);
                }
            }
            //通过题库id获取判断题目信息
            List<StudyExamQuestion> judgmentList = studyExamQuestionService.list(Wrappers.<StudyExamQuestion>lambdaQuery()
                    .eq(StudyExamQuestion::getQuestionCode, QuestionCode.JUDGMENT.code)
                    .in(StudyExamQuestion::getDatabaseId, databaseIds)
            );
            if (CollectionUtils.isNotEmpty(judgmentList)) {
                //打乱顺序
                Collections.shuffle(judgmentList);
                //截取
                List<StudyExamQuestion> judgments = judgmentList.subList(0, judgmentNumber);
                for (StudyExamQuestion judgment : judgments) {
                    //转换类型
                    StudyExamQuestionVo studyExamQuestionVo = dozerMapper.map(judgment, StudyExamQuestionVo.class);
                    //获取题目的选项
                    List<StudyExamQuestionAnswer> studyExamQuestionAnswers = studyExamQuestionAnswerService.list(Wrappers.<StudyExamQuestionAnswer>lambdaQuery()
                            .eq(StudyExamQuestionAnswer::getQuestionId, studyExamQuestionVo.getId()));
                    //设置正确答案
                    studyExamQuestionAnswers.forEach(xamQuestionAnswer -> xamQuestionAnswer.setIsTrue(YesNo.YES.code));
                    //将选项放进当前题目中
                    studyExamQuestionVo.setAnswers(studyExamQuestionAnswers);
                    //设置分值
                    studyExamQuestionVo.setQuestionScore(studyExamPaperVo.getJudgmentScore());
                    examQuestionVos.add(studyExamQuestionVo);
                }
            }
            //通过题库id获取填空题目信息
            List<StudyExamQuestion> completionList = studyExamQuestionService.list(Wrappers.<StudyExamQuestion>lambdaQuery()
                    .eq(StudyExamQuestion::getQuestionCode, QuestionCode.COMPLETION.code)
                    .in(StudyExamQuestion::getDatabaseId, databaseIds)
            );
            if (CollectionUtils.isNotEmpty(completionList)) {
                //打乱顺序
                Collections.shuffle(completionList);
                //截取
                List<StudyExamQuestion> completions = completionList.subList(0, completionNumber);
                List<StudyExamQuestionVo> completionQuestionVos = DozerUtils.mapList(dozerMapper, completions, StudyExamQuestionVo.class);
                List<Long> questionIdList = completionQuestionVos.stream().map(StudyExamQuestionVo::getId).collect(Collectors.toList());
                List<StudyExamQuestionAnswer> questionAnswers = studyExamQuestionAnswerService.list(Wrappers.<StudyExamQuestionAnswer>lambdaQuery()
                        .in(StudyExamQuestionAnswer::getQuestionId, questionIdList));
                Map<Long, List<StudyExamQuestionAnswer>> map = questionAnswers.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswer::getQuestionId));
                for (StudyExamQuestionVo questionVo : completionQuestionVos){
                    List<StudyExamQuestionAnswer> studyExamQuestionAnswers = map.get(questionVo.getId());
                    questionVo.setAnswers(studyExamQuestionAnswers);
                }
                //设置分值
                completionQuestionVos.forEach(examQuestionVo -> examQuestionVo.setQuestionScore(studyExamPaperVo.getCompletionScore()));
                examQuestionVos.addAll(completionQuestionVos);
            }
            //通过题库id获取简答题目信息
            List<StudyExamQuestion> shortAnswerList = studyExamQuestionService.list(Wrappers.<StudyExamQuestion>lambdaQuery()
                    .eq(StudyExamQuestion::getQuestionCode, QuestionCode.SHORT_ANSWER.code)
                    .in(StudyExamQuestion::getDatabaseId, databaseIds)
            );
            if (CollectionUtils.isNotEmpty(shortAnswerList)) {
                //打乱顺序
                Collections.shuffle(shortAnswerList);
                //截取
                List<StudyExamQuestion> shortAnswers = shortAnswerList.subList(0, shortAnswerNumber);
                List<StudyExamQuestionVo> shortAnswerQuestionVos = DozerUtils.mapList(dozerMapper, shortAnswers, StudyExamQuestionVo.class);
                List<Long> questionIdList = shortAnswerQuestionVos.stream().map(StudyExamQuestionVo::getId).collect(Collectors.toList());
                List<StudyExamQuestionAnswer> questionAnswers = studyExamQuestionAnswerService.list(Wrappers.<StudyExamQuestionAnswer>lambdaQuery()
                        .in(StudyExamQuestionAnswer::getQuestionId, questionIdList));
                Map<Long, List<StudyExamQuestionAnswer>> map = questionAnswers.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswer::getQuestionId));
                for (StudyExamQuestionVo questionVo : shortAnswerQuestionVos){
                    List<StudyExamQuestionAnswer> studyExamQuestionAnswers = map.get(questionVo.getId());
                    questionVo.setAnswers(studyExamQuestionAnswers);
                }
                //设置分值
                shortAnswerQuestionVos.forEach(examQuestionVo -> examQuestionVo.setQuestionScore(studyExamPaperVo.getShortAnswerScore()));
                examQuestionVos.addAll(shortAnswerQuestionVos);
            }
            //排序（题目类型）
            if (CollectionUtils.isNotEmpty(examQuestionVos)) {

                //利用Java8的stream流和Comparator实现集合排序
                examQuestionVos.stream().sorted(Comparator.comparing(StudyExamQuestionVo::getQuestionCode)).collect(Collectors.toList());
                //定义修改为被引用的题目数据集合
                List<StudyExamQuestion> studyExamQuestions = new ArrayList<>();
                //给每一条题目设置序号
                examQuestionVos.forEach(examQuestionVo -> {
                    examQuestionVo.setSortNumber(examQuestionVos.indexOf(examQuestionVo) + 1);
                    examQuestionVo.setIsQuote(YesNo.YES.code);
                    studyExamQuestions.add(dozerMapper.map(examQuestionVo, StudyExamQuestion.class));
                });
                //修改为被引用的题目数据集合
                studyExamQuestionService.updateBatchById(studyExamQuestions);
                log.info("生成试卷-随机试卷获取题目信息-完成:{}", examQuestionVos);
                studyExamPaperVo.setQuestionList(examQuestionVos);
            }
            log.info("---------------生成试卷-随机试卷结束---------");
        }
        studyExamPaperVo.setAnswerStartTime(LocalDateTime.now());
        return studyExamPaperVo;
    }

    /**
     * 提交试卷
     *
     * @param userExamId
     * @return Boolean
     */
    @Override
    public Boolean submittedPapers(Long userExamId) {
        if (null==userExamId){
            throw new IncloudException("请选择一条考试记录提交");
        }
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        log.info(appUser.getUserName() + "提交试卷[" + userExamId + "]成功");
        //获取试卷id
        StudyUserExam studyUserExam = studyUserExamService.getById(userExamId);
        //获取试卷信息
        StudyExamPaper studyExamPaper = studyExamPaperService.getById(studyUserExam.getPaperId());
        //判断当前试卷是否包含简答题
        if (YesNo.NO.code == studyExamPaper.getIsHaveShortAnswer()) {//否：自动阅卷   给出分数、分值 、修改状态为以阅卷
            studyExamPaperService.autoMarkPaper(userExamId);//自动阅卷
           if (!studyExamPaperService.autoMarkPaper(userExamId)){//如果自动阅卷失败进老师阅卷
               log.info("自动阅卷失败，交由老师阅卷;参数:{}",userExamId);
               return studyUserExamService.update(Wrappers.<StudyUserExam>lambdaUpdate()
                       .eq(StudyUserExam::getId, userExamId)
                       .set(StudyUserExam::getPaperStatus, PaperStatusEnum.SUBMITTED_PAPERS.code)
                       .set(StudyUserExam::getHandTime, LocalDateTime.now())
                       .set(StudyUserExam::getIsSubmit,YesNo.YES.code)
               );
           }
            log.info("自动阅卷成功，修改阅卷状态为以阅卷;参数:{}",userExamId);
            return studyUserExamService.update(Wrappers.<StudyUserExam>lambdaUpdate()//修改试卷状态--已阅卷
                    .eq(StudyUserExam::getId, userExamId)
                    .set(StudyUserExam::getPaperStatus, PaperStatusEnum.MARKED_PAPERS.code)
                    .set(StudyUserExam::getMarkingTime, LocalDateTime.now())
            );
        } else {  //有：修改阅卷状态--已交卷
            return studyUserExamService.update(Wrappers.<StudyUserExam>lambdaUpdate()
                    .eq(StudyUserExam::getId, userExamId)
                    .set(StudyUserExam::getPaperStatus, PaperStatusEnum.SUBMITTED_PAPERS.code)
                    .set(StudyUserExam::getHandTime, LocalDateTime.now())
                    .set(StudyUserExam::getIsSubmit,YesNo.YES.code)
            );

        }
    }
}
