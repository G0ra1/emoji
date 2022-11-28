package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.biz.study.constants.*;
import com.netwisd.biz.study.dto.*;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.fegin.WfClient;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.*;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.util.StudyTimeUtil;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题定义表 功能描述...
 * @date 2022-05-13 10:59:05
 */
@Service
@Slf4j
public class StudySpecialServiceImpl extends BatchServiceImpl<StudySpecialMapper, StudySpecial> implements StudySpecialService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudySpecialMapper studySpecialMapper;

    @Autowired
    private StudySpecialJieyeService studySpecialJieyeService;//专题结业设置service
    @Autowired
    private StudySpecialJieyeMapper studySpecialJieyeMapper;//专题结业设置mapper

    @Autowired
    private StudySpecialRangeService studySpecialRangeService;//专题人员service
    @Autowired
    private StudySpecialRangeMapper studySpecialRangeMapper;//专题人员mapper

    @Autowired
    private StudySpecialMustExamUserService studySpecialMustExamUserService;//专题必考人员service

    @Autowired
    private StudySpecialMustExamUserMapper studySpecialMustExamUserMapper;//专题必考人员mapper

    @Autowired
    private StudySpecialLessonCouService studySpecialLessonCouService;//专题课程课件service
    @Autowired
    private StudySpecialLessonCouMapper studySpecialLessonCouMapper;//专题课程课件mapper

    @Autowired
    private StudySpecialMaterialsService studySpecialMaterialsService;//专题学习资料service

    @Autowired
    private StudySpecialMaterialsMapper studySpecialMaterialsMapper;//专题与学习资料mapper

    @Autowired
    private StudySpecialLessonService studySpecialLessonService;//专题课程service

    @Autowired
    private StudySpecialLessonMapper studySpecialLessonMapper;//专题课程mapper

    @Autowired
    private StudyLessonMapper studyLessonMapper;

    @Autowired
    private StudyUserExamMapper studyUserExamMapper;

    @Autowired
    private StudyBrowseService studyBrowseService;

    @Autowired
    private StudyUserStudyRecordsMapper studyUserStudyRecordsMapper;

    @Autowired
    private StudyUserLearnApplyMapper studyUserLearnApplyMapper;

    @Autowired
    private StudyCollectionMapper studyCollectionMapper;

    @Autowired
    private MdmClient mdmClient;//主数据client
    @Autowired
    private WfClient wfClient;

    /**
     * 专题台账
     *
     * @param studySpecialDto
     * @return
     */
    @Override
    public Page<StudySpecialVo> list(StudySpecialDto studySpecialDto) {
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
        Integer studyUserRole = mdmClient.getStudyUserRole(appUser.getId());//获取当前登陆人的角色
        LambdaQueryWrapper<StudySpecial> queryWrapper = new LambdaQueryWrapper<>();
        //如果当前登陆人是老师，尽可查看自己上传。
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {//判断是否是老师
            queryWrapper.eq(StudySpecial::getCreateUserId, appUser.getId());
        }
        //根据实际业务构建具体的参数做查询
        if (StringUtils.isNotBlank(studySpecialDto.getLabelCode())) {
            String[] labelCodeList = studySpecialDto.getLabelCode().split(",");
            for (String labelCode : labelCodeList) {
                queryWrapper.or().like(StudySpecial::getLabelCode, labelCode);//标签
            }
        }
        queryWrapper.like(StringUtils.isNotBlank(studySpecialDto.getSpecialName()), StudySpecial::getSpecialName, studySpecialDto.getSpecialName());
        queryWrapper.eq(null != studySpecialDto.getTypeCode(), StudySpecial::getTypeCode, studySpecialDto.getTypeCode());
        queryWrapper.eq(null != studySpecialDto.getIsIndex(), StudySpecial::getIsIndex, studySpecialDto.getIsIndex());
        queryWrapper.eq(null != studySpecialDto.getIsEnable(), StudySpecial::getIsEnable, studySpecialDto.getIsEnable());
        queryWrapper.eq(null != studySpecialDto.getSpecialTimeType(), StudySpecial::getSpecialTimeType, studySpecialDto.getSpecialTimeType());
        queryWrapper.orderByDesc(StudySpecial::getCreateTime);
        Page<StudySpecial> page = studySpecialMapper.selectPage(studySpecialDto.getPage(), queryWrapper);
        Page<StudySpecialVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudySpecialVo.class);
        //组合专题时间
        if (CollectionUtils.isNotEmpty(pageVo.getRecords())) {
            for (StudySpecialVo studySpecialVo : pageVo.getRecords()) {
                if (studySpecialVo.getSpecialTimeType().equals(SpecialTimeTypeEnum.ORDINARY.getCode())) {
                    LocalDateTime specialStartTime = studySpecialVo.getSpecialStartTime();
                    String startTime = specialStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDateTime specialEndTime = studySpecialVo.getSpecialEndTime();
                    String endTime = specialEndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    studySpecialVo.setSpecialTime(startTime.split("-")[0] + "年" + startTime.split("-")[1] + "月" + startTime.split("-")[2] + "日-" + endTime.split("-")[0] + "年" + endTime.split("-")[1] + "月" + endTime.split("-")[2] + "日");
                } else {
                    LocalDateTime specialStartTime = studySpecialVo.getSpecialStartTime();
                    String startTime = specialStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    studySpecialVo.setSpecialTime(startTime.split("-")[0] + "年" + startTime.split("-")[1] + "月" + startTime.split("-")[2] + "日开始");
                }

            }
        }
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 专题详情
     *
     * @param id
     * @return
     */
    @Override
    public StudySpecialVo detail(Long id) {
        log.info("专题详情：{}", id);
        StudySpecialVo studySpecialVo = dozerMapper.map(super.getById(id), StudySpecialVo.class);
        studySpecialVo = buildChildren(studySpecialVo);
        log.info("专题详情返回值：{}", studySpecialVo);
        return studySpecialVo;
    }

    /**
     * 启用停用
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean isEnable(Long id) {
        log.info("启用停用：{}", id);
        Integer isEnable = YesNo.YES.code;
        StudySpecial studySpecial = super.getById(id);
        if (ObjectUtils.isNotEmpty(studySpecial)) {
            if (studySpecial.getIsEnable().equals(YesNo.YES.code)) {
                isEnable = YesNo.NO.code;
            }
        }
        super.update(Wrappers.<StudySpecial>lambdaUpdate().eq(StudySpecial::getId, id).set(StudySpecial::getIsEnable, isEnable));
        return true;
    }

    /**
     * 是否首页展示
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean showIndex(Long id) {
        log.info("是否首页展示：{}", id);
        Integer isIndex = YesNo.YES.code;
        StudySpecial studySpecial = super.getById(id);
        if (ObjectUtils.isNotEmpty(studySpecial)) {
            if (studySpecial.getIsIndex().equals(YesNo.YES.code)) {
                isIndex = YesNo.NO.code;
            }
        }
        super.update(Wrappers.<StudySpecial>lambdaUpdate().eq(StudySpecial::getId, id).set(StudySpecial::getIsIndex, isIndex));
        return true;
    }

    /**
     * 流程保存
     *
     * @param studySpecialDto
     * @return
     */
    @Override
    @Transactional
    public StudySpecialVo procSave(StudySpecialDto studySpecialDto) {
        log.info("流程保存数据:{}", studySpecialDto);
        Optional.ofNullable(studySpecialDto).orElseThrow(() -> new IncloudException("请将专题信息填写完整"));
        //非空校验
        this.notNull(studySpecialDto);
        StudySpecial studySpecial = dozerMapper.map(studySpecialDto, StudySpecial.class);
        if (null == studySpecial.getFileId()) {
            throw new IncloudException("请上传封面");
        }
        //专题选择了考试试卷才设置阅卷老师,默认为当前登录人
        if (studySpecialDto.getSpecialPaperName()!=null) {
            LoginAppUser appUser = AppUserUtil.getLoginAppUser();
            Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
            studySpecial.setSpecialExamPaperTeacherId(appUser.getId());
            studySpecial.setSpecialExamPaperTeacherName(appUser.getUsername());

        }
        if (ObjectUtils.isEmpty(studySpecialDto.getStatus())) {
            studySpecial.setStatus(StudyStatus.NO_TAKE_EFFECT.code);
        }
        studySpecial.setIsIndex(YesNo.NO.code);
        studySpecial.setIsEnable(YesNo.NO.code);
        studySpecial.setHits(0L);
        if (studySpecialDto.getSpecialTimeType().equals(SpecialTimeTypeEnum.LONG_TERM.code)) {
            studySpecial.setSpecialEndTime(LocalDateTime.of(2999, 12, 31, 23, 59,59));
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        studySpecial.setCreateUserName(loginAppUser.getUserNameCh());
        studySpecial.setCreateTime(LocalDateTime.now());
        super.save(studySpecial);
        //保存专题申请子表信息（专题对象、专题必考人员、专题课程、专题课程课件、专题资料、专题结业设置子表（证书））
        saveSpecialChildren(studySpecialDto);
        //返回保存后信息，工作流要用
        StudySpecialVo infoVo = dozerMapper.map(super.getById(studySpecial.getId()), StudySpecialVo.class);
        infoVo = buildChildren(infoVo);
        return infoVo;
    }

    /**
     * 流程修改
     *
     * @param studySpecialDto
     * @return
     */
    @Override
    @Transactional
    public StudySpecialVo procUpdate(StudySpecialDto studySpecialDto) {
        log.info("流程修改数据:{}", studySpecialDto);
        Optional.ofNullable(studySpecialDto).orElseThrow(() -> new IncloudException("请将专题信息填写完整"));
        //非空校验
        this.notNull(studySpecialDto);
        StudySpecial studySpecial = dozerMapper.map(studySpecialDto, StudySpecial.class);
        //设置阅卷老师默认值---取当前登陆人
        if (ObjectUtil.isEmpty(studySpecial.getSpecialExamPaperTeacherId()) && StringUtils.isBlank(studySpecial.getSpecialExamPaperTeacherName())) {
            LoginAppUser appUser = AppUserUtil.getLoginAppUser();
            Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
            studySpecial.setSpecialExamPaperTeacherId(appUser.getId());
            studySpecial.setSpecialExamPaperTeacherName(appUser.getUsername());
        }
        if (null == studySpecial.getFileId()) {
            throw new IncloudException("请上传封面");
        }
        if (studySpecialDto.getSpecialTimeType().equals(SpecialTimeTypeEnum.LONG_TERM.code)) {
            studySpecial.setSpecialEndTime(LocalDateTime.of(2999, 12, 31, 23, 59,59));
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        studySpecial.setCreateUserName(loginAppUser.getUserNameCh());
        studySpecial.setUpdateTime(LocalDateTime.now());
        if (super.updateById(studySpecial)) {
            // 通过专题ID 删除专题子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置）
            deleteSpecialChildren(studySpecialDto.getId());
        }
        // 保存专题子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置子表（证书））
        saveSpecialChildren(studySpecialDto);
        //返回保存后信息，工作流要用
        StudySpecialVo infoVo = dozerMapper.map(super.getById(studySpecial.getId()), StudySpecialVo.class);
        infoVo = buildChildren(infoVo);
        return infoVo;
    }

    /**
     * 流程详情
     *
     * @param procInstId
     * @return
     */
    @Override
    public StudySpecialVo procDetail(String procInstId) {
        log.info("流程查看数据:{}", procInstId);
        StudySpecialVo infoVo = dozerMapper.map(Optional.ofNullable(this.getInfoByProcinstId(procInstId)).orElse(new StudySpecial()), StudySpecialVo.class);
        infoVo = buildChildren(infoVo);
        return infoVo;
    }

    /**
     * 通过id获取专题结果表信息转为调整表信息（结果表点击修改重新进修改流程查询）
     *
     * @param id
     * @return
     */
    @Override
    public StudySpecialAdjVo detailForAdj(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new IncloudException("请至少选择一条专题查看");
        }
        log.info("专题详情参数：{}", id);
        StudySpecial studySpecial = super.getById(id);
        StudySpecialAdjVo studySpecialAdjVo = null;
        if (ObjectUtils.isNotEmpty(studySpecial)) {
            if (studySpecial.getStatus().equals(StudyStatus.TAKE_EFFECT.code)) {
                studySpecialAdjVo = dozerMapper.map(studySpecial, StudySpecialAdjVo.class);
                //设置关联信息
                studySpecialAdjVo.setId(null);
                studySpecialAdjVo.setLinkId(studySpecial.getId());
                //获取专题对象（机构、部门）范围
                List<String> rangeTypeList = new ArrayList<>();
                rangeTypeList.add(RangeTypeEnum.ORG.code);
                rangeTypeList.add(RangeTypeEnum.DEPT.code);
                List<StudySpecialRange> studySpecialRangeOrgDeptList = studySpecialRangeMapper.selectList(Wrappers.<StudySpecialRange>lambdaQuery().eq(StudySpecialRange::getSpecialId, studySpecialAdjVo.getLinkId()).in(StudySpecialRange::getRangeType, rangeTypeList));
                if (CollectionUtils.isNotEmpty(studySpecialRangeOrgDeptList)) {
                    List<StudySpecialAdjRangeVo> specialAdjRangeVos = new ArrayList<>();
                    for (StudySpecialRange studySpecialRange : studySpecialRangeOrgDeptList) {
                        StudySpecialAdjRangeVo studySpecialAdjRangeVo = dozerMapper.map(studySpecialRange, StudySpecialAdjRangeVo.class);
                        studySpecialAdjRangeVo.setId(null);
                        specialAdjRangeVos.add(studySpecialAdjRangeVo);
                    }
                    studySpecialAdjVo.setStudySpecialRangeOrgDeptList(specialAdjRangeVos);
                }
                //获取专题对象（人员）范围
                List<StudySpecialRange> studySpecialRangeUserList = studySpecialRangeMapper.selectList(Wrappers.<StudySpecialRange>lambdaQuery().eq(StudySpecialRange::getSpecialId, studySpecialAdjVo.getLinkId()).eq(StudySpecialRange::getRangeType, RangeTypeEnum.USER.code));
                if (CollectionUtils.isNotEmpty(studySpecialRangeUserList)) {
                    List<StudySpecialAdjRangeVo> studySpecialAdjRangeVos = new ArrayList<>();
                    for (StudySpecialRange studySpecialRange : studySpecialRangeUserList) {
                        StudySpecialAdjRangeVo studySpecialAdjRangeVo = dozerMapper.map(studySpecialRange, StudySpecialAdjRangeVo.class);
                        studySpecialAdjRangeVo.setId(null);
                        studySpecialAdjRangeVos.add(studySpecialAdjRangeVo);
                    }
                    studySpecialAdjVo.setStudySpecialRangeUserList(studySpecialAdjRangeVos);
                }
                //获取课程信息
                List<StudySpecialLesson> studySpecialLessonList = studySpecialLessonMapper.selectList(Wrappers.<StudySpecialLesson>lambdaQuery().eq(StudySpecialLesson::getSpecialId, studySpecialAdjVo.getLinkId()));
                if (CollectionUtils.isNotEmpty(studySpecialLessonList)) {
                    List<StudySpecialAdjLessonVo> studySpecialAdjLessonVos = new ArrayList<>();
                    //获取课程课件信息
                    for (StudySpecialLesson studySpecialLesson : studySpecialLessonList) {
                        StudySpecialAdjLessonVo studySpecialAdjLessonVo = dozerMapper.map(studySpecialLesson, StudySpecialAdjLessonVo.class);
                        if (null != studySpecialLesson && null != studySpecialLesson.getId()) {
                            List<StudySpecialLessonCou> studySpecialLessonCouList = studySpecialLessonCouMapper.selectList(Wrappers.<StudySpecialLessonCou>lambdaQuery().eq(StudySpecialLessonCou::getSpecialLessonId, studySpecialLesson.getId()));
                            if (CollectionUtils.isNotEmpty(studySpecialLessonCouList)) {
                                List<StudySpecialAdjLessonCouVo> studySpecialAdjLessonCouVos = new ArrayList<>();
                                for (StudySpecialLessonCou studySpecialLessonCou : studySpecialLessonCouList) {
                                    StudySpecialAdjLessonCouVo studySpecialAdjLessonCouVo = dozerMapper.map(studySpecialLessonCou, StudySpecialAdjLessonCouVo.class);
                                    studySpecialAdjLessonCouVo.setId(null);
                                    studySpecialAdjLessonCouVos.add(studySpecialAdjLessonCouVo);
                                }
                                studySpecialAdjLessonVo.setStudySpecialLessonCouList(studySpecialAdjLessonCouVos);
                            }
                        }
                        studySpecialAdjLessonVo.setId(null);
                        studySpecialAdjLessonVos.add(studySpecialAdjLessonVo);
                    }
                    studySpecialAdjVo.setStudySpecialLessonList(studySpecialAdjLessonVos);
                }
                //获取学习资料
                List<StudySpecialMaterials> specialMaterialsList = studySpecialMaterialsMapper.selectList(Wrappers.<StudySpecialMaterials>lambdaQuery().eq(StudySpecialMaterials::getSpecialId, studySpecialAdjVo.getLinkId()));
                if (CollectionUtils.isNotEmpty(specialMaterialsList)) {
                    List<StudySpecialAdjMaterialsVo> studySpecialAdjMaterialsVos = new ArrayList<>();
                    for (StudySpecialMaterials studySpecialMaterials : specialMaterialsList) {
                        StudySpecialAdjMaterialsVo studySpecialAdjMaterialsVo = dozerMapper.map(studySpecialMaterials, StudySpecialAdjMaterialsVo.class);
                        studySpecialAdjMaterialsVo.setId(null);
                        studySpecialAdjMaterialsVos.add(studySpecialAdjMaterialsVo);
                    }
                    studySpecialAdjVo.setStudySpecialMaterialsList(studySpecialAdjMaterialsVos);
                }
                //获取结业信息
                List<StudySpecialJieye> specialJieyeList = studySpecialJieyeMapper.selectList(Wrappers.<StudySpecialJieye>lambdaQuery().eq(StudySpecialJieye::getSpecialId, studySpecialAdjVo.getLinkId()));
                if (CollectionUtils.isNotEmpty(specialJieyeList)) {
                    List<StudySpecialAdjJieyeVo> studySpecialAdjJieyeVos = new ArrayList<>();
                    for (StudySpecialJieye studySpecialJieye : specialJieyeList) {
                        StudySpecialAdjJieyeVo studySpecialAdjJieyeVo = dozerMapper.map(studySpecialJieye, StudySpecialAdjJieyeVo.class);
                        studySpecialAdjJieyeVo.setId(null);
                        studySpecialAdjJieyeVos.add(studySpecialAdjJieyeVo);
                    }
                    studySpecialAdjVo.setStudySpecialJieyeList(studySpecialAdjJieyeVos);
                }
            } else {
                throw new IncloudException("仅可已生效的可调整");
            }
        }
        log.debug("查询成功");
        return studySpecialAdjVo;

    }

    /**
     * 流程提交
     *
     * @param processInstanceId
     * @return
     */
    @Override
    @Transactional
    public Boolean procAfterSubmit(String processInstanceId) {
        super.update(Wrappers.<StudySpecial>lambdaUpdate().set(StudySpecial::getAuditSubmitTime, LocalDateTime.now()).eq(StudySpecial::getCamundaProcinsId, processInstanceId));
        return true;
    }

    @Override
    @Transactional
    public Boolean procAuditSuccess(String processInstanceId) {
        log.info("专题审核完成：{}", processInstanceId);
        super.update(Wrappers.<StudySpecial>lambdaUpdate().set(StudySpecial::getAuditSuccessTime, LocalDateTime.now()).set(StudySpecial::getIsEnable, YesNo.YES.code).set(StudySpecial::getAuditStatus, WfProcessEnum.DONE.getType()).set(StudySpecial::getStatus, StudyStatus.TAKE_EFFECT.code).eq(StudySpecial::getCamundaProcinsId, processInstanceId));
        return true;
    }

    /**
     * 流程删除
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public Boolean procDelete(String processInstanceId) {
        log.info("删除专题参数：{}", processInstanceId);
        StudySpecial studySpecial = this.getInfoByProcinstId(processInstanceId);//专题id
        if (ObjectUtil.isNotNull(studySpecial)) {
            log.info("删除:{" + studySpecial.getSpecialName() + "}专题");
            boolean SpecialDelete = super.removeById(studySpecial.getId());
            if (SpecialDelete) {
                deleteSpecialChildren(studySpecial.getId());
            }
        }
        return true;
    }

    /**
     * 专题删除
     *
     * @param specialId
     * @return
     */
    @Override
    @Transactional
    public Boolean delete(Long specialId) {
        log.info("删除专题参数：{}", specialId);
        StudySpecial studySpecial = super.getById(specialId);//专题id
        if (ObjectUtil.isNotNull(studySpecial)) {
            if (!studySpecial.getAuditStatus().equals(WfProcessEnum.DRAFT.getType())) {
                throw new IncloudException("仅审核状态为草稿可删除！");
            }
            log.info("删除:{" + studySpecial.getSpecialName() + "}专题");
            boolean SpecialDelete = super.removeById(specialId);
            if (SpecialDelete) {
                deleteSpecialChildren(specialId);
                //流程删除
                WfEngineDto.StateDto stateDto = new WfEngineDto.StateDto();
                stateDto.setCamundaProcdefId(studySpecial.getCamundaProcdefId());
                stateDto.setCamundaProcinsId(studySpecial.getCamundaProcinsId());
                wfClient.deleteOnlyProcess(stateDto);
            }
        }
        return true;
    }

    /**
     * 根据流程实例Id查询记录
     *
     * @param procinstId
     * @return
     */
    private StudySpecial getInfoByProcinstId(String procinstId) {
        return super.getOne(Wrappers.<StudySpecial>lambdaQuery().eq(StudySpecial::getCamundaProcinsId, procinstId));
    }

    //非空校验
    public void notNull(StudySpecialDto studySpecialDto) {
        //当培训类型为普通类型时，专题结束时间不可为空
        if (studySpecialDto.getSpecialTimeType() == SpecialTimeTypeEnum.ORDINARY.code) {
            if (null == studySpecialDto.getSpecialEndTime()) {
                throw new IncloudException("普通培训的专题结束时间不可为空！");
            }
        }
        //如果关联了试卷 考试开始试卷与考试结束时间都必须设置,结业设置也必填、考试合格分数必填
        if (StringUtils.isNotBlank(studySpecialDto.getSpecialPaperName())) {
            if (null == studySpecialDto.getSpecialPaperStartTime() && null == studySpecialDto.getSpecialPaperEndTime()) {
                throw new IncloudException("存在考试试卷，请设置考试时间");
            }
            if (CollectionUtils.isEmpty(studySpecialDto.getStudySpecialJieyeList())) {
                throw new IncloudException("存在考试试卷，请设置结业设置");
            }
            if (null == studySpecialDto.getSpecialExamQualifiedScore()) {
                throw new IncloudException("存在考试试卷，请设置考试合格分数");
            }
        } else if (CollectionUtils.isNotEmpty(studySpecialDto.getStudySpecialJieyeList())) {
            throw new IncloudException("考试试卷不存在，请选择考试试卷或取消结业设置信息！");
        }

        //封面必填
        if (StringUtils.isBlank(studySpecialDto.getFileUrl())) {
            throw new IncloudException("请上传封面图");
        }
        //描述必填
        if (StringUtils.isBlank(studySpecialDto.getDescription())) {
            throw new IncloudException("请填写描述（简介）信息");
        }

    }

    /**
     * 保存专题申请子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置子表（证书））
     *
     * @param studySpecialDto
     */
    public void saveSpecialChildren(StudySpecialDto studySpecialDto) {
        //--------------专题公共字段开始---------
        Long specialId = studySpecialDto.getId();
        //--------------专题公共字段结束---------
        //---------------保存专题对象(机构、部门)开始-----------
        if (CollectionUtils.isNotEmpty(studySpecialDto.getStudySpecialRangeOrgDeptList())) {
            //获取专题对象（季候、部门）信息
            List<StudySpecialRangeDto> studySpecialRangeOrgDeptList = studySpecialDto.getStudySpecialRangeOrgDeptList();
            //获取专题对象（人员）信息
            List<StudySpecialRangeDto> studySpecialRangeUserList = null;
            if (CollectionUtils.isNotEmpty(studySpecialDto.getStudySpecialRangeUserList())) {
                studySpecialRangeUserList = studySpecialDto.getStudySpecialRangeUserList();
                for (StudySpecialRangeDto studySpecialRangeDto : studySpecialRangeUserList) {
                    studySpecialRangeDto.setRangeType(RangeTypeEnum.USER.code);
                    //合并专题对象信息
                    studySpecialRangeOrgDeptList.add(studySpecialRangeDto);
                }
            }
            List<StudySpecialRange> studySpecialRangeList = new ArrayList<>();
            //关联信息
            for (StudySpecialRangeDto studySpecialRangeDto : studySpecialRangeOrgDeptList) {
                studySpecialRangeDto.setSpecialId(specialId);
                studySpecialRangeList.add(dozerMapper.map(studySpecialRangeDto, StudySpecialRange.class));
            }
            log.info("专题申请保存-专题对象（机构、部门、人员）保存参数:{}", studySpecialRangeList);
            //专题对象保存
            boolean specialRangeSave = studySpecialRangeService.saveBatch(studySpecialRangeList);
            if (specialRangeSave) {
                log.info("专题申请保存- 专题对象（机构、部门、人员）保存成功");
            }
        }
        // ---------------保存专题对象（机构、部门）结束----------
        //保存专题必考人员
        if (CollectionUtils.isNotEmpty(studySpecialDto.getStudySpecialMustExamUserList())) {
            log.info("专题申请保存-专题必考人员保存参数：{}", studySpecialDto.getStudySpecialMustExamUserList());
            List<StudySpecialMustExamUser> studySpecialMustExamUsers = new ArrayList<>();
            for (StudySpecialMustExamUserDto studySpecialMustExamUserDto : studySpecialDto.getStudySpecialMustExamUserList()) {
                studySpecialMustExamUserDto.setSpecialId(specialId);
                studySpecialMustExamUserDto.setSpecialPaperId(studySpecialDto.getSpecialPaperId());
                StudySpecialMustExamUser studySpecialMustExamUser = dozerMapper.map(studySpecialMustExamUserDto, StudySpecialMustExamUser.class);
                studySpecialMustExamUsers.add(studySpecialMustExamUser);
            }
            boolean specialMustExamUserSave = studySpecialMustExamUserService.saveBatch(studySpecialMustExamUsers);
            if (specialMustExamUserSave) {
                log.info("专题申请保存- 专题必考人员保存成功");
            }
        }
        //-----------------保存专题课程开始---------

        //获取专题课程
        if (CollectionUtils.isNotEmpty(studySpecialDto.getStudySpecialLessonList())) {
            List<StudySpecialLessonDto> studySpecialLessonDtoList = studySpecialDto.getStudySpecialLessonList();
            List<StudySpecialLesson> studySpecialLessonList = new ArrayList<>();
            for (StudySpecialLessonDto studySpecialLessonDto : studySpecialLessonDtoList) {
                //关联信息
                studySpecialLessonDto.setSpecialId(specialId);
                //转换类型
                StudySpecialLesson studySpecialLesson = dozerMapper.map(studySpecialLessonDto, StudySpecialLesson.class);
                studySpecialLessonList.add(studySpecialLesson);
                //-----------------保存专题课程课件开始---------
                if (CollectionUtils.isNotEmpty(studySpecialLessonDto.getStudySpecialLessonCouList())) {
                    List<StudySpecialLessonCouDto> studySpecialLessonCouDtoList = studySpecialLessonDto.getStudySpecialLessonCouList();
                    List<StudySpecialLessonCou> studySpecialLessonCouList = new ArrayList<>();
                    for (StudySpecialLessonCouDto studySpecialLessonCouDto : studySpecialLessonCouDtoList) {
                        //关联信息
                        studySpecialLessonCouDto.setSpecialId(specialId);
                        studySpecialLessonCouDto.setSpecialLessonId(studySpecialLessonDto.getId());
                        studySpecialLessonCouDto.setLessonId(studySpecialLessonDto.getLessonId());
                        //转换类型
                        StudySpecialLessonCou studySpecialLessonCou = dozerMapper.map(studySpecialLessonCouDto, StudySpecialLessonCou.class);
                        studySpecialLessonCouList.add(studySpecialLessonCou);
                    }
                    //保存专题课程课件
                    boolean studySpecialLessonCouSave = studySpecialLessonCouService.saveBatch(studySpecialLessonCouList);
                    if (studySpecialLessonCouSave) {
                        log.info("专题申请保存- 专题课程[" + studySpecialLessonDto.getLessonName() + "]课件保存成功");
                    }
                    //-----------------保存专题课程课件结束---------
                } else {
                    throw new IncloudException("选中课程[" + studySpecialLessonDto.getLessonName() + "]至少选中一条课件信息");
                }
            }
            log.info("专题申请保存-专题课程保存参数:{}", studySpecialLessonList);
            //保存专题课程
            boolean specialLessonSave = studySpecialLessonService.saveBatch(studySpecialLessonList);
            if (specialLessonSave) {
                log.info("专题申请保存- 专题课程保存成功");
            }
        } else {
            throw new IncloudException("请至少选择一条课程");
        }
        //-----------------保存专题课程结束------------
        //-----------------保存专题资料信息开始---------
        //获取专题资料信息
        if (CollectionUtils.isNotEmpty(studySpecialDto.getStudySpecialMaterialsList())) {
            List<StudySpecialMaterialsDto> studySpecialMaterialsDtoList = studySpecialDto.getStudySpecialMaterialsList();
            List<StudySpecialMaterials> studySpecialMaterialsList = new ArrayList<>();
            for (StudySpecialMaterialsDto studySpecialMaterialsDto : studySpecialMaterialsDtoList) {
                //关联信息
                studySpecialMaterialsDto.setSpecialId(specialId);
                //类型转换
                StudySpecialMaterials studySpecialMaterials = dozerMapper.map(studySpecialMaterialsDto, StudySpecialMaterials.class);
                studySpecialMaterialsList.add(studySpecialMaterials);
            }
            log.info("专题申请保存-专题资料保存参数:{}", studySpecialMaterialsList);
            //保存专题资料
            boolean studySpecialMaterialsSave = studySpecialMaterialsService.saveBatch(studySpecialMaterialsList);
            if (studySpecialMaterialsSave) {
                log.info("专题申请保存- 专题资料保存成功");
            }
        }
        //-----------------保存专题资料信息结束---------
        //-----------------保存专题结业设置子表（证书）开始---------
        if (CollectionUtils.isNotEmpty(studySpecialDto.getStudySpecialJieyeList())) {
            //获取专题结业设置子表（证书）信息
            List<StudySpecialJieyeDto> studySpecialJieyeList = studySpecialDto.getStudySpecialJieyeList();
            List<StudySpecialJieye> studySpecialJieyesList = new ArrayList<>();
            for (StudySpecialJieyeDto studySpecialJieyeDto : studySpecialJieyeList) {
                //关联信息
                studySpecialJieyeDto.setSpecialId(specialId);
                //类型转换
                StudySpecialJieye studySpecialJieye = dozerMapper.map(studySpecialJieyeDto, StudySpecialJieye.class);
                studySpecialJieyesList.add(studySpecialJieye);
            }
            log.info("专题申请保存-专题结业设置子表（证书）保存参数:{}", studySpecialJieyesList);
            //保存专题结业设置子表（证书）
            boolean studySpecialJieyesSave = studySpecialJieyeService.saveBatch(studySpecialJieyesList);
            if (studySpecialJieyesSave) {
                log.info("专题申请保存- 专题结业设置子表（证书）保存成功");
            }
        }
        //-----------------保存专题结业设置子表（证书）结束---------
    }

    /**
     * 设置专题是否首页展示或启用
     *
     * @param studySpecialDtos
     * @return
     */
    @Override
    @Transactional
    public Boolean updateIsIndexOrEnable(List<StudySpecialDto> studySpecialDtos) {
        log.debug("设置专题是否首页展示或启用参数：{}", studySpecialDtos);
        //校验是否是管理员
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));//获取当前登陆人
        Integer studyUserRole = mdmClient.getStudyUserRole(appUser.getId());//获取当前登陆人的角色
        if (studyUserRole != StudyUserTypeEnum.ADMIN.code) {
            throw new IncloudException("您当前没有权限操作！");
        }
        List<StudySpecial> studySpecials = DozerUtils.mapList(dozerMapper, studySpecialDtos, StudySpecial.class);
        boolean updateIsIndex = super.updateBatchById(studySpecials);
        if (updateIsIndex) {
            log.info("设置专题是否首页展示或启用成功");
        }
        return updateIsIndex;
    }

    @Override
    public Page xueyuanPageList(StudySpecialDto specialDto) {
        log.info("学员端首页专题分页查询，参数：{}", specialDto.toString());
        if (StringUtils.isNotBlank(specialDto.getLabelCode())) {
            specialDto.setLabelCodeList(Arrays.asList(specialDto.getLabelCode().split(",")));
        }
        Page<StudySpecialVo> page = studySpecialMapper.xueyuanPageList(specialDto.getPage(), specialDto);
        List<StudySpecialVo> records = page.getRecords();
        //专题范围
        List<Long> specialIdList = records.stream().map(StudySpecialVo::getId).collect(Collectors.toList());
        List<StudySpecialRange> specialRangeList = this.getSpecialRangeList(specialIdList);

        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        //查询当前申请人对这些专题的申请中的记录
        Set<Long> applyIngSpecialId = new HashSet<>();
        if (appUser != null && CollectionUtils.isNotEmpty(specialIdList)) {
            List<StudyUserLearnApply> applyIngList = studyUserLearnApplyMapper.selectList(Wrappers.<StudyUserLearnApply>lambdaQuery().eq(StudyUserLearnApply::getUserId, appUser.getId()).eq(StudyUserLearnApply::getTargetType, LearnApplyTargetTypeEnum.SPECIAL.code).in(StudyUserLearnApply::getTargetId, specialIdList).eq(StudyUserLearnApply::getApplyStatus, LearnApplyStatusEnum.NO_AUDIT.code));
            if (CollectionUtils.isNotEmpty(applyIngList)) {
                applyIngSpecialId = applyIngList.stream().map(StudyUserLearnApply::getTargetId).collect(Collectors.toSet());
            }
        }
        log.info("获取到的专题信息:{}", specialIdList.size());
        for (StudySpecialVo record : records) {
            //是否需要报名
            if (this.isSpecialRange(specialRangeList, record)) {
                record.setApply(SpecialPromisStatus.NO_NEED.getCode());
            } else {
                record.setApply(applyIngSpecialId.contains(record.getId()) ? SpecialPromisStatus.APPLY_ING.getCode() : SpecialPromisStatus.NO_NEED.getCode());
            }
            //转换学时展示
            record.setStudyTimeText(StudyTimeUtil.buildStudyTimeStr(record.getStudyTime()));
        }
        return page;
    }

    @Override
    @Transactional
    public StudySpecialVo xueyuanDetail(Long specialId) {
        log.info("学员端首页专题详情查询:{}", specialId);
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        StudySpecialVo specialVo = dozerMapper.map(Optional.ofNullable(super.getById(specialId)).orElseThrow(() -> new IncloudException("专题丢失")), StudySpecialVo.class);
        //设置剩余过期时间
        if (specialVo.getSpecialTimeType() == SpecialTimeTypeEnum.ORDINARY.code) {//普通培训
            if (specialVo.getSpecialEndTime() != null) {
                specialVo.setRemaingTime(DateUtil.formatBetween(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), Date.from(specialVo.getSpecialEndTime().atZone(ZoneId.systemDefault()).toInstant()), BetweenFormatter.Level.MINUTE));
            }
        } else {
            specialVo.setRemaingTime("长期有效");
        }
        //获取专题总学时
        specialVo.setStudyTime(studySpecialMapper.countSpecialStudyTime(specialId));
        specialVo.setStudyTimeText(StudyTimeUtil.buildStudyTimeStr(specialVo.getStudyTime()));
        List<Long> specialIdList = CollectionUtil.newArrayList(specialVo.getId());
        //是否需要报名
        List<StudySpecialRange> specialRangeList = this.getSpecialRangeList(specialIdList);

        //是否需要报名
        if (this.isSpecialRange(specialRangeList, specialVo)) {
            specialVo.setApply(SpecialPromisStatus.NO_NEED.getCode());
        } else {
            //判断是否已经申请中
            if (studyUserLearnApplyMapper.selectCount(Wrappers.<StudyUserLearnApply>lambdaQuery().eq(StudyUserLearnApply::getUserId, appUser.getId()).eq(StudyUserLearnApply::getTargetType, LearnApplyTargetTypeEnum.SPECIAL.code).eq(StudyUserLearnApply::getTargetId, specialVo.getId()).eq(StudyUserLearnApply::getApplyStatus, LearnApplyStatusEnum.NO_AUDIT.code)) > 0) {
                specialVo.setApply(SpecialPromisStatus.APPLY_ING.getCode());
            } else {
                specialVo.setApply(SpecialPromisStatus.NO_NEED.getCode());
            }

        }
        //课程
        List<StudySpecialLesson> specialLessonList = this.getSpecialLessonList(specialIdList);
        //设置课程数量
        specialVo.setLessCount(specialLessonList.size());

        List<Long> specialLessonIdList = specialLessonList.stream().map(StudySpecialLesson::getId).collect(Collectors.toList());
        //课程对应的课件
        List<StudySpecialLessonCou> specialLessonCouList = this.getSpecialLessonCouList(specialLessonIdList);
        //给课件分组
        Map<Long, List<StudySpecialLessonCou>> lessonIdToCouListMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(specialLessonCouList)) {
            lessonIdToCouListMap = specialLessonCouList.stream().collect(Collectors.groupingBy(StudySpecialLessonCou::getLessonId));
        }
        //课程ID到学习记录的映射
        Map<Long, StudyUserStudyRecords> couIdToStudyRecordsMap = new HashMap<Long, StudyUserStudyRecords>();
        if (appUser != null && appUser.getId() != null) {
            //获取当前用户对该专题所有课件的学习记录
            List<StudyUserStudyRecords> userStudyRecordsList = studyUserStudyRecordsMapper.selectList(Wrappers.<StudyUserStudyRecords>lambdaQuery().eq(StudyUserStudyRecords::getSpecialId, specialId).eq(StudyUserStudyRecords::getUserId, appUser.getId()));
            if (CollectionUtils.isNotEmpty(userStudyRecordsList)) {
                couIdToStudyRecordsMap = userStudyRecordsList.stream().collect(Collectors.toMap(StudyUserStudyRecords::getCouId, x -> x, (k1, k2) -> k1));
            }
        }

        //查询课程列表（非专题关联的课程）
        List<Long> lessonIdList = specialLessonList.stream().map(StudySpecialLesson::getLessonId).collect(Collectors.toList());
        List<StudyLesson> studyLessonList = studyLessonMapper.selectList(Wrappers.<StudyLesson>lambdaQuery().in(StudyLesson::getId, lessonIdList));
        //课程名称到课程信息的对应
        Map<Long, StudyLesson> lessonIdToLessonMap = studyLessonList.stream().collect(Collectors.toMap(StudyLesson::getId, x -> x, (k1, k2) -> k1));

        //专题课程vo集合
        List<StudySpecialLessonVo> specialLessonVoList = new ArrayList<>();
        //必须课程是否都已学习完成
        boolean specialBxLessonIsFinish = false;
        for (StudySpecialLesson studySpecialLesson : specialLessonList) {
            StudySpecialLessonVo specialLessonVo = dozerMapper.map(studySpecialLesson, StudySpecialLessonVo.class);
            //课程简介
            specialLessonVo.setDescription(lessonIdToLessonMap.get(studySpecialLesson.getLessonId()).getDescription());
            //课程的课件
            List<StudySpecialLessonCou> specLessonCouList = Optional.ofNullable(lessonIdToCouListMap.get(studySpecialLesson.getLessonId())).orElse(new ArrayList<>());
            specialLessonVo.setCouNum(specLessonCouList.size());
            specialLessonVo.setBxCouNum((int) specLessonCouList.stream().filter(x -> x.getCouIsCompulsory() == YesNo.YES.code).count());
            specialLessonVo.setStudySpecialLessonCouList(DozerUtils.mapList(dozerMapper, specLessonCouList, StudySpecialLessonCouVo.class));
            //课程学习进度状态
            specialLessonVo.setLearnStatus(getLessonLearnStatus(specLessonCouList, couIdToStudyRecordsMap));
            specialLessonVoList.add(specialLessonVo);
        }
        specialVo.setStudySpecialLessonList(specialLessonVoList);

        //考试信息
        if (specialVo.getSpecialPaperId() != null && !specialVo.getSpecialPaperId().equals(0L)) {
            List<StudyUserExam> userExamList = Optional.ofNullable(studyUserExamMapper.selectList(Wrappers.<StudyUserExam>lambdaQuery().eq(StudyUserExam::getSpecialId, specialId).eq(StudyUserExam::getUserId, appUser.getId()).orderByAsc(StudyUserExam::getAnswerEndTime))).orElseGet(() -> Lists.newArrayList());
            //设置已考次数
            specialVo.setExamedNum(userExamList.size());
            //设置答题记录
            specialVo.setStudyUserExamList(DozerUtils.mapList(dozerMapper, userExamList, StudyUserExamVo.class));
            //是否已提交试卷
            specialVo.setIsSubmitExam(userExamList.stream().anyMatch(x -> x.getPaperStatus() != PaperStatusEnum.QUESTIONS_ANSWER.code));
            //现在是否可以考试，目前只判断考试时间和考试次数
            if (specialVo.getIsSubmitExam()) {//已提交考试不能再考试
                specialVo.setIsCanExam(false);
            } else {
                if (userExamList.size() >= Optional.ofNullable(specialVo.getSpecialExamNum()).orElseGet(() -> 1)) {//考试次数已用完不能考试
                    specialVo.setIsCanExam(false);
                } else if ((specialVo.getSpecialPaperStartTime() != null && specialVo.getSpecialPaperStartTime().isAfter(LocalDateTime.now())) || (specialVo.getSpecialPaperEndTime() != null && specialVo.getSpecialPaperEndTime().isBefore(LocalDateTime.now()))) {
                    //考试时间未到
                    specialVo.setIsCanExam(false);
                } else {
                    specialVo.setIsCanExam(true);
                }
            }
        }
        //资料区
        List<StudySpecialMaterialsVo> specialMaterialsList = studySpecialMaterialsMapper.getSpecialMaterialsListBySpecialId(specialVo.getId());
        specialVo.setStudySpecialMaterialsList(specialMaterialsList);
        //更新浏览量
        super.update(Wrappers.<StudySpecial>lambdaUpdate().setSql(" hits = hits + 1").eq(StudySpecial::getId, specialVo.getId()));

        //加入最近浏览
        studyBrowseService.save(new StudyBrowseDto(appUser.getId(), 1, specialId, null));

        //判断是否收藏
        if (studyCollectionMapper.selectCount(Wrappers.<StudyCollection>lambdaQuery().eq(StudyCollection::getCollectionId, specialId).eq(StudyCollection::getCollectionType, CollectionTypeEnum.SPECIAL.code).eq(StudyCollection::getUserId, appUser.getId())) > 0) {
            specialVo.setIsCollect(YesNo.YES.code);
        } else {
            specialVo.setIsCollect(YesNo.NO.code);
        }
        return specialVo;
    }

    //获取课程学习状态
    private int getLessonLearnStatus(List<StudySpecialLessonCou> couList, Map<Long, StudyUserStudyRecords> couIdToStudyRecordsMap) {
        if (CollectionUtils.isEmpty(couList)) {
            return LearnStatusEnum.LEARNED.code;
        }
        if (couIdToStudyRecordsMap == null || couIdToStudyRecordsMap.isEmpty()) {
            return LearnStatusEnum.NO_LEARN.code;
        }

        //是否包含未学习的课程
        boolean haveNoLearn = false;
        //是否包含已学完课程
        boolean haveLearned = false;
        for (StudySpecialLessonCou specialLessonCou : couList) {
            StudyUserStudyRecords userStudyRecords = couIdToStudyRecordsMap.get(specialLessonCou.getCouId());
            if (userStudyRecords == null) {
                haveNoLearn = true;
            } else if ((Optional.ofNullable(userStudyRecords.getStudyBestLessTime()).orElseGet(() -> 0l)) > (Optional.ofNullable(userStudyRecords.getCumulativeStudyTime()).orElseGet(() -> 0l))) {
                return LearnStatusEnum.LEARNING.code;
            } else {
                haveLearned = true;
            }
        }
        if (haveNoLearn) {
            return haveLearned ? LearnStatusEnum.LEARNING.code : LearnStatusEnum.NO_LEARN.code;
        }
        //如果上面的都不满足  说明学习完成了
        return LearnStatusEnum.LEARNED.code;
    }

    //专题范围
    private List<StudySpecialRange> getSpecialRangeList(List<Long> specialIdList) {
        if (CollectionUtils.isEmpty(specialIdList)) {
            return Lists.newArrayList();
        }
        return studySpecialRangeMapper.selectList(Wrappers.<StudySpecialRange>lambdaQuery().in(StudySpecialRange::getSpecialId, specialIdList));
    }

    //课程
    private List<StudySpecialLesson> getSpecialLessonList(List<Long> specialIdList) {
        if (CollectionUtils.isEmpty(specialIdList)) {
            return Lists.newArrayList();
        }
        return studySpecialLessonMapper.selectList(Wrappers.<StudySpecialLesson>lambdaQuery().in(StudySpecialLesson::getSpecialId, specialIdList));
    }

    //课件
    private List<StudySpecialLessonCou> getSpecialLessonCouList(List<Long> specialLessonIdList) {
        if (CollectionUtils.isEmpty(specialLessonIdList)) {
            return Lists.newArrayList();
        }
        return studySpecialLessonCouMapper.selectList(Wrappers.<StudySpecialLessonCou>lambdaQuery().in(StudySpecialLessonCou::getSpecialLessonId, specialLessonIdList));
    }

    //专题对应的范围过滤
    private boolean isSpecialRange(List<StudySpecialRange> specialRangeList, StudySpecialVo special) {
        //机构
        List<Long> orgIdList = specialRangeList.stream().filter(x -> x.getRangeType().equals(RangeTypeEnum.ORG.code) && special.getId().equals(x.getSpecialId())).map(StudySpecialRange::getRangeId).collect(Collectors.toList());
        //部门
        List<Long> deptIdList = specialRangeList.stream().filter(x -> x.getRangeType().equals(RangeTypeEnum.DEPT.code) && special.getId().equals(x.getSpecialId())).map(StudySpecialRange::getRangeId).collect(Collectors.toList());
        //人员
        List<Long> userIdList = specialRangeList.stream().filter(x -> x.getRangeType().equals(RangeTypeEnum.USER.code) && special.getId().equals(x.getSpecialId())).map(StudySpecialRange::getRangeId).collect(Collectors.toList());
        //登陆人
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        List<Long> orgFullIdList = ObjectUtil.isNull(appUser) ? Lists.newArrayList() : Stream.of(appUser.getOrgFullId().split(",")).map(Long::valueOf).collect(Collectors.toList());
        Long userId = ObjectUtil.isNull(appUser) ? 0L : appUser.getId();

        for (Long userOgId : orgFullIdList) {
            if (userIdList.contains(userId) || orgIdList.contains(userOgId) || deptIdList.contains(userOgId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 构建子表数据
     *
     * @param studySpecialVo
     * @return
     */
    private StudySpecialVo buildChildren(StudySpecialVo studySpecialVo) {

        if (studySpecialVo != null && studySpecialVo.getId() != null) {
            //获取专题对象（机构、部门）范围
            List<String> rangeTypeList = new ArrayList<>();
            rangeTypeList.add(RangeTypeEnum.ORG.code);
            rangeTypeList.add(RangeTypeEnum.DEPT.code);
            List<StudySpecialRange> studySpecialRangeOrgDeptList = studySpecialRangeMapper.selectList(Wrappers.<StudySpecialRange>lambdaQuery().eq(StudySpecialRange::getSpecialId, studySpecialVo.getId()).in(StudySpecialRange::getRangeType, rangeTypeList));
            if (CollectionUtils.isNotEmpty(studySpecialRangeOrgDeptList)) {
                studySpecialVo.setStudySpecialRangeOrgDeptList(DozerUtils.mapList(dozerMapper, studySpecialRangeOrgDeptList, StudySpecialRangeVo.class));
            }
            //获取专题对象（人员）范围
            List<StudySpecialRange> studySpecialRangeUserList = studySpecialRangeMapper.selectList(Wrappers.<StudySpecialRange>lambdaQuery().eq(StudySpecialRange::getSpecialId, studySpecialVo.getId()).eq(StudySpecialRange::getRangeType, RangeTypeEnum.USER.code));
            if (CollectionUtils.isNotEmpty(studySpecialRangeUserList)) {
                studySpecialVo.setStudySpecialRangeUserList(DozerUtils.mapList(dozerMapper, studySpecialRangeUserList, StudySpecialRangeVo.class));
            }
            //获取专题必考人员信息
            List<StudySpecialMustExamUser> studySpecialMustExamUsers = studySpecialMustExamUserMapper.selectList(Wrappers.<StudySpecialMustExamUser>lambdaQuery().eq(StudySpecialMustExamUser::getSpecialId, studySpecialVo.getId()));
            if (CollectionUtils.isNotEmpty(studySpecialMustExamUsers)) {
                studySpecialVo.setStudySpecialMustExamUserList(DozerUtils.mapList(dozerMapper, studySpecialMustExamUsers, StudySpecialMustExamUserVo.class));
            }
            //获取课程信息
            List<StudySpecialLesson> studySpecialLessonList = studySpecialLessonMapper.selectList(Wrappers.<StudySpecialLesson>lambdaQuery().eq(StudySpecialLesson::getSpecialId, studySpecialVo.getId()));
            if (CollectionUtils.isNotEmpty(studySpecialLessonList)) {
                List<StudySpecialLessonVo> studySpecialLessonVoList = new ArrayList<>();
                //获取课程课件信息
                for (StudySpecialLesson studySpecialLesson : studySpecialLessonList) {
                    StudySpecialLessonVo studySpecialLessonVo = dozerMapper.map(studySpecialLesson, StudySpecialLessonVo.class);
                    if (null != studySpecialLesson && null != studySpecialLesson.getId()) {
                        List<StudySpecialLessonCou> studySpecialLessonCouList = studySpecialLessonCouMapper.selectList(Wrappers.<StudySpecialLessonCou>lambdaQuery().eq(StudySpecialLessonCou::getSpecialLessonId, studySpecialLesson.getId()));
                        if (CollectionUtils.isNotEmpty(studySpecialLessonCouList)) {
                            List<StudySpecialLessonCouVo> studySpecialLessonCouVoList = DozerUtils.mapList(dozerMapper, studySpecialLessonCouList, StudySpecialLessonCouVo.class);
                            studySpecialLessonVo.setStudySpecialLessonCouList(studySpecialLessonCouVoList);
                        }
                    }
                    studySpecialLessonVoList.add(studySpecialLessonVo);
                }
                studySpecialVo.setStudySpecialLessonList(studySpecialLessonVoList);
            }
            //获取学习资料
            List<StudySpecialMaterials> specialMaterialsList = studySpecialMaterialsMapper.selectList(Wrappers.<StudySpecialMaterials>lambdaQuery().eq(StudySpecialMaterials::getSpecialId, studySpecialVo.getId()));
            if (CollectionUtils.isNotEmpty(specialMaterialsList)) {
                studySpecialVo.setStudySpecialMaterialsList(DozerUtils.mapList(dozerMapper, specialMaterialsList, StudySpecialMaterialsVo.class));
            }

            //获取结业信息
            List<StudySpecialJieye> specialJieyeList = studySpecialJieyeMapper.selectList(Wrappers.<StudySpecialJieye>lambdaQuery().eq(StudySpecialJieye::getSpecialId, studySpecialVo.getId()));
            if (CollectionUtils.isNotEmpty(specialJieyeList)) {
                studySpecialVo.setStudySpecialJieyeList(DozerUtils.mapList(dozerMapper, specialJieyeList, StudySpecialJieyeVo.class));
            }
        }
        return studySpecialVo;
    }

    /**
     * 通过专题ID 删除专题子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置）
     *
     * @param specialId
     */
    public void deleteSpecialChildren(Long specialId) {
        log.info("先删除专题" + specialId + "子表信息");
        if (ObjectUtil.isNotEmpty(specialId)) {
            //删除专题必考人员
            int specialMustExamUserDelete = studySpecialMustExamUserMapper.delete(Wrappers.<StudySpecialMustExamUser>lambdaQuery().eq(StudySpecialMustExamUser::getSpecialId, specialId));
            if (specialMustExamUserDelete > 0) {
                log.info("专题申请修改- 删除专题必考人员成功");
            }
            //删除专题对象
            int specialRangeDelete = studySpecialRangeMapper.delete(Wrappers.<StudySpecialRange>lambdaQuery().eq(StudySpecialRange::getSpecialId, specialId));
            if (specialRangeDelete > 0) {
                log.info("专题申请修改- 删除专题对象成功");
            }
            //获取专题课程Id
            List<StudySpecialLesson> studySpecialLessonList = studySpecialLessonMapper.selectList(Wrappers.<StudySpecialLesson>lambdaQuery().eq(StudySpecialLesson::getSpecialId, specialId));
            List<Long> specialLessonIdList = null;
            if (CollectionUtils.isNotEmpty(studySpecialLessonList)) {
                specialLessonIdList = studySpecialLessonList.stream().map(StudySpecialLesson::getId).collect(Collectors.toList());
            }
            //删除专题课程
            int specialLessonDelete = studySpecialLessonMapper.delete(Wrappers.<StudySpecialLesson>lambdaQuery().eq(StudySpecialLesson::getSpecialId, specialId));
            if (specialLessonDelete > 0) {
                log.info("专题申请修改- 删除专题课程成功");
            }
            //删除专题课程课件
            if (CollectionUtils.isNotEmpty(specialLessonIdList)) {
                log.info("专题申请修改- 删除课程课件参数：{}", specialLessonIdList);
                int specialLessonCouDelete = studySpecialLessonCouMapper.delete(Wrappers.<StudySpecialLessonCou>lambdaQuery().in(StudySpecialLessonCou::getSpecialLessonId, specialLessonIdList));
                if (specialLessonCouDelete > 0) {
                    log.info("专题申请修改- 删除专题课程课件成功");
                }
            }
            //删除专题资料
            int specialMaterialsDelete = studySpecialMaterialsMapper.delete(Wrappers.<StudySpecialMaterials>lambdaQuery().eq(StudySpecialMaterials::getSpecialId, specialId));
            if (specialMaterialsDelete > 0) {
                log.info("专题申请修改- 删除专题资料成功");
            }
            //删除专题结业设置
            int specialJieyeDelete = studySpecialJieyeMapper.delete(Wrappers.<StudySpecialJieye>lambdaQuery().eq(StudySpecialJieye::getSpecialId, specialId));
            if (specialJieyeDelete > 0) {
                log.info("专题申请修改- 删除专题结业设置成功");
            }
        }

    }

    @Override
    public Boolean isCanExam(Long id) {
        //学员再线、所有必修课程都学完、考试时间范围内、考试次数还有剩余
        LoginAppUser appUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(appUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        StudySpecial special = super.getById(id);
        Optional.ofNullable(special).orElseThrow(() -> new IncloudException("专题信息已不存在"));
        //专题是否有关联考试
        Optional.ofNullable(special.getSpecialPaperId()).orElseThrow(() -> new IncloudException("该专题未关联考试"));
        //判断时间
        if (special.getSpecialPaperStartTime() != null && special.getSpecialPaperStartTime().isAfter(LocalDateTime.now())) {
            new IncloudException("未到考试时间");
        }
        if (special.getSpecialPaperEndTime() != null && special.getSpecialPaperEndTime().isBefore(LocalDateTime.now())) {
            new IncloudException("考试时间已结束");
        }
        //判断考试次数是否已用完
        List<StudyUserExam> studyUserExamList = studyUserExamMapper.selectList(Wrappers.<StudyUserExam>lambdaQuery().eq(StudyUserExam::getSpecialId, id).eq(StudyUserExam::getUserId, appUser.getId()).eq(StudyUserExam::getPaperId, special.getSpecialPaperId()).eq(StudyUserExam::getPaperType, PaperTypeEnum.KST.code));
        if (CollectionUtils.isNotEmpty(studyUserExamList)) {
            //已经考试次数>=设置次数 则不可以进行考试
            if (studyUserExamList.size() >= special.getSpecialExamNum()) {
                throw new IncloudException("考试次数已用完");
            }
            //如果有已经提交的考试则不能继续考试
            if (studyUserExamList.stream().filter(x -> x.getPaperStatus() != PaperStatusEnum.QUESTIONS_ANSWER.code).count() > 0) {
                throw new IncloudException("考试已提交不能再进行考试");
            }
        }
        //判断所有必须课程是否都已学完
        //查询所有必须课件
        List<StudySpecialLessonCou> specialLessonCouList = studySpecialLessonCouMapper.selectList(Wrappers.<StudySpecialLessonCou>lambdaQuery().eq(StudySpecialLessonCou::getSpecialId, id).eq(StudySpecialLessonCou::getCouIsCompulsory, YesNo.YES.code));
        if (CollectionUtils.isNotEmpty(specialLessonCouList)) {
            //查询这些课件的的学习记录
            List<StudyUserStudyRecords> studyRecordList = studyUserStudyRecordsMapper.selectList(Wrappers.<StudyUserStudyRecords>lambdaQuery().eq(StudyUserStudyRecords::getUserId, appUser.getId()).eq(StudyUserStudyRecords::getSpecialId, id).in(StudyUserStudyRecords::getCouId, specialLessonCouList.stream().map(StudySpecialLessonCou::getCouId).collect(Collectors.toList())));
            if (CollectionUtils.isEmpty(studyRecordList) || studyRecordList.size() < specialLessonCouList.size()) {
                throw new IncloudException("专题必须课程未全部学习完成");
            }
            Map<Long, StudyUserStudyRecords> couIdToStudyRecordsMap = studyRecordList.stream().collect(Collectors.toMap(StudyUserStudyRecords::getCouId, x -> x, (k1, k2) -> k1));
            for (StudySpecialLessonCou specialLessonCou : specialLessonCouList) {
                StudyUserStudyRecords studyRecords = couIdToStudyRecordsMap.get(specialLessonCou.getCouId());
                if (studyRecords == null || (Optional.ofNullable(studyRecords.getStudyBestLessTime()).orElseGet(() -> 0l)) > (Optional.ofNullable(studyRecords.getCumulativeStudyTime()).orElseGet(() -> 0l))) {
                    throw new IncloudException("专题必须课程未全部学习完成");
                }
            }
        }
        return true;
    }

    @Override
    public StudyMySpecialVo findMySpecial(StudySpecialDto specialDto) {
        //最终返回的信息
        StudyMySpecialVo studyMySpecialVo = new StudyMySpecialVo();
        //根据登陆人员查询专题人员表
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<String> rangeIds = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(loginAppUser) && ObjectUtils.isNotEmpty(loginAppUser.getId())) {
            rangeIds.add(loginAppUser.getId().toString());
        }
        if (ObjectUtils.isNotEmpty(loginAppUser) && StringUtils.isNotBlank(loginAppUser.getOrgFullId())) {
            String orgFullId = loginAppUser.getOrgFullId();
            List<String> orgIds = Arrays.asList(orgFullId.split(","));
            rangeIds.addAll(orgIds);
        }
        if (CollectionUtils.isEmpty(rangeIds)) {
            rangeIds.add("0");
        }
        LambdaQueryWrapper<StudySpecialRange> specialRangeWrapper = new LambdaQueryWrapper<>();
        specialRangeWrapper.in(StudySpecialRange::getRangeId, rangeIds);
        List<StudySpecialRange> studySpecialRanges = studySpecialRangeMapper.selectList(specialRangeWrapper);
        if (CollectionUtils.isNotEmpty(studySpecialRanges)) {
            //通过专题人员表 获取专题id
            List<Long> specialIds = studySpecialRanges.stream().map(StudySpecialRange::getSpecialId).collect(Collectors.toList());
            //通过专题id 分类id 专题开始时间 结束时间 查询需要学习的专题
            LambdaQueryWrapper<StudySpecial> specialWrapper = new LambdaQueryWrapper<>();
            specialWrapper.in(StudySpecial::getId, specialIds);
            specialWrapper.eq(StringUtils.isNotBlank(specialDto.getTypeCode()), StudySpecial::getTypeCode, specialDto.getTypeCode());
            specialWrapper.like(StringUtils.isNotBlank(specialDto.getSpecialName()), StudySpecial::getSpecialName, specialDto.getSpecialName());
            specialWrapper.le(StudySpecial::getSpecialStartTime, LocalDateTime.now());
            specialWrapper.ge(StudySpecial::getSpecialEndTime, LocalDateTime.now());
            List<StudySpecial> studySpecials = studySpecialMapper.selectList(specialWrapper);
            if (CollectionUtils.isNotEmpty(studySpecials)) {
                //往查出来的专题信息中 加入课程信息
                List<StudySpecialVo> specialVos = DozerUtils.mapList(dozerMapper, studySpecials, StudySpecialVo.class);
                //根据所有专题id查出所有专题课程信息
                LambdaQueryWrapper<StudySpecialLesson> specialLessonWrapper = new LambdaQueryWrapper<>();
                specialLessonWrapper.in(StudySpecialLesson::getSpecialId, specialVos.stream().map(StudySpecialVo::getId).collect(Collectors.toList()));
                List<StudySpecialLesson> specialLessonList = studySpecialLessonMapper.selectList(specialLessonWrapper);
                //获取所有课程id集合
                List<Long> lessonIdList = specialLessonList.stream().map(StudySpecialLesson::getLessonId).collect(Collectors.toList());
                //根据课程id集合查出所有课件信息 按照专题id进行分组
                LambdaQueryWrapper<StudySpecialLessonCou> specialLessonCouWrapper = new LambdaQueryWrapper<>();
                specialLessonCouWrapper.in(StudySpecialLessonCou::getLessonId,lessonIdList);
                List<StudySpecialLessonCou> specialLessonCouList = studySpecialLessonCouMapper.selectList(specialLessonCouWrapper);
                Map<Long, List<StudySpecialLessonCou>> couSpecialIdMap = specialLessonCouList.stream().collect(Collectors.groupingBy(StudySpecialLessonCou::getSpecialId));
                //按照专题id进行分组
                Map<Long, List<StudySpecialLesson>> specialIdMap = specialLessonList.stream().collect(Collectors.groupingBy(StudySpecialLesson::getSpecialId));
                //循环专题信息
                for (StudySpecialVo specialVo : specialVos) {
                    //获取到专题下面的所有课件
                    List<StudySpecialLessonCou> lessonCouList = couSpecialIdMap.get(specialVo.getId());
                    //按照专题下面的课程进行分组
                    Map<Long, List<StudySpecialLessonCou>> listMap = lessonCouList.stream().collect(Collectors.groupingBy(StudySpecialLessonCou::getLessonId));
                    //获取专题下面所有课程的课件的时长
                    long lessonTime = 0l;
                    for (StudySpecialLessonCou specialLessonCou : lessonCouList){
                        if (StringUtils.isNotBlank(specialLessonCou.getCouDuration())) {
                            Long couDuration = Long.valueOf(specialLessonCou.getCouDuration());
                            lessonTime += couDuration;
                        }
                    }
                    String specialStudyTimeSize = StudyTimeUtil.buildStudyTimeStr(lessonTime);
                    specialVo.setSpecialStudyTimeSize(specialStudyTimeSize);
                    //组装返回信息
                    List<StudySpecialLesson> studySpecialLessons = specialIdMap.get(specialVo.getId());
                    List<StudySpecialLessonVo> specialLessonVos = DozerUtils.mapList(dozerMapper, studySpecialLessons, StudySpecialLessonVo.class);
                    specialLessonVos.forEach(specialLessonVo->{
                        specialLessonVo.setSpecialName(specialVo.getSpecialName());
                        List<StudySpecialLessonCou> couList = listMap.get(specialLessonVo.getLessonId());
                        int couIsCompulsory = YesNo.NO.code;
                        for (StudySpecialLessonCou cou : couList){
                            if (cou.getCouIsCompulsory().equals(YesNo.YES.code)) {
                                couIsCompulsory = YesNo.YES.code;
                            }
                        }
                        specialLessonVo.setCouIsCompulsory(couIsCompulsory);
                    });
                    specialVo.setStudySpecialLessonList(specialLessonVos);
                }
                studyMySpecialVo.setTodoSpecialNum(specialVos.size());
                studyMySpecialVo.setMySpecialList(specialVos);
            }
        }
        return studyMySpecialVo;
    }

}
