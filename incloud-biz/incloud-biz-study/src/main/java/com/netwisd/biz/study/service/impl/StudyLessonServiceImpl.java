package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.user.eunm.EnableStateEnum;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.biz.study.constants.RangeTypeEnum;
import com.netwisd.biz.study.constants.StudyStatus;
import com.netwisd.biz.study.constants.UseRangeEnum;
import com.netwisd.biz.study.dto.StudyLessonCouDto;
import com.netwisd.biz.study.dto.StudyLessonDto;
import com.netwisd.biz.study.dto.StudyLessonMarterialsDto;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.fegin.WfClient;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.*;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.util.StudyTimeUtil;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ???????????? lhy@netwisd.com
 * @Description ????????? ????????????...
 * @date 2022-04-19 19:15:31
 */
@Service
@Slf4j
public class StudyLessonServiceImpl extends ServiceImpl<StudyLessonMapper, StudyLesson> implements StudyLessonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonMapper studyLessonMapper;

    @Autowired
    private StudyLessonCouMapper studyLessonCouMapper;

    @Autowired
    private StudyLessonCouService studyLessonCouService;

    @Autowired
    private StudyCouMapper studyCouMapper;

    @Autowired
    private StudyLessonMarterialsMapper studyLessonMarterialsMapper;

    @Autowired
    private StudyLessonMarterialsService studyLessonMarterialsService;

    @Autowired
    private StudyMarterialsMapper studyMarterialsMapper;

    @Autowired
    private StudyUserStudyRecordsMapper studyUserStudyRecordsMapper;

    @Autowired
    private StudySpecialLessonMapper studySpecialLessonMapper;

    @Autowired
    private StudyExamPaperMapper studyExamPaperMapper;

    @Autowired
    private StudySpecialLessonCouMapper studySpecialLessonCouMapper;

    @Autowired
    private StudyLessonCouPermMapper studyLessonCouPermMapper;

    @Autowired
    private StudyLessonCouPermService studyLessonCouPermService;

    @Autowired
    private StudyLessonScoreService studyLessonScoreService;

    @Autowired
    private StudySpecialMapper studySpecialMapper;

    @Autowired
    private MdmClient mdmClient;

    @Autowired
    private WfClient wfClient;

    /**
     * ????????????????????????
     *
     * @param studyLessonDto
     * @return
     */
    @Override
    public Page list(StudyLessonDto studyLessonDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper<StudyLesson> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(studyLessonDto.getLabelCode())) {
            String[] labelCodeList = studyLessonDto.getLabelCode().split(",");
            for (String labelCode : labelCodeList) {
                queryWrapper.or().like(StudyLesson::getLabelCode, labelCode);
            }
        }
        queryWrapper.eq(StringUtils.isNotBlank(studyLessonDto.getTypeCode()), StudyLesson::getTypeCode, studyLessonDto.getTypeCode());
        queryWrapper.like(StringUtils.isNotBlank(studyLessonDto.getLessonName()), StudyLesson::getLessonName, studyLessonDto.getLessonName());
        queryWrapper.eq(studyLessonDto.getIsEnable()!=null,StudyLesson::getIsEnable,studyLessonDto.getIsEnable());
        queryWrapper.eq(studyLessonDto.getIsIndex() != null, StudyLesson::getIsIndex, studyLessonDto.getIsIndex());
        queryWrapper.orderByDesc(StudyLesson::getCreateTime);
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //??????????????????????????????????????????
            queryWrapper.eq(StudyLesson::getCreateUserId, loginAppUser.getId());
        }
        Page<StudyLesson> page = studyLessonMapper.selectPage(studyLessonDto.getPage(), queryWrapper);
        Page<StudyLessonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyLessonVo.class);
        log.debug("????????????:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * ?????????????????????
     *
     * @param studyLessonDto
     * @return
     */
    @Override
    public List<StudyLessonForSpecialVo> lists(StudyLessonDto studyLessonDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper<StudyLesson> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(studyLessonDto.getLabelCode())) {
            String[] labelCodeList = studyLessonDto.getLabelCode().split(",");
            for (String labelCode : labelCodeList) {
                queryWrapper.or().like(StudyLesson::getLabelCode, labelCode);
            }
        }
        queryWrapper.eq(StringUtils.isNotBlank(studyLessonDto.getTypeCode()), StudyLesson::getTypeCode, studyLessonDto.getTypeCode());
        queryWrapper.like(StringUtils.isNotBlank(studyLessonDto.getLessonName()), StudyLesson::getLessonName, studyLessonDto.getLessonName());
        queryWrapper.eq(StudyLesson::getIsEnable, EnableStateEnum.ENABLE.code);
        queryWrapper.eq(StudyLesson::getStatus, StudyStatus.TAKE_EFFECT.code);
        queryWrapper.orderByDesc(StudyLesson::getCreateTime);
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //??????????????????????????????????????????
            queryWrapper.eq(StudyLesson::getCreateUserId, loginAppUser.getId());
        }
        List<StudyLesson> studyLessons = studyLessonMapper.selectList(queryWrapper);
        List<StudyLessonForSpecialVo> lessonVoList = DozerUtils.mapList(dozerMapper, studyLessons, StudyLessonForSpecialVo.class);
        if (CollectionUtils.isNotEmpty(lessonVoList)) {
            //?????????????????????????????????
            for (StudyLessonForSpecialVo lessonVo : lessonVoList) {
                this.findSonListForSpecial(lessonVo);
            }
        }
        return lessonVoList;
    }

    /**
     * ??????ID????????????
     *
     * @param id
     * @return
     */
    @Override
    public StudyLessonVo get(Long id) {
        StudyLesson studyLesson = super.getById(id);
        StudyLessonVo studyLessonVo = null;
        if (studyLesson != null) {
            studyLessonVo = dozerMapper.map(studyLesson, StudyLessonVo.class);
            findLessonCouAndMarterials(studyLessonVo);
        }
        return studyLessonVo;
    }

    /**
     * ????????????id????????????????????????
     *
     * @param id
     * @return
     */
    @Override
    public Boolean updateIsIndex(Long id) {
        StudyLesson studyLesson = studyLessonMapper.selectById(id);
        if (!studyLesson.getAuditStatus().equals(WfProcessEnum.DONE.getType())) {
            throw new IncloudException("?????????????????????????????????????????????");
        }
        if (studyLesson.getIsIndex().equals(YesNo.YES.code)) {
            studyLesson.setIsIndex(YesNo.NO.code);
        } else {
            studyLesson.setIsIndex(YesNo.YES.code);
        }
        studyLessonMapper.updateById(studyLesson);
        return true;
    }

    /**
     * ????????????id??????????????????
     *
     * @param id
     * @return
     */
    @Override
    public Boolean updateIsEnable(Long id) {
        StudyLesson studyLesson = studyLessonMapper.selectById(id);
        if (!studyLesson.getAuditStatus().equals(WfProcessEnum.DONE.getType())) {
            throw new IncloudException("?????????????????????????????????????????????");
        }
        if (studyLesson.getIsEnable().equals(YesNo.YES.code)) {
            studyLesson.setIsEnable(YesNo.NO.code);
        } else {
            studyLesson.setIsEnable(YesNo.YES.code);
        }
        studyLessonMapper.updateById(studyLesson);
        return true;
    }

    @Override
    @Transactional
    public StudyLessonVo procSaveOrUpdate(StudyLessonDto lessonDto) {
        this.checkData(lessonDto);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("??????????????????"));
        lessonDto.setCreateUserName(loginAppUser.getUserNameCh());
        lessonDto.setIsEnable(YesNo.NO.code);
        StudyLesson lesson = dozerMapper.map(lessonDto, StudyLesson.class);
        super.saveOrUpdate(lesson);
        //??????????????????
        if (CollectionUtils.isNotEmpty(lessonDto.getCouList())) {
            //1.??????
            LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
            lessonCouWrapper.eq(StudyLessonCou::getLessonId,lesson.getId());
            studyLessonCouMapper.delete(lessonCouWrapper);
            //2.??????
            List<StudyLessonCouDto> couDtoList = lessonDto.getCouList();
            couDtoList.forEach(couDto->{
                couDto.setId(IdGenerator.getIdGenerator());
                couDto.setLessonId(lesson.getId());
                //???????????????????????? ????????????????????????
                if (UseRangeEnum.SIYOU.code.equals(couDto.getUseRange())) {
                    this.saveLessonCouPerm(couDto);
                }
            });
            List<StudyLessonCou> lessonCouList = DozerUtils.mapList(dozerMapper, couDtoList, StudyLessonCou.class);
            studyLessonCouService.saveBatch(lessonCouList);
        }
        //??????????????????
        if (CollectionUtils.isNotEmpty(lessonDto.getMarterialsList())) {
            //1.??????
            LambdaQueryWrapper<StudyLessonMarterials> lessonMarterialsWrapper = new LambdaQueryWrapper<>();
            lessonMarterialsWrapper.eq(StudyLessonMarterials::getLessonId,lesson.getId());
            studyLessonMarterialsMapper.delete(lessonMarterialsWrapper);
            //2.??????
            List<StudyLessonMarterialsDto> marterialsDtoList = lessonDto.getMarterialsList();
            marterialsDtoList.forEach(marterials -> {
                marterials.setId(IdGenerator.getIdGenerator());
                marterials.setLessonId(lesson.getId());
            });
            List<StudyLessonMarterials> studyLessonMarterials = DozerUtils.mapList(dozerMapper, marterialsDtoList, StudyLessonMarterials.class);
            studyLessonMarterialsService.saveBatch(studyLessonMarterials);
        }
        return this.get(lesson.getId());
    }

    @Override
    public StudyLessonVo procDetail(String procInstId) {
        StudyLesson studyLesson = this.getByProcInstId(procInstId);
        StudyLessonVo lessonVo = dozerMapper.map(studyLesson, StudyLessonVo.class);
        this.findLessonCouAndMarterials(lessonVo);
        return lessonVo;
    }

    @Override
    public StudyLessonAdjVo detailForAdj(Long id) {
        StudyLesson lesson = super.getById(id);
        if (StudyStatus.NO_TAKE_EFFECT.code.equals(lesson.getStatus())) {
            throw new IncloudException("???????????????????????????????????????????????????");
        }
        if (StudyStatus.IN_UPDATE.code.equals(lesson.getStatus())) {
            throw new IncloudException("?????????????????????????????????????????????????????????");
        }
        StudyLessonAdjVo lessonAdjVo = dozerMapper.map(lesson, StudyLessonAdjVo.class);
        if (ObjectUtils.isNotEmpty(lessonAdjVo)) {
            //????????????
            LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
            lessonCouWrapper.eq(StudyLessonCou::getLessonId, id);
            List<StudyLessonCou> lessonCous = studyLessonCouMapper.selectList(lessonCouWrapper);
            List<StudyLessonAdjCouVo> lessonAdjCouVos = DozerUtils.mapList(dozerMapper, lessonCous, StudyLessonAdjCouVo.class);
            //????????????????????????
            this.setLessonAdjCouPerms(lessonAdjCouVos,id);
            lessonAdjVo.setCouList(lessonAdjCouVos);
            //??????????????????
            LambdaQueryWrapper<StudyLessonMarterials> lessonMarterialsWrapper = new LambdaQueryWrapper<>();
            lessonMarterialsWrapper.eq(StudyLessonMarterials::getLessonId, id);
            List<StudyLessonMarterials> lessonMarterials = studyLessonMarterialsMapper.selectList(lessonMarterialsWrapper);
            List<StudyLessonAdjMarterialsVo> lessonAdjMarterialsVos = DozerUtils.mapList(dozerMapper, lessonMarterials, StudyLessonAdjMarterialsVo.class);
            lessonAdjVo.setMarterialsList(lessonAdjMarterialsVos);
        }
        lessonAdjVo.setLinkId(lesson.getId());
        this.dataEmpty(lessonAdjVo);
        return lessonAdjVo;
    }

    @Override
    public Boolean procAfterSubmit(String procInstId) {
        //??????????????????
        StudyLesson studyLesson = this.getByProcInstId(procInstId);
        studyLesson.setAuditSubmitTime(LocalDateTime.now());
        if (ObjectUtils.isEmpty(studyLesson.getStatus())) {//?????????????????????
            studyLesson.setStatus(StudyStatus.NO_TAKE_EFFECT.code);
        }
        studyLessonMapper.updateById(studyLesson);
        return true;
    }

    @Override
    @Transactional
    public Boolean procAuditSuccess(String procInstId) {
        //????????????????????????????????????????????????????????????
        StudyLesson studyLesson = this.getByProcInstId(procInstId);
        studyLesson.setStatus(StudyStatus.TAKE_EFFECT.code);
        studyLesson.setIsEnable(YesNo.YES.code);
        studyLesson.setIsIndex(YesNo.NO.code);
        studyLesson.setAuditSuccessTime(LocalDateTime.now());
        studyLessonMapper.updateById(studyLesson);
        return true;
    }

    @Override
    @Transactional
    public Boolean procDelete(String procInstId) {
        StudyLesson lesson = this.getByProcInstId(procInstId);
        studyLessonMapper.deleteById(lesson.getId());
        //???????????????????????????
        LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
        lessonCouWrapper.eq(StudyLessonCou::getLessonId, lesson.getId());
        studyLessonCouMapper.delete(lessonCouWrapper);
        //?????????????????????????????????
        LambdaQueryWrapper<StudyLessonCouPerm> lessonCouPermWrapper = new LambdaQueryWrapper<>();
        lessonCouPermWrapper.eq(StudyLessonCouPerm::getLessonId,lesson.getId());
        studyLessonCouPermMapper.delete(lessonCouPermWrapper);
        //???????????????????????????
        LambdaQueryWrapper<StudyLessonMarterials> lessonMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonMarterialsWrapper.eq(StudyLessonMarterials::getLessonId, lesson.getId());
        studyLessonMarterialsMapper.delete(lessonMarterialsWrapper);
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Long lessonId) {
        StudyLesson lesson = this.getById(lessonId);
        if (!lesson.getAuditStatus().equals(WfProcessEnum.DRAFT.getType())) {
            throw new IncloudException("????????????????????????????????????");
        }
        studyLessonMapper.deleteById(lesson.getId());
        //???????????????????????????
        LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
        lessonCouWrapper.eq(StudyLessonCou::getLessonId, lesson.getId());
        studyLessonCouMapper.delete(lessonCouWrapper);
        //?????????????????????????????????
        LambdaQueryWrapper<StudyLessonCouPerm> lessonCouPermWrapper = new LambdaQueryWrapper<>();
        lessonCouPermWrapper.eq(StudyLessonCouPerm::getLessonId,lesson.getId());
        studyLessonCouPermMapper.delete(lessonCouPermWrapper);
        //???????????????????????????
        LambdaQueryWrapper<StudyLessonMarterials> lessonMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonMarterialsWrapper.eq(StudyLessonMarterials::getLessonId, lesson.getId());
        studyLessonMarterialsMapper.delete(lessonMarterialsWrapper);
        //??????????????????
        WfEngineDto.StateDto stateDto = new WfEngineDto.StateDto();
        stateDto.setCamundaProcdefId(lesson.getCamundaProcinsId());
        stateDto.setCamundaProcinsId(lesson.getCamundaProcinsId());
        wfClient.deleteOnlyProcess(stateDto);
        return true;
    }

    @Override
    public StudySpecialLessonDetailVo getLessonDetailForSpecial(Long specialId,Long lessonId) {
        if (ObjectUtils.isEmpty(specialId)) {
            throw new IncloudException("????????????id");
        }
        if (ObjectUtils.isEmpty(lessonId)) {
            throw new IncloudException("????????????id");
        }
        //1.????????????
        //1.1??????????????????
        StudyLesson lesson = super.getById(lessonId);
        StudySpecialLessonDetailVo specialLessonDetailVo = dozerMapper.map(lesson, StudySpecialLessonDetailVo.class);
        //1.2???????????? ???????????????????????? ????????????
        //1.3????????????
        specialLessonDetailVo.setScore(studyLessonScoreService.getLessonScore(lessonId));
        //1.4?????????????????????
        specialLessonDetailVo.setIsScore(studyLessonScoreService.getIsScore(lessonId));
        //????????????
        StudySpecial studySpecial = studySpecialMapper.selectById(specialId);
        specialLessonDetailVo.setSpecialLecturer(studySpecial.getSpecialLecturer());
        //2.????????????
        //2.1????????????????????????????????? ?????????????????????????????????id??????
        LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
        lessonCouWrapper.eq(StudyLessonCou::getLessonId,lessonId);
        List<StudyLessonCou> lessonCous = studyLessonCouMapper.selectList(lessonCouWrapper);
        List<Long> couIds = lessonCous.stream().map(StudyLessonCou::getCouId).collect(Collectors.toList());//????????????id??????
        Map<Long, List<StudyLessonCou>> couIdMap = lessonCous.stream().collect(Collectors.groupingBy(StudyLessonCou::getCouId));//????????????id??????
        //2.2????????????id?????? ???????????????????????????
        LambdaQueryWrapper<StudyCou> couWrapper = new LambdaQueryWrapper<>();
        couWrapper.in(StudyCou::getId,couIds);
        List<StudyCou> cous = studyCouMapper.selectList(couWrapper);
        List<StudySpecialLessonCouDetailVo> specialLessonCouDetailVos = DozerUtils.mapList(dozerMapper, cous, StudySpecialLessonCouDetailVo.class);
        specialLessonDetailVo.setCouSize(specialLessonCouDetailVos.size());
        //1.2???????????? ??????????????????????????? ???????????? ??????????????????
        long lessonVideoTimes = cous.stream().filter(x->x.getStudyTime()!=null).mapToLong(StudyCou::getStudyTime).sum();
        String lessonVideoTimesStr = StudyTimeUtil.buildStudyTimeStr(lessonVideoTimes);//???????????????????????????????????????
        specialLessonDetailVo.setLessonStudyTime(lessonVideoTimesStr);
        //2.3????????????id,??????id?????????id?????????????????????????????????????????? ???????????????id?????? ???????????????????????????
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        LambdaQueryWrapper<StudyUserStudyRecords> userStudyRecordsWrapper = new LambdaQueryWrapper<>();
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getUserId,loginAppUser.getId());
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getSpecialId,specialId);
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getLessonId,lessonId);
        List<StudyUserStudyRecords> userStudyRecords = studyUserStudyRecordsMapper.selectList(userStudyRecordsWrapper);
        Map<Long, List<StudyUserStudyRecords>> couStudyRecordsMap = userStudyRecords.stream().collect(Collectors.groupingBy(StudyUserStudyRecords::getCouId));
        //2.4????????????id?????????id??????????????????????????????????????????????????????
        LambdaQueryWrapper<StudySpecialLessonCou> specialLessonCouWrapper = new LambdaQueryWrapper<>();
        specialLessonCouWrapper.eq(StudySpecialLessonCou::getSpecialId,specialId);
        specialLessonCouWrapper.eq(StudySpecialLessonCou::getLessonId,lessonId);
        List<StudySpecialLessonCou> specialLessonCous = studySpecialLessonCouMapper.selectList(specialLessonCouWrapper);
        Map<Long, List<StudySpecialLessonCou>> couIdSpecialLessonMap = specialLessonCous.stream().collect(Collectors.groupingBy(StudySpecialLessonCou::getCouId));
        //2.5??????????????????
        for (StudySpecialLessonCouDetailVo couDetailVo : specialLessonCouDetailVos){
            //?????????????????????????????????????????????
            List<StudyUserStudyRecords> couStudyRecords = couStudyRecordsMap.get(couDetailVo.getId());
            if (CollectionUtils.isNotEmpty(couStudyRecords)) {
                //????????????????????????????????????
                Long cumulativeStudyTime = couStudyRecords.get(0).getCumulativeStudyTime();//??????????????????
                Long studyBestLessTime = couStudyRecords.get(0).getStudyBestLessTime();//??????????????????
                BigDecimal cumulative = new BigDecimal(cumulativeStudyTime);
                BigDecimal bestLess = new BigDecimal(studyBestLessTime);
                BigDecimal divide = cumulative.divide(bestLess,4, RoundingMode.HALF_DOWN);
                BigDecimal one = new BigDecimal(1);
                if (divide.compareTo(one) > -1) {
                    divide = one;
                }
                DecimalFormat df = new DecimalFormat("0.00%");
                couDetailVo.setFinishSize(df.format(divide));
                couDetailVo.setStudyBestLessTime(StudyTimeUtil.timeToStr(studyBestLessTime));
                couDetailVo.setLastVideoTime(couStudyRecords.get(0).getLastVideoTime());
            }else {
                //??????????????????????????????????????? ???????????????????????????
                //?????????
                couDetailVo.setFinishSize("0.00");
                //??????????????????
                List<StudyLessonCou> studyLessonCous = couIdMap.get(couDetailVo.getId());
                Integer studyBestLessTime = studyLessonCous.get(0).getStudyBestLessTime();
                couDetailVo.setStudyBestLessTime(StudyTimeUtil.timeToStr(studyBestLessTime*60));
            }
            //????????????????????????
            List<StudySpecialLessonCou> couSpecialLessonCous = couIdSpecialLessonMap.get(couDetailVo.getId());
            if (CollectionUtils.isNotEmpty(couSpecialLessonCous)) {
                Integer couIsCompulsory = couSpecialLessonCous.get(0).getCouIsCompulsory();//?????????????????? 0???1???
                couDetailVo.setCouIsCompulsory(couIsCompulsory);
            }
        }
        specialLessonDetailVo.setCouList(specialLessonCouDetailVos);
        //3????????????
        LambdaQueryWrapper<StudyLessonMarterials> lessonMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonMarterialsWrapper.eq(StudyLessonMarterials::getLessonId,lessonId);
        List<StudyLessonMarterials> lessonMarterials = studyLessonMarterialsMapper.selectList(lessonMarterialsWrapper);
        if (CollectionUtils.isNotEmpty(lessonMarterials)) {
            List<Long> marterialIds = lessonMarterials.stream().map(StudyLessonMarterials::getMarterialsId).collect(Collectors.toList());
            LambdaQueryWrapper<StudyMarterials> marterialsWrapper = new LambdaQueryWrapper<>();
            marterialsWrapper.in(StudyMarterials::getId,marterialIds);
            List<StudyMarterials> marterials = studyMarterialsMapper.selectList(marterialsWrapper);
            List<StudyMarterialsVo> marterialsVos = DozerUtils.mapList(dozerMapper, marterials, StudyMarterialsVo.class);
            specialLessonDetailVo.setMarterialsList(marterialsVos);
        }
        //4?????????
        //4.1????????????id?????????id ???????????????id
        LambdaQueryWrapper<StudySpecialLesson> specialLessonWrapper = new LambdaQueryWrapper<>();
        specialLessonWrapper.eq(StudySpecialLesson::getSpecialId,specialId);
        specialLessonWrapper.eq(StudySpecialLesson::getLessonId,lessonId);
        List<StudySpecialLesson> specialLessons = studySpecialLessonMapper.selectList(specialLessonWrapper);
        if (CollectionUtils.isNotEmpty(specialLessons)) {
            Long paperId = specialLessons.get(0).getPractisePaperId();
            //4.2???????????????id?????????????????????
            LambdaQueryWrapper<StudyExamPaper> examPaperWrapper = new LambdaQueryWrapper<>();
            examPaperWrapper.eq(StudyExamPaper::getId,paperId);
            List<StudyExamPaper> examPapers = studyExamPaperMapper.selectList(examPaperWrapper);
            if (CollectionUtils.isNotEmpty(examPapers)) {
                StudyExamPaperVo examPaperVo = dozerMapper.map(examPapers.get(0), StudyExamPaperVo.class);
                specialLessonDetailVo.setExamPaper(examPaperVo);
            }
        }
        return specialLessonDetailVo;
    }

    /////////////////////////////////////////????????????????????????/////////////////////////////////////////

    /**
     * ????????????
     *
     * @param lessonVo
     */
    private void findSonListForSpecial(StudyLessonForSpecialVo lessonVo) {
        //????????????
        LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
        lessonCouWrapper.eq(StudyLessonCou::getLessonId, lessonVo.getId());
        List<StudyLessonCou> lessonCous = studyLessonCouMapper.selectList(lessonCouWrapper);
        if (CollectionUtils.isNotEmpty(lessonCous)) {
            List<Long> couIdList = lessonCous.stream().map(StudyLessonCou::getCouId).collect(Collectors.toList());
            LambdaQueryWrapper<StudyCou> couWrapper = new LambdaQueryWrapper<>();
            couWrapper.in(StudyCou::getId, couIdList);
            List<StudyCou> studyCouList = studyCouMapper.selectList(couWrapper);
            if (CollectionUtils.isNotEmpty(studyCouList)) {
                List<StudyCouVo> couVoList = DozerUtils.mapList(dozerMapper, studyCouList, StudyCouVo.class);
                couVoList.forEach(couVo -> {
                    couVo.setStudyTimeSize(StudyTimeUtil.buildStudyTimeStr(couVo.getStudyTime()));
                });
                lessonVo.setCouList(couVoList);
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param lessonDto
     */
    private void checkData(StudyLessonDto lessonDto) {
        if (StringUtils.isBlank(lessonDto.getLabelCode())) {
            throw new IncloudException("???????????????");
        }
        if (StringUtils.isBlank(lessonDto.getTypeCode())) {
            throw new IncloudException("???????????????");
        }
        if (StringUtils.isBlank(lessonDto.getLessonName())) {
            throw new IncloudException("?????????????????????");
        }
        if (StringUtils.isBlank(lessonDto.getImgUrl())) {
            throw new IncloudException("??????????????????");
        }
        if (CollectionUtils.isEmpty(lessonDto.getCouList())) {
            throw new IncloudException("????????????????????????");
        }
        if (ObjectUtils.isEmpty(lessonDto.getHits())) {
            lessonDto.setHits(0L);
        }
        if (ObjectUtils.isEmpty(lessonDto.getStatus())) {
            lessonDto.setStatus(StudyStatus.NO_TAKE_EFFECT.code);
        }
        if (CollectionUtils.isNotEmpty(lessonDto.getCouList())) {
            List<StudyLessonCouDto> couDtoList = lessonDto.getCouList();
            for (StudyLessonCouDto couDto : couDtoList){
                if (ObjectUtils.isEmpty(couDto.getStudyBestLessTime())) {
                    throw new IncloudException("???????????????"+couDto.getCouName()+"?????????????????????????????????");
                }
                if (ObjectUtils.isNotEmpty(couDto.getUseRange()) && UseRangeEnum.SIYOU.code.equals(couDto.getUseRange())){
                    if (CollectionUtils.isEmpty(couDto.getOrgPermList()) && CollectionUtils.isEmpty(couDto.getUserPermList())) {
                        throw new IncloudException("????????????:"+couDto.getCouName() + "???????????????????????????????????????????????????????????????????????????");
                    }
                }
            }
        }
    }

    /**
     * ??????procInstId??????????????????
     *
     * @param procInstId
     * @return
     */
    private StudyLesson getByProcInstId(String procInstId) {
        LambdaQueryWrapper<StudyLesson> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLesson::getCamundaProcinsId, procInstId);
        return studyLessonMapper.selectOne(queryWrapper);
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param lessonVo
     */
    private void findLessonCouAndMarterials(StudyLessonVo lessonVo) {
        //???????????????????????????
        LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
        lessonCouWrapper.eq(StudyLessonCou::getLessonId, lessonVo.getId());
        List<StudyLessonCou> lessonCouList = studyLessonCouMapper.selectList(lessonCouWrapper);
        List<StudyLessonCouVo> lessonCouVoList = DozerUtils.mapList(dozerMapper, lessonCouList, StudyLessonCouVo.class);
        if (CollectionUtils.isNotEmpty(lessonCouVoList)) {
            //??????????????????????????????
            LambdaQueryWrapper<StudyLessonCouPerm> lessonCouPermWrapper = new LambdaQueryWrapper<>();
            lessonCouPermWrapper.eq(StudyLessonCouPerm::getLessonId,lessonVo.getId());
            List<StudyLessonCouPerm> lessonCouPerms = studyLessonCouPermMapper.selectList(lessonCouPermWrapper);
            if (CollectionUtils.isNotEmpty(lessonCouPerms)) {
                //????????????id????????????
                Map<Long, List<StudyLessonCouPerm>> couIdMap = lessonCouPerms.stream().collect(Collectors.groupingBy(StudyLessonCouPerm::getCouId));
                for (StudyLessonCouVo lessonCouVo : lessonCouVoList){
                    //??????????????????????????????????????????
                    if (UseRangeEnum.SIYOU.code.equals(lessonCouVo.getUseRange())) {
                        List<StudyLessonCouPermVo> orgPermList = new ArrayList<>();
                        List<StudyLessonCouPermVo> userPermList = new ArrayList<>();
                        //????????????id??????????????????????????????
                        List<StudyLessonCouPerm> lessonCouPermList = couIdMap.get(lessonCouVo.getCouId());
                        //???????????????????????????????????????
                        for (StudyLessonCouPerm lessonCouPerm : lessonCouPermList){
                            StudyLessonCouPermVo lessonCouPermVo = dozerMapper.map(lessonCouPerm, StudyLessonCouPermVo.class);
                            if (RangeTypeEnum.USER.code.equals(lessonCouPerm.getRangeType())) {
                                userPermList.add(lessonCouPermVo);
                            }else {
                                orgPermList.add(lessonCouPermVo);
                            }
                        }
                        lessonCouVo.setOrgPermList(orgPermList);
                        lessonCouVo.setUserPermList(userPermList);
                    }
                }
            }
        }
        lessonVo.setCouList(lessonCouVoList);
        //?????????????????????????????????
        LambdaQueryWrapper<StudyLessonMarterials> lessonMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonMarterialsWrapper.eq(StudyLessonMarterials::getLessonId, lessonVo.getId());
        List<StudyLessonMarterials> lessonMarterialsList = studyLessonMarterialsMapper.selectList(lessonMarterialsWrapper);
        List<StudyLessonMarterialsVo> lessonMarterialsVoList = DozerUtils.mapList(dozerMapper, lessonMarterialsList, StudyLessonMarterialsVo.class);
        lessonVo.setMarterialsList(lessonMarterialsVoList);
    }

    /**
     * ????????????????????????
     *
     * @param lessonAdjVo
     */
    private void dataEmpty(StudyLessonAdjVo lessonAdjVo) {
        lessonAdjVo.setId(null);
        lessonAdjVo.setCreateTime(null);
        lessonAdjVo.setUpdateTime(null);
        lessonAdjVo.setCreateUserId(null);
        lessonAdjVo.setCreateUserName(null);
        lessonAdjVo.setCreateUserParentOrgId(null);
        lessonAdjVo.setCreateUserParentOrgName(null);
        lessonAdjVo.setCreateUserParentDeptId(null);
        lessonAdjVo.setCreateUserParentDeptName(null);
        lessonAdjVo.setCreateUserOrgFullId(null);
        if (CollectionUtils.isNotEmpty(lessonAdjVo.getCouList())) {
            List<StudyLessonAdjCouVo> lessonAdjCouVoList = lessonAdjVo.getCouList();
            lessonAdjCouVoList.forEach(lessonAdjCouVo -> {
                lessonAdjCouVo.setId(null);
                lessonAdjCouVo.setCreateTime(null);
                lessonAdjCouVo.setUpdateTime(null);
                lessonAdjCouVo.setCreateUserId(null);
                lessonAdjCouVo.setCreateUserName(null);
                lessonAdjCouVo.setCreateUserParentOrgId(null);
                lessonAdjCouVo.setCreateUserParentOrgName(null);
                lessonAdjCouVo.setCreateUserParentDeptId(null);
                lessonAdjCouVo.setCreateUserParentDeptName(null);
                lessonAdjCouVo.setCreateUserOrgFullId(null);
                lessonAdjCouVo.setLessonId(null);
            });
        }
        if (CollectionUtils.isNotEmpty(lessonAdjVo.getMarterialsList())) {
            List<StudyLessonAdjMarterialsVo> lessonAdjMarterialsVoList = lessonAdjVo.getMarterialsList();
            lessonAdjMarterialsVoList.forEach(lessonAdjMarterialsVo -> {
                lessonAdjMarterialsVo.setId(null);
                lessonAdjMarterialsVo.setCreateTime(null);
                lessonAdjMarterialsVo.setUpdateTime(null);
                lessonAdjMarterialsVo.setCreateUserId(null);
                lessonAdjMarterialsVo.setCreateUserName(null);
                lessonAdjMarterialsVo.setCreateUserParentOrgId(null);
                lessonAdjMarterialsVo.setCreateUserParentOrgName(null);
                lessonAdjMarterialsVo.setCreateUserParentDeptId(null);
                lessonAdjMarterialsVo.setCreateUserParentDeptName(null);
                lessonAdjMarterialsVo.setCreateUserOrgFullId(null);
                lessonAdjMarterialsVo.setLessonId(null);
            });
        }
    }

    /**
     * ??????????????????????????????
     * @param lessonCouDto
     */
    private void saveLessonCouPerm(StudyLessonCouDto lessonCouDto){
        //1.?????????????????????
        LambdaQueryWrapper<StudyLessonCouPerm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonCouPerm::getLessonId,lessonCouDto.getLessonId());
        queryWrapper.eq(StudyLessonCouPerm::getCouId,lessonCouDto.getCouId());
        studyLessonCouPermMapper.delete(queryWrapper);
        //2.?????????????????????
        List<StudyLessonCouPerm> addLessonCouPermList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(lessonCouDto.getOrgPermList())) {
            List<StudyLessonCouPerm> orgPermList = DozerUtils.mapList(dozerMapper, lessonCouDto.getOrgPermList(), StudyLessonCouPerm.class);
            orgPermList.forEach(perm -> {
                perm.setLessonId(lessonCouDto.getLessonId());
                perm.setCouId(lessonCouDto.getCouId());
                addLessonCouPermList.add(perm);
            });
        }
        if (CollectionUtils.isNotEmpty(lessonCouDto.getUserPermList())) {
            List<StudyLessonCouPerm> userPermList = DozerUtils.mapList(dozerMapper, lessonCouDto.getUserPermList(), StudyLessonCouPerm.class);
            userPermList.forEach(perm -> {
                perm.setLessonId(lessonCouDto.getLessonId());
                perm.setCouId(lessonCouDto.getCouId());
                perm.setRangeType(RangeTypeEnum.USER.code);
                addLessonCouPermList.add(perm);
            });
        }
        if (CollectionUtils.isNotEmpty(addLessonCouPermList)) {
            studyLessonCouPermService.saveBatch(addLessonCouPermList);
        }
    }

    /**
     * ??????????????????????????? ??????????????????????????????
     * @param lessonAdjCouVos
     */
    private void setLessonAdjCouPerms(List<StudyLessonAdjCouVo> lessonAdjCouVos,Long lessonId){
        if (CollectionUtils.isNotEmpty(lessonAdjCouVos)) {
            //????????????id ?????????????????????????????????????????????
            LambdaQueryWrapper<StudyLessonCouPerm> lessonCouPermWrapper = new LambdaQueryWrapper<>();
            lessonCouPermWrapper.eq(StudyLessonCouPerm::getLessonId,lessonId);
            List<StudyLessonCouPerm> lessonCouPermList = studyLessonCouPermMapper.selectList(lessonCouPermWrapper);
            if (CollectionUtils.isNotEmpty(lessonCouPermList)) {
                //????????????????????????????????? ????????????id????????????
                Map<Long, List<StudyLessonCouPerm>> couIdMap = lessonCouPermList.stream().collect(Collectors.groupingBy(StudyLessonCouPerm::getCouId));
                //???????????????????????? ??????????????????????????????????????????
                for (StudyLessonAdjCouVo lessonAdjCouVo : lessonAdjCouVos){
                    List<StudyLessonCouPerm> lessonCouPerms = couIdMap.get(lessonAdjCouVo.getCouId());
                    List<StudyLessonAdjCouPermVo> orgPermList = new ArrayList<>();
                    List<StudyLessonAdjCouPermVo> userPermList = new ArrayList<>();
                    if (UseRangeEnum.SIYOU.code.equals(lessonAdjCouVo.getUseRange())) {
                        for (StudyLessonCouPerm lessonCouPerm : lessonCouPerms){
                            StudyLessonAdjCouPermVo lessonAdjCouPermVo = dozerMapper.map(lessonCouPerm, StudyLessonAdjCouPermVo.class);
                            if (lessonCouPerm.getRangeType().equals(RangeTypeEnum.USER.code)) {
                                userPermList.add(lessonAdjCouPermVo);
                            }else {
                                orgPermList.add(lessonAdjCouPermVo);
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(orgPermList)) {
                        lessonAdjCouVo.setOrgPermList(orgPermList);
                    }
                    if (CollectionUtils.isNotEmpty(userPermList)) {
                        lessonAdjCouVo.setUserPermList(userPermList);
                    }
                }
            }
        }
    }
}
