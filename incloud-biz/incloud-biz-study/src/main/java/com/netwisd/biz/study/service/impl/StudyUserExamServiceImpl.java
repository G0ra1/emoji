package com.netwisd.biz.study.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.biz.study.constants.PaperStatusEnum;
import com.netwisd.biz.study.constants.PaperTypeEnum;
import com.netwisd.biz.study.constants.YesNo;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.service.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.mapper.StudyUserExamMapper;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyUserExamDto;
import com.netwisd.biz.study.vo.StudyUserExamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.netwisd.biz.study.vo.StudyUserExamQuestionVo;
import com.netwisd.biz.study.dto.StudyUserExamQuestionDto;
import com.netwisd.biz.study.vo.StudyUserExamQuestionDetailVo;
import com.netwisd.biz.study.dto.StudyUserExamQuestionDetailDto;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 人员考试 功能描述...
 * @date 2022-05-26 17:08:29
 */
@Service
@Slf4j
public class StudyUserExamServiceImpl extends BatchServiceImpl<StudyUserExamMapper, StudyUserExam> implements StudyUserExamService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyUserExamMapper studyUserExamMapper;

    @Autowired
    private StudyUserExamQuestionService studyUserExamQuestionService;

    @Autowired
    private StudyUserExamQuestionDetailService studyUserExamQuestionDetailService;

    @Autowired
    private StudyExamPaperService studyExamPaperService;//试卷service

    @Autowired
    private  StudyXyExamService studyXyExamService;//学员考试service

    @Autowired
    private StudySpecialService studySpecialService;

    /**
     * 学员答题保存
     *
     * @param studyUserExamDto
     * @return
     */
    @Override
    @Transactional
    public Boolean save(StudyUserExamDto studyUserExamDto) {
        log.info("学员答题-保存参数：{}", studyUserExamDto);
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        if (ObjectUtil.isEmpty(studyUserExamDto)) {
            throw new IncloudException("请先答题再保存-1");
        }
        if (CollectionUtils.isEmpty(studyUserExamDto.getQuestionList())) {
            throw new IncloudException("请先答题再保存-2");
        }

        //获取专题考试次数
        StudySpecial studySpecial = studySpecialService.getById(studyUserExamDto.getSpecialId());
        Optional.ofNullable(studySpecial).orElseThrow(() -> new IncloudException("专题不存在"));
        //校验次数与是否提交
        List<StudyUserExam> studyUserExamList = studyUserExamMapper.selectList(Wrappers.<StudyUserExam>lambdaQuery()
                .eq(StudyUserExam::getSpecialId, studyUserExamDto.getSpecialId())
                .eq(StudyUserExam::getUserId, appUser.getId())
                .eq(StudyUserExam::getPaperId, studyUserExamDto.getPaperId())
                .eq(StudyUserExam::getPaperType, PaperTypeEnum.KST.code));
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(studyUserExamList)) {
            //已经考试次数>=设置次数 则不可以进行考试
            if (studyUserExamList.size() >= studySpecial.getSpecialExamNum()) {
                throw new IncloudException("考试次数已用完");
            }
            //如果有已经提交的考试则不能继续考试
            if (studyUserExamList.stream().filter(x -> x.getPaperStatus() != PaperStatusEnum.QUESTIONS_ANSWER.code).count() > 0) {
                throw new IncloudException("考试已提交不能再进行考试");
            }
        }
        StudyUserExam studyUserExam = dozerMapper.map(studyUserExamDto, StudyUserExam.class);
        //设置id
        studyUserExam.setId(IdGenerator.getIdGenerator());
        //人员答题id
        Long userExamId = studyUserExam.getId();
        //设置考生信息
        studyUserExam.setUserId(appUser.getId());
        studyUserExam.setUserName(appUser.getUserName());
        //设置时长
        studyUserExam.setSpecialExamTime(studyUserExamDto.getSpecialExamTime() / 60000);
        //设置试卷状态
        studyUserExam.setPaperStatus(PaperStatusEnum.QUESTIONS_ANSWER.code);
        //设置阅卷老师信息
        studyUserExam.setMarkingId(studySpecial.getSpecialExamPaperTeacherId());
        studyUserExam.setMarkingName(studySpecial.getSpecialExamPaperTeacherName());
        //设置答题结束时间
        studyUserExam.setAnswerEndTime(LocalDateTime.now());
        //设置保存公共信息（新增/修改时间、新增/修改人、新增/修改人name）
        studyUserExam.setCreateTime(LocalDateTime.now());
        studyUserExam.setCreateUserId(appUser.getId());
        studyUserExam.setCreateUserName(appUser.getUserNameCh());
        studyUserExam.setUpdateTime(LocalDateTime.now());
        //保存人员考试表
        boolean userExamsave = super.save(studyUserExam);
        if (userExamsave) {//保存人员答题题目表开始
            List<StudyUserExamQuestionDto> studyUserExamQuestionList = studyUserExamDto.getQuestionList();
            List<StudyUserExamQuestion> studyUserExamQuestions = new ArrayList<>();
            for (StudyUserExamQuestionDto studyUserExamQuestionDto : studyUserExamQuestionList) {
                StudyUserExamQuestion studyUserExamQuestion = dozerMapper.map(studyUserExamQuestionDto, StudyUserExamQuestion.class);
                studyUserExamQuestion.setUserExamId(userExamId);
                studyUserExamQuestion.setQuestionId(studyUserExamQuestionDto.getId());
                studyUserExamQuestion.setId(IdGenerator.getIdGenerator());
                //设置保存公共信息（新增时间、新增人、新增人name）
                studyUserExamQuestion.setCreateTime(LocalDateTime.now());
                studyUserExamQuestion.setCreateUserId(appUser.getId());
                studyUserExamQuestion.setCreateUserName(appUser.getUserName());
                studyUserExamQuestion.setUpdateTime(LocalDateTime.now());
                studyUserExamQuestions.add(studyUserExamQuestion);
                //保存人员考试题目详情表
                if (CollectionUtils.isNotEmpty(studyUserExamQuestionDto.getAnswers())) {
                    List<StudyUserExamQuestionDetailDto> studyUserExamQuestionDetailDtos = studyUserExamQuestionDto.getAnswers();
                    List<StudyUserExamQuestionDetail> studyUserExamQuestionDetails = new ArrayList<>();
                    for (StudyUserExamQuestionDetailDto studyUserExamQuestionDetailDto : studyUserExamQuestionDetailDtos) {
                        studyUserExamQuestionDetailDto.setUserExamQuestionId(studyUserExamQuestion.getId());
                        studyUserExamQuestionDetailDto.setId(IdGenerator.getIdGenerator());
                        //设置保存公共信息（新增时间、新增人、新增人name）
                        studyUserExamQuestionDetailDto.setCreateTime(LocalDateTime.now());
                        studyUserExamQuestionDetailDto.setCreateUserId(appUser.getId());
                        studyUserExamQuestionDetailDto.setCreateUserName(appUser.getUserName());
                        studyUserExamQuestionDetailDto.setUpdateTime(LocalDateTime.now());
                        StudyUserExamQuestionDetail studyUserExamQuestionDetail = dozerMapper.map(studyUserExamQuestionDetailDto, StudyUserExamQuestionDetail.class);
                        studyUserExamQuestionDetails.add(studyUserExamQuestionDetail);
                    }
                    log.info("学员答题-保存人员考试题目-详情参数：{}", studyUserExamQuestionDetails);
                    studyUserExamQuestionDetailService.saveBatch(studyUserExamQuestionDetails);
                }
            }
            //保存人员考试题目表结束
            log.info("学员答题-保存人员考试题目参数：{}", studyUserExamQuestions);
            studyUserExamQuestionService.saveBatch(studyUserExamQuestions);
            if (YesNo.YES.code.equals(studyUserExamDto.getIsSubmit())) {
                studyXyExamService.submittedPapers(userExamId);
            }
        }

        return true;
    }

    /**
     * 个人中心- 我的考试
     *
     * @param studyUserExamDto
     * @return
     */
    @Override
    public Page<StudyUserExamVo> pageList(StudyUserExamDto studyUserExamDto) {
        log.info("个人中心-我的考试查寻参数：", studyUserExamDto);
        //获取当前登陆人信息
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        //定义状态筛选
        ArrayList<Integer> list = new ArrayList<>();
        list.add(PaperStatusEnum.SUBMITTED_PAPERS.code);
        list.add(PaperStatusEnum.MARKED_PAPERS.code);
        Page<StudyUserExamVo> page = super.page(studyUserExamDto.page, Wrappers.<StudyUserExam>lambdaQuery()
                .eq(StudyUserExam::getUserId, appUser.getId())
                .eq(StudyUserExam::getUserName, appUser.getUsername())
                .like(null != studyUserExamDto.getPaperName(), StudyUserExam::getPaperName, studyUserExamDto.getPaperName())
                .eq(null != studyUserExamDto.getPaperStatus(), StudyUserExam::getPaperStatus, studyUserExamDto.getPaperStatus())
                .in(StudyUserExam::getPaperStatus, list)
                .orderByDesc(StudyUserExam::getCreateTime));
        log.info("个人中心-我的考试查寻结果：", page);
        return page;
    }

    /**
     * 个人中心-我的考试-详情
     *
     * @param id
     * @return
     */
    @Override
    public StudyUserExamVo getUserExamDetail(Long id) {
        log.info("个人中心-我的考试-详情参数：", id);
        StudyUserExam studyUserExam = super.getById(id);
        Optional.ofNullable(id).orElseThrow(() -> new IncloudException("至少选中一条记录"));
        StudyUserExamVo studyUserExamVo = dozerMapper.map(studyUserExam, StudyUserExamVo.class);
        List<StudyUserExamQuestion> studyUserExamQuestions = studyUserExamQuestionService.list(Wrappers.<StudyUserExamQuestion>lambdaQuery().eq(StudyUserExamQuestion::getUserExamId, id));
        if (CollectionUtils.isEmpty(studyUserExamQuestions)) {
            throw new IncloudException("当前试卷题目信息异常（不存在）");
        }
        List<StudyUserExamQuestionVo> studyUserExamQuestionVos = new ArrayList<>();
        for (StudyUserExamQuestion studyUserExamQuestion : studyUserExamQuestions) {
            StudyUserExamQuestionVo studyUserExamQuestionVo = dozerMapper.map(studyUserExamQuestion, StudyUserExamQuestionVo.class);
            //获取选项/答案信息
            List<StudyUserExamQuestionDetail> studyUserExamQuestionDetailList = studyUserExamQuestionDetailService.list(Wrappers.<StudyUserExamQuestionDetail>lambdaQuery()
                    .eq(StudyUserExamQuestionDetail::getUserExamQuestionId, studyUserExamQuestion.getId()));
            if (CollectionUtils.isNotEmpty(studyUserExamQuestionDetailList)) {
                List<StudyUserExamQuestionDetailVo> studyUserExamQuestionDetailVos = DozerUtils.mapList(dozerMapper, studyUserExamQuestionDetailList, StudyUserExamQuestionDetailVo.class);
                //定义字母开始值
                char sortText = 'A';
                for (StudyUserExamQuestionDetailVo studyUserExamQuestionDetailVo : studyUserExamQuestionDetailVos) {
                    studyUserExamQuestionDetailVo.setSortText(sortText);
                    sortText = (char) ((char) sortText + 1);
                }
                studyUserExamQuestionVo.setAnswers(studyUserExamQuestionDetailVos);
            }
            studyUserExamQuestionVos.add(studyUserExamQuestionVo);
        }
        studyUserExamVo.setQuestionList(studyUserExamQuestionVos);
        return studyUserExamVo;
    }

}
