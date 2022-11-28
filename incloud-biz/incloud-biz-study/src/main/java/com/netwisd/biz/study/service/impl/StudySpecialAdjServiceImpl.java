package com.netwisd.biz.study.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.biz.study.constants.RangeTypeEnum;
import com.netwisd.biz.study.constants.SpecialTimeTypeEnum;
import com.netwisd.biz.study.constants.StudyStatus;
import com.netwisd.biz.study.dto.*;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.fegin.WfClient;
import com.netwisd.biz.study.mapper.StudySpecialMapper;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.mapper.StudySpecialAdjMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题调整申请表 功能描述...
 * @date 2022-05-13 11:27:37
 */
@Service
@Slf4j
public class StudySpecialAdjServiceImpl extends BatchServiceImpl<StudySpecialAdjMapper, StudySpecialAdj> implements StudySpecialAdjService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudySpecialAdjMapper studySpecialAdjMapper;

    @Autowired
    private StudySpecialService studySpecialService;
    @Autowired
    private StudySpecialMustExamUserService studySpecialMustExamUserService;
    @Autowired
    private StudySpecialRangeService studySpecialRangeService;

    @Autowired
    private StudySpecialLessonCouService studySpecialLessonCouService;

    @Autowired
    private StudySpecialAdjMustExamUserService studySpecialAdjMustExamUserService;


    @Autowired
    private StudySpecialLessonService studySpecialLessonService;

    @Autowired
    private StudySpecialMaterialsService studySpecialMaterialsService;

    @Autowired
    private StudySpecialJieyeService studySpecialJieyeService;
    @Autowired
    private StudySpecialAdjRangeService studySpecialAdjRangeService;

    @Autowired
    private StudySpecialAdjLessonCouService studySpecialAdjLessonCouService;

    @Autowired
    private StudySpecialAdjLessonService studySpecialAdjLessonService;

    @Autowired
    private StudySpecialAdjMaterialsService studySpecialAdjMaterialsService;

    @Autowired
    private StudySpecialAdjJieyeService studySpecialAdjJieyeService;

    @Autowired
    private StudySpecialHisService studySpecialHisService;

    @Autowired
    private StudySpecialHisRangeService studySpecialHisRangeService;

    @Autowired
    private StudySpecialHisLessonCouService studySpecialHisLessonCouService;

    @Autowired
    private StudySpecialHisLessonService studySpecialHisLessonService;

    @Autowired
    private StudySpecialHisMaterialsService studySpecialHisMaterialsService;

    @Autowired
    private StudySpecialHisJieyeService studySpecialHisJieyeService;

    @Autowired
    private StudySpecialHisMustExamUserService studySpecialHisMustExamUserService;

    @Autowired
    WfClient wfClient;

    /**
     * 获取专题的调整记录
     *
     * @param specialId
     * @return
     */
    @Override
    public List<StudySpecialAdjVo> adjListForSpecial(Long specialId) {
        log.info("获取专题的调整记录：{}", specialId);
        LambdaQueryWrapper<StudySpecialAdj> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(StudySpecialAdj::getLinkId, specialId);
        queryWrapper.orderByDesc(StudySpecialAdj::getCreateTime);
        List<StudySpecialAdj> studySpecialAdjs = studySpecialAdjMapper.selectList(queryWrapper);
        List<StudySpecialAdjVo> studySpecialAdjVos = DozerUtils.mapList(dozerMapper, studySpecialAdjs, StudySpecialAdjVo.class);
        //组合专题时间
        if (CollectionUtils.isNotEmpty(studySpecialAdjVos)) {
            for (StudySpecialAdjVo studySpecialAdjVo : studySpecialAdjVos) {
                if (studySpecialAdjVo.getSpecialTimeType().equals(SpecialTimeTypeEnum.ORDINARY.getCode())) {
                    LocalDateTime specialStartTime = studySpecialAdjVo.getSpecialStartTime();
                    String startTime = specialStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDateTime specialEndTime = studySpecialAdjVo.getSpecialEndTime();
                    String endTime = specialEndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    studySpecialAdjVo.setSpecialTime(startTime.split("-")[0] + "年" + startTime.split("-")[1] + "月" + startTime.split("-")[2] + "日-"
                            + endTime.split("-")[0] + "年" + endTime.split("-")[1] + "月" + endTime.split("-")[2] + "日");
                } else {
                    LocalDateTime specialStartTime = studySpecialAdjVo.getSpecialStartTime();
                    String startTime = specialStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    studySpecialAdjVo.setSpecialTime(startTime.split("-")[0] + "年" + startTime.split("-")[1] + "月" + startTime.split("-")[2] + "日开始");
                }

            }
        }
        log.debug("查询条数:" + studySpecialAdjVos.size());
        return studySpecialAdjVos;
    }

    /**
     * 通过id查询详情
     *
     * @param id
     * @return
     */
    @Override
    public StudySpecialAdjVo detail(Long id) {
        log.info("流程查看数据:{}", id);
        StudySpecialAdjVo infoVo = dozerMapper.map(super.getById(id), StudySpecialAdjVo.class);
        infoVo = buildChildren(infoVo);
        return infoVo;
    }

    /**
     * 流程新增
     *
     * @param studySpecialAdjDto
     * @return
     */
    @Override
    @Transactional
    public StudySpecialAdjVo procSave(StudySpecialAdjDto studySpecialAdjDto) {
        log.info("流程保存数据:{}", studySpecialAdjDto);
        Optional.ofNullable(studySpecialAdjDto).orElseThrow(() -> new IncloudException("请将专题信息填写完整"));
        //非空校验
        this.notNull(studySpecialAdjDto);
        StudySpecialAdj studySpecialAdj = dozerMapper.map(studySpecialAdjDto, StudySpecialAdj.class);
        //设置阅卷老师默认值---取当前登陆人
        if (ObjectUtil.isEmpty(studySpecialAdj.getSpecialExamPaperTeacherId()) && StringUtils.isBlank(studySpecialAdj.getSpecialExamPaperTeacherName())) {
            LoginAppUser appUser = AppUserUtil.getLoginAppUser();
            Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
            studySpecialAdj.setSpecialExamPaperTeacherId(appUser.getId());
            studySpecialAdj.setSpecialExamPaperTeacherName(appUser.getUsername());
        }
        StudySpecial studySpecial = studySpecialService.getById(studySpecialAdjDto.getLinkId());
        LocalDateTime startTime = studySpecial.getSpecialPaperStartTime();
        LocalDateTime endTime = studySpecial.getSpecialPaperEndTime();
        //调整的专题关联的有考试试卷
        if(studySpecial.getSpecialPaperId()!=null && studySpecial.getSpecialPaperId()!=0){
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = df.format(new Date());
            LocalDateTime parse = LocalDateTime.parse(format, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (parse.isAfter(startTime) && parse.isBefore(endTime)) {
                throw new IncloudException("试卷在指定时间范围内已生效,不能取消");
            }
        }
        if (null == studySpecialAdj.getFileId()) {
            throw new IncloudException("请上传封面");
        }
        if (studySpecialAdjDto.getSpecialTimeType().equals(SpecialTimeTypeEnum.LONG_TERM.code)) {
            studySpecialAdj.setSpecialEndTime(LocalDateTime.of(2999, 12, 31, 23, 59,59));
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        studySpecialAdj.setCreateUserName(loginAppUser.getUserNameCh());
        studySpecialAdj.setCreateTime(LocalDateTime.now());
        studySpecialAdj.setUpdateTime(LocalDateTime.now());
        super.save(studySpecialAdj);
        //保存专题申请子表信息（专题对象、专题必考人员、专题课程、专题课程课件、专题资料、专题结业设置子表（证书））
        saveSpecialAdjChildren(studySpecialAdjDto);
        //返回保存后信息，工作流要用
        StudySpecialAdjVo infoVo = dozerMapper.map(super.getById(studySpecialAdj.getId()), StudySpecialAdjVo.class);
        infoVo = buildChildren(infoVo);
        return infoVo;
    }

    /**
     * 流程修改
     *
     * @param studySpecialAdjDto
     * @return
     */
    @Override
    @Transactional
    public StudySpecialAdjVo procUpdate(StudySpecialAdjDto studySpecialAdjDto) {
        log.info("流程修改数据:{}", studySpecialAdjDto);
        Optional.ofNullable(studySpecialAdjDto).orElseThrow(() -> new IncloudException("请将专题信息填写完整"));
        //非空校验
        this.notNull(studySpecialAdjDto);
        StudySpecialAdj studySpecialAdj = dozerMapper.map(studySpecialAdjDto, StudySpecialAdj.class);
        //设置阅卷老师默认值---取当前登陆人
        if (ObjectUtil.isEmpty(studySpecialAdj.getSpecialExamPaperTeacherId()) && StringUtils.isBlank(studySpecialAdj.getSpecialExamPaperTeacherName())) {
            LoginAppUser appUser = AppUserUtil.getLoginAppUser();
            Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
            studySpecialAdj.setSpecialExamPaperTeacherId(appUser.getId());
            studySpecialAdj.setSpecialExamPaperTeacherName(appUser.getUsername());
        }
        StudySpecial studySpecial = studySpecialService.getById(studySpecialAdjDto.getLinkId());
        LocalDateTime startTime = studySpecial.getSpecialPaperStartTime();
        LocalDateTime endTime = studySpecial.getSpecialPaperEndTime();
        //调整的专题关联的有考试试卷
        if(studySpecial.getSpecialPaperId()!=null && studySpecial.getSpecialPaperId()!=0){
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = df.format(new Date());
            LocalDateTime parse = LocalDateTime.parse(format, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (parse.isAfter(startTime) && parse.isBefore(endTime)) {
                throw new IncloudException("试卷在指定时间范围内已生效,不能取消");
            }
        }
        if (null == studySpecialAdj.getFileId()) {
            throw new IncloudException("请上传封面");
        }
        if (studySpecialAdjDto.getSpecialTimeType().equals(SpecialTimeTypeEnum.LONG_TERM.code)) {
            studySpecialAdj.setSpecialEndTime(LocalDateTime.of(2999, 12, 31, 23, 59,59));
        }
        studySpecialAdj.setUpdateTime(LocalDateTime.now());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        studySpecialAdj.setCreateUserName(loginAppUser.getUserNameCh());
        if (super.updateById(studySpecialAdj)) {
            // 通过专题ID 删除专题子表信息（专题对象、专题必考人员、专题课程、专题课程课件、专题资料、专题结业设置）
            deleteSpecialAdjChildren(studySpecialAdjDto.getId());
        }
        // 保存专题子表信息（专题对象、专题必考人员、专题课程、专题课程课件、专题资料、专题结业设置子表（证书））
        saveSpecialAdjChildren(studySpecialAdjDto);
        //返回保存后信息，工作流要用
        StudySpecialAdjVo infoVo = dozerMapper.map(super.getById(studySpecialAdj.getId()), StudySpecialAdjVo.class);
        infoVo = buildChildren(infoVo);
        return infoVo;
    }

    /**
     * 流程回显获取详情
     *
     * @param procInstId
     * @return
     */
    @Override
    public StudySpecialAdjVo procDetail(String procInstId) {
        log.info("流程查看数据:{}", procInstId);
        StudySpecialAdjVo infoVo = dozerMapper.map(Optional.ofNullable(this.getInfoByProcinstId(procInstId)).orElse(new StudySpecialAdj()), StudySpecialAdjVo.class);
        infoVo = buildChildren(infoVo);
        return infoVo;
    }

    /**
     * 流程提交触发操作
     *
     * @param processInstanceId
     * @return
     */
    @Override
    @Transactional
    public Boolean procAfterSubmit(String processInstanceId) {
        super.update(Wrappers.<StudySpecialAdj>lambdaUpdate()
                .set(StudySpecialAdj::getAuditSubmitTime, LocalDateTime.now())
                .eq(StudySpecialAdj::getCamundaProcinsId, processInstanceId)
                .set(StudySpecialAdj::getUpdateTime, LocalDateTime.now()));
        StudySpecialAdj studySpecialAdj = super.getOne(Wrappers.<StudySpecialAdj>lambdaQuery().eq(StudySpecialAdj::getCamundaProcinsId, processInstanceId));
        studySpecialService.update(Wrappers.<StudySpecial>lambdaUpdate()
                .eq(StudySpecial::getId, studySpecialAdj.getLinkId())
                .set(StudySpecial::getStatus, StudyStatus.IN_UPDATE.code)
                .set(StudySpecial::getUpdateTime, LocalDateTime.now()));
        return true;
    }
    /**
     * 审批完成触发操作
     *
     * @param processInstanceId
     * @return
     */
    @Override
    @Transactional
    public Boolean procAuditSuccess(String processInstanceId) {
        //1、赋值审批完成时间
        super.update(Wrappers.<StudySpecialAdj>lambdaUpdate().set(StudySpecialAdj::getAuditSuccessTime, LocalDateTime.now())
                .eq(StudySpecialAdj::getCamundaProcinsId, processInstanceId)
                .set(StudySpecialAdj::getUpdateTime, LocalDateTime.now()));
        //2、查询原数据
        //专题调整
        StudySpecialAdjVo studySpecialAdjVo = this.procDetail(processInstanceId);
        StudySpecialVo studySpecialVo = studySpecialService.detail(studySpecialAdjVo.getLinkId());

        //3、原数据转移至历史
        log.info("原数据转移至历史:{}", studySpecialVo);
        StudySpecialHis studySpecialHis = dozerMapper.map(studySpecialVo, StudySpecialHis.class);
        Long specialHisId = IdGenerator.getIdGenerator();
        studySpecialHis.setId(specialHisId);
        studySpecialHis.setLinkId(studySpecialVo.getId());
        studySpecialHis.setCreateTime(LocalDateTime.now());
        studySpecialHisService.save(studySpecialHis);
        //保存专题申请子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置子表（证书））
        saveSpecialHisChildren(specialHisId, studySpecialVo);
        //4、更新变更数据至原数据
        StudySpecial studySpecial = dozerMapper.map(studySpecialAdjVo, StudySpecial.class);
        studySpecial.setId(studySpecialAdjVo.getLinkId());
        if (studySpecialAdjVo.getSpecialPaperTotalScore()==null){
            studySpecial.setSpecialPaperTotalScore(BigDecimal.ZERO);
        }
        if (studySpecialAdjVo.getSpecialExamTime()==null){
            studySpecial.setSpecialExamTime(0);
        }
        if (studySpecialAdjVo.getSpecialExamPaperTeacherName()==null){
            studySpecial.setSpecialExamPaperTeacherName("");
        }
        if (studySpecialAdjVo.getSpecialExamQualifiedScore()==null){
            studySpecial.setSpecialExamQualifiedScore(BigDecimal.ZERO);
        }
        boolean studySpecialUpdate = studySpecialService.updateById(studySpecial);
        if (studySpecialUpdate) {
            //先删元数据子表信息
            studySpecialService.deleteSpecialChildren(studySpecialAdjVo.getLinkId());
            //重新添加子表信息
            saveSpecialChildren(studySpecialAdjVo.getLinkId(), studySpecialAdjVo);
        }
        //5、恢复原数据状态为”已生效“
        studySpecialService.update(Wrappers.<StudySpecial>lambdaUpdate()
                .eq(StudySpecial::getId, studySpecialAdjVo.getLinkId())
                .set(studySpecialAdjVo.getSpecialPaperStartTime()==null,StudySpecial::getSpecialPaperStartTime,null)
                .set(studySpecialAdjVo.getSpecialPaperEndTime()==null,StudySpecial::getSpecialPaperEndTime,null)
                .set(StudySpecial::getStatus, StudyStatus.TAKE_EFFECT.code)
                .set(StudySpecial::getUpdateTime, LocalDateTime.now()));

        return true;
    }

    /**
     * 专题删除
     *
     * @param specialAdjId
     * @return
     */
    @Override
    @Transactional
    public Boolean delete(Long specialAdjId) {
        log.info("删除专题参数：{}", specialAdjId);
        StudySpecialAdj studySpecialAdj = super.getById(specialAdjId);//专题id
        if (ObjectUtil.isNotNull(studySpecialAdj)) {
            if (!studySpecialAdj.getAuditStatus().equals(WfProcessEnum.DRAFT.getType())) {
                throw new IncloudException("仅审核状态为草稿可删除！");
            }
            log.info("删除:{" + studySpecialAdj.getSpecialName() + "}专题");
            boolean SpecialDelete = super.removeById(specialAdjId);
            if (SpecialDelete) {
                deleteSpecialAdjChildren(specialAdjId);
                wfClient.deleteOnlyProcess(new WfEngineDto.StateDto(studySpecialAdj.getCamundaProcinsId(), studySpecialAdj.getCamundaProcdefId(), null));
            }
        }
        return true;
    }

    /**
     * 流程删除
     *
     * @param processInstanceId
     * @return
     */
    @Override
    @Transactional
    public Boolean procDelete(String processInstanceId) {
        log.info("删除专题参数：{}", processInstanceId);
        StudySpecialAdj studySpecialAdj = this.getInfoByProcinstId(processInstanceId);//专题id
        if (ObjectUtil.isNotNull(studySpecialAdj)) {
            log.info("删除:{" + studySpecialAdj.getSpecialName() + "}专题");
            boolean SpecialDelete = super.removeById(studySpecialAdj.getId());
            if (SpecialDelete) {
                deleteSpecialAdjChildren(studySpecialAdj.getId());
            }
        }
        return true;
    }

    //非空校验
    public void notNull(StudySpecialAdjDto studySpecialAdjDto) {
        //当培训类型为普通类型时，专题结束时间不可为空
        if (SpecialTimeTypeEnum.ORDINARY.code == studySpecialAdjDto.getSpecialTimeType()) {
            if (null == studySpecialAdjDto.getSpecialEndTime()) {
                throw new IncloudException("普通培训的专题结束时间不可为空！");
            }
        }
        //如果关联了试卷 考试开始试卷与考试结束时间都必须设置,结业设置也必填，合格分数也必填
        if (StringUtils.isNotBlank(studySpecialAdjDto.getSpecialPaperName())) {
            if (null == studySpecialAdjDto.getSpecialPaperStartTime() && null == studySpecialAdjDto.getSpecialPaperEndTime()) {
                throw new IncloudException("存在考试试卷，请设置考试时间");
            }
            if (CollectionUtils.isEmpty(studySpecialAdjDto.getStudySpecialJieyeList())) {
                throw new IncloudException("存在考试试卷，请设置结业设置");
            }
            if (null==studySpecialAdjDto.getSpecialExamQualifiedScore()){
                throw new IncloudException("存在考试试卷，请设置考试合格分数");
            }
        }else if (CollectionUtils.isNotEmpty(studySpecialAdjDto.getStudySpecialJieyeList())){
            throw new IncloudException("考试试卷不存在，请选择考试试卷或取消结业设置信息！");
        }
        //封面必填
        if (StringUtils.isBlank(studySpecialAdjDto.getFileUrl())) {
            throw new IncloudException("请上传封面图");
        }
        //描述必填
        if (StringUtils.isBlank(studySpecialAdjDto.getDescription())) {
            throw new IncloudException("请填写描述（简介）信息");
        }

    }

    /**
     * 构建子表数据
     *
     * @param studySpecialAdjVo
     * @return
     */
    private StudySpecialAdjVo buildChildren(StudySpecialAdjVo studySpecialAdjVo) {

        if (studySpecialAdjVo != null && studySpecialAdjVo.getId() != null) {
            //获取专题对象（机构、部门）范围
            List<String> rangeTypeList = new ArrayList<>();
            rangeTypeList.add(RangeTypeEnum.ORG.code);
            rangeTypeList.add(RangeTypeEnum.DEPT.code);
            List<StudySpecialAdjRange> studySpecialAdjRangeOrgDeptList = studySpecialAdjRangeService.list(Wrappers.<StudySpecialAdjRange>lambdaQuery()
                    .eq(StudySpecialAdjRange::getSpecialId, studySpecialAdjVo.getId())
                    .in(StudySpecialAdjRange::getRangeType, rangeTypeList));
            if (CollectionUtils.isNotEmpty(studySpecialAdjRangeOrgDeptList)) {
                studySpecialAdjVo.setStudySpecialRangeOrgDeptList(DozerUtils.mapList(dozerMapper, studySpecialAdjRangeOrgDeptList, StudySpecialAdjRangeVo.class));
            }
            //获取专题对象（人员）范围
            List<StudySpecialAdjRange> studySpecialAdjRangeUserList = studySpecialAdjRangeService.list(Wrappers.<StudySpecialAdjRange>lambdaQuery()
                    .eq(StudySpecialAdjRange::getSpecialId, studySpecialAdjVo.getId())
                    .eq(StudySpecialAdjRange::getRangeType, RangeTypeEnum.USER.code));
            if (CollectionUtils.isNotEmpty(studySpecialAdjRangeUserList)) {
                studySpecialAdjVo.setStudySpecialRangeUserList(DozerUtils.mapList(dozerMapper, studySpecialAdjRangeUserList, StudySpecialAdjRangeVo.class));
            }

            //获取专题必考人员信息
            List<StudySpecialAdjMustExamUser> studySpecialAdjMustExamUsers = studySpecialAdjMustExamUserService.list(Wrappers.<StudySpecialAdjMustExamUser>lambdaQuery().eq(StudySpecialAdjMustExamUser::getSpecialId, studySpecialAdjVo.getId()));
            if (CollectionUtils.isNotEmpty(studySpecialAdjMustExamUsers)) {
                studySpecialAdjVo.setStudySpecialMustExamUserList(DozerUtils.mapList(dozerMapper, studySpecialAdjMustExamUsers, StudySpecialAdjMustExamUserVo.class));
            }
            //获取课程信息
            List<StudySpecialAdjLesson> studySpecialAdjLessonList = studySpecialAdjLessonService.list(Wrappers.<StudySpecialAdjLesson>lambdaQuery().eq(StudySpecialAdjLesson::getSpecialId, studySpecialAdjVo.getId()));
            if (CollectionUtils.isNotEmpty(studySpecialAdjLessonList)) {
                List<StudySpecialAdjLessonVo> studySpecialAdjLessonVoList = new ArrayList<>();
                //获取课程课件信息
                for (StudySpecialAdjLesson studySpecialAdjLesson : studySpecialAdjLessonList) {
                    StudySpecialAdjLessonVo studySpecialAdjLessonVo = dozerMapper.map(studySpecialAdjLesson, StudySpecialAdjLessonVo.class);
                    if (null != studySpecialAdjLesson && null != studySpecialAdjLesson.getId()) {
                        List<StudySpecialAdjLessonCou> studySpecialAdjLessonCouList = studySpecialAdjLessonCouService.list(Wrappers.<StudySpecialAdjLessonCou>lambdaQuery().eq(StudySpecialAdjLessonCou::getSpecialLessonId, studySpecialAdjLesson.getId()));
                        if (CollectionUtils.isNotEmpty(studySpecialAdjLessonCouList)) {
                            List<StudySpecialAdjLessonCouVo> studySpecialAdjLessonCouVoList = DozerUtils.mapList(dozerMapper, studySpecialAdjLessonCouList, StudySpecialAdjLessonCouVo.class);
                            studySpecialAdjLessonVo.setStudySpecialLessonCouList(studySpecialAdjLessonCouVoList);
                        }
                    }
                    studySpecialAdjLessonVoList.add(studySpecialAdjLessonVo);
                }
                studySpecialAdjVo.setStudySpecialLessonList(studySpecialAdjLessonVoList);
            }
            //获取学习资料
            List<StudySpecialAdjMaterials> specialAdjMaterialsList = studySpecialAdjMaterialsService.list(Wrappers.<StudySpecialAdjMaterials>lambdaQuery().eq(StudySpecialAdjMaterials::getSpecialId, studySpecialAdjVo.getId()));
            if (CollectionUtils.isNotEmpty(specialAdjMaterialsList)) {
                studySpecialAdjVo.setStudySpecialMaterialsList(DozerUtils.mapList(dozerMapper, specialAdjMaterialsList, StudySpecialAdjMaterialsVo.class));
            }

            //获取结业信息
            List<StudySpecialAdjJieye> specialAdjJieyeList = studySpecialAdjJieyeService.list(Wrappers.<StudySpecialAdjJieye>lambdaQuery().eq(StudySpecialAdjJieye::getSpecialId, studySpecialAdjVo.getId()));
            if (CollectionUtils.isNotEmpty(specialAdjJieyeList)) {
                studySpecialAdjVo.setStudySpecialJieyeList(DozerUtils.mapList(dozerMapper, specialAdjJieyeList, StudySpecialAdjJieyeVo.class));
            }
        }
        return studySpecialAdjVo;
    }

    /**
     * 保存专题申请子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置子表（证书））
     *
     * @param studySpecialAdjDto
     */
    public void saveSpecialAdjChildren(StudySpecialAdjDto studySpecialAdjDto) {
        //--------------专题公共字段开始---------
        Long specialId = studySpecialAdjDto.getId();
        //--------------专题公共字段结束---------
        //---------------保存专题对象(机构、部门)开始-----------
        if (CollectionUtils.isNotEmpty(studySpecialAdjDto.getStudySpecialRangeOrgDeptList())) {
            //获取专题对象（季候、部门）信息
            List<StudySpecialAdjRangeDto> studySpecialAdjRangeOrgDeptList = studySpecialAdjDto.getStudySpecialRangeOrgDeptList();
            //获取专题对象（人员）信息
            List<StudySpecialAdjRangeDto> studySpecialAdjRangeUserList;
            if (CollectionUtils.isNotEmpty(studySpecialAdjDto.getStudySpecialRangeUserList())) {
                studySpecialAdjRangeUserList = studySpecialAdjDto.getStudySpecialRangeUserList();
                for (StudySpecialAdjRangeDto studySpecialAdjRangeDto : studySpecialAdjRangeUserList) {
                    studySpecialAdjRangeDto.setRangeType(RangeTypeEnum.USER.code);
                    //合并专题对象信息
                    studySpecialAdjRangeOrgDeptList.add(studySpecialAdjRangeDto);
                }
            }
            List<StudySpecialAdjRange> studySpecialAdjRangeList = new ArrayList<>();
            //关联信息
            for (StudySpecialAdjRangeDto studySpecialAdjRangeDto : studySpecialAdjRangeOrgDeptList) {
                studySpecialAdjRangeDto.setSpecialId(specialId);
                studySpecialAdjRangeList.add(dozerMapper.map(studySpecialAdjRangeDto, StudySpecialAdjRange.class));
            }
            log.info("专题申请保存-专题对象（机构、部门、人员）保存参数:{}", studySpecialAdjRangeList);
            //专题对象保存
            boolean specialAdjRangeSave = studySpecialAdjRangeService.saveBatch(studySpecialAdjRangeList);
            if (specialAdjRangeSave) {
                log.info("专题申请保存- 专题对象（机构、部门、人员）保存成功");
            }
        }
        // ---------------保存专题对象（机构、部门）结束----------
        //保存专题必考人员
        if (CollectionUtils.isNotEmpty(studySpecialAdjDto.getStudySpecialMustExamUserList())) {
            log.info("专题申请保存-专题必考人员保存参数：{}", studySpecialAdjDto.getStudySpecialMustExamUserList());
            List<StudySpecialAdjMustExamUser> studySpecialAdjMustExamUsers = new ArrayList<>();
            for (StudySpecialAdjMustExamUserDto studySpecialAdjMustExamUserDto : studySpecialAdjDto.getStudySpecialMustExamUserList()) {
                studySpecialAdjMustExamUserDto.setSpecialId(specialId);
                studySpecialAdjMustExamUserDto.setSpecialPaperId(studySpecialAdjDto.getSpecialPaperId());
                StudySpecialAdjMustExamUser studySpecialAdjMustExamUser = dozerMapper.map(studySpecialAdjMustExamUserDto, StudySpecialAdjMustExamUser.class);
                studySpecialAdjMustExamUsers.add(studySpecialAdjMustExamUser);
            }
            boolean specialAdjMustExamUserSave = studySpecialAdjMustExamUserService.saveBatch(studySpecialAdjMustExamUsers);
            if (specialAdjMustExamUserSave) {
                log.info("专题申请保存- 专题必考人员保存成功");
            }
        }
        //-----------------保存专题课程开始---------
        //获取专题课程
        if (CollectionUtils.isNotEmpty(studySpecialAdjDto.getStudySpecialLessonList())) {
            List<StudySpecialAdjLessonDto> studySpecialAdjLessonDtoList = studySpecialAdjDto.getStudySpecialLessonList();
            List<StudySpecialAdjLesson> studySpecialAdjLessonList = new ArrayList<>();
            for (StudySpecialAdjLessonDto studySpecialAdjLessonDto : studySpecialAdjLessonDtoList) {
                //关联信息
                studySpecialAdjLessonDto.setSpecialId(specialId);
                //转换类型
                StudySpecialAdjLesson studySpecialAdjLesson = dozerMapper.map(studySpecialAdjLessonDto, StudySpecialAdjLesson.class);
                studySpecialAdjLessonList.add(studySpecialAdjLesson);
                //-----------------保存专题课程课件开始---------
                if (CollectionUtils.isNotEmpty(studySpecialAdjLessonDto.getStudySpecialLessonCouList())) {
                    List<StudySpecialAdjLessonCouDto> studySpecialAdjLessonCouDtoList = studySpecialAdjLessonDto.getStudySpecialLessonCouList();
                    List<StudySpecialAdjLessonCou> studySpecialAdjLessonCouList = new ArrayList<>();
                    for (StudySpecialAdjLessonCouDto studySpecialAdjLessonCouDto : studySpecialAdjLessonCouDtoList) {
                        //关联信息
                        studySpecialAdjLessonCouDto.setSpecialId(specialId);
                        studySpecialAdjLessonCouDto.setSpecialLessonId(studySpecialAdjLessonDto.getId());
                        studySpecialAdjLessonCouDto.setLessonId(studySpecialAdjLessonDto.getLessonId());
                        //转换类型
                        StudySpecialAdjLessonCou studySpecialAdjLessonCou = dozerMapper.map(studySpecialAdjLessonCouDto, StudySpecialAdjLessonCou.class);
                        studySpecialAdjLessonCouList.add(studySpecialAdjLessonCou);
                    }
                    //保存专题课程课件
                    boolean studySpecialAdjLessonCouSave = studySpecialAdjLessonCouService.saveBatch(studySpecialAdjLessonCouList);
                    if (studySpecialAdjLessonCouSave) {
                        log.info("专题申请保存- 专题课程[" + studySpecialAdjLessonDto.getLessonName() + "]课件保存成功");
                    }
                    //-----------------保存专题课程课件结束---------
                } else {
                    throw new IncloudException("选中课程[" + studySpecialAdjLessonDto.getLessonName() + "]至少选中一条课件信息");
                }
            }
            log.info("专题申请保存-专题课程保存参数:{}", studySpecialAdjLessonList);
            //保存专题课程
            boolean specialAdjLessonSave = studySpecialAdjLessonService.saveBatch(studySpecialAdjLessonList);
            if (specialAdjLessonSave) {
                log.info("专题申请保存- 专题课程保存成功");
            }
            //-----------------保存专题课程结束------------
        } else {
            throw new IncloudException("请至少选择一条课程");
        }
        //-----------------保存专题资料信息开始---------
        //获取专题资料信息
        if (CollectionUtils.isNotEmpty(studySpecialAdjDto.getStudySpecialMaterialsList())) {
            List<StudySpecialAdjMaterialsDto> studySpecialAdjMaterialsDtoList = studySpecialAdjDto.getStudySpecialMaterialsList();
            List<StudySpecialAdjMaterials> studySpecialAdjMaterialsList = new ArrayList<>();
            for (StudySpecialAdjMaterialsDto studySpecialAdjMaterialsDto : studySpecialAdjMaterialsDtoList) {
                //关联信息
                studySpecialAdjMaterialsDto.setSpecialId(specialId);
                //类型转换
                StudySpecialAdjMaterials studySpecialAdjMaterials = dozerMapper.map(studySpecialAdjMaterialsDto, StudySpecialAdjMaterials.class);
                studySpecialAdjMaterialsList.add(studySpecialAdjMaterials);
            }
            log.info("专题申请保存-专题资料保存参数:{}", studySpecialAdjMaterialsList);
            //保存专题资料
            boolean studySpecialAdjMaterialsSave = studySpecialAdjMaterialsService.saveBatch(studySpecialAdjMaterialsList);
            if (studySpecialAdjMaterialsSave) {
                log.info("专题申请保存- 专题资料保存成功");
            }
        }
        //-----------------保存专题资料信息结束---------
        //-----------------保存专题结业设置子表（证书）开始---------
        if (CollectionUtils.isNotEmpty(studySpecialAdjDto.getStudySpecialJieyeList())) {
            //获取专题结业设置子表（证书）信息
            List<StudySpecialAdjJieyeDto> studySpecialAdjJieyeList = studySpecialAdjDto.getStudySpecialJieyeList();
            List<StudySpecialAdjJieye> studySpecialAdjJieyesList = new ArrayList<>();
            for (StudySpecialAdjJieyeDto studySpecialAdjJieyeDto : studySpecialAdjJieyeList) {
                //关联信息
                studySpecialAdjJieyeDto.setSpecialId(specialId);
                //类型转换
                StudySpecialAdjJieye studySpecialAdjJieye = dozerMapper.map(studySpecialAdjJieyeDto, StudySpecialAdjJieye.class);
                studySpecialAdjJieyesList.add(studySpecialAdjJieye);
            }
            log.info("专题申请保存-专题结业设置子表（证书）保存参数:{}", studySpecialAdjJieyesList);
            //保存专题结业设置子表（证书）
            boolean studySpecialAdjJieyesSave = studySpecialAdjJieyeService.saveBatch(studySpecialAdjJieyesList);
            if (studySpecialAdjJieyesSave) {
                log.info("专题申请保存- 专题结业设置子表（证书）保存成功");
            }
        }
        //-----------------保存专题结业设置子表（证书）结束---------
    }

    /**
     * 保存专题历史子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置子表（证书））
     *
     * @param studySpecialVo
     * @param specialHisId
     */
    public void saveSpecialHisChildren(Long specialHisId, StudySpecialVo studySpecialVo) {
        //---------------保存专题对象(机构、部门、人员)开始-----------
        if (CollectionUtils.isNotEmpty(studySpecialVo.getStudySpecialRangeOrgDeptList())) {
            //获取专题对象（季候、部门）信息
            List<StudySpecialRangeVo> studySpecialRangeOrgDeptList = studySpecialVo.getStudySpecialRangeOrgDeptList();
            //获取专题对象（人员）信息
            if (CollectionUtils.isNotEmpty(studySpecialVo.getStudySpecialRangeUserList())) {
                //合并专题对象
                studySpecialRangeOrgDeptList.addAll(studySpecialVo.getStudySpecialRangeUserList());
            }
            List<StudySpecialHisRange> studySpecialHisRangeList = new ArrayList<>();
            //关联信息
            for (StudySpecialRangeVo studySpecialRangeVo : studySpecialRangeOrgDeptList) {
                studySpecialRangeVo.setSpecialId(specialHisId);
                studySpecialRangeVo.setId(IdGenerator.getIdGenerator());
                studySpecialHisRangeList.add(dozerMapper.map(studySpecialRangeVo, StudySpecialHisRange.class));
            }
            log.info("专题历史保存-专题对象（机构、部门、人员）保存参数:{}", studySpecialHisRangeList);
            //专题历史对象保存
            boolean specialHisRangeSave = studySpecialHisRangeService.saveBatch(studySpecialHisRangeList);
            if (specialHisRangeSave) {
                log.info("专题历史保存- 专题对象（机构、部门、人员）保存成功");
            }
        }
        // ---------------保存专题对象（机构、部门、人员）结束----------
        //保存专题必考人员
        if (CollectionUtils.isNotEmpty(studySpecialVo.getStudySpecialMustExamUserList())) {
            log.info("专题申请保存-专题必考人员保存参数：{}", studySpecialVo.getStudySpecialMustExamUserList());
            List<StudySpecialHisMustExamUser> studySpecialHisMustExamUsers = new ArrayList<>();
            for (StudySpecialMustExamUserVo studySpecialMustExamUserVo : studySpecialVo.getStudySpecialMustExamUserList()) {
                studySpecialMustExamUserVo.setSpecialId(specialHisId);
                studySpecialMustExamUserVo.setSpecialPaperId(studySpecialVo.getSpecialPaperId());
                studySpecialMustExamUserVo.setId(IdGenerator.getIdGenerator());
                StudySpecialHisMustExamUser studySpecialHisMustExamUser = dozerMapper.map(studySpecialMustExamUserVo, StudySpecialHisMustExamUser.class);
                studySpecialHisMustExamUsers.add(studySpecialHisMustExamUser);
            }
            boolean specialMustExamUserSave = studySpecialHisMustExamUserService.saveBatch(studySpecialHisMustExamUsers);
            if (specialMustExamUserSave) {
                log.info("专题申请保存- 专题必考人员保存成功");
            }
        }
        //-----------------保存专题课程开始---------
        //获取专题课程
        if (CollectionUtils.isNotEmpty(studySpecialVo.getStudySpecialLessonList())) {
            List<StudySpecialLessonVo> studySpecialLessonVos = studySpecialVo.getStudySpecialLessonList();
            List<StudySpecialHisLesson> studySpecialHisLessonList = new ArrayList<>();
            for (StudySpecialLessonVo studySpecialLessonVo : studySpecialLessonVos) {
                //关联信息
                studySpecialLessonVo.setId(IdGenerator.getIdGenerator());
                studySpecialLessonVo.setSpecialId(specialHisId);
                //转换类型
                StudySpecialHisLesson studySpecialHisLesson = dozerMapper.map(studySpecialLessonVo, StudySpecialHisLesson.class);
                studySpecialHisLessonList.add(studySpecialHisLesson);
                //-----------------保存专题课程课件开始---------

                if (CollectionUtils.isNotEmpty(studySpecialLessonVo.getStudySpecialLessonCouList())) {
                    List<StudySpecialLessonCouVo> studySpecialLessonCouList = studySpecialLessonVo.getStudySpecialLessonCouList();
                    List<StudySpecialHisLessonCou> studySpecialHisLessonCouList = new ArrayList<>();
                    for (StudySpecialLessonCouVo studySpecialLessonCouVo : studySpecialLessonCouList) {
                        //关联信息
                        studySpecialLessonCouVo.setSpecialId(specialHisId);
                        studySpecialLessonCouVo.setSpecialLessonId(studySpecialLessonVo.getId());
                        studySpecialLessonCouVo.setId(IdGenerator.getIdGenerator());
                        //转换类型
                        StudySpecialHisLessonCou studySpecialHisLessonCou = dozerMapper.map(studySpecialLessonCouVo, StudySpecialHisLessonCou.class);
                        studySpecialHisLessonCouList.add(studySpecialHisLessonCou);
                    }
                    //保存专题历史课程课件
                    boolean studySpecialHisLessonCouSave = studySpecialHisLessonCouService.saveBatch(studySpecialHisLessonCouList);
                    if (studySpecialHisLessonCouSave) {
                        log.info("专题历史保存- 专题课程[" + studySpecialLessonVo.getLessonName() + "]课件保存成功");
                    }
                } else {
                    throw new IncloudException("选中课程[" + studySpecialHisLesson.getLessonName() + "]至少选中一条课件信息");
                }
                //-----------------保存专题课程课件结束---------
            }
            log.info("专题历史保存-专题课程保存参数:{}", studySpecialHisLessonList);
            //保存专题课程
            boolean specialHisLessonSave = studySpecialHisLessonService.saveBatch(studySpecialHisLessonList);
            if (specialHisLessonSave) {
                log.info("专题历史保存- 专题课程保存成功");
            }
        } else {
            throw new IncloudException("请至少选择一条课程");
        }
        //-----------------保存专题历史课程结束------------
        //-----------------保存专题历史资料信息开始---------
        //获取专题资料信息
        if (CollectionUtils.isNotEmpty(studySpecialVo.getStudySpecialMaterialsList())) {
            List<StudySpecialMaterialsVo> studySpecialMaterialsVoList = studySpecialVo.getStudySpecialMaterialsList();
            List<StudySpecialHisMaterials> studySpecialHisMaterialsList = new ArrayList<>();
            for (StudySpecialMaterialsVo studySpecialMaterialsVo : studySpecialMaterialsVoList) {
                //关联信息
                studySpecialMaterialsVo.setSpecialId(specialHisId);
                studySpecialMaterialsVo.setId(IdGenerator.getIdGenerator());
                //类型转换
                StudySpecialHisMaterials studySpecialHisMaterials = dozerMapper.map(studySpecialMaterialsVo, StudySpecialHisMaterials.class);
                studySpecialHisMaterialsList.add(studySpecialHisMaterials);
            }
            log.info("专题历史保存-专题资料保存参数:{}", studySpecialHisMaterialsList);
            //保存专题资料
            boolean studySpecialHisMaterialsSave = studySpecialHisMaterialsService.saveBatch(studySpecialHisMaterialsList);
            if (studySpecialHisMaterialsSave) {
                log.info("专题历史保存- 专题资料保存成功");
            }
        }
        //-----------------保存专题历史资料信息结束---------
        //-----------------保存专题历史结业设置子表（证书）开始---------
        if (CollectionUtils.isNotEmpty(studySpecialVo.getStudySpecialJieyeList())) {
            //获取专题结业设置子表（证书）信息
            List<StudySpecialJieyeVo> studySpecialJieyeVoList = studySpecialVo.getStudySpecialJieyeList();
            List<StudySpecialHisJieye> studySpecialHisJieyesList = new ArrayList<>();
            for (StudySpecialJieyeVo studySpecialJieyeVo : studySpecialJieyeVoList) {
                //关联信息
                studySpecialJieyeVo.setSpecialId(specialHisId);
                studySpecialJieyeVo.setId(IdGenerator.getIdGenerator());
                //类型转换
                StudySpecialHisJieye studySpecialHisJieye = dozerMapper.map(studySpecialJieyeVo, StudySpecialHisJieye.class);
                studySpecialHisJieyesList.add(studySpecialHisJieye);
            }
            log.info("专题历史保存-专题结业设置子表（证书）保存参数:{}", studySpecialHisJieyesList);
            //保存专题结业设置子表（证书）
            boolean studySpecialJieyesSave = studySpecialHisJieyeService.saveBatch(studySpecialHisJieyesList);
            if (studySpecialJieyesSave) {
                log.info("专题历史保存- 专题结业设置子表（证书）保存成功");
            }
        }
        //-----------------保存专题历史结业设置子表（证书）结束---------

    }

    /**
     * 保存专题原子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置子表（证书））
     *
     * @param studySpecialAdjVo
     * @param specialId
     */
    public void saveSpecialChildren(Long specialId, StudySpecialAdjVo studySpecialAdjVo) {
        //---------------保存专题对象(机构、部门、人员)开始-----------
        if (CollectionUtils.isNotEmpty(studySpecialAdjVo.getStudySpecialRangeOrgDeptList())) {
            //获取专题对象（季候、部门）信息
            List<StudySpecialAdjRangeVo> studySpecialAdjRangeOrgDeptList = studySpecialAdjVo.getStudySpecialRangeOrgDeptList();
            //获取专题对象（人员）信息
            if (CollectionUtils.isNotEmpty(studySpecialAdjVo.getStudySpecialRangeUserList())) {
                //合并专题对象
                studySpecialAdjRangeOrgDeptList.addAll(studySpecialAdjVo.getStudySpecialRangeUserList());
            }
            List<StudySpecialRange> studySpecialRangeList = new ArrayList<>();
            //关联信息
            for (StudySpecialAdjRangeVo studySpecialAdjRangeVo : studySpecialAdjRangeOrgDeptList) {
                studySpecialAdjRangeVo.setSpecialId(specialId);
                studySpecialAdjRangeVo.setId(IdGenerator.getIdGenerator());
                studySpecialRangeList.add(dozerMapper.map(studySpecialAdjRangeVo, StudySpecialRange.class));
            }
            log.info("专题保存-专题对象（机构、部门、人员）保存参数:{}", studySpecialRangeList);
            //专题历史对象保存
            boolean specialRangeSave = studySpecialRangeService.saveBatch(studySpecialRangeList);
            if (specialRangeSave) {
                log.info("专题保存- 专题对象（机构、部门、人员）保存成功");
            }
        }
        // ---------------保存专题对象（机构、部门、人员）结束----------
        //保存专题必考人员
        if (CollectionUtils.isNotEmpty(studySpecialAdjVo.getStudySpecialMustExamUserList())) {
            log.info("专题申请保存-专题必考人员保存参数：{}", studySpecialAdjVo.getStudySpecialMustExamUserList());
            List<StudySpecialMustExamUser> studySpecialMustExamUsers = new ArrayList<>();
            for (StudySpecialAdjMustExamUserVo studySpecialAdjMustExamUserVo : studySpecialAdjVo.getStudySpecialMustExamUserList()) {
                studySpecialAdjMustExamUserVo.setSpecialId(specialId);
                studySpecialAdjMustExamUserVo.setSpecialPaperId(studySpecialAdjVo.getSpecialPaperId());
                StudySpecialMustExamUser studySpecialMustExamUser = dozerMapper.map(studySpecialAdjMustExamUserVo, StudySpecialMustExamUser.class);
                studySpecialMustExamUsers.add(studySpecialMustExamUser);
            }
            boolean specialMustExamUserSave = studySpecialMustExamUserService.saveBatch(studySpecialMustExamUsers);
            if (specialMustExamUserSave) {
                log.info("专题申请保存- 专题必考人员保存成功");
            }
        }
        //-----------------保存专题课程开始---------

        //获取专题课程
        if (CollectionUtils.isNotEmpty(studySpecialAdjVo.getStudySpecialLessonList())) {
            List<StudySpecialAdjLessonVo> studySpecialAdjLessonVos = studySpecialAdjVo.getStudySpecialLessonList();
            List<StudySpecialLesson> studySpecialLessonList = new ArrayList<>();
            for (StudySpecialAdjLessonVo studySpecialAdjLessonVo : studySpecialAdjLessonVos) {
                //关联信息
                studySpecialAdjLessonVo.setId(IdGenerator.getIdGenerator());
                studySpecialAdjLessonVo.setSpecialId(specialId);
                //转换类型
                StudySpecialLesson studySpecialLesson = dozerMapper.map(studySpecialAdjLessonVo, StudySpecialLesson.class);
                studySpecialLessonList.add(studySpecialLesson);
                //-----------------保存专题课程课件开始---------
                if (CollectionUtils.isNotEmpty(studySpecialAdjLessonVo.getStudySpecialLessonCouList())) {
                    List<StudySpecialAdjLessonCouVo> studySpecialAdjLessonCouList = studySpecialAdjLessonVo.getStudySpecialLessonCouList();
                    List<StudySpecialLessonCou> studySpecialLessonCouList = new ArrayList<>();
                    for (StudySpecialAdjLessonCouVo studySpecialAdjLessonCouVo : studySpecialAdjLessonCouList) {
                        //关联信息
                        studySpecialAdjLessonCouVo.setSpecialId(specialId);
                        studySpecialAdjLessonCouVo.setSpecialLessonId(studySpecialAdjLessonVo.getId());
                        studySpecialAdjLessonCouVo.setId(IdGenerator.getIdGenerator());
                        //转换类型
                        StudySpecialLessonCou studySpecialLessonCou = dozerMapper.map(studySpecialAdjLessonCouVo, StudySpecialLessonCou.class);
                        studySpecialLessonCouList.add(studySpecialLessonCou);
                    }
                    //保存专题课程课件
                    boolean studySpecialLessonCouSave = studySpecialLessonCouService.saveBatch(studySpecialLessonCouList);
                    if (studySpecialLessonCouSave) {
                        log.info("专题保存- 专题课程[" + studySpecialAdjLessonVo.getLessonName() + "]课件保存成功");
                    }
                } else {
                    throw new IncloudException("选中课程[" + studySpecialAdjLessonVo.getLessonName() + "]至少选中一条课件信息");
                }
                //-----------------保存专题课程课件结束---------
            }
            log.info("专题保存-专题课程保存参数:{}", studySpecialLessonList);
            //保存专题课程
            boolean specialLessonSave = studySpecialLessonService.saveBatch(studySpecialLessonList);
            if (specialLessonSave) {
                log.info("专题保存- 专题课程保存成功");
            }
        } else {
            throw new IncloudException("请至少选择一条课程");
        }
        //-----------------保存专题课程结束------------
        //-----------------保存专题资料信息开始---------
        //获取专题资料信息
        if (CollectionUtils.isNotEmpty(studySpecialAdjVo.getStudySpecialMaterialsList())) {
            List<StudySpecialAdjMaterialsVo> studySpecialAdjMaterialsVoList = studySpecialAdjVo.getStudySpecialMaterialsList();
            List<StudySpecialMaterials> studySpecialMaterialsList = new ArrayList<>();
            for (StudySpecialAdjMaterialsVo studySpecialAdjMaterialsVo : studySpecialAdjMaterialsVoList) {
                //关联信息
                studySpecialAdjMaterialsVo.setSpecialId(specialId);
                studySpecialAdjMaterialsVo.setId(IdGenerator.getIdGenerator());
                //类型转换
                StudySpecialMaterials studySpecialMaterials = dozerMapper.map(studySpecialAdjMaterialsVo, StudySpecialMaterials.class);
                studySpecialMaterialsList.add(studySpecialMaterials);
            }
            log.info("专题保存-专题资料保存参数:{}", studySpecialMaterialsList);
            //保存专题资料
            boolean studySpecialMaterialsSave = studySpecialMaterialsService.saveBatch(studySpecialMaterialsList);
            if (studySpecialMaterialsSave) {
                log.info("专题保存- 专题资料保存成功");
            }
        }
        //-----------------保存专题资料信息结束---------
        //-----------------保存专题结业设置子表（证书）开始---------
        if (CollectionUtils.isNotEmpty(studySpecialAdjVo.getStudySpecialJieyeList())) {
            //获取专题结业设置子表（证书）信息
            List<StudySpecialAdjJieyeVo> studySpecialAdjJieyeVoList = studySpecialAdjVo.getStudySpecialJieyeList();
            List<StudySpecialJieye> studySpecialJieyesList = new ArrayList<>();
            for (StudySpecialAdjJieyeVo studySpecialAdjJieyeVo : studySpecialAdjJieyeVoList) {
                //关联信息
                studySpecialAdjJieyeVo.setSpecialId(specialId);
                studySpecialAdjJieyeVo.setId(IdGenerator.getIdGenerator());
                //类型转换
                StudySpecialJieye studySpecialJieye = dozerMapper.map(studySpecialAdjJieyeVo, StudySpecialJieye.class);
                studySpecialJieyesList.add(studySpecialJieye);
            }
            log.info("专题保存-专题结业设置子表（证书）保存参数:{}", studySpecialJieyesList);
            //保存专题结业设置子表（证书）
            boolean studySpecialJieyesSave = studySpecialJieyeService.saveBatch(studySpecialJieyesList);
            if (studySpecialJieyesSave) {
                log.info("专题保存- 专题结业设置子表（证书）保存成功");
            }
        }
        //-----------------保存专题结业设置子表（证书）结束---------

    }

    /**
     * 通过专题ID 删除专题调整子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置）
     *
     * @param specialId
     */
    public void deleteSpecialAdjChildren(Long specialId) {
        log.info("先删除专题" + specialId + "子表信息");
        if (ObjectUtil.isNotEmpty(specialId)) {
            //删除专题必考人员
            boolean specialAdjMustExamUserDelete = studySpecialAdjMustExamUserService.remove(Wrappers.<StudySpecialAdjMustExamUser>lambdaQuery().eq(StudySpecialAdjMustExamUser::getSpecialId, specialId));
            if (specialAdjMustExamUserDelete) {
                log.info("专题申请修改- 删除专题必考人员成功");
            }
            //删除专题对象
            boolean specialAdjRangeRemove = studySpecialAdjRangeService.remove(Wrappers.<StudySpecialAdjRange>lambdaQuery().eq(StudySpecialAdjRange::getSpecialId, specialId));
            if (specialAdjRangeRemove) {
                log.info("专题申请修改- 删除专题对象成功");
            }
            //获取专题课程Id
            List<StudySpecialAdjLesson> studySpecialAdjLessonList = studySpecialAdjLessonService.list(Wrappers.<StudySpecialAdjLesson>lambdaQuery().eq(StudySpecialAdjLesson::getSpecialId, specialId));
            List<Long> specialAdjLessonIdList = null;
            if (CollectionUtils.isNotEmpty(studySpecialAdjLessonList)) {
                specialAdjLessonIdList = studySpecialAdjLessonList.stream().map(StudySpecialAdjLesson::getId).collect(Collectors.toList());
            }
            //删除专题课程
            boolean specialAdjLessonRemove = studySpecialAdjLessonService.remove(Wrappers.<StudySpecialAdjLesson>lambdaQuery().eq(StudySpecialAdjLesson::getSpecialId, specialId));
            if (specialAdjLessonRemove) {
                log.info("专题申请修改- 删除专题课程成功");
            }
            //删除专题课程课件
            if (CollectionUtils.isNotEmpty(specialAdjLessonIdList)) {
                log.info("专题申请修改- 删除课程课件参数：{}", specialAdjLessonIdList);
                boolean specialAdjLessonCouDelete = studySpecialAdjLessonCouService.remove(Wrappers.<StudySpecialAdjLessonCou>lambdaQuery().in(StudySpecialAdjLessonCou::getSpecialLessonId, specialAdjLessonIdList));
                if (specialAdjLessonCouDelete) {
                    log.info("专题申请修改- 删除专题课程课件成功");
                }
            }
            //删除专题资料
            boolean specialAdjMaterialsRemove = studySpecialAdjMaterialsService.remove(Wrappers.<StudySpecialAdjMaterials>lambdaQuery().eq(StudySpecialAdjMaterials::getSpecialId, specialId));
            if (specialAdjMaterialsRemove) {
                log.info("专题申请修改- 删除专题资料成功");
            }
            //删除专题结业设置
            boolean specialAdjJieyeRemove = studySpecialAdjJieyeService.remove(Wrappers.<StudySpecialAdjJieye>lambdaQuery().eq(StudySpecialAdjJieye::getSpecialId, specialId));
            if (specialAdjJieyeRemove) {
                log.info("专题申请修改- 删除专题结业设置成功");
            }
        }

    }

    /**
     * 根据流程实例Id查询记录
     *
     * @param procinstId
     * @return
     */
    private StudySpecialAdj getInfoByProcinstId(String procinstId) {
        return super.getOne(Wrappers.<StudySpecialAdj>lambdaQuery().eq(StudySpecialAdj::getCamundaProcinsId, procinstId));
    }
}
